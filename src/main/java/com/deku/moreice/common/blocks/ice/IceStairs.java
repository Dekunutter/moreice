package com.deku.moreice.common.blocks.ice;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.common.extensions.IForgeBlock;

public class IceStairs extends StairBlock implements IForgeBlock {
    public IceStairs() {
        super(Blocks.ICE.defaultBlockState(), Properties.ofLegacyCopy(Blocks.ICE));
    }
}
