package com.deku.moreice.common.blocks.ice;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ChiseledIce extends Block implements Ice {
    public ChiseledIce() {
        super(Properties.ofLegacyCopy(Blocks.ICE));
    }

    // NOTE: Lazily copied from IceBlock.
    public void randomTick(BlockState state, ServerLevel level, BlockPos position, RandomSource random) {
        if (level.getBrightness(LightLayer.BLOCK, position) > 11 - state.getLightBlock(level, position)) {
            this.melt(state, level, position);
        }
    }

    // NOTE: Copied from HalfTransparentBlock. Since I'm inheriting from SlabBlock for the half-block props I'm instead gonna lazy copy the ice rendering logic in here
    public boolean skipRendering(BlockState state, BlockState otherState, Direction direction) {
        return otherState.is(this) ? true : super.skipRendering(state, otherState, direction);
    }

    // NOTE: Lazily copied from IceBlock.
    public void playerDestroy(Level level, Player player, BlockPos position, BlockState state, @Nullable BlockEntity entity, ItemStack item) {
        super.playerDestroy(level, player, position, state, entity, item);
        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, item) == 0) {
            if (level.dimensionType().ultraWarm()) {
                level.removeBlock(position, false);
                return;
            }

            BlockState blockState = level.getBlockState(position.below());
            if (blockState.blocksMotion() || blockState.liquid()) {
                level.setBlockAndUpdate(position, meltsInto());
            }
        }
    }
}
