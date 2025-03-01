package com.jipthechip.block.base;

import com.jipthechip.Fermecraft;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;

public class GeoBlockItemContainer<T extends BlockItem & GeoItem> {

    private final String registryName;
    private final Block block;

    private final Item.Settings settings;
    private BlockItemFactory<T> blockItemFactory;
    private GeoBlockItemBase.RendererFactory<T> rendererFactory;

    public GeoBlockItemContainer(String registryName, Block block, Item.Settings settings,
                                 BlockItemFactory<T> blockItemFactory,
                                 @Nullable GeoBlockItemBase.RendererFactory<T> rendererFactory) {

        this.registryName = registryName;
        this.block = block;
        this.settings = settings;
        this.blockItemFactory = blockItemFactory;
        this.rendererFactory = rendererFactory;
    }

    public T getBlockItem(){
        return blockItemFactory.create(registryName, block, settings, rendererFactory == null ? this::createBaseItemRenderer : rendererFactory);
    }

    public GeoItemRenderer<T> createBaseItemRenderer(){
        return new GeoItemRenderer<T>(getModel());
    }

    public GeoModel<T> getModel(){
        return new GeoModel<>() {
            @Override
            public Identifier getModelResource(T animatable, @Nullable GeoRenderer<T> geoRenderer) {
                return Identifier.of(Fermecraft.MOD_ID, "geo/" + registryName + ".geo.json");
            }

            @Override
            public Identifier getTextureResource(T animatable, @Nullable GeoRenderer<T> geoRenderer) {
                return Identifier.of(Fermecraft.MOD_ID, "textures/block/" + registryName + ".png");
            }

            @Override
            public Identifier getAnimationResource(T animatable) {
                return Identifier.of(Fermecraft.MOD_ID, "animations/" + registryName + ".animation.json");
            }

            @Override
            public @Nullable RenderLayer getRenderType(T animatable, Identifier texture) {
                return RenderLayer.getEntityTranslucent(texture);
            }
        };
    }

    @FunctionalInterface
    public interface BlockItemFactory<T extends BlockItem & GeoItem> {
        public T create(String registryName, Block block, Item.Settings settings, GeoBlockItemBase.RendererFactory<T> renderer);
    }
}
