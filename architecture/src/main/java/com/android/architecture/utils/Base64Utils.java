package com.android.architecture.utils;

import org.apache.commons.codec.ext.binary.Base64;

import java.nio.charset.StandardCharsets;

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/12/24
 * Modify date: 2022/12/24
 * Version: 1
 */
public class Base64Utils {

    public static String encode(byte[] data) {
        return new String(Base64.encodeBase64(data), StandardCharsets.UTF_8);
    }

    public static byte[] decode(String data) {
        return Base64.decodeBase64(data.getBytes(StandardCharsets.UTF_8));
    }

    public static void main(String[] args) {
        String data = "hello world 你好世界";
        String encode = encode(data.getBytes());
        System.out.println("encode:" + encode);
        byte[] decode = decode(encode);
        System.out.println("decode:" + new String(decode));
    }

}
