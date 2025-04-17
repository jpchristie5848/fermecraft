package com.jipthechip.client.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class GUITextureDrawContext {

    private final int guiPosX;
    private final int guiPosY;

    private final int texturePosX;

    private final int texturePosY;
    private final int textureWidth;
    private final int textureHeight;

    private final int backgroundWidth;
    private final int backgroundHeight;

    public GUITextureDrawContext(int guiPosX, int guiPosY, int texturePosX, int texturePosY, int textureWidth, int textureHeight, int backgroundWidth, int backgroundHeight) {
        this.guiPosX = guiPosX;
        this.guiPosY = guiPosY;
        this.texturePosX = texturePosX;
        this.texturePosY = texturePosY;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.backgroundWidth = backgroundWidth;
        this.backgroundHeight = backgroundHeight;
    }

    public void draw(DrawContext context, Identifier texture, int x, int y){
        context.drawTexture(RenderLayer::getGuiTextured, texture, x+guiPosX, y+guiPosY, texturePosX, texturePosY,  textureWidth, textureHeight, backgroundWidth, backgroundHeight);
    }

    public void drawCropped(DrawContext context, Identifier texture, int x, int y, int croppedLength, TextureCropDirection textureCropDirection){
        switch(textureCropDirection){
            case HORIZONTAL_RIGHT -> {
                context.drawTexture(RenderLayer::getGuiTextured, texture, x+guiPosX, y+guiPosY, texturePosX, texturePosY, croppedLength, textureHeight, backgroundWidth, backgroundHeight);
            }
            case HORIZONTAL_LEFT -> {
                context.drawTexture(RenderLayer::getGuiTextured, texture, x+guiPosX, y+guiPosY, texturePosX+(textureWidth - croppedLength), texturePosY, croppedLength, textureHeight, backgroundWidth, backgroundHeight);
            }
            case VERTICAL_UP -> {
                context.drawTexture(RenderLayer::getGuiTextured, texture, x+guiPosX, y+guiPosY+(textureHeight - croppedLength), texturePosX, texturePosY+(textureHeight - croppedLength), textureWidth, croppedLength, backgroundWidth, backgroundHeight);
            }
            case VERTICAL_DOWN -> {
                context.drawTexture(RenderLayer::getGuiTextured, texture, x+guiPosX, y+guiPosY, texturePosX, texturePosY, textureWidth, croppedLength, backgroundWidth, backgroundHeight);
            }
        }
    }

    public float getWidth() {
        return textureWidth;
    }

    public float getHeight() {
        return textureHeight;
    }
}
