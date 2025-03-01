package com.jipthechip.util;

import net.minecraft.item.ItemStack;

public class InventoryUtil {

    public static ItemStack takeItemFromStack(ItemStack stack){
        int count = stack.getCount();
        return count <= 1 ? ItemStack.EMPTY : stack.copyWithCount(count - 1);
    }
}
