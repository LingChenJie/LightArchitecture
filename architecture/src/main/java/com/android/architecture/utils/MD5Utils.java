package com.android.architecture.utils;

import java.security.MessageDigest;

/**
 * 提供md5相关的函数
 */
public class MD5Utils {

    /**
     * 对字符串进行md5加密
     *
     * @param inputData
     * @return
     */
    public static byte[] md5(String inputData) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(inputData.getBytes());
            return md.digest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}