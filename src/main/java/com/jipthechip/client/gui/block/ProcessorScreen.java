package com.jipthechip.client.gui.block;

import com.jipthechip.block.base.GeoBlockEntityBase;
import com.jipthechip.client.gui.GUITextureDrawContext;
import com.jipthechip.client.gui.TextureCropDirection;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public interface ProcessorScreen<T extends GeoBlockEntityBase & Inventory> {

    default void drawProgressBar(DrawContext context, int x, int y, T blockEntity, GUITextureDrawContext progressBarTextureContext, Identifier texture, TextureCropDirection textureCropDirection) {

        float progress = getProgress(blockEntity);

        int progressBarSize;

        if(textureCropDirection == TextureCropDirection.HORIZONTAL_LEFT || textureCropDirection == TextureCropDirection.HORIZONTAL_RIGHT){
            progressBarSize = MathHelper.ceil(progress*progressBarTextureContext.getWidth());
        }else{
            progressBarSize = MathHelper.ceil(progress*progressBarTextureContext.getHeight());
        }

        progressBarTextureContext.drawCropped(context, texture, x, y, progressBarSize, textureCropDirection);
    }

    default float getProgress(T blockEntity){
        return blockEntity.getCraftingProgress()/blockEntity.getMaxCraftingProgress();
    }


}
