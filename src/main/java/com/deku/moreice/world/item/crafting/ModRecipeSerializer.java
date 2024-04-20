package com.deku.moreice.world.item.crafting;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.deku.moreice.Main.MOD_ID;

public class ModRecipeSerializer {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, MOD_ID);

    public static Supplier<RecipeSerializer<FreezingRecipe>> FREEZING_RECIPE = RECIPE_SERIALIZERS.register("freezing", () -> new SimpleCookingSerializer<>(FreezingRecipe::new, 100));
}
