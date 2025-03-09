package com.jipthechip.block.blockentity;

import com.jipthechip.Fermecraft;
import com.jipthechip.RegistryNames;
import com.jipthechip.block.ModBlocks;
import com.jipthechip.block.base.GeoBlockEntityBase;
import com.jipthechip.client.gui.block.QuernScreenHandler;
import com.jipthechip.item.ModItems;
import com.jipthechip.network.BlockPosPayload;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import com.jipthechip.util.ImageUtil;

import java.util.List;

public class QuernBlockEntity extends GeoBlockEntityBase implements Inventory, ExtendedScreenHandlerFactory<BlockPosPayload> {

    private float craftingProgress;
    private final float craftingProgressIncrement = 20.0f;
    private final float maxCraftingProgress = 100.0f;

    private long lastTurnedTime;

    private boolean justTurned;

    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);

    private static final RawAnimation TURN_ANIM = RawAnimation.begin().thenPlay("turn");

    public static final Text TITLE = Text.translatable("container."+ Fermecraft.MOD_ID+".quern");

    public QuernBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.blockEntityMap.get(RegistryNames.QUERN), pos, state);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        AnimationController<QuernBlockEntity> controller = new AnimationController<QuernBlockEntity>(this, "quern_controller", state -> PlayState.STOP).triggerableAnim("turn", TURN_ANIM);
        controllerRegistrar.add(controller);
    }

    @Override
    public double getBoneResetTime() {
        return super.getBoneResetTime();
    }

    public static void ticker(World world, BlockPos blockPos, BlockState blockState, QuernBlockEntity quernBlockEntity) {
        quernBlockEntity.tick(world, blockPos, blockState);
    }

    private void tick(World world, BlockPos pos, BlockState state){
        if(justTurned && System.currentTimeMillis() - lastTurnedTime >= 3000){
            justTurned = false;
            craftingProgress += craftingProgressIncrement;
            System.out.println("progress is now "+craftingProgress);
            if(craftingProgress >= maxCraftingProgress){
                System.out.println("finished crafting, setting progress back to 0");
                  craftingProgress = 0;
                ItemStack inputStack = getStack(0);
                ItemStack outputStack = getStack(1);
                if(outputStack.getItem() == ModItems.MILLED_GRAIN && outputStack.getCount() < outputStack.getMaxCount()){
                    outputStack.setCount(outputStack.getCount() + 1);
                }else if(outputStack == ItemStack.EMPTY){
                    ItemStack milledGrainStack = new ItemStack(ModItems.MILLED_GRAIN);
                    milledGrainStack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(List.of(0.0f), List.of(),List.of("milled_grain"),List.of(ImageUtil.getBeerColor(0.0f))));
                    setStack(1, milledGrainStack);
                }
                if(inputStack.getCount() <= 1){
                    setStack(0,ItemStack.EMPTY);
                }else{
                    inputStack.setCount(inputStack.getCount()-1);
                }
            }
            markDirty();
        }
        if(!hasRecipe()){
            craftingProgress = 0;
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        Inventories.writeNbt(nbt, inventory, registries);
        nbt.putFloat("craftingProgress", craftingProgress);
        nbt.putLong("lastTurnedTime", lastTurnedTime);
        nbt.putBoolean("justTurned", justTurned);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        Inventories.readNbt(nbt, inventory, registries);
        craftingProgress = nbt.getFloat("craftingProgress");
        lastTurnedTime = nbt.getLong("lastTurnedTime");
        justTurned = nbt.getBoolean("justTurned");
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        NbtCompound nbt = new NbtCompound();
        Inventories.writeNbt(nbt, inventory, registries);
        nbt.putFloat("craftingProgress", craftingProgress);
        nbt.putLong("lastTurnedTime", lastTurnedTime);
        nbt.putBoolean("justTurned", justTurned);
        return nbt;
    }

    public boolean turn(){
        if(System.currentTimeMillis() - lastTurnedTime > 3000){
            triggerAnim("quern_controller", "turn");
            lastTurnedTime = System.currentTimeMillis();
            justTurned = true;
            return true;
        }
        return false;
    }

    private boolean hasRecipe(){
        return getStack(0).getItem() == Items.WHEAT && getStack(1).getCount() < getStack(1).getMaxCount();
    }

    public float getCraftingProgress(){
        return this.craftingProgress;
    }
    public float getMaxCraftingProgress() {
        return this.maxCraftingProgress;
    }


    @Override
    public int size() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack stack = inventory.get(slot);
        if(amount == stack.getCount()){
            return removeStack(slot);
        }
        stack.setCount(stack.getCount()-amount);
        return stack.copyWithCount(amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        ItemStack copy = inventory.get(slot).copy();
        inventory.set(slot, ItemStack.EMPTY);
        return copy;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public BlockPosPayload getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return new BlockPosPayload(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return TITLE;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new QuernScreenHandler(syncId, playerInventory, this);
    }

}
