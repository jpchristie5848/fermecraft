package com.jipthechip.block.blockentity;

import com.jipthechip.RegistryNames;
import com.jipthechip.block.ModBlocks;
import com.jipthechip.block.base.GeoCrafterBlockEntityBase;
import com.jipthechip.block.base.GeoInventoryBlockEntityBase;
import com.jipthechip.block.base.WaterContainerBlockEntity;
import com.jipthechip.client.gui.block.MaltRoasterScreenHandler;
import com.jipthechip.client.gui.block.MalterScreenHandler;
import com.jipthechip.item.MaltItem;
import com.jipthechip.item.ModItems;
import com.jipthechip.util.ImageUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
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

import java.util.List;

public class MalterBlockEntity extends GeoCrafterBlockEntityBase implements WaterContainerBlockEntity {

    private boolean containsWater = false;

    public MalterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.blockEntityMap.get(RegistryNames.MALTER), pos, state, RegistryNames.MALTER, 2, 2, 100, 1000, true);
    }

    public static void ticker(World world, BlockPos blockPos, BlockState blockState, MalterBlockEntity malterBlockEntity) {
        malterBlockEntity.tick(world, blockPos, blockState);
    }

    @Override
    protected void craft() {
        setStack(1, ModItems.createItemStackWithComponent(ModItems.MALT, getStack(0).getCount(),
                MaltItem.createComponent(0)));
        setStack(0, ItemStack.EMPTY);
    }

    @Override
    public boolean containsWater() {
        return containsWater;
    }

    @Override
    public void setContainsWater(boolean containsWater) {
        this.containsWater = containsWater;
    }

    @Override
    protected boolean hasRecipe() {
        return containsWater && (inventory.get(1).isEmpty() || inventory.get(1) == ItemStack.EMPTY) && inventory.get(0).getItem() == Items.WHEAT;
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        containsWater = nbt.getBoolean("containsWater");
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        nbt.putBoolean("containsWater", containsWater);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        NbtCompound nbt = super.toInitialChunkDataNbt(registries);
        nbt.putBoolean("containsWater", containsWater);
        return nbt;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new MalterScreenHandler(syncId, playerInventory, this);
    }

}
