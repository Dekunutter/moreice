package com.deku.moreice.common.blocks.ice;

import com.deku.moreice.common.blocks.ModBlockSetType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.neoforged.neoforge.common.extensions.IBlockExtension;

public class BlueIcePressurePlate extends PressurePlateBlock implements Ice, IBlockExtension {
    public BlueIcePressurePlate() {
        super(ModBlockSetType.BLUE_ICE, Properties.ofLegacyCopy(Blocks.BLUE_ICE).noCollission());
    }
}
