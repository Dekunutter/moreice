package com.deku.moreice.world.item.crafting;

import com.deku.moreice.common.blocks.ModBlocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class FreezingRecipe extends AbstractCookingRecipe {
    public FreezingRecipe(String name, CookingBookCategory category, Ingredient ingredient, ItemStack item, float f, int i) {
        super(ModRecipeType.FREEZING.get(), name, category, ingredient, item, f, i);
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.FREEZER.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializer.FREEZING_RECIPE.get();
    }
}
