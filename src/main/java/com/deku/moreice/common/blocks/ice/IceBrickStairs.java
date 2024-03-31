package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.common.extensions.IForgeBlock;

public class IceBrickStairs extends StairBlock implements Ice, IForgeBlock {
    public IceBrickStairs() {
        super(Blocks.BLUE_ICE.defaultBlockState(), Properties.ofLegacyCopy(Blocks.BLUE_ICE));
    }
}
