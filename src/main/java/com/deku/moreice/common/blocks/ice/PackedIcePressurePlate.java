package com.deku.moreice.common.blocks.ice;

import com.deku.moreice.common.blocks.ModBlockSetType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.neoforged.neoforge.common.extensions.IBlockExtension;

public class PackedIcePressurePlate extends PressurePlateBlock implements Ice, IBlockExtension {
    public PackedIcePressurePlate() {
        super(ModBlockSetType.PACKED_ICE, Properties.ofLegacyCopy(Blocks.PACKED_ICE).noCollission());
    }
}
