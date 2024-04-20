package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.neoforged.neoforge.common.extensions.IBlockExtension;

public class BlueIceBrickStairs extends StairBlock implements Ice, IBlockExtension {
    public BlueIceBrickStairs() {
        super(Blocks.BLUE_ICE.defaultBlockState(), Properties.ofLegacyCopy(Blocks.BLUE_ICE));
    }
}
