package com.jipthechip.model;

import com.jipthechip.util.ImageUtil;

import java.awt.*;

public class Beer extends AbstractAlcohol{

    private final BeerType beerType;
    float ibu;
    float srm;

    private static final Color LIGHTEST_COLOR = new Color(0xF7F4B3);
    private static final Color DARKEST_COLOR =  new Color(0x240B0B);

    public Beer(float abv, float amount, float ibu, float srm, BeerType beerType) {
        super(abv, amount);
        this.ibu = ibu;
        this.srm = srm;
        this.beerType = beerType;
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

    public BeerType getBeerType(){
        return beerType;
    }

    public AlcoholType getAlcoholType() {
        for (AlcoholType type : AlcoholType.values()) {
            if (matchesCriteria(type, ibu, srm, beerType)) {
                return type;
            }
        }
        return null; // No match found
    }

    private static boolean matchesCriteria(AlcoholType type, float ibu, float srm, BeerType beerType) {
        return switch (type) {
            case BLONDE_ALE -> beerType == BeerType.ALE && ibu >= 15 && ibu <= 30 && srm >= 4 && srm <= 8;
            case PALE_ALE -> beerType == BeerType.ALE && ibu >= 30 && ibu <= 45 && srm >= 8 && srm <= 10;
            case INDIAN_PALE_ALE -> beerType == BeerType.ALE && ibu >= 45 && srm >= 8 && srm <= 10;
            case AMBER_ALE -> beerType == BeerType.ALE && ibu >= 18 && ibu <= 30 && srm >= 14 && srm <= 22;
            case IRISH_RED_ALE -> beerType == BeerType.ALE && ibu >= 18 && ibu <= 30 && srm >= 10 && srm <= 14;
            case IMPERIAL_RED_ALE -> beerType == BeerType.ALE && ibu >= 30 && ibu <= 60 && srm >= 14 && srm <= 22;
            case BLACK_IPA -> beerType == BeerType.ALE && ibu >= 50 && srm >= 35 && srm <= 40;
            case PORTER -> beerType == BeerType.ALE && ibu >= 20 && ibu <= 35 && srm >= 23;
            case STOUT -> beerType == BeerType.ALE && ibu >= 35 && ibu <= 50 && srm >= 30;
            case PILSNER -> beerType == BeerType.LAGER && ibu >= 30 && ibu <= 45 && srm >= 3 && srm <= 6;
            case KOLSCH -> beerType == BeerType.LAGER && ibu >= 18 && ibu <= 30 && srm >= 4 && srm <= 7;
            case PALE_LAGER -> beerType == BeerType.LAGER && ibu >= 8 && ibu <= 18 && srm >= 3 && srm <= 6;
            case MUNICH_HELLES -> beerType == BeerType.LAGER && ibu >= 15 && ibu <= 30 && srm >= 7 && srm <= 12;
            case MARZEN -> beerType == BeerType.LAGER && ibu >= 18 && ibu <= 28 && srm >= 12 && srm <= 18;
            case DUNKEL -> beerType == BeerType.LAGER && ibu >= 15 && ibu <= 25 && srm >= 18 && srm <= 24;
            case SCHWARZBIER -> beerType == BeerType.LAGER && ibu >= 20 && ibu <= 30 && srm >= 25 && srm <= 40;
            case BALTIC_PORTER -> beerType == BeerType.LAGER && ibu >= 30 && srm >= 30 && srm <= 40;
        };
    }

    public int getColor(){
        return ImageUtil.getBeerColor((srm / 40));
    }


}
