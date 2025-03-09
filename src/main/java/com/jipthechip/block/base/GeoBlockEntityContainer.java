package com.jipthechip.block.base;

import com.jipthechip.Fermecraft;
import com.jipthechip.block.ModBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;

import static com.jipthechip.block.ModBlocks.*;
import static com.jipthechip.item.ModItems.FERMECRAFT_ITEM_GROUP_KEY;
import static com.jipthechip.item.ModItems.getItemSettingsWithRegistryKey;

public class GeoBlockEntityContainer<B extends BlockWithEntity, I extends BlockItem & GeoItem, E extends BlockEntity & GeoBlockEntity> {
    private final String registryName;
    private final CustomBlockEntityRendererFactory<E> blockEntityRendererFactory;

    FabricBlockEntityTypeBuilder.Factory<? extends BlockEntity> blockEntityFactory;
    private BlockWithEntity block;
    private BlockItem blockItem;

    private GeoModel<E> blockModel;

    public GeoBlockEntityContainer(String registryName, AbstractBlock.Settings blockSettings, Item.Settings itemSettings,
                                   BlockFactory<B> blockFactory, GeoBlockItemContainer.BlockItemFactory<I> blockItemFactory,
                                   FabricBlockEntityTypeBuilder.Factory<E> blockEntityFactory, BlockEntityTicker<E> blockEntityTicker,
                                   @Nullable CustomBlockEntityRendererFactory<E> blockEntityRendererFactory,
                                   @Nullable GeoBlockItemBase.RendererFactory<I> blockItemRendererFactory) {

        this.registryName = registryName;
        this.blockEntityRendererFactory = blockEntityRendererFactory;

        blockSettings = getBlockSettingsWithRegistryKey(blockSettings, registryName);
        itemSettings = getItemSettingsWithRegistryKey(itemSettings, registryName);

        this.block = blockFactory.create(blockSettings, registryName, blockEntityFactory, blockEntityTicker);

        GeoBlockItemContainer<I> blockItemContainer = new GeoBlockItemContainer<>(registryName, block, itemSettings, blockItemFactory, blockItemRendererFactory);

        this.blockItem = blockItemContainer.getBlockItem();
        this.blockEntityFactory = blockEntityFactory;


        blockMap.put(registryName, Registry.register(Registries.BLOCK, Identifier.of(Fermecraft.MOD_ID, registryName), block));
        blockItemMap.put(registryName, Registry.register(Registries.ITEM, Identifier.of(Fermecraft.MOD_ID, registryName), blockItem));
        blockEntityMap.put(registryName, Registry.register(Registries.BLOCK_ENTITY_TYPE, Fermecraft.MOD_ID+":"+registryName, FabricBlockEntityTypeBuilder.create(blockEntityFactory, block).build(null)));

        ItemGroupEvents.modifyEntriesEvent(FERMECRAFT_ITEM_GROUP_KEY).register(entries -> {
            entries.add(ModBlocks.blockItemMap.get(registryName));
        });
    }

    public void registerClient(){
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.blockMap.get(registryName), RenderLayer.getCutout());
        BlockEntityRendererFactories.register((BlockEntityType<E>)ModBlocks.blockEntityMap.get(registryName), blockEntityRendererFactory == null ? this::createBaseBlockRenderer : this::createCustomBlockRenderer);
    }

    public GeoBlockRenderer<E> createBaseBlockRenderer(BlockEntityRendererFactory.Context context){
        return new GeoBlockRenderer<E>(createBaseBlockModel());
    }

    public GeoBlockRenderer<E> createCustomBlockRenderer(BlockEntityRendererFactory.Context context){
        return blockEntityRendererFactory.create(createBaseBlockModel());
    }

    public GeoModel<E> createBaseBlockModel(){
        blockModel = new GeoModel<E>() {
            @Override
            public Identifier getModelResource(E animatable, @Nullable GeoRenderer<E> geoRenderer) {
                return Identifier.of(Fermecraft.MOD_ID, "geo/"+registryName+".geo.json");
            }

            @Override
            public Identifier getTextureResource(E animatable, @Nullable GeoRenderer<E> geoRenderer) {
                return Identifier.of(Fermecraft.MOD_ID, "textures/block/"+registryName+".png");
            }

            @Override
            public Identifier getAnimationResource(E animatable) {
                return Identifier.of(Fermecraft.MOD_ID, "animations/"+registryName+".animation.json");
            }

            @Override
            public @Nullable RenderLayer getRenderType(E animatable, Identifier texture) {
                return RenderLayer.getEntityTranslucent(texture);
            }
        };
        return blockModel;
    }

    public GeoModel<E> getBlockModel() {
        return blockModel;
    }

    @FunctionalInterface
    public interface BlockFactory<T extends BlockWithEntity> {
        public T create(AbstractBlock.Settings settings, String registryName, FabricBlockEntityTypeBuilder.Factory blockEntityFactory, BlockEntityTicker ticker);
    }

    @FunctionalInterface
    public interface CustomBlockEntityRendererFactory<T extends BlockEntity & GeoAnimatable>{

        public GeoBlockRenderer<T> create(GeoModel<T> geoModel);
    }

}
