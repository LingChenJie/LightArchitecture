package com.android.architecture.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by SuQi on 2021/1/16.
 * Describe:Double计算工具类
 */
public class DoubleUtils {

    /**
     * double 相加
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double add(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.add(bd2).doubleValue();
    }


    /**
     * double 相减
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double subtract(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.subtract(bd2).doubleValue();
    }

    /**
     * double 相乘
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double multiply(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.multiply(bd2).doubleValue();
    }

    /**
     * 四舍五入保留两位小数
     *
     * @param d
     * @return
     */
    public static double halfUpTwoDecimalPlaces(double d) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        return bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public static double format(double d) {
        BigDecimal b = new BigDecimal(Double.toString(d));
        return b.setScale(2, RoundingMode.DOWN).doubleValue();
    }

    public static void main(String[] args) {
        double d = 1.8;
        double format = format(d);
        System.out.println(format);
    }

}
