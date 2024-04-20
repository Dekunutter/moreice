package com.deku.moreice.world.inventory;

import com.deku.moreice.common.blockEntities.FreezerBlockEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class FreezerResultSlot extends Slot {
    private final Player player;
    private int removeCount;

    public FreezerResultSlot(Player player, Container container, int slotOrder, int positionX, int positionY) {
        super(container, slotOrder, positionX, positionY);
        this.player = player;
    }

    public boolean mayPlace(ItemStack itemStack) {
        return false;
    }

    public ItemStack remove(int count) {
        if (hasItem()) {
            removeCount += Math.min(count, getItem().getCount());
        }

        return super.remove(count);
    }

    public void onTake(Player player, ItemStack itemStack) {
        this.checkTakeAchievements(itemStack);
        super.onTake(player, itemStack);
    }

    protected void onQuickCraft(ItemStack itemStack, int count) {
        removeCount += count;
        checkTakeAchievements(itemStack);
    }

    protected void checkTakeAchievements(ItemStack itemStack) {
         itemStack.onCraftedBy(player.level(), player, removeCount);
         Player player = this.player;
         if (player instanceof ServerPlayer serverPlayer) {
             Container container = this.container;
             if (container instanceof FreezerBlockEntity freezer) {
                 freezer.awardUsedRecipesAndPopExperience(serverPlayer);
             }
         }

         removeCount = 0;
         //ForgeEventFactory.firePlayerSmeltedEvent(player, itemStack);
    }
}
