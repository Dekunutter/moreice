package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.extensions.IBlockExtension;

public class PackedIceBricks extends Block implements Ice, IBlockExtension {
    public PackedIceBricks() {
        super(Properties.ofLegacyCopy(Blocks.PACKED_ICE));
    }
}
