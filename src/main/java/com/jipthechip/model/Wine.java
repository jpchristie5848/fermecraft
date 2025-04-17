package com.jipthechip.model;

import net.minecraft.item.ItemStack;

import java.util.Optional;

public class Wine extends AbstractAlcohol{
    public Wine(float abv, float amount, float capacity, float saturation) {
        super(abv, amount, capacity, saturation);
    }

    @Override
    public <T extends AbstractAlcohol> float transferToOther(Optional<T> other, float amount, float otherContainerCapacity) {
        return 0;
    }

    @Override
    public boolean isCompatibleWith(AbstractAlcohol other) {
        return false;
    }

    @Override
    public int getColor() {
        return 0;
    }

    @Override
    public Optional<AbstractAlcohol> transferToNew(float amount) {
        return Optional.empty();
    }

}
