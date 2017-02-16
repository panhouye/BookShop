package com.mly.panhouye.bookshop;

/**
 * Created by panchengjia on 2017/1/17 0017.
 */

public class DoubleUtils {
    public static double format(double num){
        double d= Math.round(num*100)/100.00;
        return d;
    }
}
