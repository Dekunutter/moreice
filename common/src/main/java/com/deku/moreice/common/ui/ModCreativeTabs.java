package com.deku.moreice.common.ui;

import com.deku.moreice.common.items.ModItems;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import static com.deku.moreice.MoreIce.MOD_ID;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TABS = DeferredRegister.create(MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static RegistrySupplier<CreativeModeTab> MOD_CREATIVE_TAB = CREATIVE_MOD_TABS.register("mod_creative_tab", () -> new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 0).icon(() -> new ItemStack(ModItems.ICE_STAIRS.get())).title(Component.translatable("More Ice")).displayItems((featureFlags, output) -> {
        // ICE
        output.accept(ModItems.ICE_STAIRS.get());
        output.accept(ModItems.ICE_SLAB.get());
        output.accept(ModItems.ICE_WALL.get());
        output.accept(ModItems.ICE_BRICKS.get());
        output.accept(ModItems.ICE_BRICK_SLAB.get());
        output.accept(ModItems.ICE_BRICK_WALL.get());
        output.accept(ModItems.ICE_BRICK_STAIRS.get());
        output.accept(ModItems.ICE_PRESSURE_PLATE.get());
        output.accept(ModItems.ICE_BUTTON.get());
        output.accept(ModItems.CHISELED_ICE.get());

        // PACKED ICE
        output.accept(ModItems.PACKED_ICE_STAIRS.get());
        output.accept(ModItems.PACKED_ICE_SLAB.get());
        output.accept(ModItems.PACKED_ICE_WALL.get());
        output.accept(ModItems.PACKED_ICE_BRICKS.get());
        output.accept(ModItems.PACKED_ICE_BRICK_SLAB.get());
        output.accept(ModItems.PACKED_ICE_BRICK_WALL.get());
        output.accept(ModItems.PACKED_ICE_BRICK_STAIRS.get());
        output.accept(ModItems.PACKED_ICE_PRESSURE_PLATE.get());
        output.accept(ModItems.PACKED_ICE_BUTTON.get());
        output.accept(ModItems.CHISELED_PACKED_ICE.get());

        // BLUE ICE
        output.accept(ModItems.BLUE_ICE_STAIRS.get());
        output.accept(ModItems.BLUE_ICE_SLAB.get());
        output.accept(ModItems.BLUE_ICE_WALL.get());
        output.accept(ModItems.BLUE_ICE_BRICKS.get());
        output.accept(ModItems.BLUE_ICE_BRICK_SLAB.get());
        output.accept(ModItems.BLUE_ICE_BRICK_WALL.get());
        output.accept(ModItems.BLUE_ICE_BRICK_STAIRS.get());
        output.accept(ModItems.BLUE_ICE_PRESSURE_PLATE.get());
        output.accept(ModItems.BLUE_ICE_BUTTON.get());
        output.accept(ModItems.CHISELED_BLUE_ICE.get());

        //MISC
        output.accept(ModItems.FREEZER.get());
    }).build());
}
