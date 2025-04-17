package com.jipthechip.client.gui.block;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

public class MasherScreen extends BrewContainerScreenBase<MasherScreenHandler>{
    public MasherScreen(MasherScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
}
