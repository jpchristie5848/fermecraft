package com.jipthechip.util;

public class MathUtil {

    public static float getWeightedAverage(float f1, float f2, float w1, float w2){
        return ((w1 * f1) + (w2 * f2)) / (w1+w2);
    }

    public static void main(String[] args){

        System.out.println(getWeightedAverage(4,8,1,1)); // should print 6

        System.out.println(getWeightedAverage(4,8,1,2)); // should print 7 or something?

    }
}
