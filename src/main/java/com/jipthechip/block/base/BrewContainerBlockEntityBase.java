package com.jipthechip.block.base;

import com.jipthechip.model.AbstractAlcohol;
import com.jipthechip.util.DataUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class BrewContainerBlockEntityBase extends GeoCrafterBlockEntityBase{

    protected AbstractAlcohol alcohol;
    protected final float capacity;

    public BrewContainerBlockEntityBase(BlockEntityType type, BlockPos pos, BlockState state, String registryName, int invSize, float craftingProgressIncrement, float maxCraftingProgress, int updateDelay, boolean updateIsConstant, float capacity) {
        super(type, pos, state, registryName, invSize, craftingProgressIncrement, maxCraftingProgress, updateDelay, updateIsConstant);
        this.capacity = capacity;
    }

    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        putComponentInNbt(nbt);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        getComponentFromNbt(nbt);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        NbtCompound nbt = super.toInitialChunkDataNbt(registries);
        putComponentInNbt(nbt);
        return nbt;
    }

    private void getComponentFromNbt(NbtCompound nbt){
        CustomModelDataComponent component = DataUtil.getComponentFromNbt(nbt);

        Optional<Object> objOptional = DataUtil.deserializeComponent(component, capacity);

        if(objOptional.isPresent() && objOptional.get() instanceof AbstractAlcohol alcohol){
            this.alcohol = alcohol;
        }else{
            System.out.println("issue deserializing (reading) alcohol object from nbt on brew container at "+this.pos);
        }
    }

    private void putComponentInNbt(NbtCompound nbt){
        Optional<CustomModelDataComponent> component = DataUtil.serializeComponent(alcohol);

        if(component.isPresent()){
            DataUtil.putComponentInNbt(component.get(), nbt);
        }else{
            System.out.println("issue serializing (writing) alcohol object to nbt on brew container at "+this.pos);
        }
    }

    public AbstractAlcohol getAlcohol(){
        return alcohol;
    }

    public float transferToContainer(BrewContainerBlockEntityBase otherContainer, int amount){
        float amountTransferred = getAlcohol().transferToOther(otherContainer.getAlcohol(), amount);
        if(amountTransferred > 0){
            markDirty();
            otherContainer.markDirty();
        }
        return amountTransferred;
    }

}
