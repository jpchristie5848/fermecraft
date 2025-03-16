package com.jipthechip.client.gui.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.Arrays;
import java.util.List;

public class InputSlot extends Slot {

    List<Item> items;

    public InputSlot(Inventory inventory, int index, int x, int y, Item... items) {
        super(inventory,index, x, y);
        this.items = Arrays.asList(items);
    }

    public boolean canInsert(ItemStack stack) {
        return matches(stack);
    }

    public boolean matches(ItemStack stack) {
        return items.contains(stack.getItem());
    }

    @Override
    public ItemStack insertStack(ItemStack stack, int count) {
        return super.insertStack(stack, count);
    }

    public int getMaxItemCount() {
        return 64;
    }
}
