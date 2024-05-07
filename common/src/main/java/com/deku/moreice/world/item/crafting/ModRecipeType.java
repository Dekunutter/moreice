package com.deku.moreice.world.item.crafting;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;

import static com.deku.moreice.MoreIce.MOD_ID;

public class ModRecipeType {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(MOD_ID, Registries.RECIPE_TYPE);

    public static RegistrySupplier<RecipeType<FreezingRecipe>> FREEZING = RECIPE_TYPES.register("freezing", () -> new RecipeType<FreezingRecipe>() {
        public String toString() {
            return MOD_ID + ":freezing";
        }
    });
}
