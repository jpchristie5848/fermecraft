package com.jipthechip.client.gui.slot;

import com.jipthechip.block.base.BrewContainerBlockEntity;
import com.jipthechip.item.AlcoholContainerItem;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class AlcoholInputSlot extends InputSlot{

    public AlcoholInputSlot(Inventory inventory, int index, int x, int y, Item... items) {
        super(inventory, index, x, y, items);
    }

    @Override
    public boolean matches(ItemStack stack) {
        return (super.matches(stack) && !items.isEmpty()) || stack.getItem() instanceof AlcoholContainerItem || stack.getItem() == Items.WATER_BUCKET;
    }

    @Override
    public ItemStack insertStack(ItemStack stack, int count) {
        if(inventory instanceof BrewContainerBlockEntity brewContainerBlockEntity ){
            if(stack.getItem() instanceof AlcoholContainerItem){
                return brewContainerBlockEntity.transferFromContainerItem(stack);
            }
        }
        return super.insertStack(stack, count);
    }
}
