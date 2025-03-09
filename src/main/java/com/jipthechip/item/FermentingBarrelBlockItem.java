package com.jipthechip.item;

import com.jipthechip.client.geo.item.FermentingBarrelItemRenderer;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class FermentingBarrelBlockItem extends BlockItem implements GeoItem {

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public FermentingBarrelBlockItem(Block block, Settings settings) {
        super(block, settings);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private FermentingBarrelItemRenderer renderer;
            @Override
            public GeoItemRenderer<FermentingBarrelBlockItem> getGeoItemRenderer() {
                if(this.renderer == null)
                    this.renderer = new FermentingBarrelItemRenderer();

                return this.renderer;
            }
        });
    }

    @Override
    public Object getRenderProvider() {
        return GeoItem.super.getRenderProvider();
    }
}
