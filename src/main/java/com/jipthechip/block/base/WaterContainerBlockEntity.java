package com.jipthechip.block.base;

public interface WaterContainerBlockEntity {

    boolean containsWater();

    void setContainsWater(boolean containsWater);

    public default boolean addWater(){
        if(!containsWater()){
            setContainsWater(true);
            return true;
        }
        return false;
    }

    public default boolean removeWater(){
        if(containsWater()){
            setContainsWater(false);
            return true;
        }
        return false;
    }
}
