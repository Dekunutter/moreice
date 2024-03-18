package com.deku.moreice.common.blocks.ice;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public interface Ice {
    default BlockState meltsInto() {
        return Blocks.WATER.defaultBlockState();
    }

    default void melt(BlockState state, Level level, BlockPos position) {
        if (level.dimensionType().ultraWarm()) {
            level.removeBlock(position, false);
        } else {
            level.setBlockAndUpdate(position, meltsInto());
            level.neighborChanged(position, meltsInto().getBlock(), position);
        }
    }
}
