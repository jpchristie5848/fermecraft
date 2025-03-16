package com.jipthechip.model;

import com.jipthechip.util.ImageUtil;
import com.jipthechip.util.MathUtil;

import java.awt.*;
import java.util.Optional;

public class Beer extends AbstractAlcohol{

    private final BeerCategory beerCategory;
    float ibu;
    float srm;

    private static final Color LIGHTEST_COLOR = new Color(0xF7F4B3);
    private static final Color DARKEST_COLOR =  new Color(0x240B0B);

    public Beer(float abv, float amount, float capacity, float ibu, float srm, BeerCategory beerCategory) {
        super(abv, amount, capacity);
        this.ibu = ibu;
        this.srm = srm;
        this.beerCategory = beerCategory;
    }

    public float getAbv(){
        return this.abv;
    }

    public float getIbu(){
        return srm;
    }

    public float getSrm(){
        return srm;
    }

    public void setIbu(float ibu) {
        this.ibu = ibu;
    }

    public void setSrm(float srm) {
        this.srm = srm;
    }

    public BeerCategory getBeerCategory(){
        return beerCategory;
    }

    public Optional<BeerType> getBeerType() {
        for (BeerType type : BeerType.values()) {
            if (matchesCriteria(type, ibu, srm, beerCategory)) {
                return Optional.of(type);
            }
        }
        return Optional.empty(); // No match found
    }

    private static boolean matchesCriteria(BeerType type, float ibu, float srm, BeerCategory beerCategory) {
        return switch (type) {
            case BLONDE_ALE -> beerCategory == BeerCategory.ALE && ibu >= 15 && ibu <= 30 && srm >= 4 && srm <= 8;
            case PALE_ALE -> beerCategory == BeerCategory.ALE && ibu >= 30 && ibu <= 45 && srm >= 8 && srm <= 10;
            case INDIAN_PALE_ALE -> beerCategory == BeerCategory.ALE && ibu >= 45 && srm >= 8 && srm <= 10;
            case AMBER_ALE -> beerCategory == BeerCategory.ALE && ibu >= 18 && ibu <= 30 && srm >= 14 && srm <= 22;
            case IRISH_RED_ALE -> beerCategory == BeerCategory.ALE && ibu >= 18 && ibu <= 30 && srm >= 10 && srm <= 14;
            case IMPERIAL_RED_ALE -> beerCategory == BeerCategory.ALE && ibu >= 30 && ibu <= 60 && srm >= 14 && srm <= 22;
            case BLACK_IPA -> beerCategory == BeerCategory.ALE && ibu >= 50 && srm >= 35 && srm <= 40;
            case PORTER -> beerCategory == BeerCategory.ALE && ibu >= 20 && ibu <= 35 && srm >= 23;
            case STOUT -> beerCategory == BeerCategory.ALE && ibu >= 35 && ibu <= 50 && srm >= 30;
            case PILSNER -> beerCategory == BeerCategory.LAGER && ibu >= 30 && ibu <= 45 && srm >= 3 && srm <= 6;
            case KOLSCH -> beerCategory == BeerCategory.LAGER && ibu >= 18 && ibu <= 30 && srm >= 4 && srm <= 7;
            case PALE_LAGER -> beerCategory == BeerCategory.LAGER && ibu >= 8 && ibu <= 18 && srm >= 3 && srm <= 6;
            case MUNICH_HELLES -> beerCategory == BeerCategory.LAGER && ibu >= 15 && ibu <= 30 && srm >= 7 && srm <= 12;
            case MARZEN -> beerCategory == BeerCategory.LAGER && ibu >= 18 && ibu <= 28 && srm >= 12 && srm <= 18;
            case DUNKEL -> beerCategory == BeerCategory.LAGER && ibu >= 15 && ibu <= 25 && srm >= 18 && srm <= 24;
            case SCHWARZBIER -> beerCategory == BeerCategory.LAGER && ibu >= 20 && ibu <= 30 && srm >= 25 && srm <= 40;
            case BALTIC_PORTER -> beerCategory == BeerCategory.LAGER && ibu >= 30 && srm >= 30 && srm <= 40;
        };
    }

    public int getColor(){
        return ImageUtil.getBeerColor((srm / 40));
    }


    @Override
    public <T extends AbstractAlcohol> float transferToOther(T other, float amount) {
        if(other instanceof Beer otherBeer && getBeerCategory() == otherBeer.getBeerCategory()){

            float amountToTransfer = Math.min(getAmount(), other.getCapacity() - other.getAmount());
            setAmount(getAmount() - amountToTransfer);

            // set properties of other using weighted average
            otherBeer.setAbv(MathUtil.getWeightedAverage(getAbv(), otherBeer.getAbv(), amountToTransfer, otherBeer.getAmount()));
            otherBeer.setIbu(MathUtil.getWeightedAverage(getIbu(), otherBeer.getIbu(), amountToTransfer, otherBeer.getAmount()));
            otherBeer.setSrm(MathUtil.getWeightedAverage(getSrm(), otherBeer.getSrm(), amountToTransfer, otherBeer.getAmount()));

            otherBeer.setAmount(other.getAmount() + amountToTransfer);

            return amountToTransfer;
        }
        return 0;
    }
}
