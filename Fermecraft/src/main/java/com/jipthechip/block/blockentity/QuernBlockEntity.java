package com.jipthechip.block.blockentity;

import com.jipthechip.block.ModBlocks;
import com.jipthechip.block.base.GeoBlockEntityBase;
import com.jipthechip.item.MilledGrainItem;
import com.jipthechip.item.ModItems;
import com.jipthechip.util.ImageUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class QuernBlockEntity extends GeoBlockEntityBase implements Inventory {

    private float craftingProgress;

    private long lastTurnedTime;

    private boolean justTurned;

    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);

    public QuernBlockEntity(BlockEntityType type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public static void ticker(World world, BlockPos blockPos, BlockState blockState, QuernBlockEntity quernBlockEntity) {
        quernBlockEntity.tick(world, blockPos, blockState);
    }

    private void tick(World world, BlockPos pos, BlockState state){
        if(justTurned && hasRecipe() && System.currentTimeMillis() - lastTurnedTime > 1000){
            justTurned = false;
            craftingProgress += 20;
            if(craftingProgress >= 100){
                ItemStack inputStack = getStack(0);
                ItemStack outputStack = getStack(1);
                if(outputStack.getItem() == ModItems.MILLED_GRAIN && outputStack.getCount() < outputStack.getMaxCount()){
                    outputStack.setCount(outputStack.getCount() + 1);
                }else if(outputStack == ItemStack.EMPTY){
                    ItemStack milledGrainStack = new ItemStack(ModItems.MILLED_GRAIN);
                    milledGrainStack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(List.of(0.0f),List.of(),List.of(),List.of(ImageUtil.getBeerColor(0.0f))));
                    setStack(1, milledGrainStack);
                }
            }
        }
        if(!hasRecipe()){
            craftingProgress = 0;
        }
    }

    private boolean hasRecipe(){
        return getStack(0).getItem() == Items.WHEAT && getStack(1).getCount() < getStack(1).getMaxCount();
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        Inventories.writeNbt(nbt, inventory, registries);
        nbt.putFloat("craftingProgress", craftingProgress);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        Inventories.readNbt(nbt, inventory, registries);
        craftingProgress = nbt.getFloat("craftingProgress");
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        NbtCompound nbt = new NbtCompound();
        Inventories.writeNbt(nbt, inventory, registries);
        nbt.putFloat("craftingProgress", craftingProgress);
        return nbt;
    }

    @Override
    public int size() {
        return 1;
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
    public boolean canPlayerUse(PlayerEntity player) {
        return false;
    }

    @Override
    public void clear() {

    }
}
