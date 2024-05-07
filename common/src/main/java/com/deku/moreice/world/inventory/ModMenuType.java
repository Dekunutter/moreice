package com.deku.moreice.world.inventory;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

import static com.deku.moreice.MoreIce.MOD_ID;


public class ModMenuType {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(MOD_ID, Registries.MENU);

    public static final RegistrySupplier<MenuType<FreezerMenu>> FREEZER = MENU_TYPES.register("freezer_menu", () -> new MenuType<>(FreezerMenu::new, FeatureFlags.VANILLA_SET));
}
