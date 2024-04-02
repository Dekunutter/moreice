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
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

import static com.deku.moreice.Main.MOD_ID;

public class FreezerBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeHolder, StackedContentsCompatible, LidBlockEntity {
    private static final int EVENT_SET_OPEN_COUNT = 1;
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

    private final ContainerOpenersCounter opens = new ContainerOpenersCounter() {
        protected void onOpen(Level level, BlockPos position, BlockState state) {
            FreezerBlockEntity.playSound(level, position, state, SoundEvents.IRON_DOOR_OPEN);
        }

        protected void onClose(Level level, BlockPos position, BlockState state) {
            FreezerBlockEntity.playSound(level, position, state, SoundEvents.IRON_DOOR_CLOSE);
        }

        protected void openerCountChanged(Level level, BlockPos position, BlockState state, int eventId, int eventValue) {
            signalOpenCount(level, position, state, EVENT_SET_OPEN_COUNT, eventValue);
        }

        protected boolean isOwnContainer(Player player) {
            if (!(player.containerMenu instanceof FreezerMenu)) {
                return false;
            } else {
                Container container = ((FreezerMenu) player.containerMenu).getContainer();
                return container == FreezerBlockEntity.this || container instanceof CompoundContainer && ((CompoundContainer)container).contains(FreezerBlockEntity.this);
            }
        }
    };

    private final ChestLidController lidController = new ChestLidController();

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
    public void setRecipeUsed(@Nullable Recipe<?> recipe) {
        if (recipe != null) {
            ResourceLocation resourcelocation = recipe.getId();
            recipesUsed.addTo(resourcelocation, 1);
        }
    }

    @Nullable
    @Override
    public Recipe<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void fillStackedContents(StackedContents contents) {
        for(ItemStack itemstack : items) {
            contents.accountStack(itemstack);
        }
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer player) {
        List<Recipe<?>> list = this.getRecipesToAwardAndPopExperience(player.serverLevel(), player.position());
        player.awardRecipes(list);

        for(Recipe<?> recipe : list) {
            if (recipe != null) {
                player.triggerRecipeCrafted(recipe, this.items);
            }
        }

        this.recipesUsed.clear();
    }

