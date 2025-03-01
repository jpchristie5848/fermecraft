package com.jipthechip.client;

import com.jipthechip.item.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.render.item.tint.CustomModelDataTintSource;
import net.minecraft.client.render.item.tint.TintSource;

public class ItemModelProvider extends FabricModelProvider {
    public ItemModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        CustomModelDataTintSource tintSource = new CustomModelDataTintSource(0, 0);
        itemModelGenerator.registerWithTintedLayer(ModItems.MUG, null, tintSource);
    }
}
