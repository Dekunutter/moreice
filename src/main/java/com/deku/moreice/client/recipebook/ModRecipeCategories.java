package com.deku.moreice.client.recipebook;

import com.google.common.base.Suppliers;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Supplier;

public class ModRecipeCategories {
    public static final Supplier<RecipeBookCategories> FREEZING = Suppliers.memoize(() -> RecipeBookCategories.create("FREEZING", new ItemStack(Items.ICE)));


}
