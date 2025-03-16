package com.jipthechip.util;

import com.jipthechip.model.Beer;
import com.jipthechip.model.BeerCategory;
import com.jipthechip.model.Wine;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DataUtil {


    public static Optional<Object> deserializeComponent(CustomModelDataComponent component, float containerCapacity){

        if(component == null){
            return Optional.empty();
        }

        String label = component.getString(0);

        switch(label){
            case "beer" :
                float abv = Objects.requireNonNull(component.getFloat(0));
                float amount = Objects.requireNonNull(component.getFloat(1));
                float ibu = Objects.requireNonNull(component.getFloat(2));
                float srm = Objects.requireNonNull(component.getFloat(3));
                boolean beerCategory = Objects.requireNonNull(component.getFlag(0));
                return Optional.of(new Beer(abv, amount, containerCapacity, ibu, srm, beerCategory ? BeerCategory.ALE : BeerCategory.LAGER));
            case "wine" :
                // TODO
                return Optional.empty();
            case "liquor" :
                // TODO
                return Optional.empty();
            default :
                return Optional.empty();
        }
    }

    public static Optional<CustomModelDataComponent> serializeComponent(Object object){
        CustomModelDataComponent component;
        if(object instanceof Beer beer){
            component = new CustomModelDataComponent(List.of(beer.getAbv(), beer.getAmount(), beer.getIbu(), beer.getSrm()), List.of(beer.getBeerCategory() == BeerCategory.ALE), List.of("beer"), List.of(beer.getColor()));
            return Optional.of(component);
        }if(object instanceof Wine wine){
            // TODO
        }

        return Optional.empty();
    }

    public static void putComponentInNbt(CustomModelDataComponent component, NbtCompound nbt){
        for(int i = 0; i < component.floats().size(); i++){
            nbt.putFloat("float"+i, component.getFloat(i));
        }
        for(int i = 0; i < component.flags().size(); i++){
            nbt.putBoolean("boolean"+i, component.getFlag(i));
        }
        for(int i = 0; i < component.strings().size(); i++){
            nbt.putString("string"+i, component.getString(i));
        }
        for(int i = 0; i < component.colors().size(); i++){
            nbt.putInt("int"+i, component.getColor(i));
        }
    }

    public static CustomModelDataComponent getComponentFromNbt(NbtCompound nbt){
        List<Float> floats = new ArrayList<>();
        List<Boolean> flags = new ArrayList<>();
        List<String> strings = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();

        int index = 0;
        while(nbt.contains("float"+index)){
            floats.add(nbt.getFloat("float"+index));
            index++;
        }
        index = 0;
        while(nbt.contains("boolean"+index)){
            flags.add(nbt.getBoolean("boolean"+index));
            index++;
        }
        index = 0;
        while(nbt.contains("string"+index)){
            strings.add(nbt.getString("string"+index));
            index++;
        }
        index = 0;
        while(nbt.contains("int"+index)){
            colors.add(nbt.getInt("int"+index));
            index++;
        }
        return new CustomModelDataComponent(floats, flags, strings, colors);
    }
}
