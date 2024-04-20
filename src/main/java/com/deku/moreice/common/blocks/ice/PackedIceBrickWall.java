package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;
import net.neoforged.neoforge.common.extensions.IBlockExtension;

public class PackedIceBrickWall extends WallBlock implements Ice, IBlockExtension {
    public PackedIceBrickWall() {
        super(Properties.ofLegacyCopy(Blocks.PACKED_ICE).forceSolidOn());
    }
}
