package com.jipthechip.util;

import com.jipthechip.item.HopsItem;
import com.jipthechip.item.MilledMaltItem;
import com.jipthechip.model.AbstractAlcohol;
import com.jipthechip.model.Beer;
import com.jipthechip.model.Wine;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataUtil {


    public static Optional<? extends AbstractAlcohol> componentToAlcohol(CustomModelDataComponent component, float containerCapacity){

        if(component == null){
            return Optional.empty();
        }

        String label = component.getString(0);

        switch(label){
            case "beer" :
                return Beer.fromComponent(component, containerCapacity);
            case "wine" :
                // TODO
                return Optional.empty();
            case "liquor" :
                // TODO
                return Optional.empty();
            case "milled_malt":
                return MilledMaltItem.alcoholFromComponent(component, containerCapacity);
            case "hops":
                return HopsItem.alcoholFromComponent(component, containerCapacity);
            case null:
                return Optional.empty();
            default :
                return Optional.empty();
        }
    }

    public static Optional<CustomModelDataComponent> alcoholToComponent(Optional<AbstractAlcohol> alcoholOptional){
        CustomModelDataComponent component;

        if(alcoholOptional.isPresent()){
            if(alcoholOptional.get() instanceof Beer beer){
                component = new CustomModelDataComponent(List.of(beer.getAbv(), beer.getAmount(), beer.getIbu(), beer.getSrm(), beer.getFermentableSugars(), (float)beer.getBeerCategory().ordinal(), beer.getSaturation()), List.of(), List.of("beer"), List.of(beer.getColor()));
                return Optional.of(component);
            }if(alcoholOptional.get() instanceof Wine wine){
                // TODO
            }
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
