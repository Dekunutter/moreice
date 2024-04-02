package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.common.extensions.IForgeBlock;

public class PackedIceStairs extends StairBlock implements Ice, IForgeBlock {
    public PackedIceStairs() {
        super(Blocks.PACKED_ICE.defaultBlockState(), Properties.copy(Blocks.PACKED_ICE));
    }
}
