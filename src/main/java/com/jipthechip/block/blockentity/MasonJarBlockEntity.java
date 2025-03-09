package com.jipthechip.block.blockentity;

import com.jipthechip.RegistryNames;
import com.jipthechip.block.base.GeoBlockEntityBase;
import com.jipthechip.block.ModBlocks;
import com.jipthechip.util.InventoryUtil;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.*;

public class MasonJarBlockEntity extends GeoBlockEntityBase {

    private boolean isCovered;
    private boolean hasWheat;
    private boolean hasSugar;
    private boolean hasWater;

    private boolean hasYeast;

    private float recipeProgress = 0;

    private long lastUpdatedTime = 0;

    private static final RawAnimation FERMENT_ANIM = RawAnimation.begin().thenWait(10).thenLoop("ferment");
    private static final RawAnimation LID_ON_ANIM = RawAnimation.begin().thenPlayAndHold("lid_on");

    public MasonJarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.blockEntityMap.get(RegistryNames.MASON_JAR), pos, state);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        isCovered = nbt.getBoolean("isCovered");
        hasWheat = nbt.getBoolean("hasWheat");
        hasSugar = nbt.getBoolean("hasSugar");
        hasWater = nbt.getBoolean("hasWater");
        hasYeast = nbt.getBoolean("hasYeast");
        recipeProgress = nbt.getFloat("recipeProgress");

    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        nbt.putBoolean("isCovered", isCovered);
        nbt.putBoolean("hasWheat", hasWheat);
        nbt.putBoolean("hasSugar", hasSugar);
        nbt.putBoolean("hasWater", hasWater);
        nbt.putBoolean("hasYeast", hasYeast);
        nbt.putFloat("recipeProgress", recipeProgress);
    }

    public boolean hasRecipe(){
        return hasWheat && hasSugar && hasWater;
    }

    public static void ticker(World world, BlockPos blockPos, BlockState blockState, MasonJarBlockEntity masonJarBlockEntity) {
        masonJarBlockEntity.tick(world, blockPos, blockState);
    }

    private void tick(World world, BlockPos pos, BlockState state){
        if(System.currentTimeMillis() - lastUpdatedTime > 2000){
            if(hasRecipe() && isCovered){
                recipeProgress += 20;
                System.out.println("recipe progress: "+recipeProgress);
                markDirty();
            }
            if(recipeProgress >= 100){
                recipeProgress = 0;
                hasWheat = false;
                hasWater = false;
                hasSugar = false;
                hasYeast = true;
                System.out.println("recipe completed, yeast available");
            }
            lastUpdatedTime = System.currentTimeMillis();
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<MasonJarBlockEntity> controller = new AnimationController<MasonJarBlockEntity>(this, "mason_jar_controller", this::predicate);
        AnimationController<MasonJarBlockEntity> controller2 = new AnimationController<MasonJarBlockEntity>(this, "mason_jar_controller2", state -> PlayState.STOP).triggerableAnim("lid_on", LID_ON_ANIM);
        controllers.add(controller);
        controllers.add(controller2);
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event){
        if(hasRecipe() && isCovered){
            event.setAnimation(FERMENT_ANIM);
        }else{
            return PlayState.STOP;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        NbtCompound nbt = new NbtCompound();
        nbt.putBoolean("isCovered", isCovered);
        nbt.putBoolean("hasWheat", hasWheat);
        nbt.putBoolean("hasSugar", hasSugar);
        nbt.putBoolean("hasWater", hasWater);
        nbt.putBoolean("hasYeast", hasYeast);
        nbt.putFloat("recipeProgress", recipeProgress);
        return nbt;
    }

    public boolean cover(){
        if(!isCovered && hasRecipe()){
            isCovered = true;
            triggerAnim("mason_jar_controller2", "lid_on");
            return true;
        }
        return false;
    }

    public boolean addWheat(){
        if(!isCovered && !hasWheat){
            hasWheat = true;
            return true;
        }
        return false;
    }

    public boolean addSugar(){
        if(!isCovered && !hasSugar){
            hasSugar = true;
            return true;
        }
        return false;
    }

    public boolean takeYeast(){
        if(isCovered && hasYeast){
            hasYeast = false;
            isCovered = false;
            return true;
        }
        return false;
    }

    public boolean isCovered() {
        return isCovered;
    }

    public void setCovered(boolean covered) {
        isCovered = covered;
    }

    public void setHasWater(boolean hasWater) {
        this.hasWater = hasWater;
    }

    public boolean hasWheat() {
        return this.hasWheat;
    }

    public boolean hasSugar(){
        return this.hasSugar;
    }

    public boolean hasYeast() {
        return hasYeast;
    }
}
