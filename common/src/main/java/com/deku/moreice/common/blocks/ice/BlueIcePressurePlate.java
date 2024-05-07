package com.deku.moreice.common.blocks.ice;

import com.deku.moreice.common.blocks.ModBlockSetType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BlueIcePressurePlate extends PressurePlateBlock implements Ice {
    public BlueIcePressurePlate() {
        super(ModBlockSetType.BLUE_ICE, Properties.ofLegacyCopy(Blocks.BLUE_ICE).noCollission());
    }

    // NOTE: Pressure plates are too small, make them melt into air instead of water
    @Override
    public BlockState meltsInto() {
        return Blocks.AIR.defaultBlockState();
    }
}