    public List<Recipe<?>> getRecipesToAwardAndPopExperience(ServerLevel level, Vec3 position) {
        List<Recipe<?>> list = Lists.newArrayList();

        for(Object2IntMap.Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
            level.getRecipeManager().byKey(entry.getKey()).ifPresent((recipe) -> {
                list.add(recipe);
                createExperience(level, position, entry.getIntValue(), ((AbstractCookingRecipe)recipe).getExperience());
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

    public static void clientTick(Level level, BlockPos position, BlockState state, FreezerBlockEntity blockEntity) {
        lidAnimateTick(level, position, state, blockEntity);
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
            Recipe<?> recipe;
            if (hasIngredient) {
                // TODO: Recipe not being found?
                recipe = blockEntity.quickCheck.getRecipeFor(blockEntity, level).orElse(null);
                //RecipeHolder<?> holder2 = getMatchingRecipe()
            } else {
                recipe = null;
            }

            // If the ingredient is freezable but we arent actively cooling yet, kick it off
            int maxStackSize = blockEntity.getMaxStackSize();
            // TODO: Cause recipe is null we cant get in here
            if (!blockEntity.isCooling() && blockEntity.canFreeze(level.registryAccess(), recipe, blockEntity.items, maxStackSize)) {
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
            if (blockEntity.isCooling() && blockEntity.canFreeze(level.registryAccess(), recipe, blockEntity.items, maxStackSize)) {
                ++blockEntity.freezingProgress;
                if (blockEntity.freezingProgress == blockEntity.freezingTotalTime) {
                    blockEntity.freezingProgress = 0;
                    blockEntity.freezingTotalTime = getTotalCoolingTime(level, blockEntity);
                    if (blockEntity.freeze(level.registryAccess(), recipe, blockEntity.items, maxStackSize)) {
                        blockEntity.setRecipeUsed(recipe);
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

    private boolean canFreeze(RegistryAccess registry, @javax.annotation.Nullable Recipe<?> recipe, NonNullList<ItemStack> items, int flag) {
        if (!items.get(0).isEmpty() && recipe != null) {
            ItemStack itemstack = ((Recipe<WorldlyContainer>)recipe).assemble(this, registry);
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

    private boolean freeze(RegistryAccess registry, @javax.annotation.Nullable Recipe<?> recipe, NonNullList<ItemStack> items, int flag) {
        if (recipe != null && canFreeze(registry, recipe, items, flag)) {
            ItemStack itemstack = items.get(0);
            ItemStack itemstack1 = ((Recipe<WorldlyContainer>) recipe).assemble(this, registry);
            ItemStack itemstack2 = items.get(2);
            // NOTE: Setting count increments of 4 here as a hardcoded result to all recipes cause I dont want to bother to make a new recipe serializer just to have a working count value for cooking recipe results
            int resultCount = 1;
            if (itemstack.is(Items.WATER_BUCKET)) {
                resultCount = 16;
            } else if (itemstack.is(Items.SNOW_BLOCK)) {
                resultCount = 4;
            }

            if (itemstack2.isEmpty()) {
                ItemStack copy = itemstack1.copy();
                copy.setCount(resultCount);
                items.set(2, copy);
            } else if (itemstack2.is(itemstack1.getItem())) {
                ItemStack copy = itemstack1.copy();
                copy.setCount(resultCount);
                itemstack2.grow(copy.getCount());
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
        return blockEntity.quickCheck.getRecipeFor(blockEntity, level).map(AbstractCookingRecipe::getCookingTime).orElse(200);
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
        add(fuelMap, Items.SNOW_BLOCK, 400);
        add(fuelMap, Items.SNOWBALL, 100);

        add(fuelMap, Blocks.FROSTED_ICE, 500);

        add(fuelMap, Items.ICE, 500);
        add(fuelMap, ModItems.ICE_STAIRS, 400);
        add(fuelMap, ModItems.ICE_SLAB, 200);
        add(fuelMap, ModItems.ICE_WALL, 200);
        add(fuelMap, ModItems.ICE_BRICKS, 500);
        add(fuelMap, ModItems.ICE_BRICK_SLAB, 200);
        add(fuelMap, ModItems.ICE_PRESSURE_PLATE, 50);

        add(fuelMap, Items.PACKED_ICE, 500);
        add(fuelMap, ModItems.PACKED_ICE_STAIRS, 400);
        add(fuelMap, ModItems.PACKED_ICE_SLAB, 200);
        add(fuelMap, ModItems.PACKED_ICE_WALL, 200);
        add(fuelMap, ModItems.PACKED_ICE_BRICKS, 500);
        add(fuelMap, ModItems.PACKED_ICE_BRICK_SLAB, 200);
        add(fuelMap, ModItems.PACKED_ICE_PRESSURE_PLATE, 50);

        add(fuelMap, Items.BLUE_ICE, 500);
        add(fuelMap, ModItems.BLUE_ICE_STAIRS, 400);
        add(fuelMap, ModItems.BLUE_ICE_SLAB, 200);
        add(fuelMap, ModItems.BLUE_ICE_WALL, 200);
        add(fuelMap, ModItems.BLUE_ICE_BRICKS, 500);
        add(fuelMap, ModItems.BLUE_ICE_BRICK_SLAB, 200);
        add(fuelMap, ModItems.BLUE_ICE_PRESSURE_PLATE, 50);
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

    public void startOpen(Player player) {
        if (!remove && !player.isSpectator()) {
            opens.incrementOpeners(player, getLevel(), getBlockPos(), getBlockState());
        }
    }

    public void stopOpen(Player player) {
        if (!remove && !player.isSpectator()) {
            opens.decrementOpeners(player, getLevel(), getBlockPos(), getBlockState());
        }
    }

    public static int getOpenCount(BlockGetter blockGetter, BlockPos position) {
        BlockState blockstate = blockGetter.getBlockState(position);
        if (blockstate.hasBlockEntity()) {
            BlockEntity blockEntity = blockGetter.getBlockEntity(position);
            if (blockEntity instanceof FreezerBlockEntity) {
                return ((FreezerBlockEntity) blockEntity).opens.getOpenerCount();
            }
        }

        return 0;
    }

    protected static void playSound(Level level, BlockPos position, BlockState state, SoundEvent soundEvent) {
        double stepX = (double) position.getX() + 0.5D;
        double stepY = (double) position.getY() + 0.5D;
        double stepZ = (double) position.getZ() + 0.5D;

        level.playSound((Player) null, stepX, stepY, stepZ, soundEvent, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
    }

    public void recheckOpen() {
        if (!remove) {
            opens.recheckOpeners(getLevel(), getBlockPos(), getBlockState());
        }

    }

    protected void signalOpenCount(Level level, BlockPos position, BlockState state, int eventId, int eventValue) {
        Block block = state.getBlock();
        level.blockEvent(position, block, eventId, eventValue);
    }

    public static void lidAnimateTick(Level level, BlockPos position, BlockState state, FreezerBlockEntity entity) {
        entity.lidController.tickLid();
    }

    public boolean triggerEvent(int eventId, int eventValue) {
        if (eventId == 1) {
            lidController.shouldBeOpen(eventValue > 0);
            return true;
        } else {
            return super.triggerEvent(eventId, eventValue);
        }
    }

    public float getOpenNess(float value) {
        return lidController.getOpenness(value);
    }
}
