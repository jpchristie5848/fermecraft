package com.jipthechip.item;

import com.jipthechip.model.AbstractAlcohol;
import com.jipthechip.model.Beer;
import com.jipthechip.util.DataUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.Optional;

public class EmptyAlcoholContainerItem extends Item {
    private final float capacity;
    public EmptyAlcoholContainerItem(Settings settings, float capacity) {
        super(settings);
        this.capacity = capacity;
    }

    public float getCapacity() {
        return capacity;
    }


    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal("capacity: "+capacity+"mB").formatted(Formatting.RED));
    }
}
