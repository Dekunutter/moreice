package com.deku.moreice.common.blocks;

import com.deku.moreice.common.blocks.ice.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.deku.moreice.Main.MOD_ID;


public class ModBlockInitializer {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);

    // ICE
    public static final RegistryObject<Block> ICE_STAIRS = BLOCKS.register("ice_stairs", IceStairs::new);
    public static final RegistryObject<Block> ICE_SLAB = BLOCKS.register("ice_slab", IceSlab::new);
    public static final RegistryObject<Block> ICE_WALL = BLOCKS.register("ice_wall", IceWall::new);
    public static final RegistryObject<Block> ICE_BRICKS = BLOCKS.register("ice_bricks", IceBricks::new);
    public static final RegistryObject<Block> ICE_BRICK_SLAB = BLOCKS.register("ice_brick_slab", IceBrickSlab::new);
    public static final RegistryObject<Block> ICE_PRESSURE_PLATE = BLOCKS.register("ice_pressure_plate", IcePressurePlate::new);

    // PACKED ICE
    public static final RegistryObject<Block> PACKED_ICE_STAIRS = BLOCKS.register("packed_ice_stairs", PackedIceStairs::new);
    public static final RegistryObject<Block> PACKED_ICE_SLAB = BLOCKS.register("packed_ice_slab", PackedIceSlab::new);
    public static final RegistryObject<Block> PACKED_ICE_WALL = BLOCKS.register("packed_ice_wall", PackedIceWall::new);
    public static final RegistryObject<Block> PACKED_ICE_BRICKS = BLOCKS.register("packed_ice_bricks", PackedIceBricks::new);
    public static final RegistryObject<Block> PACKED_ICE_BRICK_SLAB = BLOCKS.register("packed_ice_brick_slab", PackedIceBrickSlab::new);
    public static final RegistryObject<Block> PACKED_ICE_PRESSURE_PLATE = BLOCKS.register("packed_ice_pressure_plate", PackedIcePressurePlate::new);

    // BLUE ICE
    public static final RegistryObject<Block> BLUE_ICE_STAIRS = BLOCKS.register("blue_ice_stairs", BlueIceStairs::new);
    public static final RegistryObject<Block> BLUE_ICE_SLAB = BLOCKS.register("blue_ice_slab", BlueIceSlab::new);
    public static final RegistryObject<Block> BLUE_ICE_WALL = BLOCKS.register("blue_ice_wall", BlueIceWall::new);
    public static final RegistryObject<Block> BLUE_ICE_BRICKS = BLOCKS.register("blue_ice_bricks", BlueIceBricks::new);
    public static final RegistryObject<Block> BLUE_ICE_BRICK_SLAB = BLOCKS.register("blue_ice_brick_slab", BlueIceBrickSlab::new);
    public static final RegistryObject<Block> BLUE_ICE_PRESSURE_PLATE = BLOCKS.register("blue_ice_pressure_plate", BlueIcePressurePlate::new);

    // MISC
    public static final RegistryObject<Block> FREEZER = BLOCKS.register("freezer", Freezer::new);
}
