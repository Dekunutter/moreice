package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.extensions.IForgeBlock;

public class BlueIceBricks extends Block implements Ice, IForgeBlock {
    public BlueIceBricks() {
        super(Properties.ofLegacyCopy(Blocks.BLUE_ICE));
    }
}
