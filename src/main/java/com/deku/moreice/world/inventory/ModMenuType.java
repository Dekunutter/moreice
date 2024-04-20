package com.deku.moreice.world.inventory;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.deku.moreice.Main.MOD_ID;

public class ModMenuType {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, MOD_ID);

    public static final Supplier<MenuType<FreezerMenu>> FREEZER = MENU_TYPES.register("freezer_menu", () -> new MenuType(FreezerMenu::new, FeatureFlags.DEFAULT_FLAGS));
}
