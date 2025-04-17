package com.jipthechip.util;

import java.awt.*;
import java.util.List;

public class ImageUtil {

    private static final Color LIGHTEST_BEER_COLOR = new Color(0xF7F4B3);
    private static final Color MEDIUM_BEER_COLOR = new Color(0x942A15);
    private static final Color DARKEST_BEER_COLOR =  new Color(0x240B0B);


    private static final Color LIGHTEST_HOPS_COLOR = new Color(0xC4D56D);
    private static final Color DARKEST_HOPS_COLOR =  new Color(0x336C03);

    public static Color getAvgColor(List<Color> colors){
        int totalR = 0, totalG = 0, totalB = 0;

        for(Color color : colors){
            totalR += color.getRed();
            totalG += color.getGreen();
            totalB += color.getBlue();
        }

        return new Color(totalR / colors.size(), totalG / colors.size(), totalB / colors.size());
    }

    public static int getHopsColor(float bitterness){
        return getColorInRange(bitterness, LIGHTEST_HOPS_COLOR, DARKEST_HOPS_COLOR);
    }

    public static int getBeerColor(float darkness){
        if(darkness < 0.5f){
            return getColorInRange(darkness/0.5f, LIGHTEST_BEER_COLOR, MEDIUM_BEER_COLOR);
        }else{
            return getColorInRange((darkness-0.5f)/0.5f, MEDIUM_BEER_COLOR, DARKEST_BEER_COLOR);
        }
    }

    public static int getColorInRange(float value, Color color1, Color color2){
        value = Math.min(value, 1.0f);
        return ImageUtil.getAvgColor(color2, color1, (float) Math.pow(value * 2, 2)).getRGB();
    }

    public static Color getAvgColor(Color color1, Color color2, float weight){
        return new Color((int) (((color1.getRed() * weight) + color2.getRed()) / (1 + weight)), (int) (((color1.getGreen() * weight) + color2.getGreen()) / (1 + weight)), (int) (((color1.getBlue() * weight) + color2.getBlue()) / (1 + weight)));
    }
}
