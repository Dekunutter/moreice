package com.deku.moreice.common.blocks.ice;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.extensions.IForgeBlock;

public class IceBrickSlab extends SlabBlock implements Ice, IForgeBlock {
    public IceBrickSlab() {
        super(Properties.copy(Blocks.ICE));
    }

    public void randomTick(BlockState state, ServerLevel level, BlockPos position, RandomSource random) {
        if (level.getBrightness(LightLayer.BLOCK, position) > 11 - state.getLightBlock(level, position)) {
            this.melt(state, level, position);
        }
    }
}
