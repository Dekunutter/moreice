package com.deku.moreice.client;

import com.deku.moreice.client.gui.screens.inventory.FreezerScreen;
import com.deku.moreice.world.inventory.ModMenuType;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientRegistrar {
    private IEventBus eventBus;

    public ClientRegistrar(IEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void registerClientOnlyEvents() {
        eventBus.addListener(this::doClientStuff);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenuType.FREEZER.get(), FreezerScreen::new);
        });
    }
}
