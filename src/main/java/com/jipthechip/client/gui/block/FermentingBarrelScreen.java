package com.jipthechip.client.gui.block;

import com.jipthechip.client.gui.GUITextureDrawContext;
import com.jipthechip.client.gui.TextureCropDirection;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

public class FermentingBarrelScreen extends ProcessorScreenBase<FermentingBarrelScreenHandler>{


    private static final GUITextureDrawContext PROGRESS_BAR_TEXTURE_CONTEXT = new GUITextureDrawContext(104, 23, 176, 0, 22,43, 256,256);

    public FermentingBarrelScreen(FermentingBarrelScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, PROGRESS_BAR_TEXTURE_CONTEXT, TextureCropDirection.VERTICAL_UP);
    }
}
