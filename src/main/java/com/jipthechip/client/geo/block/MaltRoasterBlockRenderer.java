package com.jipthechip.client.geo.block;

import com.jipthechip.block.blockentity.MaltRoasterBlockEntityGeo;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.layer.FastBoneFilterGeoLayer;
import software.bernie.geckolib.renderer.specialty.DynamicGeoBlockRenderer;

import java.util.List;

public class MaltRoasterBlockRenderer extends DynamicGeoBlockRenderer<MaltRoasterBlockEntityGeo> {

    public MaltRoasterBlockRenderer(GeoModel<MaltRoasterBlockEntityGeo> model) {
        super(model);
        addRenderLayer(new FastBoneFilterGeoLayer<>(this, ()-> List.of("tray","malt"), ((geoBone, maltRoasterBlockEntity, aFloat) -> {
            geoBone.setHidden(!maltRoasterBlockEntity.hasMalt());
        })));
    }
}
