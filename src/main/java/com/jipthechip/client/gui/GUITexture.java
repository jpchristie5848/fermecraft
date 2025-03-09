package com.jipthechip.client.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class GUITexture {

    private final Identifier TEXTURE;
    private final int guiPosX;
    private final int guiPosY;

    private final int texturePosX;

    private final int texturePosY;
    private final int textureWidth;
    private final int textureHeight;

    private final int backgroundWidth;
    private final int backgroundHeight;

    public GUITexture(Identifier TEXTURE,
                      int guiPosX, int guiPosY, int texturePosX, int texturePosY, int textureWidth, int textureHeight, int backgroundWidth, int backgroundHeight) {
        this.TEXTURE = TEXTURE;
        this.guiPosX = guiPosX;
        this.guiPosY = guiPosY;
        this.texturePosX = texturePosX;
        this.texturePosY = texturePosY;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.backgroundWidth = backgroundWidth;
        this.backgroundHeight = backgroundHeight;
    }

    public void draw(DrawContext context, int x, int y){
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x+guiPosX, y+guiPosY, texturePosX, texturePosY,  textureWidth, textureHeight, backgroundWidth, backgroundHeight);
    }

    public void draw(DrawContext context, int x, int y, int textureWidth){
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x+guiPosX, y+guiPosY, texturePosX, texturePosY, textureWidth, textureHeight, backgroundWidth, backgroundHeight);
    }
}
