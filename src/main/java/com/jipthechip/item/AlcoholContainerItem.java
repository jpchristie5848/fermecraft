package com.jipthechip.item;

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

public class AlcoholContainerItem extends Item {
    private final float capacity;
    public AlcoholContainerItem(Settings settings, float capacity) {
        super(settings);
        this.capacity = capacity;
    }

    public float getCapacity() {
        return capacity;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        CustomModelDataComponent component = stack.get(DataComponentTypes.CUSTOM_MODEL_DATA);
        Optional<Object> objOptional = DataUtil.deserializeComponent(component, capacity);

        if(objOptional.isPresent() && objOptional.get() instanceof Beer beer){
            tooltip.add(Text.literal("ABV: "+beer.getAbv()).formatted(Formatting.BLUE));
            tooltip.add(Text.literal("Amount: "+beer.getAmount()).formatted(Formatting.YELLOW));
            tooltip.add(Text.literal("IBU: "+beer.getIbu()).formatted(Formatting.RED));
            tooltip.add(Text.literal("SRM: "+beer.getSrm()).formatted(Formatting.AQUA));
            tooltip.add(Text.literal("Beer type: "+(beer.getBeerType().isPresent() ? beer.getBeerType() : "invalid")).formatted(Formatting.GREEN));
        }else{
            tooltip.add(Text.literal("data null").formatted(Formatting.RED));
        }
    }
}
