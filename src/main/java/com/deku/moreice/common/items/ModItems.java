package com.deku.moreice.common.items;

import com.deku.moreice.common.blocks.ModBlockInitializer;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.deku.moreice.Main.MOD_ID;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    // ICE
    public static final Supplier<BlockItem> ICE_STAIRS = ITEMS.registerSimpleBlockItem("ice_stairs", ModBlockInitializer.ICE_STAIRS);

    public static final Supplier<BlockItem> ICE_SLAB = ITEMS.registerSimpleBlockItem("ice_slab", ModBlockInitializer.ICE_SLAB);

    public static final Supplier<BlockItem> ICE_WALL = ITEMS.registerSimpleBlockItem("ice_wall", ModBlockInitializer.ICE_WALL);

    public static final Supplier<BlockItem> ICE_BRICKS = ITEMS.registerSimpleBlockItem("ice_bricks", ModBlockInitializer.ICE_BRICKS);

    public static final Supplier<BlockItem> ICE_BRICK_SLAB = ITEMS.registerSimpleBlockItem("ice_brick_slab", ModBlockInitializer.ICE_BRICK_SLAB);

    public static final Supplier<BlockItem> ICE_BRICK_WALL = ITEMS.registerSimpleBlockItem("ice_brick_wall", ModBlockInitializer.ICE_BRICK_WALL);

    public static final Supplier<BlockItem> ICE_BRICK_STAIRS = ITEMS.registerSimpleBlockItem("ice_brick_stairs", ModBlockInitializer.ICE_BRICK_STAIRS);

    public static final Supplier<BlockItem> ICE_PRESSURE_PLATE = ITEMS.registerSimpleBlockItem("ice_pressure_plate", ModBlockInitializer.ICE_PRESSURE_PLATE);

    public static final Supplier<BlockItem> ICE_BUTTON = ITEMS.registerSimpleBlockItem("ice_button", ModBlockInitializer.ICE_BUTTON);

    // PACKED ICE
    public static final Supplier<BlockItem> PACKED_ICE_STAIRS = ITEMS.registerSimpleBlockItem("packed_ice_stairs", ModBlockInitializer.PACKED_ICE_STAIRS);

    public static final Supplier<BlockItem> PACKED_ICE_SLAB = ITEMS.registerSimpleBlockItem("packed_ice_slab", ModBlockInitializer.PACKED_ICE_SLAB);

    public static final Supplier<BlockItem> PACKED_ICE_WALL = ITEMS.registerSimpleBlockItem("packed_ice_wall", ModBlockInitializer.PACKED_ICE_WALL);

    public static final Supplier<BlockItem> PACKED_ICE_BRICKS = ITEMS.registerSimpleBlockItem("packed_ice_bricks", ModBlockInitializer.PACKED_ICE_BRICKS);

    public static final Supplier<BlockItem> PACKED_ICE_BRICK_SLAB = ITEMS.registerSimpleBlockItem("packed_ice_brick_slab", ModBlockInitializer.PACKED_ICE_BRICK_SLAB);

    public static final Supplier<BlockItem> PACKED_ICE_BRICK_WALL = ITEMS.registerSimpleBlockItem("packed_ice_brick_wall", ModBlockInitializer.PACKED_ICE_BRICK_WALL);

    public static final Supplier<BlockItem> PACKED_ICE_BRICK_STAIRS = ITEMS.registerSimpleBlockItem("packed_ice_brick_stairs", ModBlockInitializer.PACKED_ICE_BRICK_STAIRS);

    public static final Supplier<BlockItem> PACKED_ICE_PRESSURE_PLATE = ITEMS.registerSimpleBlockItem("packed_ice_pressure_plate", ModBlockInitializer.PACKED_ICE_PRESSURE_PLATE);

    public static final Supplier<BlockItem> PACKED_ICE_BUTTON = ITEMS.registerSimpleBlockItem("packed_ice_button", ModBlockInitializer.PACKED_ICE_BUTTON);

    // BLUE ICE
    public static final Supplier<BlockItem> BLUE_ICE_STAIRS = ITEMS.registerSimpleBlockItem("blue_ice_stairs", ModBlockInitializer.BLUE_ICE_STAIRS);

    public static final Supplier<BlockItem> BLUE_ICE_SLAB = ITEMS.registerSimpleBlockItem("blue_ice_slab", ModBlockInitializer.BLUE_ICE_SLAB);

    public static final Supplier<BlockItem> BLUE_ICE_WALL = ITEMS.registerSimpleBlockItem("blue_ice_wall", ModBlockInitializer.BLUE_ICE_WALL);

    public static final Supplier<BlockItem> BLUE_ICE_BRICKS = ITEMS.registerSimpleBlockItem("blue_ice_bricks", ModBlockInitializer.BLUE_ICE_BRICKS);

    public static final Supplier<BlockItem> BLUE_ICE_BRICK_SLAB = ITEMS.registerSimpleBlockItem("blue_ice_brick_slab", ModBlockInitializer.BLUE_ICE_BRICK_SLAB);

    public static final Supplier<BlockItem> BLUE_ICE_BRICK_WALL = ITEMS.registerSimpleBlockItem("blue_ice_brick_wall", ModBlockInitializer.BLUE_ICE_BRICK_WALL);

    public static final Supplier<BlockItem> BLUE_ICE_BRICK_STAIRS = ITEMS.registerSimpleBlockItem("blue_ice_brick_stairs", ModBlockInitializer.BLUE_ICE_BRICK_STAIRS);

    public static final Supplier<BlockItem> BLUE_ICE_PRESSURE_PLATE = ITEMS.registerSimpleBlockItem("blue_ice_pressure_plate", ModBlockInitializer.BLUE_ICE_PRESSURE_PLATE);

    public static final Supplier<BlockItem> BLUE_ICE_BUTTON = ITEMS.registerSimpleBlockItem("blue_ice_button", ModBlockInitializer.BLUE_ICE_BUTTON);

    // MISC
    public static final Supplier<BlockItem> FREEZER = ITEMS.registerSimpleBlockItem("freezer", ModBlockInitializer.FREEZER);
}
