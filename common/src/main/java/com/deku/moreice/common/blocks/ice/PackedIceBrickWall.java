package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;

public class PackedIceBrickWall extends WallBlock implements Ice {
    public PackedIceBrickWall() {
        super(Properties.ofLegacyCopy(Blocks.PACKED_ICE).forceSolidOn());
    }
}
