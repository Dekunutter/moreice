package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.extensions.IForgeBlock;

public class PackedIceBricks extends Block implements Ice, IForgeBlock {
    public PackedIceBricks() {
        super(Properties.copy(Blocks.PACKED_ICE));
    }
}
