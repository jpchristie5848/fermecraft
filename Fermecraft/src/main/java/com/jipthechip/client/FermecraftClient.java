package com.jipthechip.client;

import com.jipthechip.RegistryNames;
import com.jipthechip.block.ModBlocks;
import com.jipthechip.block.blockentity.FermentingBarrelBlockEntity;
import com.jipthechip.client.geo.block.FermentingBarrelBlockRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class FermecraftClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

        ModBlocks.blockEntityContainerMap.get(RegistryNames.FERMENTING_BARREL).registerClient();
        ModBlocks.blockEntityContainerMap.get(RegistryNames.MASON_JAR).registerClient();
	}
}