package com.android.architecture.utils;

import java.util.Random;

/**
 * File describe:
 * Author: SuQi
 * Create date: 2024/10/15
 * Modify date: 2024/10/15
 * Version: 1
 */
public class RandomUtils {

    private final static String HEX = "0123456789ABCDEF";

    public static void main(String[] args) {
        String randomHex = getRandomHexString(16);
        System.out.println(randomHex);
    }

    public static String getRandomHexString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        // 每次从 HEX 中随机选择一个字符
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(HEX.length());
            sb.append(HEX.charAt(index));
        }
        return sb.toString();
    }


}
