package com.jipthechip.client.gui.block;

import com.jipthechip.block.blockentity.FermentingBarrelBlockEntity;
import com.jipthechip.client.gui.GUITextureDrawContext;
import com.jipthechip.client.gui.TextureCropDirection;
import com.jipthechip.model.AbstractAlcohol;
import com.jipthechip.model.Beer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import com.jipthechip.util.MathUtil;
import net.minecraft.text.TextContent;

import java.util.ArrayList;
import java.util.List;

public class FermentingBarrelScreen extends BrewContainerScreenBase<FermentingBarrelScreenHandler> implements ProcessorScreen<FermentingBarrelBlockEntity>{


    private static final GUITextureDrawContext PROGRESS_BAR_TEXTURE_CONTEXT = new GUITextureDrawContext(104, 23, 176, 0, 22,43, 256,256);

    public FermentingBarrelScreen(FermentingBarrelScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        super.drawBackground(context, delta, mouseX, mouseY);
        drawProgressBar(context, x, y, handler.getBlockEntity(), PROGRESS_BAR_TEXTURE_CONTEXT, TEXTURE, TextureCropDirection.VERTICAL_UP);
    }
}
