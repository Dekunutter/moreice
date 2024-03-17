package com.deku.moreice.common.blocks.ice;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.extensions.IForgeBlock;

public abstract class AbstractIceStairs extends StairBlock implements IForgeBlock {
    public AbstractIceStairs(BlockState state, Properties properties) {
        super(state, properties);
    }

    public static BlockState meltsInto() {
        return Blocks.WATER.defaultBlockState();
    }

    protected void melt(BlockState p_54169_, Level p_54170_, BlockPos p_54171_) {
        if (p_54170_.dimensionType().ultraWarm()) {
            p_54170_.removeBlock(p_54171_, false);
        } else {
            p_54170_.setBlockAndUpdate(p_54171_, meltsInto());
            p_54170_.neighborChanged(p_54171_, meltsInto().getBlock(), p_54171_);
        }
    }
}
