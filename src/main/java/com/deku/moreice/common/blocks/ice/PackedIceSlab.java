package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraftforge.common.extensions.IForgeBlock;

public class PackedIceSlab extends SlabBlock implements Ice, IForgeBlock {
    public PackedIceSlab() {
        super(Properties.copy(Blocks.PACKED_ICE));
    }
}
