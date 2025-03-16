package com.jipthechip.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.Objects;

public class MilledGrainItem extends Item {
    public MilledGrainItem(Settings settings) {
        super(settings);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        CustomModelDataComponent component = stack.get(DataComponentTypes.CUSTOM_MODEL_DATA);
        if(component != null && Objects.equals(component.getString(0), "milled_grain")){
            float roastLevel = component.getFloat(0);

            Formatting formatColor;

            if(roastLevel >= 0.8f){
                formatColor = Formatting.BLACK;
            }else if(roastLevel  >= 0.6f){
                formatColor = Formatting.DARK_RED;
            }else if(roastLevel >= 0.4f){
                formatColor = Formatting.RED;
            }else if(roastLevel >= 0.2f){
                formatColor = Formatting.GOLD;
            }else{
                formatColor = Formatting.YELLOW;
            }
            tooltip.add(Text.literal("Roast level: "+roastLevel).formatted(formatColor));
        }else if(component == null){
            tooltip.add(Text.literal("data null"));
        }
    }
}
