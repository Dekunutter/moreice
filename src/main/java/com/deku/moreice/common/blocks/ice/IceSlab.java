package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraftforge.common.extensions.IForgeBlock;

public class IceSlab extends SlabBlock implements Ice, IForgeBlock {
    public IceSlab() {
        super(Properties.ofLegacyCopy(Blocks.ICE));
    }
}
