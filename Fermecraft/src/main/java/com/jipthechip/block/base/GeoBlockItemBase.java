package com.jipthechip.block.base;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class GeoBlockItemBase extends BlockItem implements GeoItem{

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private String registryName;

    private final RendererFactory rendererFactory;

    public GeoBlockItemBase(String registryName, Block block, Item.Settings settings, RendererFactory rendererFactory) {
        super(block, settings);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
        this.registryName = registryName;
        this.rendererFactory = rendererFactory;
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
            private GeoItemRenderer renderer;
            @Override
            public GeoItemRenderer<? extends GeoBlockItemBase> getGeoItemRenderer() {
                if(this.renderer == null)
                    this.renderer = rendererFactory.create();

                return this.renderer;
            }
        });
    }

    @Override
    public Object getRenderProvider() {
        return GeoItem.super.getRenderProvider();
    }

    @FunctionalInterface
    public interface RendererFactory<T extends BlockItem & GeoItem> {
        public GeoItemRenderer<T> create();
    }
}
