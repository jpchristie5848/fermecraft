package com.jipthechip.client.gui.block;

import com.jipthechip.block.blockentity.MaltRoasterBlockEntityGeo;
import com.jipthechip.client.gui.GUITextureDrawContext;
import com.jipthechip.client.gui.TextureCropDirection;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

public class MaltRoasterScreen extends InventoryScreenBase<MaltRoasterScreenHandler> implements ProcessorScreen<MaltRoasterBlockEntityGeo>{

    private static final GUITextureDrawContext PROGRESS_BAR_TEXTURE_CONTEXT = new GUITextureDrawContext(81, 37, 176, 0, 14,14, 256,256);

    public MaltRoasterScreen(MaltRoasterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        super.drawBackground(context, delta, mouseX, mouseY);
        drawProgressBar(context, x, y, handler.getBlockEntity(), PROGRESS_BAR_TEXTURE_CONTEXT, TEXTURE, TextureCropDirection.VERTICAL_UP);
    }

    public float getProgress(MaltRoasterBlockEntityGeo malterBlockEntity) {
        return malterBlockEntity.getPercentFuelRemaining();
    }
}
