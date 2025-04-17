package com.jipthechip.client.gui.block;

import com.jipthechip.block.blockentity.MasherBlockEntity;
import com.jipthechip.client.gui.slot.AlcoholInputSlot;
import com.jipthechip.client.gui.slot.AlcoholOutputSlot;
import com.jipthechip.item.ModItems;
import com.jipthechip.network.BlockPosPayload;
import net.minecraft.entity.player.PlayerInventory;

public class MasherScreenHandler extends InventoryScreenHandlerBase<MasherBlockEntity>{
    public MasherScreenHandler(int syncId, PlayerInventory playerInventory, MasherBlockEntity blockEntity) {
        super(syncId, playerInventory, blockEntity);

        addSlot(new AlcoholInputSlot(this.blockEntity, 0, 34, 25, ModItems.MILLED_MALT));
        addSlot(new AlcoholInputSlot(this.blockEntity, 1, 34, 48, ModItems.MILLED_MALT));
        addSlot(new AlcoholOutputSlot(this.blockEntity, 2, 134, 37));
    }

    public MasherScreenHandler(int syncId, PlayerInventory playerInventory, BlockPosPayload payload) {
        this(syncId, playerInventory, (MasherBlockEntity) playerInventory.player.getWorld().getBlockEntity(payload.pos()));
    }
}
