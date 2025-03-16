package com.jipthechip.client.gui.block;

import com.jipthechip.client.gui.GUITextureDrawContext;
import com.jipthechip.client.gui.TextureCropDirection;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class ProcessorScreenBase<T extends InventoryScreenHandlerBase> extends InventoryScreenBase<T>{

    private final GUITextureDrawContext progressBarTexture;
    private final TextureCropDirection textureCropDirection;

    public ProcessorScreenBase(T handler, PlayerInventory inventory, Text title, GUITextureDrawContext progressBarTexture, TextureCropDirection textureCropDirection) {
        super(handler, inventory, title);
        this.progressBarTexture = progressBarTexture;
        this.textureCropDirection = textureCropDirection;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        super.drawBackground(context, delta, mouseX, mouseY);

        float progress = this.handler.getBlockEntity().getCraftingProgress()/this.handler.getBlockEntity().getMaxCraftingProgress();
        int progressBarSize;

        if(textureCropDirection == TextureCropDirection.HORIZONTAL_LEFT || textureCropDirection == TextureCropDirection.HORIZONTAL_RIGHT){
            progressBarSize = MathHelper.ceil(progress*progressBarTexture.getWidth());
        }else{
            progressBarSize = MathHelper.ceil(progress*progressBarTexture.getHeight());
        }

        progressBarTexture.drawCropped(context, TEXTURE, x, y, progressBarSize, textureCropDirection);
    }
}
