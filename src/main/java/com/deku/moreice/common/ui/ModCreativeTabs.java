package com.deku.moreice.common.ui;

import com.deku.moreice.common.items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.deku.moreice.Main.MOD_ID;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static Supplier<CreativeModeTab> MOD_CREATIVE_TAB = CREATIVE_MOD_TABS.register("mod_creative_tab", () -> new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 0).icon(() -> new ItemStack(ModItems.ICE_STAIRS.get())).title(Component.translatable("More Ice")).displayItems((featureFlags, output) -> {
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

        //MISC
        output.accept(ModItems.FREEZER.get());
    }).build());
}
