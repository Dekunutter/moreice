package com.deku.moreice.neoforge.client;

import com.deku.moreice.client.MoreIceClient;
import com.deku.moreice.client.gui.screens.inventory.FreezerScreen;
import com.deku.moreice.neoforge.client.recipebook.ModRecipeCategories;
import com.deku.moreice.neoforge.world.inventory.ModRecipeBookType;
import com.deku.moreice.world.inventory.ModMenuType;
import com.google.common.collect.ImmutableList;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import static com.deku.moreice.MoreIce.MOD_ID;

@EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientRegistrar {
    private IEventBus eventBus;

    public ClientRegistrar(IEventBus eventBus) {
        this.eventBus = eventBus;
    }

    @SubscribeEvent
    public static void onClientSetup(RegisterEvent event) {
        MoreIceClient.preInitClient();
    }

    public void registerClientOnlyEvents() {
        eventBus.addListener(this::onRegisterRecipeBookCategories);
        eventBus.addListener(this::onRegisterMenuScreens);
    }
    private void onRegisterRecipeBookCategories(RegisterRecipeBookCategoriesEvent event) {
        // TODO: Not sure why this is stillcoming up as an unknown recipe category. Dont think its very important though
        event.registerBookCategories(ModRecipeBookType.FREEZING_RECIPE_TYPE, ImmutableList.of(ModRecipeCategories.FREEZING.get()));
    }

    private void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuType.FREEZER.get(), FreezerScreen::new);
    }
}
