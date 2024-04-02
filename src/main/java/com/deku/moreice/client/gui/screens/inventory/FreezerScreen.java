package com.deku.moreice.client.gui.screens.inventory;

import com.deku.moreice.world.inventory.FreezerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.deku.moreice.Main.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class FreezerScreen extends AbstractContainerScreen<FreezerMenu> {
    //  TODO: In 1.20.1 the texture is all in one file so I'll need to combine my seperate textures into one and update the rendering logic to match the original
    private static final ResourceLocation TEXTURE = new ResourceLocation(MOD_ID, "textures/gui/container/freezer.png");

    public FreezerScreen(FreezerMenu menu, Inventory inventory, Component networkComponent) {
        super(menu, inventory, networkComponent);
    }

    public void render(GuiGraphics graphics, int x, int y, float z) {
        this.renderBackground(graphics);
        super.render(graphics, x, y, z);
        this.renderTooltip(graphics, x, y);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float x, int y, int z) {
        int i = leftPos;
        int j = topPos;
        graphics.blit(TEXTURE, i, j, 0, 0, imageWidth, imageHeight);
        if (menu.isCooling()) {
            int k = 14;
            int l = Mth.ceil(menu.getProgress() * 13.0F) + 1;
            graphics.blit(TEXTURE, i + 56, j + 36 + 12 - l, 176, 12 - l, k, l + 1);
        }

        int i1 = 24;
        int j1 = Mth.ceil(menu.getFreezingProgress() * 24.0F);
        graphics.blit(TEXTURE, i + 79, j + 34, 176, 14, j1 + 1, 16);
    }

    public boolean isPauseScreen() {
        return false;
    }

    public void removed() {
        if (minecraft.player != null) {
            menu.removed(minecraft.player);
        }
    }

    public void onClose() {
        minecraft.player.closeContainer();
        super.onClose();
    }
}
