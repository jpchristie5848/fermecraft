package com.jipthechip.block;

import com.jipthechip.Fermecraft;
import com.jipthechip.RegistryNames;
import com.jipthechip.block.base.GeoBlockBase;
import com.jipthechip.block.base.GeoBlockEntityContainer;
import com.jipthechip.block.base.GeoBlockItemBase;
import com.jipthechip.block.blockentity.MasonJarBlockEntity;
import com.jipthechip.block.blockentity.FermentingBarrelBlockEntity;
import com.jipthechip.block.blockentity.QuernBlockEntity;
import com.jipthechip.client.geo.block.MasonJarBlockRenderer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

import static com.jipthechip.item.ModItems.getItemSettingsWithRegistryKey;

public class ModBlocks {

    public static HashMap<String, Block> blockMap = new HashMap<>();
    public static HashMap<String, BlockEntityType<? extends BlockEntity>> blockEntityMap = new HashMap<>();

    public static HashMap<String, BlockItem> blockItemMap = new HashMap<>();

    public static HashMap<String, GeoBlockEntityContainer> blockEntityContainerMap = new HashMap<>();

    public static void registerBlocksWithoutEntity(){
    }

    public static void registerBlockEntities(){

        blockEntityContainerMap.put(RegistryNames.FERMENTING_BARREL, new GeoBlockEntityContainer<>(RegistryNames.FERMENTING_BARREL, AbstractBlock.Settings.copy(Blocks.BARREL).nonOpaque(),
                new BlockItem.Settings().maxCount(64), GeoBlockBase::new, GeoBlockItemBase::new, FermentingBarrelBlockEntity::new, FermentingBarrelBlockEntity::ticker, null, null));

        blockEntityContainerMap.put(RegistryNames.MASON_JAR, new GeoBlockEntityContainer<>(RegistryNames.MASON_JAR, AbstractBlock.Settings.copy(Blocks.GLASS).nonOpaque(),
                new BlockItem.Settings().maxCount(64), MasonJarBlock::new, GeoBlockItemBase::new, MasonJarBlockEntity::new, MasonJarBlockEntity::ticker, MasonJarBlockRenderer::new, null));

        blockEntityContainerMap.put(RegistryNames.QUERN, new GeoBlockEntityContainer<>(RegistryNames.QUERN, AbstractBlock.Settings.copy(Blocks.BARREL).nonOpaque(),
                new BlockItem.Settings().maxCount(64), QuernBlock::new, GeoBlockItemBase::new, QuernBlockEntity::new, QuernBlockEntity::ticker, null, null));
    }

    private static <T extends BlockEntity> void registerBlockWithEntity(String name, Block block, Item.Settings itemSettings, FabricBlockEntityTypeBuilder.Factory<T> blockEntityTypeFactory, BlockItemFactory blockItemFactory){
        registerBlock(name, block, itemSettings, blockItemFactory);
        registerBlockEntity(name, block, blockEntityTypeFactory);
    }

    private static void registerBlock(String name, Block block, Item.Settings itemSettings, @Nullable BlockItemFactory blockItemFactory){
        blockMap.put(name, Registry.register(Registries.BLOCK, Identifier.of(Fermecraft.MOD_ID, name), block));
        Item.Settings itemSettingsWithRegistryKey = getItemSettingsWithRegistryKey(itemSettings, name);
        blockItemMap.put(name, Registry.register(Registries.ITEM, Identifier.of(Fermecraft.MOD_ID, name),
                blockItemFactory == null ? new BlockItem(block, itemSettingsWithRegistryKey) : blockItemFactory.create(block, itemSettingsWithRegistryKey)));
    }

    private static <T extends BlockEntity> void registerBlockEntity(String name, Block block, FabricBlockEntityTypeBuilder.Factory<T> factory){
        blockEntityMap.put(name, Registry.register(Registries.BLOCK_ENTITY_TYPE, Fermecraft.MOD_ID+":"+name, FabricBlockEntityTypeBuilder.create(factory, block).build(null)));
    }

    public static AbstractBlock.Settings getBlockSettingsWithRegistryKey(AbstractBlock.Settings settings, String name){
        return settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Fermecraft.MOD_ID, name)));
    }

    @FunctionalInterface
    private interface BlockItemFactory<T extends BlockItem>{
        T create(Block block, Item.Settings settings);
    }
}
