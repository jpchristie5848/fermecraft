package com.jipthechip.block.base;

import com.jipthechip.network.BlockPosPayload;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Optional;

public class GeoInventoryBlockEntityBase extends GeoBlockEntityBase implements Inventory, ExtendedScreenHandlerFactory<BlockPosPayload> {

    protected DefaultedList<ItemStack> inventory;

    public GeoInventoryBlockEntityBase(BlockEntityType type, BlockPos pos, BlockState state, String registryName, int invSize) {
        super(type, pos, state, registryName);
        inventory = DefaultedList.ofSize(invSize, ItemStack.EMPTY);
    }

    @Override
    public BlockPosPayload getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return new BlockPosPayload(this.pos);
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return inventory.stream().allMatch(ItemStack.EMPTY::equals);
    }

    @Override
    public ItemStack getStack(int slot) {
        return slot >= inventory.size() ? ItemStack.EMPTY : inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack stack = inventory.get(slot);
        if(amount == stack.getCount()){
            return removeStack(slot);
        }
        stack.setCount(stack.getCount()-amount);
        return stack.copyWithCount(amount);    }

    @Override
    public ItemStack removeStack(int slot) {
        ItemStack copy = inventory.get(slot).copy();
        inventory.set(slot, ItemStack.EMPTY);
        return copy;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if(slot < inventory.size())
            inventory.set(slot, stack);
    }

    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        Inventories.writeNbt(nbt, inventory, registries);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        Inventories.readNbt(nbt, inventory, registries);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        NbtCompound nbt = new NbtCompound();
        Inventories.writeNbt(nbt, inventory, registries);
        return nbt;
    }


    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return false;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(registryName);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return null;
    }

    @Override
    public void clear() {
        inventory.replaceAll(ignored -> ItemStack.EMPTY);
    }


    @Override
    public Optional<Point> getInventoryGuiPos() {
        return Optional.of(new Point(8, 84));
    }
}
