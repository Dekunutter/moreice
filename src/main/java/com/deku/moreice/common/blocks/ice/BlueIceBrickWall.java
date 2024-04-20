package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;
import net.neoforged.neoforge.common.extensions.IBlockExtension;

public class BlueIceBrickWall extends WallBlock implements Ice, IBlockExtension {
    public BlueIceBrickWall() {
        super(Properties.ofLegacyCopy(Blocks.BLUE_ICE).forceSolidOn());
    }
}
