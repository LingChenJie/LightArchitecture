package com.android.architecture.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/12/24
 * Modify date: 2022/12/24
 * Version: 1
 */
public class Des3Utils {

    public static final String DESede = "DESede";

    public static final String ECB_NO_PADDING = "DESede/ECB/NoPadding";
    public static final String ECB_PKCS1_PADDING = "DESede/ECB/PKCS1Padding";
    public static final String ECB_PKCS5_PADDING = "DESede/ECB/PKCS5Padding";
    public static final String ECB_PKCS7_PADDING = "DESede/ECB/PKCS7Padding";


    public static byte[] encrypt(byte[] key, byte[] data) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(build3DesKey(key), DESede);
            Cipher cipher = Cipher.getInstance(ECB_NO_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decrypt(byte[] key, byte[] data) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(build3DesKey(key), DESede);
            Cipher cipher = Cipher.getInstance(ECB_NO_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 支持双倍长密钥
     */
    private static byte[] build3DesKey(byte[] key) {
        if (key.length == 16) {
            byte[] tempKey = new byte[24];
            System.arraycopy(key, 0, tempKey, 0, 16);
            System.arraycopy(key, 0, tempKey, 16, 8);
            return tempKey;
        }
        return key;
    }

    public static void main(String[] args) {
        byte[] key = ByteUtil.hexStr2Bytes("11223344556677881122334455667799");
        byte[] data = ByteUtil.hexStr2Bytes("11223344556677881122334455667788");
        byte[] encrypt = encrypt(key, data);
        System.out.println("encrypt:" + ByteUtil.bytes2HexStr(encrypt));
        byte[] decrypt = decrypt(key, encrypt);
        System.out.println("decrypt:" + ByteUtil.bytes2HexStr(decrypt));
    }

}
