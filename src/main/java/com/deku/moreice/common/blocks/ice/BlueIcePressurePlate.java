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

public class BlueIcePressurePlate extends PressurePlateBlock implements Ice, IForgeBlock {
    public BlueIcePressurePlate() {
        super(ModBlockSetType.BLUE_ICE, Properties.ofLegacyCopy(Blocks.BLUE_ICE).noCollission());
    }
}
