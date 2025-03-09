package com.jipthechip.client.gui.block;

import com.jipthechip.block.ModBlocks;
import com.jipthechip.block.blockentity.QuernBlockEntity;
import com.jipthechip.client.gui.ModGUIs;
import com.jipthechip.client.gui.slot.InputSlot;
import com.jipthechip.client.gui.slot.OutputSlot;
import com.jipthechip.item.ModItems;
import com.jipthechip.network.BlockPosPayload;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.book.RecipeBookType;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Pair;
import net.minecraft.util.thread.TaskQueue;

public class QuernScreenHandler extends AbstractRecipeScreenHandler {

    private final QuernBlockEntity quernBlockEntity;
    private final ScreenHandlerContext context;

    private final Pair<Integer, Integer> playerInventoryGuiPos = new Pair<>(8, 84);


    // server constructor
    public QuernScreenHandler(int syncId, PlayerInventory playerInventory, QuernBlockEntity quernBlockEntity){
        super(ModGUIs.QUERN_SCREEN_HANDLER, syncId);
        this.quernBlockEntity = quernBlockEntity;
        this.context = ScreenHandlerContext.create(this.quernBlockEntity.getWorld(), this.quernBlockEntity.getPos());

        addSlot(new InputSlot(quernBlockEntity, 0, 56,34, Items.WHEAT));
        addSlot(new OutputSlot(quernBlockEntity, 1,116, 34));

        addPlayerSlots(playerInventory, playerInventoryGuiPos.getLeft(), playerInventoryGuiPos.getRight());
    }

    // client constructor
    public QuernScreenHandler(int syncId, PlayerInventory playerInventory, BlockPosPayload payload){
        this(syncId, playerInventory, (QuernBlockEntity) playerInventory.player.getWorld().getBlockEntity(payload.pos()));
    }

    public QuernBlockEntity getBlockEntity(){
        return this.quernBlockEntity;
    }

    public float getCraftingProgress(){
        return this.quernBlockEntity.getCraftingProgress();
    }

    public float getMaxCraftingProgress(){
        return this.quernBlockEntity.getMaxCraftingProgress();
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(context,player, ModBlocks.blockMap.get("quern"));
    }

    @Override
    public PostFillAction fillInputSlots(boolean craftAll, boolean creative, RecipeEntry<?> recipe, ServerWorld world, PlayerInventory inventory) {
        return null;
    }

    @Override
    public void populateRecipeFinder(RecipeFinder finder) {

    }

    @Override
    public RecipeBookType getCategory() {
        return null;
    }
}
