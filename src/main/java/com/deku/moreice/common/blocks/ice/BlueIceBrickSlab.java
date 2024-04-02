package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraftforge.common.extensions.IForgeBlock;

public class BlueIceBrickSlab extends SlabBlock implements Ice, IForgeBlock {
    public BlueIceBrickSlab() {
        super(Properties.copy(Blocks.BLUE_ICE));
    }
}
