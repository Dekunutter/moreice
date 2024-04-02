package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.common.extensions.IForgeBlock;

public class BlueIceBrickWall extends WallBlock implements Ice, IForgeBlock {
    public BlueIceBrickWall() {
        super(Properties.copy(Blocks.BLUE_ICE).forceSolidOn());
    }
}
