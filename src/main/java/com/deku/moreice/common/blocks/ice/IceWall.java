package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.common.extensions.IForgeBlock;

public class IceWall extends WallBlock implements Ice, IForgeBlock {
    public IceWall() {
        super(Properties.ofLegacyCopy(Blocks.ICE).forceSolidOn());
    }
}
