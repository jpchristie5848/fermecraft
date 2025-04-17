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

public class MaltItem extends Item {
    public MaltItem(Settings settings) {
        super(settings);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        CustomModelDataComponent component = stack.get(DataComponentTypes.CUSTOM_MODEL_DATA);
        if(component != null && Objects.equals(component.getString(0), "malt")){
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

    public static Optional<? extends AbstractAlcohol> alcoholFromComponent(CustomModelDataComponent component, float containerCapacity) {
        float darkness = Objects.requireNonNull(component.getFloat(0));
        return Optional.of(new Beer(0, 100, containerCapacity, 0, 2 + (darkness * 120), 100.0f * (1.0f - (float)(Math.sqrt(darkness))), BeerCategory.WORT, 1.0f));
    }

    public static float getDarknessFromStack(ItemStack stack){
        if(stack.getItem() == ModItems.MALT){
            CustomModelDataComponent component = stack.getComponents().get(DataComponentTypes.CUSTOM_MODEL_DATA);
            if(component != null){
                return Objects.requireNonNullElse(component.getFloat(0), 0.0f);
            }
        }
        return 0.0f;
    }
    public static void setComponentInStack(ItemStack stack, float darkness){
        if(stack.getItem() != ModItems.MALT) return;
        CustomModelDataComponent component = new CustomModelDataComponent(List.of(darkness), List.of(), List.of("malt"), List.of(ImageUtil.getBeerColor(darkness)));
        stack.set(DataComponentTypes.CUSTOM_MODEL_DATA, component);
    }

    public static CustomModelDataComponent createComponent(float darkness){
        return new CustomModelDataComponent(List.of(darkness), List.of(), List.of("malt"), List.of(ImageUtil.getBeerColor(darkness)));
    }
}
