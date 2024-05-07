package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class PackedIceBricks extends Block implements Ice {
    public PackedIceBricks() {
        super(Properties.ofLegacyCopy(Blocks.PACKED_ICE));
    }
}
