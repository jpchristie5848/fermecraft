package com.jipthechip.client.gui.block;

import com.jipthechip.block.blockentity.QuernBlockEntity;
import com.jipthechip.client.gui.GUITextureDrawContext;
import com.jipthechip.client.gui.TextureCropDirection;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

public class QuernScreen extends InventoryScreenBase<QuernScreenHandler> implements ProcessorScreen<QuernBlockEntity> {

    private static final GUITextureDrawContext PROGRESS_BAR_TEXTURE_CONTEXT = new GUITextureDrawContext(79, 34, 176, 0, 24,17, 256,256);

    public QuernScreen(QuernScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        super.drawBackground(context, delta, mouseX, mouseY);
        drawProgressBar(context, x, y, handler.getBlockEntity(), PROGRESS_BAR_TEXTURE_CONTEXT, TEXTURE, TextureCropDirection.HORIZONTAL_RIGHT);
    }
}
