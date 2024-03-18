package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraftforge.common.extensions.IForgeBlock;

public class BlueIceSlab extends SlabBlock implements Ice, IForgeBlock {
    public BlueIceSlab() {
        super(Properties.ofLegacyCopy(Blocks.BLUE_ICE));
    }
}
