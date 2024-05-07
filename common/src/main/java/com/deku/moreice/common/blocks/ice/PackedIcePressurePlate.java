package com.deku.moreice.common.blocks.ice;

import com.deku.moreice.common.blocks.ModBlockSetType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.BlockState;

public class PackedIcePressurePlate extends PressurePlateBlock implements Ice {
    public PackedIcePressurePlate() {
        super(ModBlockSetType.PACKED_ICE, Properties.ofLegacyCopy(Blocks.PACKED_ICE).noCollission());
    }

    // NOTE: Pressure plates are too small, make them melt into air instead of water
    @Override
    public BlockState meltsInto() {
        return Blocks.AIR.defaultBlockState();
    }
}
