package com.jipthechip.client.geo.block;

import com.jipthechip.block.blockentity.FermentingBarrelBlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.specialty.DynamicGeoBlockRenderer;

public class FermentingBarrelBlockRenderer extends GeoBlockRenderer<FermentingBarrelBlockEntity> {
    public FermentingBarrelBlockRenderer(BlockEntityRendererFactory.Context context) {
        super(new FermentingBarrelBlockModel());
    }
}
