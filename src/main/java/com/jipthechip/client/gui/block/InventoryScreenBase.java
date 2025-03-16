package com.jipthechip.client.gui.block;

import com.jipthechip.Fermecraft;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class InventoryScreenBase<T extends InventoryScreenHandlerBase> extends HandledScreen<T> {
    protected final Identifier TEXTURE;

    public InventoryScreenBase(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        TEXTURE = Identifier.of(Fermecraft.MOD_ID, "textures/ui/container/"+handler.getBlockEntity().getRegistryName()+".png");
    }

    @Override
    protected void init() {
        super.init();
        this.titleX = (this.backgroundWidth - this.textRenderer.getWidth(this.title))/2;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, this.x, this.y, 0,0, this.backgroundWidth, this.backgroundHeight, 256,256);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
