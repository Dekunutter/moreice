package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.neoforged.neoforge.common.extensions.IBlockExtension;

public class PackedIceBrickSlab extends SlabBlock implements Ice, IBlockExtension {
    public PackedIceBrickSlab() {
        super(Properties.ofLegacyCopy(Blocks.PACKED_ICE));
    }
}
