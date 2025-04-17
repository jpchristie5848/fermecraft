package com.jipthechip.block.blockentity;

import com.jipthechip.Fermecraft;
import com.jipthechip.RegistryNames;
import com.jipthechip.block.ModBlocks;
import com.jipthechip.block.base.GeoCrafterBlockEntityBase;
import com.jipthechip.client.gui.block.QuernScreenHandler;
import com.jipthechip.item.MaltItem;
import com.jipthechip.item.MilledMaltItem;
import com.jipthechip.item.ModItems;
import com.jipthechip.util.ImageUtil;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.*;

import java.util.List;

public class QuernBlockEntity extends GeoCrafterBlockEntityBase {

    private static final RawAnimation TURN_ANIM = RawAnimation.begin().thenPlay("turn");

    public static final Text TITLE = Text.translatable("container."+ Fermecraft.MOD_ID+".quern");

    public QuernBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.blockEntityMap.get(RegistryNames.QUERN), pos, state, RegistryNames.QUERN, 2, 20.0f, 100.0f, 3000, false);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        AnimationController<QuernBlockEntity> controller = new AnimationController<QuernBlockEntity>(this, "quern_controller", state -> PlayState.STOP).triggerableAnim("turn", TURN_ANIM);
        controllerRegistrar.add(controller);
    }

    @Override
    public double getBoneResetTime() {
        return 0;
    }

    public static void ticker(World world, BlockPos blockPos, BlockState blockState, QuernBlockEntity quernBlockEntity) {
        quernBlockEntity.tick(world, blockPos, blockState);
    }

    @Override
    protected void craft() {
        ItemStack inputStack = getStack(0);
        ItemStack outputStack = getStack(1);

        float maltDarkness = MaltItem.getDarknessFromStack(inputStack);

        if(outputStack.getItem() == ModItems.MILLED_MALT && outputStack.getCount() < outputStack.getMaxCount() &&
                MilledMaltItem.getDarknessFromStack(outputStack) == MaltItem.getDarknessFromStack(inputStack)){
            outputStack.setCount(outputStack.getCount() + 1);
        }else if(outputStack == ItemStack.EMPTY){
            setStack(1, ModItems.createItemStackWithComponent(ModItems.MILLED_MALT, 1, MilledMaltItem.createComponent(maltDarkness)));
        }
        if(inputStack.getCount() <= 1){
            setStack(0,ItemStack.EMPTY);
        }else{
            inputStack.setCount(inputStack.getCount()-1);
        }
    }

    public boolean triggerUpdate(){
        if(super.triggerUpdate()){
            triggerAnim("quern_controller", "turn");
            return true;
        }
        return false;
    }

    protected boolean hasRecipe(){
        return getStack(0).getItem() == ModItems.MALT && getStack(1).getCount() < getStack(1).getMaxCount();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
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
