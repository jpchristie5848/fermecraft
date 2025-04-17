package com.jipthechip.item;

import com.jipthechip.Fermecraft;
import com.jipthechip.RegistryNames;
import com.jipthechip.model.Beer;
import com.jipthechip.model.BeerCategory;
import com.jipthechip.util.DataUtil;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ModItems {

    public static Item HOPS = new HopsItem(getItemSettingsWithRegistryKey(new Item.Settings().maxCount(64), RegistryNames.HOPS));
    public static AlcoholContainerItem BREW_BUCKET = new AlcoholContainerItem(getItemSettingsWithRegistryKey(new Item.Settings().maxCount(1), RegistryNames.BREW_BUCKET), 1000, Optional.of(Items.BUCKET));
    public static AlcoholContainerItem MUG = new AlcoholContainerItem(getItemSettingsWithRegistryKey(new Item.Settings().maxCount(1), RegistryNames.MUG), 100, Optional.empty());

    public static Item YEAST = new Item(getItemSettingsWithRegistryKey(new Item.Settings().maxCount(64), RegistryNames.YEAST));

    public static Item MILLED_MALT = new MilledMaltItem(getItemSettingsWithRegistryKey(new Item.Settings().maxCount(64), RegistryNames.MILLED_MALT));
    public static Item MALT = new MaltItem(getItemSettingsWithRegistryKey(new Item.Settings().maxCount(64), RegistryNames.MALT));
    public static void registerItems(){
        MUG = registerItem(MUG, RegistryNames.MUG);
        YEAST = registerItem(YEAST, RegistryNames.YEAST);
        MILLED_MALT = registerItem(MILLED_MALT, RegistryNames.MILLED_MALT);
        HOPS = registerItem(HOPS, RegistryNames.HOPS);
        MALT = registerItem(MALT, RegistryNames.MALT);
        BREW_BUCKET = registerItem(BREW_BUCKET, RegistryNames.BREW_BUCKET);

        ItemGroupEvents.modifyEntriesEvent(FERMECRAFT_ITEM_GROUP_KEY).register(entries -> {

            entries.add(MUG);
            addItemEntryWithComponent(entries, MUG, DataUtil.alcoholToComponent(Optional.of(new Beer(5,100, MUG.getCapacity(),20,40, 50, BeerCategory.LAGER, 1.0f))).get());
            addItemEntryWithComponent(entries, BREW_BUCKET, DataUtil.alcoholToComponent(Optional.of(new Beer(5,1000, 1000,20,40, 50, BeerCategory.LAGER, 1.0f))).get());
            entries.add(MUG);

            entries.add(YEAST);


            addItemEntryWithComponent(entries, MALT, MaltItem.createComponent(0.0f));

            addItemEntryWithComponent(entries, MALT, MaltItem.createComponent(0.5f));

            addItemEntryWithComponent(entries, MALT, MaltItem.createComponent(1.0f));

            addItemEntryWithComponent(entries, MILLED_MALT, MilledMaltItem.createComponent(0.0f));

            addItemEntryWithComponent(entries, MILLED_MALT, MilledMaltItem.createComponent(0.5f));

            addItemEntryWithComponent(entries, MILLED_MALT, MilledMaltItem.createComponent(1.0f));

            addItemEntryWithComponent(entries, HOPS, HopsItem.createComponent(0.0f));

            addItemEntryWithComponent(entries, HOPS, HopsItem.createComponent(0.5f));

            addItemEntryWithComponent(entries, HOPS, HopsItem.createComponent(1.0f));

        });
    }

    private static void addItemEntryWithComponent(FabricItemGroupEntries entries, Item item, CustomModelDataComponent component){
        ItemStack stack = createItemStackWithComponent(item, component);
        entries.add(stack);
    }

    public static ItemStack createItemStackWithComponent(Item item, int count, CustomModelDataComponent component){
        ItemStack stack = new ItemStack(item, count);
        stack.set(DataComponentTypes.CUSTOM_MODEL_DATA, component);
        return stack;
    }

    public static ItemStack createItemStackWithComponent(Item item, CustomModelDataComponent component){
        return createItemStackWithComponent(item, 1, component);
    }

    public static <T extends Item> T registerItem(T item, String registryName){
        return Registry.register(Registries.ITEM, Identifier.of(Fermecraft.MOD_ID, registryName), item);
    }

    public static final RegistryKey<ItemGroup> FERMECRAFT_ITEM_GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(Fermecraft.MOD_ID, "fermecraft"));

    private static final ItemGroup FERMECRAFT_ITEM_GROUP = FabricItemGroup.builder()
            .displayName(Text.literal("Fermecraft"))
            .icon(()->new ItemStack(MUG))
            .build();

    public static void registerItemGroups(){
        Registry.register(Registries.ITEM_GROUP, FERMECRAFT_ITEM_GROUP_KEY, FERMECRAFT_ITEM_GROUP);
    }

    public static Item.Settings getItemSettingsWithRegistryKey(Item.Settings settings, String name){
        return settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Fermecraft.MOD_ID, name)));
    }
}
