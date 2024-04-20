package com.deku.moreice.common.blocks;

import com.deku.moreice.common.blocks.ice.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.deku.moreice.Main.MOD_ID;


public class ModBlockInitializer {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, MOD_ID);

    // ICE
    public static final DeferredHolder<Block, IceStairs> ICE_STAIRS = BLOCKS.register("ice_stairs", IceStairs::new);
    public static final DeferredHolder<Block, IceSlab> ICE_SLAB = BLOCKS.register("ice_slab", IceSlab::new);
    public static final DeferredHolder<Block, IceWall> ICE_WALL = BLOCKS.register("ice_wall", IceWall::new);
    public static final DeferredHolder<Block, IceBricks> ICE_BRICKS = BLOCKS.register("ice_bricks", IceBricks::new);
    public static final DeferredHolder<Block, IceBrickSlab> ICE_BRICK_SLAB = BLOCKS.register("ice_brick_slab", IceBrickSlab::new);
    public static final DeferredHolder<Block, IceBrickWall> ICE_BRICK_WALL = BLOCKS.register("ice_brick_wall", IceBrickWall::new);
    public static final DeferredHolder<Block, IceBrickStairs> ICE_BRICK_STAIRS = BLOCKS.register("ice_brick_stairs", IceBrickStairs::new);
    public static final DeferredHolder<Block, IcePressurePlate> ICE_PRESSURE_PLATE = BLOCKS.register("ice_pressure_plate", IcePressurePlate::new);
    public static final DeferredHolder<Block, IceButton> ICE_BUTTON = BLOCKS.register("ice_button", IceButton::new);

    // PACKED ICE
    public static final DeferredHolder<Block, PackedIceStairs> PACKED_ICE_STAIRS = BLOCKS.register("packed_ice_stairs", PackedIceStairs::new);
    public static final DeferredHolder<Block, PackedIceSlab> PACKED_ICE_SLAB = BLOCKS.register("packed_ice_slab", PackedIceSlab::new);
    public static final DeferredHolder<Block, PackedIceWall> PACKED_ICE_WALL = BLOCKS.register("packed_ice_wall", PackedIceWall::new);
    public static final DeferredHolder<Block, PackedIceBricks> PACKED_ICE_BRICKS = BLOCKS.register("packed_ice_bricks", PackedIceBricks::new);
    public static final DeferredHolder<Block, PackedIceBrickSlab> PACKED_ICE_BRICK_SLAB = BLOCKS.register("packed_ice_brick_slab", PackedIceBrickSlab::new);
    public static final DeferredHolder<Block, PackedIceBrickWall> PACKED_ICE_BRICK_WALL = BLOCKS.register("packed_ice_brick_wall", PackedIceBrickWall::new);
    public static final DeferredHolder<Block, PackedIceBrickStairs> PACKED_ICE_BRICK_STAIRS = BLOCKS.register("packed_ice_brick_stairs", PackedIceBrickStairs::new);
    public static final DeferredHolder<Block, PackedIcePressurePlate> PACKED_ICE_PRESSURE_PLATE = BLOCKS.register("packed_ice_pressure_plate", PackedIcePressurePlate::new);
    public static final DeferredHolder<Block, PackedIceButton> PACKED_ICE_BUTTON = BLOCKS.register("packed_ice_button", PackedIceButton::new);

    // BLUE ICE
    public static final DeferredHolder<Block, BlueIceStairs> BLUE_ICE_STAIRS = BLOCKS.register("blue_ice_stairs", BlueIceStairs::new);
    public static final DeferredHolder<Block, BlueIceSlab> BLUE_ICE_SLAB = BLOCKS.register("blue_ice_slab", BlueIceSlab::new);
    public static final DeferredHolder<Block, BlueIceWall> BLUE_ICE_WALL = BLOCKS.register("blue_ice_wall", BlueIceWall::new);
    public static final DeferredHolder<Block, BlueIceBricks> BLUE_ICE_BRICKS = BLOCKS.register("blue_ice_bricks", BlueIceBricks::new);
    public static final DeferredHolder<Block, BlueIceBrickSlab> BLUE_ICE_BRICK_SLAB = BLOCKS.register("blue_ice_brick_slab", BlueIceBrickSlab::new);
    public static final DeferredHolder<Block, BlueIceBrickWall> BLUE_ICE_BRICK_WALL = BLOCKS.register("blue_ice_brick_wall", BlueIceBrickWall::new);
    public static final DeferredHolder<Block, BlueIceBrickStairs> BLUE_ICE_BRICK_STAIRS = BLOCKS.register("blue_ice_brick_stairs", BlueIceBrickStairs::new);
    public static final DeferredHolder<Block, BlueIcePressurePlate> BLUE_ICE_PRESSURE_PLATE = BLOCKS.register("blue_ice_pressure_plate", BlueIcePressurePlate::new);
    public static final DeferredHolder<Block, BlueIceButton> BLUE_ICE_BUTTON = BLOCKS.register("blue_ice_button", BlueIceButton::new);

    // MISC
    public static final DeferredHolder<Block, Freezer> FREEZER = BLOCKS.register("freezer", () -> new Freezer());
}
