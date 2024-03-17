package com.deku.moreice.common.ui;

import com.deku.moreice.common.items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.deku.moreice.Main.MOD_ID;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static RegistryObject<CreativeModeTab> MOD_CREATIVE_TAB = CREATIVE_MOD_TABS.register("mod_creative_tab", () -> new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 0).icon(() -> new ItemStack(ModItems.ICE_STAIRS)).title(Component.translatable("More Ice")).displayItems((featureFlags, output) -> {
        // ICE
        output.accept(ModItems.ICE_STAIRS);

        // PACKED ICE
        output.accept(ModItems.PACKED_ICE_STAIRS);

        // BLUE ICE
        output.accept(ModItems.BLUE_ICE_STAIRS);
    }).build());
}
