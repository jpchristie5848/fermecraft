package com.jipthechip.item;

import com.jipthechip.model.AbstractAlcohol;
import com.jipthechip.model.Beer;
import com.jipthechip.model.BeerCategory;
import com.jipthechip.util.ImageUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class HopsItem extends Item {
    public HopsItem(Settings settings) {
        super(settings);
    }


    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        CustomModelDataComponent component = stack.get(DataComponentTypes.CUSTOM_MODEL_DATA);
        if(component != null && Objects.equals(component.getString(0), "hops")){
            float bitternessLevel = component.getFloat(0);

            Formatting formatColor = Formatting.GREEN;

            tooltip.add(Text.literal("Bitterness level: "+bitternessLevel).formatted(formatColor));
        }else if(component == null){
            tooltip.add(Text.literal("data null"));
        }
    }

    public static Optional<? extends AbstractAlcohol> alcoholFromComponent(CustomModelDataComponent component, float containerCapacity){
        float bitternessLevel = Objects.requireNonNull(component.getFloat(0));
        return Optional.of(new Beer(0, 50, containerCapacity, 60 + (bitternessLevel * 60), 0, 0, BeerCategory.WORT, 1.0f));
    }


    public static CustomModelDataComponent createComponent(float bitterness){
        return new CustomModelDataComponent(List.of(bitterness), List.of(), List.of("hops"), List.of(ImageUtil.getHopsColor(bitterness)));
    }
}
