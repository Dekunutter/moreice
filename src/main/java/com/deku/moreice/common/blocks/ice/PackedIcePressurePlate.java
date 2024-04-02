package com.deku.moreice.common.blocks.ice;

import com.deku.moreice.common.blocks.ModBlockSetType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraftforge.common.extensions.IForgeBlock;

public class PackedIcePressurePlate extends PressurePlateBlock implements Ice, IForgeBlock {
    public PackedIcePressurePlate() {
        super(PressurePlateBlock.Sensitivity.EVERYTHING, Properties.copy(Blocks.PACKED_ICE).noCollission(), ModBlockSetType.PACKED_ICE);
    }
}
