package com.jipthechip.client;

import com.jipthechip.block.ModBlocks;
import com.jipthechip.client.gui.ModGUIs;
import com.jipthechip.client.gui.block.QuernScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class FermecraftClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

        for(String registryName : ModBlocks.blockEntityContainerMap.keySet()){
            ModBlocks.blockEntityContainerMap.get(registryName).registerClient();
        }

        HandledScreens.register(ModGUIs.QUERN_SCREEN_HANDLER, QuernScreen::new);
	}
}