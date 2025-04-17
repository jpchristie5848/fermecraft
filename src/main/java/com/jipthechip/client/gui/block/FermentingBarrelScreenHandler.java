package com.jipthechip.client.gui.block;

import com.jipthechip.block.blockentity.FermentingBarrelBlockEntity;
import com.jipthechip.client.gui.slot.AlcoholInputSlot;
import com.jipthechip.client.gui.slot.AlcoholOutputSlot;
import com.jipthechip.network.BlockPosPayload;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;

public class FermentingBarrelScreenHandler extends InventoryScreenHandlerBase<FermentingBarrelBlockEntity>{

    public FermentingBarrelScreenHandler(int syncId, PlayerInventory playerInventory, FermentingBarrelBlockEntity fermentingBarrelBlockEntity){
        super(syncId, playerInventory, fermentingBarrelBlockEntity);

        addSlot(new AlcoholInputSlot(fermentingBarrelBlockEntity, 0, 34,37));
        addSlot(new AlcoholOutputSlot(fermentingBarrelBlockEntity, 1,134, 37));
    }

    public FermentingBarrelScreenHandler(int syncId, PlayerInventory playerInventory, BlockPosPayload payload){
        this(syncId, playerInventory, (FermentingBarrelBlockEntity) playerInventory.player.getWorld().getBlockEntity(payload.pos()));
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.blockEntity.close();
    }

}
