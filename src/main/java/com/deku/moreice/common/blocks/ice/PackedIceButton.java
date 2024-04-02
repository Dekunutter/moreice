package com.deku.moreice.common.blocks.ice;

import com.deku.moreice.common.blocks.ModBlockSetType;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.material.PushReaction;

public class PackedIceButton extends ButtonBlock {
    public PackedIceButton() {
        super(Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY), ModBlockSetType.PACKED_ICE, 20, true);
    }
}
