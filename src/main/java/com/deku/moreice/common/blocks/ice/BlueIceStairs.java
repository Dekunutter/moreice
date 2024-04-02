package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.common.extensions.IForgeBlock;

public class BlueIceStairs extends StairBlock implements Ice, IForgeBlock {
    public BlueIceStairs() {
        super(Blocks.BLUE_ICE.defaultBlockState(), Properties.copy(Blocks.BLUE_ICE));
    }
}
