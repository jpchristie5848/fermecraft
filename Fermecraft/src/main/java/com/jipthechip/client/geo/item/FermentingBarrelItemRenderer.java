package com.jipthechip.client.geo.item;

import com.jipthechip.item.FermentingBarrelBlockItem;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class FermentingBarrelItemRenderer extends GeoItemRenderer<FermentingBarrelBlockItem> {
    public FermentingBarrelItemRenderer() {
        super(new FermentingBarrelItemModel());
    }
}
