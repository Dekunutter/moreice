package com.deku.moreice.common.items;

import net.minecraft.world.item.*;
import net.minecraftforge.registries.ObjectHolder;

import static com.deku.moreice.Main.MOD_ID;

public class ModItems {
    @ObjectHolder(registryName = "minecraft:item", value = MOD_ID + ":ice_stairs")
    public static BlockItem ICE_STAIRS;
}
