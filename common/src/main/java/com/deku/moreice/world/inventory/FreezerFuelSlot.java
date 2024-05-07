package com.deku.moreice.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class FreezerFuelSlot extends Slot {
    private final FreezerMenu menu;

    public FreezerFuelSlot(FreezerMenu menu, Container container, int slotOrder, int positionX, int positionY) {
        super(container, slotOrder, positionX, positionY);
        this.menu = menu;
    }

    public boolean mayPlace(ItemStack itemStack) {
        return menu.isFuel(itemStack) || isBucket(itemStack);
    }

    public int getMaxStackSize(ItemStack itemStack) {
        return isBucket(itemStack) ? 1 : super.getMaxStackSize(itemStack);
    }

    public static boolean isBucket(ItemStack itemStack) {
        return itemStack.is(Items.BUCKET);
    }
}
