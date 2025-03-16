package com.jipthechip.model;

public abstract class AbstractAlcohol {

    protected float abv;
    protected float amount;
    protected float capacity;

    public AbstractAlcohol(float abv, float amount, float capacity){
        this.amount = amount;
        this.abv = abv;
        this.capacity = capacity;
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

    public <T extends AbstractAlcohol> float transferToOther(T other){
        return transferToOther(other, getAmount());
    }

    public float getCapacity(){
        return capacity;
    }

    public abstract <T extends AbstractAlcohol> float  transferToOther(T other, float amount);
}
