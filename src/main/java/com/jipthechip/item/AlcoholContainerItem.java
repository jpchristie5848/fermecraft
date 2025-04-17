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

public class AlcoholContainerItem extends Item {
    private final float capacity;
    private final Optional<Item> emptyVariant;

    public AlcoholContainerItem(Settings settings, float capacity, Optional<Item> emptyVariant) {
        super(settings);
        this.capacity = capacity;
        this.emptyVariant = emptyVariant;
    }

    public float getCapacity() {
        return capacity;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        CustomModelDataComponent component = stack.get(DataComponentTypes.CUSTOM_MODEL_DATA);
        Optional<? extends AbstractAlcohol> objOptional = DataUtil.componentToAlcohol(component, capacity);

        if(objOptional.isPresent() && objOptional.get() instanceof Beer beer){
            tooltip.add(Text.literal("ABV: "+beer.getAbv()).formatted(Formatting.BLUE));
            tooltip.add(Text.literal("Amount: "+beer.getAmount()).formatted(Formatting.YELLOW));
            tooltip.add(Text.literal("IBU: "+beer.getIbu()).formatted(Formatting.RED));
            tooltip.add(Text.literal("SRM: "+beer.getSrm()).formatted(Formatting.AQUA));
            tooltip.add(Text.literal("Fermentable sugars: "+beer.getFermentableSugars()).formatted(Formatting.DARK_RED));
            tooltip.add(Text.literal("Beer type: "+(beer.getBeerType().isPresent() ? beer.getBeerType() : "invalid")).formatted(Formatting.GREEN));
        }else{
            tooltip.add(Text.literal("data null").formatted(Formatting.RED));
        }
    }

    public Optional<? extends AbstractAlcohol> getAlcoholFromItemStack(ItemStack stack){
        CustomModelDataComponent component = stack.get(DataComponentTypes.CUSTOM_MODEL_DATA);
        return DataUtil.componentToAlcohol(component, capacity);
    }

    public Optional<Item> getEmptyVariant(){
        return emptyVariant;
    }
}
