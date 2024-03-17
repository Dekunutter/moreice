package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;

public class BlueIceStairs extends AbstractIceStairs {
    public BlueIceStairs() {
        super(Blocks.BLUE_ICE.defaultBlockState(), Properties.ofLegacyCopy(Blocks.BLUE_ICE));
    }
}
