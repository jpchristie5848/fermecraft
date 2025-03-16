package com.jipthechip.block.blockentity;

import com.jipthechip.RegistryNames;
import com.jipthechip.block.base.GeoBlockEntityBase;
import com.jipthechip.block.ModBlocks;
import com.jipthechip.block.base.GeoCrafterBlockEntityBase;
import com.jipthechip.block.base.GeoInventoryBlockEntityBase;
import com.jipthechip.network.BlockPosPayload;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib.animation.AnimatableManager;

public class FermentingBarrelBlockEntity extends GeoCrafterBlockEntityBase {

    public FermentingBarrelBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.blockEntityMap.get(RegistryNames.FERMENTING_BARREL), pos, state, RegistryNames.FERMENTING_BARREL, 2, 1.0f, 100.0f, 1000, true);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    public static void ticker(World world, BlockPos blockPos, BlockState blockState, FermentingBarrelBlockEntity fermentingBarrelBlockEntity) {
        fermentingBarrelBlockEntity.tick(world, blockPos, blockState);
    }

    @Override
    protected boolean hasRecipe() {
        return super.hasRecipe();
    }

    @Override
    protected void craft() {

    }

    protected boolean update(){
        if(super.update()){
            //TODO play anim
            return true;
        }
        return false;
    }
}
