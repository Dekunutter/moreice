package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;

public class PackedIceBrickSlab extends SlabBlock implements Ice {
    public PackedIceBrickSlab() {
        super(Properties.ofLegacyCopy(Blocks.PACKED_ICE));
    }
}
