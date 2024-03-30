package com.deku.moreice.world.inventory;

import com.deku.moreice.common.blockEntities.FreezerBlockEntity;
import com.deku.moreice.world.item.crafting.ModRecipeType;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.NonNullList;
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

import java.util.List;
import java.util.Objects;

public class FreezerMenu extends AbstractContainerMenu {
    private static final int RESULT_SLOT_INDEX = 2;

    private final Container container;
    private final ContainerData data;

    // Slots and network synchronizing
    private final NonNullList<ItemStack> lastSlots = NonNullList.create();
    public final NonNullList<Slot> slots = NonNullList.create();
    private final List<DataSlot> dataSlots = Lists.newArrayList();
    private ItemStack carried = ItemStack.EMPTY;
    private final NonNullList<ItemStack> remoteSlots = NonNullList.create();
    private final IntList remoteDataSlots = new IntArrayList();
    private ItemStack remoteCarried = ItemStack.EMPTY;
    private int stateId;

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
        this.recipeType = ModRecipeType.FREEZING.get();
        checkContainerSize(entity, 3);
        checkContainerDataCount(containerData, 4);
        this.container = entity;
        data = containerData;
        level = inventory.player.level();

        this.addSlot(new Slot(container, 0, 56, 17));
        addSlot(new FreezerFuelSlot(this, container, 1, 56, 53));
        addSlot(new FreezerResultSlot(inventory.player, container, RESULT_SLOT_INDEX, 116, 35));

        // inventory slots
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // toolbar slots
        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }

        addDataSlots(containerData);
    }

//    @Override
//    public void fillCraftSlotsStackedContents(StackedContents contents) {
//        if (container instanceof StackedContentsCompatible) {
//            ((StackedContentsCompatible) container).fillStackedContents(contents);
//        }
//    }
//
//    @Override
//    public void clearCraftingContent() {
//        getSlot(0).set(ItemStack.EMPTY);
//        getSlot(2).set(ItemStack.EMPTY);
//    }
//
//    @Override
//    public boolean recipeMatches(RecipeHolder<? extends Recipe<Container>> holder) {
//        return holder.value().matches(container, level);
//    }
//
//    @Override
//    public int getResultSlotIndex() {
//        return RESULT_SLOT_INDEX;
//    }
//
//    @Override
//    public int getGridWidth() {
//        return 1;
//    }
//
//    @Override
//    public int getGridHeight() {
//        return 1;
//    }
//
//    @Override
//    public int getSize() {
//        return 3;
//    }
//
//    @Override
//    public boolean shouldMoveToInventory(int p_150635_) {
//        return false;
//    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        // TODO: Not sure what this is meant to do. Maybe some auto-fill logic via shortcuts
        return null;
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
}
