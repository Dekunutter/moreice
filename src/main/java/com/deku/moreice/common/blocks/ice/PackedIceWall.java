package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;
import net.neoforged.neoforge.common.extensions.IBlockExtension;

public class PackedIceWall extends WallBlock implements Ice, IBlockExtension {
    public PackedIceWall() {
        super(Properties.ofLegacyCopy(Blocks.PACKED_ICE).forceSolidOn());
    }
}
