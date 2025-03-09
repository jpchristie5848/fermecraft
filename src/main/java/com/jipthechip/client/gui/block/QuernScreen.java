package com.jipthechip.client.gui.block;

import com.jipthechip.Fermecraft;
import com.jipthechip.client.gui.GUITexture;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.MathHelper;

public class QuernScreen extends HandledScreen<QuernScreenHandler> {

    private static final Identifier TEXTURE = Identifier.of(Fermecraft.MOD_ID, "textures/ui/container/quern.png");
    private static final int progressBarWidth = 22;

    private GUITexture progressBarTexture;

    public QuernScreen(QuernScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        progressBarTexture = new GUITexture(TEXTURE, 79, 34, 176, 0, 24,17, 256,256);
    }

    @Override
    protected void init() {
        super.init();

        this.titleX = (this.backgroundWidth - this.textRenderer.getWidth(this.title))/2;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, this.x, this.y, 0,0, this.backgroundWidth, this.backgroundHeight, 256,256);

        int progressBarSize = MathHelper.ceil((this.handler.getCraftingProgress()/this.handler.getMaxCraftingProgress())*progressBarWidth);

        progressBarTexture.draw(context, x, y, progressBarSize);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
