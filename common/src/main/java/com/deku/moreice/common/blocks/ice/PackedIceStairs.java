package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;

public class PackedIceStairs extends StairBlock implements Ice {
    public PackedIceStairs() {
        super(Blocks.PACKED_ICE.defaultBlockState(), Properties.ofLegacyCopy(Blocks.PACKED_ICE));
    }
}
