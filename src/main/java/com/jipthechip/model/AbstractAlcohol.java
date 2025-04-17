package com.jipthechip.model;

import com.jipthechip.block.base.BrewContainerBlockEntity;
import com.jipthechip.util.DataUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;

import java.util.Optional;

public abstract class AbstractAlcohol {

    protected float abv;
    protected float amount;
    protected float capacity;

    protected float saturation = 0.0f;

    public AbstractAlcohol(float abv, float amount, float capacity, float saturation){
        this.amount = amount;
        this.abv = abv;
        this.capacity = capacity;
        this.saturation = saturation;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setAbv(float abv){
        this.abv = abv;
    }

    public void setSaturation(float saturation){this.saturation = saturation;}
    public float getSaturation(){return saturation;}

    public <T extends AbstractAlcohol> float transferToOther(Optional<T> other, float otherContainerCapacity){
        return transferToOther(other, amount, otherContainerCapacity);
    }

    public float getCapacity(){
        return capacity;
    }

    public abstract <T extends AbstractAlcohol> float  transferToOther(Optional<T> other, float amount, float otherContainerCapacity);

    public abstract boolean isCompatibleWith(AbstractAlcohol other);

    public abstract int getColor();

    public abstract Optional<AbstractAlcohol> transferToNew(float amount);

    @Override
    public String toString() {
        return "AbstractAlcohol{" +
                "abv=" + abv +
                ", amount=" + amount +
                ", capacity=" + capacity +
                ", saturation= " + saturation +
                '}';
    }
}
