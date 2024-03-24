package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraftforge.common.extensions.IForgeBlock;

public class PackedIceBrickSlab extends SlabBlock implements Ice, IForgeBlock {
    public PackedIceBrickSlab() {
        super(Properties.ofLegacyCopy(Blocks.PACKED_ICE));
    }
}
