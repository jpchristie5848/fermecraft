package com.jipthechip.client.gui.block;

import com.jipthechip.block.blockentity.FermentingBarrelBlockEntity;
import com.jipthechip.block.blockentity.QuernBlockEntity;
import com.jipthechip.client.gui.GUITextureDrawContext;
import com.jipthechip.client.gui.slot.InputSlot;
import com.jipthechip.client.gui.slot.OutputSlot;
import com.jipthechip.item.ModItems;
import com.jipthechip.network.BlockPosPayload;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

public class FermentingBarrelScreenHandler extends InventoryScreenHandlerBase<FermentingBarrelBlockEntity>{

    public FermentingBarrelScreenHandler(int syncId, PlayerInventory playerInventory, FermentingBarrelBlockEntity fermentingBarrelBlockEntity){
        super(syncId, playerInventory, fermentingBarrelBlockEntity);

        addSlot(new InputSlot(fermentingBarrelBlockEntity, 0, 34,37, ModItems.YEAST, ModItems.MILLED_GRAIN, ModItems.HOPS, Items.WATER_BUCKET));
        addSlot(new InputSlot(fermentingBarrelBlockEntity, 1,134, 37, Items.BUCKET, ModItems.MUG));
    }

    // client constructor
    public FermentingBarrelScreenHandler(int syncId, PlayerInventory playerInventory, BlockPosPayload payload){
        this(syncId, playerInventory, (FermentingBarrelBlockEntity) playerInventory.player.getWorld().getBlockEntity(payload.pos()));
    }
}
