package com.jipthechip.block.blockentity;

import com.jipthechip.RegistryNames;
import com.jipthechip.block.base.GeoBlockEntityBase;
import com.jipthechip.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib.animation.AnimatableManager;

public class FermentingBarrelBlockEntity extends GeoBlockEntityBase implements Inventory{

    public FermentingBarrelBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.blockEntityMap.get(RegistryNames.FERMENTING_BARREL), pos, state);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStack(int slot) {
        return null;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return null;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return null;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {

    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return false;
    }


    @Override
    public void clear() {

    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    public static void ticker(World world, BlockPos blockPos, BlockState blockState, FermentingBarrelBlockEntity fermentingBarrelBlockEntity) {
        fermentingBarrelBlockEntity.tick(world, blockPos, blockState);
    }

    private void tick(World world, BlockPos pos, BlockState state){
        System.out.println("fermenting barrel ticking at "+pos);
    }
}
