package com.jipthechip.model;

public abstract class AbstractAlcohol {

    protected float abv;
    protected float amount;

    public AbstractAlcohol(float abv, float amount){
        this.amount = amount;
        this.abv = abv;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
