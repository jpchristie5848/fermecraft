package com.jipthechip.item;

import com.jipthechip.model.Beer;
import com.jipthechip.model.BeerType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.Objects;

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
        if(component != null && Objects.equals(component.getString(0), "beer")){
            Beer beer = new Beer(component.getFloat(0), component.getFloat(1), component.getFloat(2), component.getFloat(3), component.getFlag(0) ? BeerType.ALE : BeerType.LAGER);
            tooltip.add(Text.literal("ABV: "+beer.getAbv()).formatted(Formatting.BLUE));
            tooltip.add(Text.literal("Amount: "+beer.getAmount()).formatted(Formatting.YELLOW));
            tooltip.add(Text.literal("IBU: "+beer.getIbu()).formatted(Formatting.RED));
            tooltip.add(Text.literal("SRM: "+beer.getSrm()).formatted(Formatting.AQUA));
            tooltip.add(Text.literal("Beer type: "+beer.getAlcoholType()).formatted(Formatting.GREEN));
        }else if(component == null){
            tooltip.add(Text.literal("data null"));
        }
    }
}
