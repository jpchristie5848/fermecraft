package com.jipthechip.block.blockentity;

import com.jipthechip.RegistryNames;
import com.jipthechip.block.ModBlocks;
import com.jipthechip.block.base.GeoFuelBurningBlockEntity;
import com.jipthechip.client.gui.block.MaltRoasterScreenHandler;
import com.jipthechip.item.MaltItem;
import com.jipthechip.item.ModItems;
import com.jipthechip.util.MathUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MaltRoasterBlockEntityGeo extends GeoFuelBurningBlockEntity {

    private static final float maltRoastIncrement = 0.01f;

    public MaltRoasterBlockEntityGeo(BlockPos pos, BlockState state) {
        super(ModBlocks.blockEntityMap.get(RegistryNames.MALT_ROASTER), pos, state, RegistryNames.MALT_ROASTER, 2);
    }


    public static void ticker(World world, BlockPos blockPos, BlockState blockState, MaltRoasterBlockEntityGeo maltRoasterBlockEntity) {
        maltRoasterBlockEntity.tick(world, blockPos, blockState);
    }

    public boolean hasMalt() {
        return getStack(0).getItem() == ModItems.MALT;
    }


    private void incrementMaltDarkness(){
        if(!world.isClient){
            ItemStack maltStack = getStack(0);
            float darkness = MaltItem.getDarknessFromStack(maltStack);
            darkness = MathUtil.roundToDecimalPlace(Math.min(darkness + maltRoastIncrement, 1.0f), 2);
            MaltItem.setComponentInStack(maltStack, darkness);
            markDirty();
        }
    }

    protected ItemStack getFuelStack(){
        return getStack(1);
    }

    protected void setFuelStack(ItemStack stack){
        setStack(1, stack);
    }

    protected boolean canBurnUpdate(){
        return hasMalt();
    }

    protected void burnUpdate(){
        incrementMaltDarkness();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new MaltRoasterScreenHandler(syncId, playerInventory, this);
    }
}
