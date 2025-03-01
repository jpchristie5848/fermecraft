package com.jipthechip.util;

import java.awt.*;
import java.util.List;

public class ImageUtil {

    private static final Color LIGHTEST_COLOR = new Color(0xF7F4B3);
    private static final Color DARKEST_COLOR =  new Color(0x240B0B);

    public static Color getAvgColor(List<Color> colors){
        int totalR = 0, totalG = 0, totalB = 0;

        for(Color color : colors){
            totalR += color.getRed();
            totalG += color.getGreen();
            totalB += color.getBlue();
        }

        return new Color(totalR / colors.size(), totalG / colors.size(), totalB / colors.size());
    }

    public static int getBeerColor(float darkness){
        darkness = Math.min(darkness, 1.0f);
        return ImageUtil.getAvgColor(DARKEST_COLOR, LIGHTEST_COLOR, (float) Math.pow(darkness * 2, 2)).getRGB();
    }

    public static Color getAvgColor(Color color1, Color color2, float weight){
        return new Color((int) (((color1.getRed() * weight) + color2.getRed()) / (1 + weight)), (int) (((color1.getGreen() * weight) + color2.getGreen()) / (1 + weight)), (int) (((color1.getBlue() * weight) + color2.getBlue()) / (1 + weight)));
    }
}
