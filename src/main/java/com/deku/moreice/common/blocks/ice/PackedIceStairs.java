package com.deku.moreice.common.blocks.ice;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class PackedIceStairs extends AbstractIceStairs {
    public PackedIceStairs() {
        super(Blocks.PACKED_ICE.defaultBlockState(), Properties.ofLegacyCopy(Blocks.PACKED_ICE));
    }
}
