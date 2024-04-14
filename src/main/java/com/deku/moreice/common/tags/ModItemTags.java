package com.deku.moreice.common.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static com.deku.moreice.Main.MOD_ID;

public class ModItemTags {
    public static final TagKey<Item> FREEZER_FUEL_50 = bind("freezer_fuel_50");
    public static final TagKey<Item> FREEZER_FUEL_100 = bind("freezer_fuel_100");
    public static final TagKey<Item> FREEZER_FUEL_200 = bind("freezer_fuel_200");
    public static final TagKey<Item> FREEZER_FUEL_300 = bind("freezer_fuel_300");
    public static final TagKey<Item> FREEZER_FUEL_400 = bind("freezer_fuel_400");
    public static final TagKey<Item> FREEZER_FUEL_500 = bind("freezer_fuel_500");

    private static TagKey<Item> bind(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, name));
    }
}
