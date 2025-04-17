package com.jipthechip.client.gui.slot;

import com.jipthechip.block.base.BrewContainerBlockEntity;
import com.jipthechip.item.ModItems;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class AlcoholOutputSlot extends InputSlot{
    public AlcoholOutputSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y, ModItems.MUG, Items.BUCKET);
    }

    @Override
    public ItemStack insertStack(ItemStack stack, int count) {

        if(inventory instanceof BrewContainerBlockEntity brewContainerBlockEntity){
            return brewContainerBlockEntity.transferToContainerItem(stack);
        }
        return super.insertStack(stack, count);
    }
}
