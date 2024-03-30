package com.deku.moreice.common.blockEntities;

import com.deku.moreice.common.blocks.ModBlockStateProperties;
import com.deku.moreice.common.items.ModItems;
import com.deku.moreice.world.inventory.FreezerMenu;
import com.deku.moreice.world.item.crafting.ModRecipeType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

import static com.deku.moreice.Main.MOD_ID;

public class FreezerBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeCraftingHolder, StackedContentsCompatible {
    private NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private int coolingTime;
    private int coolingDuration;
    private int freezingProgress;
    private int freezingTotalTime;

    // For accessing data and syncing across clients from the server
    private final ContainerData dataAccess = new ContainerData() {
        public int get(int dataPosition) {
            switch (dataPosition) {
                case 0:
                    return FreezerBlockEntity.this.coolingTime;
                case 1:
                    return FreezerBlockEntity.this.coolingDuration;
                case 2:
                    return FreezerBlockEntity.this.freezingProgress;
                case 3:
                    return FreezerBlockEntity.this.freezingTotalTime;
                default:
                    return 0;
            }
        }

        public void set(int dataPosition, int value) {
            switch (dataPosition) {
                case 0:
                    FreezerBlockEntity.this.coolingTime = value;
                    break;
                case 1:
                    FreezerBlockEntity.this.coolingDuration = value;
                    break;
                case 2:
                    FreezerBlockEntity.this.freezingProgress = value;
                    break;
                case 3:
                    FreezerBlockEntity.this.freezingTotalTime = value;
            }

        }

        public int getCount() {
            return 4;
        }
    };
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
    private final RecipeType<?> recipeType;
    private final RecipeManager.CachedCheck<Container, ? extends AbstractCookingRecipe> quickCheck;

    public FreezerBlockEntity(BlockPos position, BlockState state) {
        this(ModBlockEntityType.FREEZER_BLOCK_ENTITY.get(), position, state, ModRecipeType.FREEZING.get());
    }

    public FreezerBlockEntity(BlockEntityType<?> type, BlockPos position, BlockState state, RecipeType<?> recipeType) {
        super(type, position, state);
        quickCheck = RecipeManager.createCheck((RecipeType) recipeType);
        this.recipeType = recipeType;
    }

    @Override
    public int[] getSlotsForFace(Direction p_19238_) {
        return new int[0];
    }

