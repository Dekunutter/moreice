package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;

public class BlueIceSlab extends SlabBlock implements Ice {
    public BlueIceSlab() {
        super(Properties.ofLegacyCopy(Blocks.BLUE_ICE));
    }
}
