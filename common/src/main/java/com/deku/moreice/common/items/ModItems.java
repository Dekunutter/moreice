package com.deku.moreice.common.items;

import com.deku.moreice.common.blocks.ModBlocks;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;

import java.util.function.Supplier;

import static com.deku.moreice.MoreIce.MOD_ID;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);
    // ICE
    public static final Supplier<BlockItem> ICE_STAIRS = ITEMS.register("ice_stairs", () -> new BlockItem(ModBlocks.ICE_STAIRS.get(), new Item.Properties()));

    public static final Supplier<BlockItem> ICE_SLAB = ITEMS.register("ice_slab", () -> new BlockItem(ModBlocks.ICE_SLAB.get(), new Item.Properties()));

    public static final Supplier<BlockItem> ICE_WALL = ITEMS.register("ice_wall", () -> new BlockItem(ModBlocks.ICE_WALL.get(), new Item.Properties()));

    public static final Supplier<BlockItem> ICE_BRICKS = ITEMS.register("ice_bricks", () -> new BlockItem(ModBlocks.ICE_BRICKS.get(), new Item.Properties()));

    public static final Supplier<BlockItem> ICE_BRICK_SLAB = ITEMS.register("ice_brick_slab", () -> new BlockItem(ModBlocks.ICE_BRICK_SLAB.get(), new Item.Properties()));

    public static final Supplier<BlockItem> ICE_BRICK_WALL = ITEMS.register("ice_brick_wall", () -> new BlockItem(ModBlocks.ICE_BRICK_WALL.get(), new Item.Properties()));

    public static final Supplier<BlockItem> ICE_BRICK_STAIRS = ITEMS.register("ice_brick_stairs", () -> new BlockItem(ModBlocks.ICE_BRICK_STAIRS.get(), new Item.Properties()));

    public static final Supplier<BlockItem> ICE_PRESSURE_PLATE = ITEMS.register("ice_pressure_plate", () -> new BlockItem(ModBlocks.ICE_PRESSURE_PLATE.get(), new Item.Properties()));

    public static final Supplier<BlockItem> ICE_BUTTON = ITEMS.register("ice_button", () -> new BlockItem(ModBlocks.ICE_BUTTON.get(), new Item.Properties()));
    public static final Supplier<BlockItem> CHISELED_ICE = ITEMS.register("chiseled_ice", () -> new BlockItem(ModBlocks.CHISELED_ICE.get(), new Item.Properties()));

    // PACKED ICE
    public static final Supplier<BlockItem> PACKED_ICE_STAIRS = ITEMS.register("packed_ice_stairs", () -> new BlockItem(ModBlocks.PACKED_ICE_STAIRS.get(), new Item.Properties()));

    public static final Supplier<BlockItem> PACKED_ICE_SLAB = ITEMS.register("packed_ice_slab", () -> new BlockItem(ModBlocks.PACKED_ICE_SLAB.get(), new Item.Properties()));

    public static final Supplier<BlockItem> PACKED_ICE_WALL = ITEMS.register("packed_ice_wall", () -> new BlockItem(ModBlocks.PACKED_ICE_WALL.get(), new Item.Properties()));

    public static final Supplier<BlockItem> PACKED_ICE_BRICKS = ITEMS.register("packed_ice_bricks", () -> new BlockItem(ModBlocks.PACKED_ICE_BRICKS.get(), new Item.Properties()));

    public static final Supplier<BlockItem> PACKED_ICE_BRICK_SLAB = ITEMS.register("packed_ice_brick_slab", () -> new BlockItem(ModBlocks.PACKED_ICE_BRICK_SLAB.get(), new Item.Properties()));

    public static final Supplier<BlockItem> PACKED_ICE_BRICK_WALL = ITEMS.register("packed_ice_brick_wall", () -> new BlockItem(ModBlocks.PACKED_ICE_BRICK_WALL.get(), new Item.Properties()));

    public static final Supplier<BlockItem> PACKED_ICE_BRICK_STAIRS = ITEMS.register("packed_ice_brick_stairs", () -> new BlockItem(ModBlocks.PACKED_ICE_BRICK_STAIRS.get(), new Item.Properties()));

    public static final Supplier<BlockItem> PACKED_ICE_PRESSURE_PLATE = ITEMS.register("packed_ice_pressure_plate", () -> new BlockItem(ModBlocks.PACKED_ICE_PRESSURE_PLATE.get(), new Item.Properties()));

    public static final Supplier<BlockItem> PACKED_ICE_BUTTON = ITEMS.register("packed_ice_button", () -> new BlockItem(ModBlocks.PACKED_ICE_BUTTON.get(), new Item.Properties()));
    public static final Supplier<BlockItem> CHISELED_PACKED_ICE = ITEMS.register("chiseled_packed_ice", () -> new BlockItem(ModBlocks.CHISELED_PACKED_ICE.get(), new Item.Properties()));

    // BLUE ICE
    public static final Supplier<BlockItem> BLUE_ICE_STAIRS = ITEMS.register("blue_ice_stairs", () -> new BlockItem(ModBlocks.BLUE_ICE_STAIRS.get(), new Item.Properties()));

    public static final Supplier<BlockItem> BLUE_ICE_SLAB = ITEMS.register("blue_ice_slab", () -> new BlockItem(ModBlocks.BLUE_ICE_SLAB.get(), new Item.Properties()));

    public static final Supplier<BlockItem> BLUE_ICE_WALL = ITEMS.register("blue_ice_wall", () -> new BlockItem(ModBlocks.BLUE_ICE_WALL.get(), new Item.Properties()));

    public static final Supplier<BlockItem> BLUE_ICE_BRICKS = ITEMS.register("blue_ice_bricks", () -> new BlockItem(ModBlocks.BLUE_ICE_BRICKS.get(), new Item.Properties()));

    public static final Supplier<BlockItem> BLUE_ICE_BRICK_SLAB = ITEMS.register("blue_ice_brick_slab", () -> new BlockItem(ModBlocks.BLUE_ICE_BRICK_SLAB.get(), new Item.Properties()));

    public static final Supplier<BlockItem> BLUE_ICE_BRICK_WALL = ITEMS.register("blue_ice_brick_wall", () -> new BlockItem(ModBlocks.BLUE_ICE_BRICK_WALL.get(), new Item.Properties()));

    public static final Supplier<BlockItem> BLUE_ICE_BRICK_STAIRS = ITEMS.register("blue_ice_brick_stairs", () -> new BlockItem(ModBlocks.BLUE_ICE_BRICK_STAIRS.get(), new Item.Properties()));

    public static final Supplier<BlockItem> BLUE_ICE_PRESSURE_PLATE = ITEMS.register("blue_ice_pressure_plate", () -> new BlockItem(ModBlocks.BLUE_ICE_PRESSURE_PLATE.get(), new Item.Properties()));

    public static final Supplier<BlockItem> BLUE_ICE_BUTTON = ITEMS.register("blue_ice_button", () -> new BlockItem(ModBlocks.BLUE_ICE_BUTTON.get(), new Item.Properties()));
    public static final Supplier<BlockItem> CHISELED_BLUE_ICE = ITEMS.register("chiseled_blue_ice", () -> new BlockItem(ModBlocks.CHISELED_BLUE_ICE.get(), new Item.Properties()));

    // MISC
    public static final Supplier<BlockItem> FREEZER = ITEMS.register("freezer", () -> new BlockItem(ModBlocks.FREEZER.get(), new Item.Properties()));
}