    public boolean canPlaceItem(int position, ItemStack item) {
        if (position == 2) {
            return false;
        } else if (position != 1) {
            return true;
        } else {
            ItemStack itemstack = items.get(1);
            Map<Item, Integer> validFuels = getFuel();
            boolean isValidFuel = validFuels.containsKey(item.getItem());
            int coolingTime = 0;
            if (isValidFuel) {
                coolingTime = validFuels.get(item.getItem());
            }
            return coolingTime > 0 || item.is(Items.BUCKET) && !itemstack.is(Items.BUCKET);
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int position, ItemStack item, @Nullable Direction direction) {
        return canPlaceItem(position, item);
    }

    @Override
    public boolean canTakeItemThroughFace(int p_19239_, ItemStack p_19240_, Direction p_19241_) {
        return false;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container." + MOD_ID + ".freezer");
    }

    @Nullable
    @Override
    protected AbstractContainerMenu createMenu(int slots, Inventory inventory) {
        return new FreezerMenu(slots, inventory, this, this.dataAccess);
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int position) {
        return items.get(position);
    }

    @Override
    public ItemStack removeItem(int position, int count) {
        return ContainerHelper.removeItem(items, position, count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int position) {
        return ContainerHelper.takeItem(this.items, position);
    }

    @Override
    public void setItem(int position, ItemStack itemToSet) {
        ItemStack itemstack = items.get(position);
        boolean flag = !itemToSet.isEmpty() && ItemStack.isSameItemSameTags(itemstack, itemToSet);
        items.set(position, itemToSet);
        if (itemToSet.getCount() > getMaxStackSize()) {
            itemToSet.setCount(getMaxStackSize());
        }

        if (position == 0 && !flag) {
            freezingTotalTime = getTotalCoolingTime(level, this);
            freezingProgress = 0;
            setChanged();
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    @Override
    public void setRecipeUsed(@Nullable RecipeHolder<?> holder) {
        if (holder != null) {
            ResourceLocation resourcelocation = holder.id();
            recipesUsed.addTo(resourcelocation, 1);
        }
    }

    @Nullable
    @Override
    public RecipeHolder<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void fillStackedContents(StackedContents contents) {
        for(ItemStack itemstack : items) {
            contents.accountStack(itemstack);
        }
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer player) {
        List<RecipeHolder<?>> list = this.getRecipesToAwardAndPopExperience(player.serverLevel(), player.position());
        player.awardRecipes(list);

        for(RecipeHolder<?> recipeholder : list) {
            if (recipeholder != null) {
                player.triggerRecipeCrafted(recipeholder, this.items);
            }
        }

        this.recipesUsed.clear();
    }

    public List<RecipeHolder<?>> getRecipesToAwardAndPopExperience(ServerLevel level, Vec3 position) {
        List<RecipeHolder<?>> list = Lists.newArrayList();

        for(Object2IntMap.Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
            level.getRecipeManager().byKey(entry.getKey()).ifPresent((recipe) -> {
                list.add(recipe);
                createExperience(level, position, entry.getIntValue(), ((AbstractCookingRecipe)recipe.value()).getExperience());
            });
        }

        return list;
    }

    private static void createExperience(ServerLevel level, Vec3 position, int count, float baseRecipeExperience) {
        int i = Mth.floor((float) count * baseRecipeExperience);
        float f = Mth.frac((float) count * baseRecipeExperience);
        if (f != 0.0F && Math.random() < (double) f) {
            ++i;
        }

        ExperienceOrb.award(level, position, i);
    }

    private boolean isCooling() {
        return coolingTime > 0;
    }

    public void load(CompoundTag tag) {
        super.load(tag);

        items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, items);

        coolingTime = tag.getInt("CoolTime");
        freezingProgress = tag.getInt("CoolingProgress");
        freezingTotalTime = tag.getInt("CoolingTotalTime");
        coolingDuration = getFreezeDuration(items.get(1));

        CompoundTag compoundtag = tag.getCompound("RecipesUsed");
        for(String s : compoundtag.getAllKeys()) {
            recipesUsed.put(new ResourceLocation(s), compoundtag.getInt(s));
        }
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("CoolTime", coolingTime);
        tag.putInt("CoolingProgress", freezingProgress);
        tag.putInt("CoolingTotalTime", freezingTotalTime);

        ContainerHelper.saveAllItems(tag, items);
        CompoundTag compoundTag = new CompoundTag();
        recipesUsed.forEach((recipe, value) -> {
            compoundTag.putInt(recipe.toString(), value);
        });
        tag.put("RecipesUsed", compoundTag);
    }

    private void getMatchingRecipe() {

    }

    /**
     * Tick down the remaining cooling time. Process any completed freezes
     *
     * @param level
     * @param position
     * @param state
     * @param blockEntity
     */
    public static void serverTick(Level level, BlockPos position, BlockState state, FreezerBlockEntity blockEntity) {
        boolean isCooling = blockEntity.isCooling();
        boolean stateChanged = false;

        // Decrement remaining cooling time
        if (isCooling) {
            --blockEntity.coolingTime;
        }

        // grab the recipe for the items in play
        ItemStack fuel = blockEntity.items.get(1);
        boolean hasIngredient = !blockEntity.items.get(0).isEmpty();
        boolean hasFuel = !fuel.isEmpty();
        if (blockEntity.isCooling() || hasFuel && hasIngredient) {
            RecipeHolder<?> recipeHolder;
            if (hasIngredient) {
                // TODO: Recipe not being found?
                recipeHolder = blockEntity.quickCheck.getRecipeFor(blockEntity, level).orElse(null);
                //RecipeHolder<?> holder2 = getMatchingRecipe()
            } else {
                recipeHolder = null;
            }

            // If the ingredient is freezable but we arent actively cooling yet, kick it off
            int maxStackSize = blockEntity.getMaxStackSize();
            // TODO: Cause recipe is null we cant get in here
            if (!blockEntity.isCooling() && blockEntity.canFreeze(level.registryAccess(), recipeHolder, blockEntity.items, maxStackSize)) {
                blockEntity.coolingTime = blockEntity.getFreezeDuration(fuel);
                blockEntity.coolingDuration = blockEntity.coolingTime;
                if (blockEntity.isCooling()) {
                    stateChanged = true;
                    if (fuel.hasCraftingRemainingItem())
                        blockEntity.items.set(1, fuel.getCraftingRemainingItem());
                    else
                    if (hasFuel) {
                        fuel.shrink(1);
                        if (fuel.isEmpty()) {
                            blockEntity.items.set(1, fuel.getCraftingRemainingItem());
                        }
                    }
                }
            }

            // If the ingredient is freezable, update freezing progress
            if (blockEntity.isCooling() && blockEntity.canFreeze(level.registryAccess(), recipeHolder, blockEntity.items, maxStackSize)) {
                ++blockEntity.freezingProgress;
                if (blockEntity.freezingProgress == blockEntity.freezingTotalTime) {
                    blockEntity.freezingProgress = 0;
                    blockEntity.freezingTotalTime = getTotalCoolingTime(level, blockEntity);
                    if (blockEntity.freeze(level.registryAccess(), recipeHolder, blockEntity.items, maxStackSize)) {
                        blockEntity.setRecipeUsed(recipeHolder);
                    }

                    stateChanged = true;
                }
            } else {
                blockEntity.freezingProgress = 0;
            }
        } else if (!blockEntity.isCooling() && blockEntity.freezingProgress > 0) {
            blockEntity.freezingProgress = Mth.clamp(blockEntity.freezingProgress - 2, 0, blockEntity.freezingTotalTime);
        }

        // Sets the cooling value into block state definition for the freezer in case it isnt already
        if (isCooling != blockEntity.isCooling()) {
            stateChanged = true;
            state = state.setValue(ModBlockStateProperties.COOLING, blockEntity.isCooling());
            level.setBlock(position, state, 3);
        }

        // updates the block entity
        if (stateChanged) {
            setChanged(level, position, state);
        }
    }

    private boolean canFreeze(RegistryAccess registry, @javax.annotation.Nullable RecipeHolder<?> holder, NonNullList<ItemStack> items, int flag) {
        if (!items.get(0).isEmpty() && holder != null) {
            ItemStack itemstack = ((RecipeHolder<Recipe<WorldlyContainer>>)holder).value().assemble(this, registry);
            if (itemstack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = items.get(2);
                if (itemstack1.isEmpty()) {
                    return true;
                } else if (!ItemStack.isSameItem(itemstack1, itemstack)) {
                    return false;
                } else if (itemstack1.getCount() + itemstack.getCount() <= flag && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
                    return true;
                } else {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        } else {
            return false;
        }
    }

    private boolean freeze(RegistryAccess registry, @javax.annotation.Nullable RecipeHolder<?> holder, NonNullList<ItemStack> items, int flag) {
        if (holder != null && canFreeze(registry, holder, items, flag)) {
            ItemStack itemstack = items.get(0);
            ItemStack itemstack1 = ((RecipeHolder<Recipe<WorldlyContainer>>) holder).value().assemble(this, registry);
            ItemStack itemstack2 = items.get(2);
            if (itemstack2.isEmpty()) {
                items.set(2, itemstack1.copy());
            } else if (itemstack2.is(itemstack1.getItem())) {
                itemstack2.grow(itemstack1.getCount());
            }

            if (itemstack.is(Items.WATER_BUCKET)) {
                items.set(0, new ItemStack(Items.BUCKET));
            } else {
                itemstack.shrink(1);
            }
            return true;
        } else {
            return false;
        }
    }

    protected int getFreezeDuration(ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            return 0;
        } else {
            Map<Item, Integer> validFuels = getFuel();
            boolean isValidFuel = validFuels.containsKey(itemStack.getItem());
            if (isValidFuel) {
                return validFuels.get(itemStack.getItem());
            }
        }
        return 0;
    }

    private static int getTotalCoolingTime(Level level, FreezerBlockEntity blockEntity) {
        return blockEntity.quickCheck.getRecipeFor(blockEntity, level).map((itemToFreeze) -> {
            return itemToFreeze.value().getCookingTime();
        }).orElse(200);
    }

    public static boolean isFuel(ItemStack itemStack) {
        Map<Item, Integer> validFuels = getFuel();
        boolean isValidFuel = validFuels.containsKey(itemStack.getItem());
        if (isValidFuel) {
            return validFuels.get(itemStack.getItem()) > 0;
        }
        return false;
    }

    public static Map<Item, Integer> getFuel() {
        Map<Item, Integer> fuelMap = Maps.newLinkedHashMap();
        add(fuelMap, Blocks.SNOW, 100);
        add(fuelMap, Items.SNOW_BLOCK, 1200);
        add(fuelMap, Items.SNOWBALL, 300);

        add(fuelMap, Blocks.FROSTED_ICE, 1600);

        add(fuelMap, Items.ICE, 1600);
        add(fuelMap, ModItems.ICE_STAIRS, 1200);
        add(fuelMap, ModItems.ICE_SLAB, 800);
        add(fuelMap, ModItems.ICE_WALL, 800);
        add(fuelMap, ModItems.ICE_BRICKS, 1600);
        add(fuelMap, ModItems.ICE_BRICK_SLAB, 800);
        add(fuelMap, ModItems.ICE_PRESSURE_PLATE, 100);

        add(fuelMap, Items.PACKED_ICE, 1600);
        add(fuelMap, ModItems.PACKED_ICE_STAIRS, 1200);
        add(fuelMap, ModItems.PACKED_ICE_SLAB, 800);
        add(fuelMap, ModItems.PACKED_ICE_WALL, 800);
        add(fuelMap, ModItems.PACKED_ICE_BRICKS, 1600);
        add(fuelMap, ModItems.PACKED_ICE_BRICK_SLAB, 800);
        add(fuelMap, ModItems.PACKED_ICE_PRESSURE_PLATE, 100);

        add(fuelMap, Items.BLUE_ICE, 1600);
        add(fuelMap, ModItems.BLUE_ICE_STAIRS, 1200);
        add(fuelMap, ModItems.BLUE_ICE_SLAB, 800);
        add(fuelMap, ModItems.BLUE_ICE_WALL, 800);
        add(fuelMap, ModItems.BLUE_ICE_BRICKS, 1600);
        add(fuelMap, ModItems.BLUE_ICE_BRICK_SLAB, 800);
        add(fuelMap, ModItems.BLUE_ICE_PRESSURE_PLATE, 100);
        return fuelMap;
    }

    private static void add(Map<Item, Integer> fuelMap, TagKey<Item> fuelTag, int coolingDuration) {
        for(Holder<Item> holder : BuiltInRegistries.ITEM.getTagOrEmpty(fuelTag)) {
            fuelMap.put(holder.value(), coolingDuration);
        }
    }

    private static void add(Map<Item, Integer> fuelMap, ItemLike fuelItem, int coolingDuration) {
        Item item = fuelItem.asItem();
        fuelMap.put(item, coolingDuration);
    }
}
