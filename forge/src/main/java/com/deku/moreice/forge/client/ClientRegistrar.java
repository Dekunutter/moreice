package com.deku.moreice.forge.client;

import com.deku.moreice.client.MoreIceClient;
import com.deku.moreice.forge.client.recipebook.ModRecipeCategories;
import com.deku.moreice.forge.world.inventory.ModRecipeBookType;
import com.google.common.collect.ImmutableList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegisterEvent;

import static com.deku.moreice.MoreIce.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistrar {
    private IEventBus eventBus;

    public ClientRegistrar(IEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void registerClientOnlyEvents() {
        eventBus.addListener(this::doClientStuff);
        eventBus.addListener(this::onRegisterRecipeBookCategories);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(MoreIceClient::initClient);
    }

    @SubscribeEvent
    public static void onClientSetup(RegisterEvent event) {
        MoreIceClient.preInitClient();
    }

    private void onRegisterRecipeBookCategories(RegisterRecipeBookCategoriesEvent event) {
        // TODO: Not sure why this is stillcoming up as an unknown recipe category. Dont think its very important though
        event.registerBookCategories(ModRecipeBookType.FREEZING_RECIPE_TYPE, ImmutableList.of(ModRecipeCategories.FREEZING.get()));
    }
}
