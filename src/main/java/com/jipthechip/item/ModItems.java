package com.jipthechip.item;

import com.jipthechip.Fermecraft;
import com.jipthechip.RegistryNames;
import com.jipthechip.model.Beer;
import com.jipthechip.model.BeerCategory;
import com.jipthechip.util.ImageUtil;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModItems {

    public static Item HOPS = new Item(getItemSettingsWithRegistryKey(new Item.Settings().maxCount(64), RegistryNames.HOPS));
    public static AlcoholContainerItem MUG = new AlcoholContainerItem(getItemSettingsWithRegistryKey(new Item.Settings().maxCount(1), RegistryNames.MUG), 500);
    public static Item YEAST = new Item(getItemSettingsWithRegistryKey(new Item.Settings().maxCount(64), RegistryNames.YEAST));

    public static Item MILLED_GRAIN = new MilledGrainItem(getItemSettingsWithRegistryKey(new Item.Settings().maxCount(64), RegistryNames.MILLED_GRAIN));

    public static void registerItems(){
        MUG = registerItem(MUG, RegistryNames.MUG);
        YEAST = registerItem(YEAST, RegistryNames.YEAST);
        MILLED_GRAIN = registerItem(MILLED_GRAIN, RegistryNames.MILLED_GRAIN);
        HOPS = registerItem(HOPS, RegistryNames.HOPS);

        ItemGroupEvents.modifyEntriesEvent(FERMECRAFT_ITEM_GROUP_KEY).register(entries -> {

            Beer beer = new Beer(5,500, MUG.getCapacity(),20,40, BeerCategory.LAGER);
            ItemStack mugStack = new ItemStack(MUG);
            mugStack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(List.of(beer.getAbv(),beer.getAmount(),beer.getIbu(),beer.getSrm()), List.of(beer.getBeerCategory().ordinal() != 0), List.of("beer"), List.of(beer.getColor())));
            entries.add(mugStack);

            entries.add(YEAST);
            ItemStack milledGrainStackLight = new ItemStack(MILLED_GRAIN);
            milledGrainStackLight.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(List.of(0.0f), List.of(), List.of("milled_grain"), List.of(ImageUtil.getBeerColor(0.0f))));
            entries.add(milledGrainStackLight);

            ItemStack milledGrainStackMedium = new ItemStack(MILLED_GRAIN);
            milledGrainStackMedium.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(List.of(0.5f), List.of(), List.of("milled_grain"), List.of(ImageUtil.getBeerColor(0.5f))));
            entries.add(milledGrainStackMedium);

            ItemStack milledGrainStackDark = new ItemStack(MILLED_GRAIN);
            milledGrainStackDark.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(List.of(1.0f), List.of(), List.of("milled_grain"), List.of(ImageUtil.getBeerColor(1.0f))));
            entries.add(milledGrainStackDark);
            entries.add(HOPS);
        });
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
