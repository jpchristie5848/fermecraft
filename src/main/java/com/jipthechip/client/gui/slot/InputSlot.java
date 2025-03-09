package com.jipthechip.client.gui.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class InputSlot extends Slot {

    Item item;

    public InputSlot(Inventory inventory, int index, int x, int y, Item item) {
        super(inventory,index, x, y);
        this.item = item;
    }

    public boolean canInsert(ItemStack stack) {
        return matches(stack);
    }

    public boolean matches(ItemStack stack) {
        return stack.getItem() == item;
    }

    @Override
    public ItemStack insertStack(ItemStack stack, int count) {
        return super.insertStack(stack, count);
    }

    public int getMaxItemCount() {
        return 64;
    }
}
