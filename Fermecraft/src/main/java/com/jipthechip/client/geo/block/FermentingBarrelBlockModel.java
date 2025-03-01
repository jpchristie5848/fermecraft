package com.jipthechip.client.geo.block;

import com.jipthechip.Fermecraft;
import com.jipthechip.RegistryNames;
import com.jipthechip.block.blockentity.FermentingBarrelBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class FermentingBarrelBlockModel extends GeoModel<FermentingBarrelBlockEntity> {
    @Override
    public Identifier getModelResource(FermentingBarrelBlockEntity animatable,  @Nullable GeoRenderer<FermentingBarrelBlockEntity> geoRenderer) {
        return Identifier.of(Fermecraft.MOD_ID, "geo/"+RegistryNames.FERMENTING_BARREL+".geo.json");
    }

    @Override
    public Identifier getTextureResource(FermentingBarrelBlockEntity animatable,  @Nullable GeoRenderer<FermentingBarrelBlockEntity> geoRenderer) {
        return Identifier.of(Fermecraft.MOD_ID, "textures/block/"+RegistryNames.FERMENTING_BARREL+".png");
    }

    @Override
    public Identifier getAnimationResource(FermentingBarrelBlockEntity animatable) {
        return Identifier.of(Fermecraft.MOD_ID, "animation/"+RegistryNames.FERMENTING_BARREL+".animation.json");
    }

    @Override
    public @Nullable RenderLayer getRenderType(FermentingBarrelBlockEntity animatable, Identifier texture) {
        return RenderLayer.getEntityTranslucent(texture);
    }

    @Override
    public boolean crashIfBoneMissing() {
        return true;
    }
}
