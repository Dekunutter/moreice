package com.deku.moreice.world.item.crafting;

import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.deku.moreice.Main.MOD_ID;

public class ModRecipeType {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, MOD_ID);

    public static RegistryObject<RecipeType<FreezingRecipe>> FREEZING = RECIPE_TYPES.register("freezing", () -> new RecipeType<FreezingRecipe>() {
        public String toString() {
            return MOD_ID + ":freezing";
        }
    });
}
