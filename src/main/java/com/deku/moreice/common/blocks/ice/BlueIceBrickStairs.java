package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.common.extensions.IForgeBlock;

public class BlueIceBrickStairs extends StairBlock implements Ice, IForgeBlock {
    public BlueIceBrickStairs() {
        super(Blocks.BLUE_ICE.defaultBlockState(), Properties.copy(Blocks.BLUE_ICE));
    }
}
