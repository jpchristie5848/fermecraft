package com.jipthechip.block.blockentity;

import com.jipthechip.RegistryNames;
import com.jipthechip.block.ModBlocks;
import com.jipthechip.block.base.BrewContainerBlockEntity;
import com.jipthechip.block.base.GeoCrafterBlockEntityBase;
import com.jipthechip.client.gui.block.MasherScreenHandler;
import com.jipthechip.client.gui.block.QuernScreenHandler;
import com.jipthechip.item.ModItems;
import com.jipthechip.model.AbstractAlcohol;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;

import java.util.Optional;

public class MasherBlockEntity extends GeoCrafterBlockEntityBase implements BrewContainerBlockEntity {

    private Optional<AbstractAlcohol> alcohol;
    private boolean containsWater = false;

    private static final float capacity = 1000.0f;


    private static final RawAnimation TURN_ANIM = RawAnimation.begin().thenPlay("turn");

    public MasherBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.blockEntityMap.get(RegistryNames.MASHER), pos, state, RegistryNames.MASHER, 3, 0, 0, 3000, false);
        alcohol = Optional.empty();
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        AnimationController<MasherBlockEntity> controller = new AnimationController<MasherBlockEntity>(this, "masher_controller", state -> PlayState.STOP).triggerableAnim("turn", TURN_ANIM);
        controllerRegistrar.add(controller);
    }

    @Override
    public double getBoneResetTime() {
        return 20;
    }

    @Override
    public boolean triggerUpdate() {
        if(super.triggerUpdate()){
            // TODO trigger anim
            triggerAnim("masher_controller", "turn");
            return true;
        }
        return false;
    }

    @Override
    protected boolean update() {
        if(super.update()){
            // TODO add 1 item to mash from each input slot
            addAlcoholFromIngredient(getStack(0),1);
            addAlcoholFromIngredient(getStack(1),1);
            return true;
        }
        return false;
    }

    @Override
    protected boolean hasRecipe() {
        return true;
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
        return capacity;
    }

    @Override
    public boolean containsWater() {
        return containsWater;
    }

    @Override
    public void setContainsWater(boolean containsWater) {
        this.containsWater = containsWater;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new MasherScreenHandler(syncId, playerInventory, this);
    }

    public static void ticker(World world, BlockPos blockPos, BlockState blockState, MasherBlockEntity masherBlockEntity) {
        masherBlockEntity.tick(world, blockPos, blockState);
    }
}
