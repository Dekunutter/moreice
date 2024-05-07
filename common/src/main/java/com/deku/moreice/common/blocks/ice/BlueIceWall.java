package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;

public class BlueIceWall extends WallBlock implements Ice {
    public BlueIceWall() {
        super(Properties.ofLegacyCopy(Blocks.BLUE_ICE).forceSolidOn());
    }
}
