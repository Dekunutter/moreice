package com.deku.moreice.common.blocks.ice;

import com.deku.moreice.common.blocks.ModBlockSetType;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.material.PushReaction;

public class BlueIceButton extends ButtonBlock {
    public BlueIceButton() {
        super(Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY), ModBlockSetType.BLUE_ICE, 20, true);
    }
}
