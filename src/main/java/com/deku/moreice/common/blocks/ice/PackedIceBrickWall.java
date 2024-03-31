package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.common.extensions.IForgeBlock;

public class PackedIceBrickWall extends WallBlock implements Ice, IForgeBlock {
    public PackedIceBrickWall() {
        super(Properties.ofLegacyCopy(Blocks.PACKED_ICE).forceSolidOn());
    }
}
