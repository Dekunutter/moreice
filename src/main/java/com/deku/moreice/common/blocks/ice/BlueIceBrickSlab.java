package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.neoforged.neoforge.common.extensions.IBlockExtension;

public class BlueIceBrickSlab extends SlabBlock implements Ice, IBlockExtension {
    public BlueIceBrickSlab() {
        super(Properties.ofLegacyCopy(Blocks.BLUE_ICE));
    }
}
