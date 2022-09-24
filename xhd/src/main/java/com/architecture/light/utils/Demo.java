package com.architecture.light.utils;

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/5
 * Modify date: 2022/9/5
 * Version: 1
 */
public class Demo {

    public static void main(String[] args) {
        String amount = "2022\\/09\\/24";
        amount = amount.replaceAll("/", "");
        System.out.println(amount);
    }

}
