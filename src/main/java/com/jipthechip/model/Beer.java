package com.jipthechip.model;

import com.jipthechip.util.ImageUtil;
import com.jipthechip.util.MathUtil;
import net.minecraft.component.type.CustomModelDataComponent;

import java.awt.*;
import java.util.Objects;
import java.util.Optional;

public class Beer extends AbstractAlcohol{

    private BeerCategory beerCategory;
    float ibu;
    float srm;
    float fermentableSugars;

    private static final Color LIGHTEST_COLOR = new Color(0xF7F4B3);
    private static final Color DARKEST_COLOR =  new Color(0x240B0B);

    public Beer(float abv, float amount, float capacity, float ibu, float srm, float fermentableSugars, BeerCategory beerCategory, float saturation) {
        super(abv, amount, capacity, saturation);
        this.ibu = ibu;
        this.srm = srm;
        this.fermentableSugars = fermentableSugars;
        this.beerCategory = beerCategory;
    }

    public float getAbv(){
        return this.abv;
    }

    public float getIbu(){
        return ibu;
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

    public void setFermentableSugars(float fermentableSugars){this.fermentableSugars = fermentableSugars;}

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

            // ales

            case BLONDE_ALE -> beerCategory == BeerCategory.ALE && ibu >= 15 && ibu < 30 && srm >= 5 && srm < 10;
            case PALE_ALE -> beerCategory == BeerCategory.ALE && ibu >= 30 && ibu < 45 && srm >= 5 && srm < 10;
            case INDIAN_PALE_ALE -> beerCategory == BeerCategory.ALE && ibu >= 45 && srm >= 5 && srm < 10;

            case AMBER_ALE -> beerCategory == BeerCategory.ALE && ibu >= 18 && ibu < 30 && srm >= 15 && srm <= 22;
            case IRISH_RED_ALE -> beerCategory == BeerCategory.ALE && ibu >= 18 && ibu < 30 && srm >= 10 && srm < 15;
            case IMPERIAL_RED_ALE -> beerCategory == BeerCategory.ALE && ibu >= 30 && ibu <= 60 && srm >= 15 && srm <= 22;

            case BLACK_IPA -> beerCategory == BeerCategory.ALE && ibu >= 50 && srm >= 35 && srm <= 40;
            case PORTER -> beerCategory == BeerCategory.ALE && ibu >= 20 && ibu < 35 && srm >= 23;
            case STOUT -> beerCategory == BeerCategory.ALE && ibu >= 35 && ibu <= 50 && srm >= 30;

            // lagers

            case PILSNER -> beerCategory == BeerCategory.LAGER && ibu >= 30 && ibu <= 45 && srm >= 5 && srm < 10;
            case KOLSCH -> beerCategory == BeerCategory.LAGER && ibu >= 18 && ibu < 30 && srm >= 5 && srm < 10;
            case PALE_LAGER -> beerCategory == BeerCategory.LAGER && ibu >= 8 && ibu < 18 && srm >= 5 && srm < 10;

            case MARZEN -> beerCategory == BeerCategory.LAGER && ibu >= 18 && ibu <= 28 && srm >= 10 && srm < 18;
            case DUNKEL -> beerCategory == BeerCategory.LAGER && ibu >= 15 && ibu <= 25 && srm >= 18 && srm <= 24;

            case SCHWARZBIER -> beerCategory == BeerCategory.LAGER && ibu >= 20 && ibu < 30 && srm >= 25 && srm <= 40;
            case BALTIC_PORTER -> beerCategory == BeerCategory.LAGER && ibu >= 30 && srm >= 30 && srm <= 40;
        };
    }

    public int getColor(){
        return ImageUtil.getBeerColor((srm / 40));
    }

    @Override
    public Optional<AbstractAlcohol> transferToNew(float amount) {
        if(amount <= this.amount){
            this.amount -= amount;
            return Optional.of(new Beer(abv, amount, capacity, ibu, srm, fermentableSugars, beerCategory, saturation));
        }
        return Optional.empty();
    }

    public static Optional<Beer> fromComponent(CustomModelDataComponent component, float containerCapacity) {
        try{
            float abv = Objects.requireNonNull(component.getFloat(0));
            float amount = Objects.requireNonNull(component.getFloat(1));
            float ibu = Objects.requireNonNull(component.getFloat(2));
            float srm = Objects.requireNonNull(component.getFloat(3));
            float fermentableSugars = Objects.requireNonNull(component.getFloat(4));
            BeerCategory beerCategory = BeerCategory.values()[(int)(Objects.requireNonNull(component.getFloat(5))).floatValue()];
            float saturation = Objects.requireNonNull(component.getFloat(6));
            return Optional.of(new Beer(abv, amount, containerCapacity, ibu, srm, fermentableSugars, beerCategory, saturation));
        }catch(NullPointerException e){
            return Optional.empty();
        }
    }


    @Override
    public <T extends AbstractAlcohol> float transferToOther(Optional<T> other, float amount, float otherContainerCapacity) {
        if(other.isPresent() && isCompatibleWith(other.get())){

            Beer otherBeer = (Beer) other.get();

            float amountToTransfer = Math.min(getAmount(), otherBeer.getCapacity() - otherBeer.getAmount());
            setAmount(getAmount() - amountToTransfer);

            // set properties of other using weighted average
            otherBeer.setAbv(MathUtil.getWeightedAverage(getAbv(), otherBeer.getAbv(), amountToTransfer, otherBeer.getAmount()));
            otherBeer.setIbu(MathUtil.getWeightedAverage(getIbu(), otherBeer.getIbu(), amountToTransfer, otherBeer.getAmount()));
            otherBeer.setSrm(MathUtil.getWeightedAverage(getSrm(), otherBeer.getSrm(), amountToTransfer, otherBeer.getAmount()));
            otherBeer.setFermentableSugars(MathUtil.getWeightedAverage(getFermentableSugars(), otherBeer.getFermentableSugars(), amountToTransfer, otherBeer.getAmount()));

            otherBeer.setAmount(otherBeer.getAmount() + amountToTransfer);

            return amountToTransfer;
        }
        return 0;
    }

    @Override
    public boolean isCompatibleWith(AbstractAlcohol other) {
        return other instanceof Beer otherBeer && getBeerCategory() == otherBeer.getBeerCategory();
    }

    public float getFermentableSugars() {
        return fermentableSugars;
    }

    public void setBeerCategory(BeerCategory beerCategory) {
        this.beerCategory = beerCategory;
    }

    @Override
    public String toString() {
        return "Beer{" +
                "beerCategory=" + beerCategory +
                ", ibu=" + ibu +
                ", srm=" + srm +
                ", fermentableSugars=" + fermentableSugars +
                ", abv=" + abv +
                ", amount=" + amount +
                ", capacity=" + capacity +
                '}';
    }
}
