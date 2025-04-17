package com.jipthechip.block.base;

import com.jipthechip.item.AlcoholContainerItem;
import com.jipthechip.item.ModItems;
import com.jipthechip.model.AbstractAlcohol;
import com.jipthechip.util.DataUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public interface BrewContainerBlockEntity extends WaterContainerBlockEntity{

    Optional<AbstractAlcohol> getAlcohol();

    void setAlcohol(AbstractAlcohol alcohol);

    void emptyAlcohol();

    void markContainerDirty();

    float getCapacity();

    public default void getAlcoholComponentFromNbt(NbtCompound nbt){
        CustomModelDataComponent component = DataUtil.getComponentFromNbt(nbt);

        Optional<? extends AbstractAlcohol> alcoholOptional = DataUtil.componentToAlcohol(component, getCapacity());

        alcoholOptional.ifPresent(this::setAlcohol);
    }

    public default void putAlcoholComponentInNbt(NbtCompound nbt){
        Optional<CustomModelDataComponent> component = DataUtil.alcoholToComponent(getAlcohol());

        component.ifPresent(customModelDataComponent -> DataUtil.putComponentInNbt(customModelDataComponent, nbt));
    }

    public default float transferToContainer(BrewContainerBlockEntity otherContainer, int amount){

        if(getAlcohol().isPresent()){
            AbstractAlcohol alcohol = getAlcohol().get();
            float amountTransferred = alcohol.transferToOther(otherContainer.getAlcohol(), amount, otherContainer.getCapacity());
            if(amountTransferred > 0){
                markContainerDirty();
                otherContainer.markContainerDirty();
            }
            return amountTransferred;
        }
        return 0;
    }

    public default ItemStack transferToContainerItem(ItemStack stack){
        ItemStack stackToAddTo;
        float amount;

        if(stack.getItem() instanceof AlcoholContainerItem alcoholContainerItem && alcoholContainerItem.getAlcoholFromItemStack(stack).isEmpty()){
            stackToAddTo = stack;
            amount = alcoholContainerItem.getCapacity();
        }else if(stack.getItem() == Items.BUCKET && stack.getCount() == 1){
            stackToAddTo = new ItemStack(ModItems.BREW_BUCKET);
            amount = ModItems.BREW_BUCKET.getCapacity();
        }else{
            return stack;
        }
        if(getAlcohol().isPresent()){
            Optional<AbstractAlcohol> newAlcoholOptional = getAlcohol().get().transferToNew(amount);
            if(newAlcoholOptional.isPresent()){
                Optional<CustomModelDataComponent> component = DataUtil.alcoholToComponent(newAlcoholOptional);
                if(component.isPresent()){
                    stackToAddTo.set(DataComponentTypes.CUSTOM_MODEL_DATA, component.get());
                    if(getAlcohol().get().getAmount() <= 0.0f){
                        emptyAlcohol();
                        setContainsWater(false);
                    }
                    markContainerDirty();
                    return stackToAddTo;
                }
                // if there was an issue, transfer back to original container
                newAlcoholOptional.get().transferToOther(getAlcohol(), getCapacity());
                markContainerDirty();
            }
        }
        return stack;
    }

    public default ItemStack transferFromContainerItem(ItemStack stack){
        if(stack.getItem() instanceof AlcoholContainerItem alcoholContainerItem && alcoholContainerItem.getAlcoholFromItemStack(stack).isPresent()){
            ItemStack stackToReturn = alcoholContainerItem.getEmptyVariant().isPresent() ? new ItemStack(alcoholContainerItem.getEmptyVariant().get()) : stack;
            AbstractAlcohol itemAlcohol = alcoholContainerItem.getAlcoholFromItemStack(stack).get();
            if(getAlcohol().isPresent()){
                if(itemAlcohol.transferToOther(getAlcohol(), getCapacity()) > 0){
                    stack.set(DataComponentTypes.CUSTOM_MODEL_DATA, null);
                    markContainerDirty();
                    return stackToReturn;
                }
            }else if(itemAlcohol.getAmount() <= getCapacity()){
                setAlcohol(itemAlcohol);
                stack.set(DataComponentTypes.CUSTOM_MODEL_DATA, null);
                markContainerDirty();
                return stackToReturn;
            }
        }else if(stack.getItem() == Items.WATER_BUCKET){
            if(addWater()){
                markContainerDirty();
                return new ItemStack(Items.BUCKET);
            }
        }
        return stack;
    }

    public default boolean addAlcoholFromIngredient(ItemStack stack){
        return addAlcoholFromIngredient(stack, stack.getCount());
    }

    public default boolean addAlcoholFromIngredient(ItemStack stack, int count){

        if(stack == ItemStack.EMPTY) return false;

        Optional<? extends AbstractAlcohol> stackObj = DataUtil.componentToAlcohol(stack.get(DataComponentTypes.CUSTOM_MODEL_DATA), getCapacity());
        if(stackObj.isPresent() && stackObj.get() instanceof AbstractAlcohol newAlcohol){

            if(getAlcohol().isPresent()){
                if(!newAlcohol.isCompatibleWith(getAlcohol().get())){
                    return false;
                }
            }

            float singleItemAmount = newAlcohol.getAmount();

            int numItemsToAdd = (int)Math.min(Math.ceil((getRemainingAmount())/singleItemAmount), Math.min(count, stack.getCount()));

            if(numItemsToAdd == 0) return false;

            newAlcohol.setAmount(numItemsToAdd*singleItemAmount);

            int newStackCount = stack.getCount()-numItemsToAdd;

            stack.setCount(newStackCount);

            if(getAlcohol().isPresent()){
                newAlcohol.transferToOther(getAlcohol(), getCapacity());
            }else{
                setAlcohol(newAlcohol);
            }
            markContainerDirty();
            return true;
        }
        return false;
    }



    public default float getRemainingAmount(){
        return getAlcohol().isPresent() ? getCapacity() - getAlcohol().get().getAmount() : getCapacity();
    }

}
