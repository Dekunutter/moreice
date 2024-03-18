package com.deku.moreice.common.items;

import net.minecraft.world.item.*;
import net.minecraftforge.registries.ObjectHolder;

import static com.deku.moreice.Main.MOD_ID;

public class ModItems {
    // ICE
    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":ice_stairs")
    public static BlockItem ICE_STAIRS;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":ice_slab")
    public static BlockItem ICE_SLAB;

    // PACKED ICE
    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":packed_ice_stairs")
    public static BlockItem PACKED_ICE_STAIRS;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":packed_ice_slab")
    public static BlockItem PACKED_ICE_SLAB;

    // BLUE ICE
    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":blue_ice_stairs")
    public static BlockItem BLUE_ICE_STAIRS;

    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":blue_ice_slab")
    public static BlockItem BLUE_ICE_SLAB;
}
