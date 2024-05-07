package com.deku.moreice.world.item.crafting;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;

import static com.deku.moreice.MoreIce.MOD_ID;

public class ModRecipeSerializer {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(MOD_ID, Registries.RECIPE_SERIALIZER);
    public static RegistrySupplier<RecipeSerializer<FreezingRecipe>> FREEZING_RECIPE = RECIPE_SERIALIZERS.register("freezing", () -> new SimpleCookingSerializer<>(FreezingRecipe::new, 100));
}
