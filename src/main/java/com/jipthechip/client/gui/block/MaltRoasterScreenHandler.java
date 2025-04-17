package com.jipthechip.client.gui.block;

import com.jipthechip.block.blockentity.MaltRoasterBlockEntityGeo;
import com.jipthechip.client.gui.slot.InputSlot;
import com.jipthechip.item.ModItems;
import com.jipthechip.network.BlockPosPayload;
import net.minecraft.entity.player.PlayerInventory;

public class MaltRoasterScreenHandler extends InventoryScreenHandlerBase<MaltRoasterBlockEntityGeo>{

    public MaltRoasterScreenHandler(int syncId, PlayerInventory playerInventory, MaltRoasterBlockEntityGeo blockEntity) {
        super(syncId, playerInventory, blockEntity);

        addSlot(new InputSlot(blockEntity, 0, 80,17, ModItems.MALT));
        addSlot(new InputSlot(blockEntity, 1,80, 53));
    }

    public MaltRoasterScreenHandler(int syncId, PlayerInventory playerInventory, BlockPosPayload payload){
        this(syncId, playerInventory, (MaltRoasterBlockEntityGeo) playerInventory.player.getWorld().getBlockEntity(payload.pos()));
    }

}
