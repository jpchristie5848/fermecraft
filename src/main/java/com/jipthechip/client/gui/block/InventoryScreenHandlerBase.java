package com.jipthechip.client.gui.block;

import com.jipthechip.block.ModBlocks;
import com.jipthechip.block.base.GeoBlockEntityBase;
import com.jipthechip.client.gui.ModGUIs;
import com.jipthechip.network.BlockPosPayload;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.book.RecipeBookType;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.world.ServerWorld;

import java.awt.*;
import java.util.Optional;

public abstract class InventoryScreenHandlerBase<T extends GeoBlockEntityBase & Inventory> extends AbstractRecipeScreenHandler {

    private final ScreenHandlerContext context;
    private final String registryName;
    private T blockEntity;
    private final Optional<RecipeBookType> recipeBookTypeOptional;

    protected InventoryScreenHandlerBase(int syncId, PlayerInventory playerInventory, T blockEntity) {
        super(ModGUIs.screenHandlerMap.get(blockEntity.getRegistryName()), syncId);
        this.blockEntity = blockEntity;
        this.registryName = blockEntity.getRegistryName();
        this.recipeBookTypeOptional = blockEntity.getRecipeBookType();
        this.context = ScreenHandlerContext.create(this.blockEntity.getWorld(), this.blockEntity.getPos());

        if(blockEntity.getInventoryGuiPos().isPresent()){
            Point inventoryGuiPos = blockEntity.getInventoryGuiPos().get();
            addPlayerSlots(playerInventory, inventoryGuiPos.x, inventoryGuiPos.y);
        }
    }

    public InventoryScreenHandlerBase(int syncId, PlayerInventory playerInventory, BlockPosPayload payload){
        this(syncId, playerInventory, (T) playerInventory.player.getWorld().getBlockEntity(payload.pos()));
    }

    @Override
    public PostFillAction fillInputSlots(boolean craftAll, boolean creative, RecipeEntry<?> recipe, ServerWorld world, PlayerInventory inventory) {
        return PostFillAction.NOTHING;
    }

    @Override
    public void populateRecipeFinder(RecipeFinder finder) {
        if (this.blockEntity instanceof RecipeInputProvider) {
            ((RecipeInputProvider)this.blockEntity).provideRecipeInputs(finder);
        }
    }

    @Override
    public RecipeBookType getCategory() {
        return this.recipeBookTypeOptional.isPresent() ? recipeBookTypeOptional.get() : RecipeBookType.CRAFTING;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack stack = blockEntity.getStack(slot);
        blockEntity.setStack(slot, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(context,player, ModBlocks.blockMap.get(this.registryName));
    }

    public T getBlockEntity() {
        return blockEntity;
    }
}
