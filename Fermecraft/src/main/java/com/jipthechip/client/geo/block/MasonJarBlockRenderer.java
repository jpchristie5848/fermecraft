package com.jipthechip.client.geo.block;

import com.jipthechip.block.blockentity.MasonJarBlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.item.ItemStack;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.layer.FastBoneFilterGeoLayer;
import software.bernie.geckolib.renderer.specialty.DynamicGeoBlockRenderer;

import java.util.List;

public class MasonJarBlockRenderer extends DynamicGeoBlockRenderer<MasonJarBlockEntity> {
    public MasonJarBlockRenderer(GeoModel<MasonJarBlockEntity> model) {
        super(model);
        addRenderLayer(new FastBoneFilterGeoLayer<>(this, ()-> List.of("lid"), ((geoBone, masonJarBlockEntity, aFloat) -> {
            geoBone.setHidden(!masonJarBlockEntity.isCovered());
        })));

        addRenderLayer(new FastBoneFilterGeoLayer<>(this, ()-> List.of("culture"), ((geoBone, masonJarBlockEntity, aFloat) -> {
            geoBone.setHidden(!masonJarBlockEntity.hasRecipe() && !masonJarBlockEntity.hasYeast());
        })));
    }


}
