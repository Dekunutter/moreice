package com.deku.moreice.common.blocks.ice;

import com.deku.moreice.common.blocks.ModBlockSetType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

public class BlueIceButton extends ButtonBlock implements Ice {
    public BlueIceButton() {
        super(ModBlockSetType.BLUE_ICE, 20, Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY));
    }

    // NOTE: Buttons are too small, make them melt into air instead of water
    @Override
    public BlockState meltsInto() {
        return Blocks.AIR.defaultBlockState();
    }
}
