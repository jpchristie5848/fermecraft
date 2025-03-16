package com.jipthechip.client;

import com.jipthechip.block.ModBlocks;
import com.jipthechip.client.gui.ModGUIs;
import net.fabricmc.api.ClientModInitializer;

public class FermecraftClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

        for(String registryName : ModBlocks.blockEntityContainerMap.keySet()){
            ModBlocks.blockEntityContainerMap.get(registryName).registerClient();
        }

        for(String registryName : ModGUIs.screenHandlerContainerMap.keySet()){
            ModGUIs.screenHandlerContainerMap.get(registryName).registerClient();
        }

        //HandledScreens.register(ModGUIs.screenHandlerMap.get(RegistryNames.QUERN), QuernScreen::new);
	}
}