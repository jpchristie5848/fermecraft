package com.jipthechip.client.gui.block;

import com.jipthechip.block.base.BrewContainerBlockEntity;
import com.jipthechip.block.blockentity.FermentingBarrelBlockEntity;
import com.jipthechip.client.gui.GUITextureDrawContext;
import com.jipthechip.client.gui.TextureCropDirection;
import com.jipthechip.model.AbstractAlcohol;
import com.jipthechip.network.BlockPosPayload;
import com.jipthechip.util.MathUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class BrewContainerScreenBase<T extends InventoryScreenHandlerBase> extends InventoryScreenBase<T>{

    private static final int alcoholbarStartX = 80;
    private static final int alcoholbarStartY = 11;
    private static final int alcoholbarEndX = 95;
    private static final int alcoholbarEndY = 73;

    private static final int alcoholBarMaxHeight = alcoholbarEndY - alcoholbarStartY;

    private static final int WATER_COLOR = 0xFF2E58D3;


    public BrewContainerScreenBase(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        super.drawBackground(context, delta, mouseX, mouseY);
        List<Text> toolTipTexts = new ArrayList<>();
        int barColor = 0;
        boolean drawBar = false;
        int alcoholBarStartYOffset = 0;
        float waterAmount = 0;
        float alcoholAmount = 0;
        int waterBarEndYOffset = 0;

        if(handler.blockEntity instanceof BrewContainerBlockEntity brewContainerBlockEntity){
            if(brewContainerBlockEntity.getAlcohol().isPresent()){
                AbstractAlcohol alcohol = brewContainerBlockEntity.getAlcohol().get();
                waterAmount = brewContainerBlockEntity.containsWater() ? brewContainerBlockEntity.getCapacity() - alcohol.getAmount() : 0;
                alcoholAmount = alcohol.getAmount();
                alcoholBarStartYOffset = (int)(((brewContainerBlockEntity.getCapacity() - alcoholAmount) / brewContainerBlockEntity.getCapacity()) * alcoholBarMaxHeight);
                waterBarEndYOffset = alcoholBarMaxHeight - alcoholBarStartYOffset;
                barColor = alcohol.getColor();
                toolTipTexts.add(Text.literal(alcohol.toString()));
                drawBar = true;
            }else if(brewContainerBlockEntity.containsWater()){
                waterAmount = brewContainerBlockEntity.getCapacity();
                drawBar = true;
            }
        }

        if(drawBar){

            if(alcoholAmount > 0){
                if(MathUtil.isInBox(mouseX, mouseY, x+alcoholbarStartX,y+alcoholbarStartY+alcoholBarStartYOffset,x+alcoholbarEndX,y+alcoholbarEndY)){
                    context.drawTooltip(this.textRenderer, toolTipTexts.get(0), x+alcoholbarEndX,y+alcoholbarEndY);
                }
                context.fill(x+alcoholbarStartX,y+alcoholbarStartY+alcoholBarStartYOffset,x+alcoholbarEndX,y+alcoholbarEndY,0,barColor);
            }

            if(waterAmount > 0){
                context.fill(x+alcoholbarStartX,y+alcoholbarStartY,x+alcoholbarEndX,y+alcoholbarEndY-waterBarEndYOffset,0,WATER_COLOR);
                if(MathUtil.isInBox(mouseX, mouseY, x+alcoholbarStartX,y+alcoholbarStartY,x+alcoholbarEndX,y+alcoholbarEndY-waterBarEndYOffset)){
                    context.drawTooltip(this.textRenderer, Text.literal("water, "+waterAmount+"mB"), x+alcoholbarEndX,y+alcoholbarEndY);
                }
            }
        }
    }
}
