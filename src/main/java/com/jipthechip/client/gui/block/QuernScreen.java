package com.jipthechip.client.gui.block;

import com.jipthechip.client.gui.GUITextureDrawContext;
import com.jipthechip.client.gui.TextureCropDirection;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

public class QuernScreen extends ProcessorScreenBase<QuernScreenHandler> {

    private static final GUITextureDrawContext PROGRESS_BAR_TEXTURE_CONTEXT = new GUITextureDrawContext(79, 34, 176, 0, 24,17, 256,256);

    public QuernScreen(QuernScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, PROGRESS_BAR_TEXTURE_CONTEXT, TextureCropDirection.HORIZONTAL_RIGHT);
    }
}
