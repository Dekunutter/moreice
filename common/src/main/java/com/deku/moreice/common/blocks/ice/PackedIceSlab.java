package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;

public class PackedIceSlab extends SlabBlock implements Ice {
    public PackedIceSlab() {
        super(Properties.ofLegacyCopy(Blocks.PACKED_ICE));
    }
}
