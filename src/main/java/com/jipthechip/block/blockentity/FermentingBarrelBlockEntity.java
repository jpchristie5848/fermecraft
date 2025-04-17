package com.jipthechip.block.blockentity;

import com.jipthechip.RegistryNames;
import com.jipthechip.block.base.BrewContainerBlockEntity;
import com.jipthechip.block.ModBlocks;
import com.jipthechip.block.base.GeoCrafterBlockEntityBase;
import com.jipthechip.client.gui.block.FermentingBarrelScreenHandler;
import com.jipthechip.item.ModItems;
import com.jipthechip.model.AbstractAlcohol;
import com.jipthechip.model.Beer;
import com.jipthechip.model.BeerCategory;
import com.jipthechip.util.DataUtil;
import com.jipthechip.util.InventoryUtil;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.*;

import java.util.Optional;
import java.util.Random;

public class FermentingBarrelBlockEntity extends GeoCrafterBlockEntityBase implements BrewContainerBlockEntity {

    private Optional<AbstractAlcohol> alcohol;

    private boolean containsWater = false;

    private float capacity;


    private static final RawAnimation OPEN_ANIM = RawAnimation.begin().thenLoop("lid_open");
    private static final RawAnimation FLOAT_ANIM = RawAnimation.begin().thenLoop("lid_float");

    private static final RawAnimation[] FERMENT_ANIMS = new RawAnimation[]{RawAnimation.begin().thenPlay("lid_ferment0"),
            RawAnimation.begin().thenPlay("lid_ferment1"),
            RawAnimation.begin().thenPlay("lid_ferment2"),
            RawAnimation.begin().thenPlay("lid_ferment3")
    };

    private AnimationController<FermentingBarrelBlockEntity>[] fermentControllers = new AnimationController[]{
            new AnimationController<FermentingBarrelBlockEntity>(this, "ferment0", state -> PlayState.STOP).triggerableAnim("lid_ferment0", FERMENT_ANIMS[0]),
            new AnimationController<FermentingBarrelBlockEntity>(this, "ferment1", state -> PlayState.STOP).triggerableAnim("lid_ferment1", FERMENT_ANIMS[1]),
            new AnimationController<FermentingBarrelBlockEntity>(this, "ferment2", state -> PlayState.STOP).triggerableAnim("lid_ferment2", FERMENT_ANIMS[2]),
            new AnimationController<FermentingBarrelBlockEntity>(this, "ferment3", state -> PlayState.STOP).triggerableAnim("lid_ferment3", FERMENT_ANIMS[3])
    };

    public FermentingBarrelBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.blockEntityMap.get(RegistryNames.FERMENTING_BARREL), pos, state, RegistryNames.FERMENTING_BARREL, 2, 1.0f, 100.0f, 1000, true);
        this.capacity = 1000;
        alcohol = Optional.empty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

        controllerRegistrar.add(new AnimationController<FermentingBarrelBlockEntity>(this, "fermenter_controller", this::predicate));

        for(AnimationController<FermentingBarrelBlockEntity> controller : fermentControllers){
            controllerRegistrar.add(controller);
        }
    }

    private PlayState predicate(AnimationState<FermentingBarrelBlockEntity> fermentingBarrelBlockEntityAnimationState) {
        if(isOpen){
            if (System.currentTimeMillis() - lastOpenedTime < 1000){
                fermentingBarrelBlockEntityAnimationState.setAnimation(OPEN_ANIM);
            }else{
                fermentingBarrelBlockEntityAnimationState.setAnimation(FLOAT_ANIM);
            }
        }
        else{
            return PlayState.STOP;
        }
        return PlayState.CONTINUE;
    }

    public static void ticker(World world, BlockPos blockPos, BlockState blockState, FermentingBarrelBlockEntity fermentingBarrelBlockEntity) {
        fermentingBarrelBlockEntity.tick(world, blockPos, blockState);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        getAlcoholComponentFromNbt(nbt);
        containsWater = nbt.getBoolean("containsWater");
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        putAlcoholComponentInNbt(nbt);
        nbt.putBoolean("containsWater", containsWater);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        NbtCompound nbt = super.toInitialChunkDataNbt(registries);
        putAlcoholComponentInNbt(nbt);
        nbt.putBoolean("containsWater", containsWater);
        return nbt;
    }

    @Override
    protected boolean hasRecipe() {
        // TODO check if has yeast, hops, water, and milled grain added

        if(containsWater && alcohol.isPresent() && inventory.getFirst().getItem() == ModItems.YEAST){
            AbstractAlcohol alc = alcohol.get();
            if(alc.getAmount() == capacity && alc instanceof Beer beer){
                return beer.getBeerCategory() == BeerCategory.WORT && beer.getFermentableSugars() > 80;
            }
        }
        return false;
    }

    @Override
    protected void craft() {
        // TODO consume input and produce output in data and inv
        if(hasRecipe() && alcohol.isPresent()){
            if(alcohol.get() instanceof Beer beer){
                inventory.set(0, InventoryUtil.takeOneFromStack(inventory.getFirst()));
                BeerCategory beerCategory = BeerCategory.ALE; // TODO implement way of checking whether to make ale or lager
                                                              // mayb check if in cold biome, or cellar to make lager?
                beer.setBeerCategory(beerCategory);
                markDirty();
            }
        }
    }

    protected boolean update(){
        if(super.update()){ // will increment progress if has recipe, and craft if progress reaches max
            //TODO play anim
            int animIndex = new Random().nextInt(4);
            triggerAnim("ferment"+animIndex, "lid_ferment"+animIndex);
            return true;
        }
        return false;
    }

    @Override
    public Optional<AbstractAlcohol> getAlcohol() {
        return alcohol;
    }

    @Override
    public void setAlcohol(AbstractAlcohol alcohol) {
        this.alcohol = Optional.of(alcohol);
        markDirty();
    }

    @Override
    public void emptyAlcohol() {
        this.alcohol = Optional.empty();
    }

    @Override
    public float getCapacity() {
        return capacity;
    }


    @Override
    public void markContainerDirty() {
        markDirty();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new FermentingBarrelScreenHandler(syncId, playerInventory, this);
    }

    public boolean containsWater() {
        return containsWater;
    }

    @Override
    public void setContainsWater(boolean containsWater) {
        this.containsWater = containsWater;
    }
}
