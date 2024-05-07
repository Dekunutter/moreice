package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;

public class BlueIceBrickWall extends WallBlock implements Ice {
    public BlueIceBrickWall() {
        super(Properties.ofLegacyCopy(Blocks.BLUE_ICE).forceSolidOn());
    }
}
