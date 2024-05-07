package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;

public class BlueIceBrickSlab extends SlabBlock implements Ice {
    public BlueIceBrickSlab() {
        super(Properties.ofLegacyCopy(Blocks.BLUE_ICE));
    }
}
