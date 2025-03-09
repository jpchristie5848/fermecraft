package com.jipthechip.client.geo.item;

import com.jipthechip.Fermecraft;
import com.jipthechip.RegistryNames;
import com.jipthechip.block.blockentity.FermentingBarrelBlockEntity;
import com.jipthechip.item.FermentingBarrelBlockItem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class FermentingBarrelItemModel extends GeoModel<FermentingBarrelBlockItem> {
    @Override
    public Identifier getModelResource(FermentingBarrelBlockItem animatable,  @Nullable GeoRenderer<FermentingBarrelBlockItem> geoRenderer) {
        return Identifier.of(Fermecraft.MOD_ID, "geo/"+RegistryNames.FERMENTING_BARREL+".geo.json");
    }

    @Override
    public Identifier getTextureResource(FermentingBarrelBlockItem animatable,  @Nullable GeoRenderer<FermentingBarrelBlockItem> geoRenderer) {
        return Identifier.of(Fermecraft.MOD_ID, "textures/block/"+RegistryNames.FERMENTING_BARREL+".png");
    }

    @Override
    public Identifier getAnimationResource(FermentingBarrelBlockItem animatable) {
        return Identifier.of(Fermecraft.MOD_ID, "animation/"+RegistryNames.FERMENTING_BARREL+".animation.json");
    }

    @Override
    public @Nullable RenderLayer getRenderType(FermentingBarrelBlockItem animatable, Identifier texture) {
        return RenderLayer.getEntityTranslucent(texture);
    }

    @Override
    public boolean crashIfBoneMissing() {
        return true;
    }
}
