package com.jipthechip.client.gui.block;

import com.jipthechip.block.blockentity.MalterBlockEntity;
import com.jipthechip.client.gui.slot.InputSlot;
import com.jipthechip.client.gui.slot.OutputSlot;
import com.jipthechip.network.BlockPosPayload;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;

public class MalterScreenHandler extends InventoryScreenHandlerBase<MalterBlockEntity>{
    public MalterScreenHandler(int syncId, PlayerInventory playerInventory, MalterBlockEntity blockEntity) {
        super(syncId, playerInventory, blockEntity);
        addSlot(new InputSlot(blockEntity, 0, 56,34, Items.WHEAT));
        addSlot(new OutputSlot(blockEntity, 1,116, 34));
    }

    // client constructor
    public MalterScreenHandler(int syncId, PlayerInventory playerInventory, BlockPosPayload payload){
        this(syncId, playerInventory, (MalterBlockEntity) playerInventory.player.getWorld().getBlockEntity(payload.pos()));
    }
}
