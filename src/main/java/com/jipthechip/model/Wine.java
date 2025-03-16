package com.jipthechip.model;

public class Wine extends AbstractAlcohol{
    public Wine(float abv, float amount, float capacity) {
        super(abv, amount, capacity);
    }

    @Override
    public <T extends AbstractAlcohol> float transferToOther(T other, float amount) {
        return 0;
    }
}
