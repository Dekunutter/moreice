package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.common.extensions.IForgeBlock;

public class PackedIceWall extends WallBlock implements Ice, IForgeBlock {
    public PackedIceWall() {
        super(Properties.copy(Blocks.PACKED_ICE).forceSolidOn());
    }
}
