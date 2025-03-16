package com.jipthechip.block.base;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GeoCrafterBlockEntityBase extends GeoInventoryBlockEntityBase{

    protected float craftingProgress;
    protected final float craftingProgressIncrement;
    protected final float maxCraftingProgress;

    protected final int updateDelay;

    protected final boolean updateIsConstant;

    protected long lastUpdatedTime;

    protected boolean updateTrigger;

    public GeoCrafterBlockEntityBase(BlockEntityType type, BlockPos pos, BlockState state, String registryName, int invSize, float craftingProgressIncrement, float maxCraftingProgress, int updateDelay, boolean updateIsConstant) {
        super(type, pos, state, registryName, invSize);
        this.craftingProgressIncrement = craftingProgressIncrement;
        this.maxCraftingProgress = maxCraftingProgress;
        this.updateDelay = updateDelay;
        this.updateIsConstant = updateIsConstant;
    }

    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        nbt.putFloat("craftingProgress", craftingProgress);
        nbt.putLong("lastUpdatedTime", lastUpdatedTime);
        nbt.putBoolean("updateTrigger", updateTrigger);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        craftingProgress = nbt.getFloat("craftingProgress");
        lastUpdatedTime = nbt.getLong("lastUpdatedTime");
        updateTrigger = nbt.getBoolean("updateTrigger");
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        NbtCompound nbt = super.toInitialChunkDataNbt(registries);
        nbt.putFloat("craftingProgress", craftingProgress);
        nbt.putLong("lastUpdatedTime", lastUpdatedTime);
        nbt.putBoolean("updateTrigger", updateTrigger);
        return nbt;
    }

    protected boolean hasRecipe(){
        return false;
    }

    protected void craftIfFinished(){
        if(craftingProgress >= maxCraftingProgress && hasRecipe()){
            craft();
            craftingProgress = 0;
        }
    }

    protected void craft(){

    }

    public boolean triggerUpdate(){
        if(!updateTrigger){
            updateTrigger = true;
            markDirty();
        }
        return false;
    }

    protected void incrementCraftingProgress(){
        if(craftingProgress < maxCraftingProgress){
            craftingProgress += craftingProgressIncrement;
        }
    }

    public float getCraftingProgress(){
        return this.craftingProgress;
    }
    public float getMaxCraftingProgress() {
        return this.maxCraftingProgress;
    }

    protected boolean needsUpdate(){
        return (updateTrigger || updateIsConstant) && System.currentTimeMillis() - lastUpdatedTime >= updateDelay && hasRecipe();
    }
    protected boolean update(){
        if(needsUpdate()){
            updateTrigger = false;
            lastUpdatedTime = System.currentTimeMillis();
            incrementCraftingProgress();
            System.out.println("progress is now "+craftingProgress);
            craftIfFinished();
            markDirty();
            return true;
        }
        return false;
    }

    protected void tick(World world, BlockPos pos, BlockState state){
        if(!hasRecipe()){
            craftingProgress = 0;
            markDirty();
        }
        update();
    }
}
