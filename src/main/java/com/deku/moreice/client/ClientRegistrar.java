package com.deku.moreice.client;

import com.deku.moreice.client.gui.screens.inventory.FreezerScreen;
import com.deku.moreice.client.models.geom.ModLayerDefinition;
import com.deku.moreice.client.models.geom.ModModelLayerDefinition;
import com.deku.moreice.client.recipebook.ModRecipeCategories;
import com.deku.moreice.client.renderer.blockentity.FreezerRenderer;
import com.deku.moreice.common.blockEntities.ModBlockEntityType;
import com.deku.moreice.world.inventory.ModMenuType;
import com.deku.moreice.world.inventory.ModRecipeBookType;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.screens.MenuScreens;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterRecipeBookCategoriesEvent;

public class ClientRegistrar {
    private IEventBus eventBus;

    public ClientRegistrar(IEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void registerClientOnlyEvents() {
        eventBus.addListener(this::doClientStuff);
        eventBus.addListener(this::onRegisterRecipeBookCategories);
        eventBus.addListener(this::onRegisterRenderers);
        eventBus.addListener(this::onRegisterEntityLayers);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenuType.FREEZER.get(), FreezerScreen::new);
        });
    }

    private void onRegisterRecipeBookCategories(RegisterRecipeBookCategoriesEvent event) {
        // TODO: Not sure why this is stillcoming up as an unknown recipe category. Dont think its very important though
        event.registerBookCategories(ModRecipeBookType.FREEZING_RECIPE_TYPE, ImmutableList.of(ModRecipeCategories.FREEZING.get()));
    }

    private void onRegisterRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntityType.FREEZER_BLOCK_ENTITY.get(), FreezerRenderer::new);
    }

    public void onRegisterEntityLayers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayerDefinition.FREEZER, () -> ModLayerDefinition.FREEZER_LAYER);
    }
}
