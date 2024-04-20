package com.deku.moreice.world.item.crafting;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.deku.moreice.Main.MOD_ID;

public class ModRecipeType {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, MOD_ID);

    public static Supplier<RecipeType<FreezingRecipe>> FREEZING = RECIPE_TYPES.register("freezing", () -> new RecipeType<FreezingRecipe>() {
        public String toString() {
            return MOD_ID + ":freezing";
        }
    });
}
