package com.deku.moreice.common.blocks.ice;

import com.deku.moreice.common.blocks.ModBlockSetType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.extensions.IForgeBlock;

public class IcePressurePlate extends PressurePlateBlock implements Ice, IForgeBlock {
    public IcePressurePlate() {
        super(PressurePlateBlock.Sensitivity.EVERYTHING, Properties.copy(Blocks.ICE).noCollission(), ModBlockSetType.ICE);
    }

    public void randomTick(BlockState state, ServerLevel level, BlockPos position, RandomSource random) {
        if (level.getBrightness(LightLayer.BLOCK, position) > 11 - state.getLightBlock(level, position)) {
            this.melt(state, level, position);
        }
    }
}
