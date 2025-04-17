package com.jipthechip.block.base;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GeoFuelBurningBlockEntity extends GeoInventoryBlockEntityBase{


    private int litTimeRemaining = 0;
    private int litTotalTime = 0;
    private long lastBurningUpdateTime = 0;
    private long updateCounter = 0;

    private static final int updateFrequency = 50;

    public GeoFuelBurningBlockEntity(BlockEntityType type, BlockPos pos, BlockState state, String registryName, int invSize) {
        super(type, pos, state, registryName, invSize);
    }

    protected void tick(World world, BlockPos pos, BlockState state) {

        long timeDiff = System.currentTimeMillis() - lastBurningUpdateTime;
        if(timeDiff >= updateFrequency){
            if(isBurning()){
                if(canBurnUpdate() && updateCounter % 20 == 0){
                    burnUpdate();
                }
                litTimeRemaining -= Math.min((int) (timeDiff / 50), litTimeRemaining);
                if(!isBurning()){
                    consumeFuel();
                }
            }else if(canBurnUpdate()){
                consumeFuel();
            }

            lastBurningUpdateTime = System.currentTimeMillis();
            markDirty();
            updateCounter++;
        }
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        litTimeRemaining = nbt.getInt("litTimeRemaining");
        litTotalTime = nbt.getInt("litTotalTime");
        lastBurningUpdateTime = nbt.getLong("lastBurningUpdateTime");
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        nbt.putInt("litTimeRemaining", litTimeRemaining);
        nbt.putInt("litTotalTime", litTotalTime);
        nbt.putLong("lastBurningUpdateTime", lastBurningUpdateTime);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        NbtCompound nbt = super.toInitialChunkDataNbt(registries);
        nbt.putInt("litTimeRemaining", litTimeRemaining);
        nbt.putInt("litTotalTime", litTotalTime);
        nbt.putLong("lastBurningUpdateTime", lastBurningUpdateTime);
        return nbt;
    }

    private void consumeFuel(){
        ItemStack fuelStack = getFuelStack();
        litTimeRemaining = getFuelTime(world.getFuelRegistry(), fuelStack);
        litTotalTime = litTimeRemaining;
        if (isBurning()) {
            Item item = fuelStack.getItem();
            fuelStack.decrement(1);
            if (fuelStack.isEmpty()) {
                setFuelStack(item.getRecipeRemainder());
            }
        }
    }

    protected ItemStack getFuelStack(){
        return ItemStack.EMPTY;
    }

    protected void setFuelStack(ItemStack stack){
    }

    protected boolean canBurnUpdate(){
        return false;
    }

    protected void burnUpdate(){
    }


    private boolean isBurning() {
        return this.litTimeRemaining > 0;
    }

    protected int getFuelTime(FuelRegistry fuelRegistry, ItemStack stack) {
        return fuelRegistry.getFuelTicks(stack);
    }

    public float getPercentFuelRemaining(){
        return (float) litTimeRemaining /litTotalTime;
    }
}
