package com.deku.moreice.world.inventory;

import com.deku.moreice.common.blockEntities.FreezerBlockEntity;
import com.deku.moreice.world.item.crafting.ModRecipeType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Objects;

public class FreezerMenu extends AbstractContainerMenu {
    private static final int RESULT_SLOT_INDEX = 2;

    private final Container container;
    private final ContainerData data;

    protected final Level level;

    private final RecipeType<?> recipeType;

    public FreezerMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, new SimpleContainer(3), new SimpleContainerData(4));
    }

    public FreezerMenu(int containerId, Inventory inventory, FriendlyByteBuf data) {
        this(containerId, inventory, getTileEntity(inventory, data), new SimpleContainerData(4));
    }

    public FreezerMenu(int containerId, Inventory inventory, Container entity, ContainerData containerData) {
        super(ModMenuType.FREEZER.get(), containerId);
        recipeType = ModRecipeType.FREEZING.get();
        checkContainerSize(entity, 3);
        checkContainerDataCount(containerData, 4);
        container = entity;
        container.startOpen(inventory.player);
        data = containerData;
        level = inventory.player.level();

        addSlot(new Slot(container, 0, 56, 17));
        addSlot(new FreezerFuelSlot(this, container, 1, 56, 53));
        addSlot(new FreezerResultSlot(inventory.player, container, RESULT_SLOT_INDEX, 116, 35));

        // inventory slots
        for(int x = 0; x < 3; ++x) {
            for(int y = 0; y < 9; ++y) {
                addSlot(new Slot(inventory, y + x * 9 + 9, 8 + y * 18, 84 + x * 18));
            }
        }

        // toolbar slots
        for(int x = 0; x < 9; ++x) {
            addSlot(new Slot(inventory, x, 8 + x * 18, 142));
        }

        addDataSlots(containerData);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int position) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = slots.get(position);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (position == 2) {
                if (!moveItemStackTo(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (position != 1 && position != 0) {
                if (canFreeze(itemstack1)) {
                    if (!moveItemStackTo(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isFuel(itemstack1)) {
                    if (!moveItemStackTo(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (position >= 3 && position < 30) {
                    if (!moveItemStackTo(itemstack1, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (position >= 30 && position < 39 && !moveItemStackTo(itemstack1, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(itemstack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    protected boolean canFreeze(ItemStack itemStack) {
        return level.getRecipeManager().getRecipeFor((RecipeType<AbstractCookingRecipe>) recipeType, new SimpleContainer(itemStack), level).isPresent();
    }

    /**
     * Determines if we should keep the menu open.
     * The only case to automatically close the menu right now is if the freezer was broken or deleted somehow
     * @param player
     * @return
     */
    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }

    private static FreezerBlockEntity getTileEntity(final Inventory playerInventory, final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        final BlockEntity tileAtPos = playerInventory.player.level().getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof FreezerBlockEntity) {
            return (FreezerBlockEntity) tileAtPos;
        }
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }

    protected boolean isFuel(ItemStack itemStack) {
        return FreezerBlockEntity.isFuel(itemStack);
    }

    public float getFreezingProgress() {
        int i = data.get(2);
        int j = data.get(3);
        return j != 0 && i != 0 ? Mth.clamp((float) i / (float) j, 0.0F, 1.0F) : 0.0F;
    }

    public float getProgress() {
        int i = data.get(1);
        if (i == 0) {
            i = 200;
        }

        return Mth.clamp((float) data.get(0) / (float) i, 0.0F, 1.0F);
    }

    public boolean isCooling() {
        return data.get(0) > 0;
    }

    public void removed(Player player) {
        super.removed(player);
        container.stopOpen(player);
    }

    public Container getContainer() {
        return container;
    }
}
