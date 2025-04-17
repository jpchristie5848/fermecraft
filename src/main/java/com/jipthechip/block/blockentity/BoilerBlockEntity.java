package com.jipthechip.block.blockentity;

import com.jipthechip.RegistryNames;
import com.jipthechip.block.ModBlocks;
import com.jipthechip.block.base.BrewContainerBlockEntity;
import com.jipthechip.block.base.GeoFuelBurningBlockEntity;
import com.jipthechip.model.AbstractAlcohol;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class BoilerBlockEntity extends GeoFuelBurningBlockEntity implements BrewContainerBlockEntity {

    Optional<AbstractAlcohol> alcohol;
    private static final float capacity = 1000;

    private boolean containsWater = false;

    public BoilerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.blockEntityMap.get(RegistryNames.BOILER), pos, state, RegistryNames.BOILER, 3);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        putAlcoholComponentInNbt(nbt);
        nbt.putBoolean("containsWater", containsWater);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        getAlcoholComponentFromNbt(nbt);
        containsWater = nbt.getBoolean("containsWater");
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        NbtCompound nbt = super.toInitialChunkDataNbt(registries);
        putAlcoholComponentInNbt(nbt);
        nbt.putBoolean("containsWater", containsWater);
        return nbt;
    }

    protected ItemStack getFuelStack(){
        return getStack(1);
    }

    protected void setFuelStack(ItemStack stack){
        setStack(1, stack);
    }

    protected boolean canBurnUpdate(){
        return getAlcohol().isPresent();
    }

    protected void burnUpdate(){
        // TODO reduce contamination of brew
    }

    @Override
    public Optional<AbstractAlcohol> getAlcohol() {
        return alcohol;
    }

    @Override
    public void setAlcohol(AbstractAlcohol alcohol) {
        this.alcohol = Optional.of(alcohol);
    }

    @Override
    public void emptyAlcohol() {
        this.alcohol = Optional.empty();
    }

    @Override
    public void markContainerDirty() {
        markDirty();
    }

    @Override
    public float getCapacity() {
        return this.capacity;
    }

    @Override
    public boolean containsWater() {
        return containsWater;
    }

    @Override
    public void setContainsWater(boolean containsWater) {
        this.containsWater = containsWater;
    }
}
