package com.deku.moreice.common.blocks;

import com.deku.moreice.common.blocks.ice.*;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;

import static com.deku.moreice.MoreIce.MOD_ID;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, Registries.BLOCK);

    // ICE
    public static final RegistrySupplier<Block> ICE_STAIRS = BLOCKS.register("ice_stairs", () -> new IceStairs());
    public static final RegistrySupplier<Block> ICE_SLAB = BLOCKS.register("ice_slab", IceSlab::new);
    public static final RegistrySupplier<Block> ICE_WALL = BLOCKS.register("ice_wall", IceWall::new);
    public static final RegistrySupplier<Block> ICE_BRICKS = BLOCKS.register("ice_bricks", IceBricks::new);
    public static final RegistrySupplier<Block> ICE_BRICK_SLAB = BLOCKS.register("ice_brick_slab", IceBrickSlab::new);
    public static final RegistrySupplier<Block> ICE_BRICK_WALL = BLOCKS.register("ice_brick_wall", IceBrickWall::new);
    public static final RegistrySupplier<Block> ICE_BRICK_STAIRS = BLOCKS.register("ice_brick_stairs", IceBrickStairs::new);
    public static final RegistrySupplier<Block> ICE_PRESSURE_PLATE = BLOCKS.register("ice_pressure_plate", IcePressurePlate::new);
    public static final RegistrySupplier<Block> ICE_BUTTON = BLOCKS.register("ice_button", IceButton::new);

    // PACKED ICE
    public static final RegistrySupplier<Block> PACKED_ICE_STAIRS = BLOCKS.register("packed_ice_stairs", PackedIceStairs::new);
    public static final RegistrySupplier<Block> PACKED_ICE_SLAB = BLOCKS.register("packed_ice_slab", PackedIceSlab::new);
    public static final RegistrySupplier<Block> PACKED_ICE_WALL = BLOCKS.register("packed_ice_wall", PackedIceWall::new);
    public static final RegistrySupplier<Block> PACKED_ICE_BRICKS = BLOCKS.register("packed_ice_bricks", PackedIceBricks::new);
    public static final RegistrySupplier<Block> PACKED_ICE_BRICK_SLAB = BLOCKS.register("packed_ice_brick_slab", PackedIceBrickSlab::new);
    public static final RegistrySupplier<Block> PACKED_ICE_BRICK_WALL = BLOCKS.register("packed_ice_brick_wall", PackedIceBrickWall::new);
    public static final RegistrySupplier<Block> PACKED_ICE_BRICK_STAIRS = BLOCKS.register("packed_ice_brick_stairs", PackedIceBrickStairs::new);
    public static final RegistrySupplier<Block> PACKED_ICE_PRESSURE_PLATE = BLOCKS.register("packed_ice_pressure_plate", PackedIcePressurePlate::new);
    public static final RegistrySupplier<Block> PACKED_ICE_BUTTON = BLOCKS.register("packed_ice_button", PackedIceButton::new);

    // BLUE ICE
    public static final RegistrySupplier<Block> BLUE_ICE_STAIRS = BLOCKS.register("blue_ice_stairs", BlueIceStairs::new);
    public static final RegistrySupplier<Block> BLUE_ICE_SLAB = BLOCKS.register("blue_ice_slab", BlueIceSlab::new);
    public static final RegistrySupplier<Block> BLUE_ICE_WALL = BLOCKS.register("blue_ice_wall", BlueIceWall::new);
    public static final RegistrySupplier<Block> BLUE_ICE_BRICKS = BLOCKS.register("blue_ice_bricks", BlueIceBricks::new);
    public static final RegistrySupplier<Block> BLUE_ICE_BRICK_SLAB = BLOCKS.register("blue_ice_brick_slab", BlueIceBrickSlab::new);
    public static final RegistrySupplier<Block> BLUE_ICE_BRICK_WALL = BLOCKS.register("blue_ice_brick_wall", BlueIceBrickWall::new);
    public static final RegistrySupplier<Block> BLUE_ICE_BRICK_STAIRS = BLOCKS.register("blue_ice_brick_stairs", BlueIceBrickStairs::new);
    public static final RegistrySupplier<Block> BLUE_ICE_PRESSURE_PLATE = BLOCKS.register("blue_ice_pressure_plate", BlueIcePressurePlate::new);
    public static final RegistrySupplier<Block> BLUE_ICE_BUTTON = BLOCKS.register("blue_ice_button", BlueIceButton::new);

    // MISC
    public static final RegistrySupplier<Block> FREEZER = BLOCKS.register("freezer", Freezer::new);
}
