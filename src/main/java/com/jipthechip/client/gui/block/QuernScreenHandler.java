package com.jipthechip.client.gui.block;

import com.jipthechip.block.blockentity.QuernBlockEntity;
import com.jipthechip.client.gui.slot.InputSlot;
import com.jipthechip.client.gui.slot.OutputSlot;
import com.jipthechip.network.BlockPosPayload;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.server.world.ServerWorld;

public class QuernScreenHandler extends InventoryScreenHandlerBase<QuernBlockEntity> {

    // server constructor
    public QuernScreenHandler(int syncId, PlayerInventory playerInventory, QuernBlockEntity quernBlockEntity){
        super(syncId, playerInventory, quernBlockEntity);

        addSlot(new InputSlot(quernBlockEntity, 0, 56,34, Items.WHEAT));
        addSlot(new OutputSlot(quernBlockEntity, 1,116, 34));
    }

    // client constructor
    public QuernScreenHandler(int syncId, PlayerInventory playerInventory, BlockPosPayload payload){
        this(syncId, playerInventory, (QuernBlockEntity) playerInventory.player.getWorld().getBlockEntity(payload.pos()));
    }

}
