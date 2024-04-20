package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.neoforged.neoforge.common.extensions.IBlockExtension;

public class IceBrickStairs extends StairBlock implements Ice, IBlockExtension {
    public IceBrickStairs() {
        super(Blocks.BLUE_ICE.defaultBlockState(), Properties.ofLegacyCopy(Blocks.BLUE_ICE));
    }
}
