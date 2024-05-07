package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
public class BlueIceBricks extends Block implements Ice {
    public BlueIceBricks() {
        super(Properties.ofLegacyCopy(Blocks.BLUE_ICE));
    }
}
