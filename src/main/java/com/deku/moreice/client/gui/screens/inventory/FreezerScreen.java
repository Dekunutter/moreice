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
    private static final ResourceLocation PROGRESS_SPRITE = new ResourceLocation(MOD_ID,"container/freezer/cooling_progress");
    private static final ResourceLocation FREEZING_PROGRESS_SPRITE = new ResourceLocation(MOD_ID, "container/freezer/freezing_progress");
    private static final ResourceLocation TEXTURE = new ResourceLocation(MOD_ID, "textures/gui/container/freezer.png");

    public FreezerScreen(FreezerMenu menu, Inventory inventory, Component networkComponent) {
        super(menu, inventory, networkComponent);
    }

    public void render(GuiGraphics graphics, int x, int y, float z) {
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
            graphics.blitSprite(PROGRESS_SPRITE, 14, 14, 0, 14 - l, i + 56, j + 36 + 14 - l, 14, l);
        }

        int i1 = 24;
        int j1 = Mth.ceil(menu.getFreezingProgress() * 24.0F);
        graphics.blitSprite(FREEZING_PROGRESS_SPRITE, 24, 16, 0, 0, i + 79, j + 34, j1, 16);
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
