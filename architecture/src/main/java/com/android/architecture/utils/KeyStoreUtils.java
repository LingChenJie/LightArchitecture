package com.android.architecture.utils;

import com.android.architecture.helper.Logger;

import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * File describe:
 * Author: SuQi
 * Create date: 2023/5/17
 * Modify date: 2023/5/17
 * Version: 1
 */
public class KeyStoreUtils {

    public static byte[] encryptFromKeyStore(String plainText, String keyAlias) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        SecretKey key = (SecretKey) keyStore.getKey(keyAlias, null);
        Logger.d("suqi", "getAlgorithm: " + key.getAlgorithm());
        Logger.d("suqi", "getFormat: " + key.getFormat());
        Logger.d("suqi", "getEncoded: " + ByteUtil.bytes2HexStr(key.getEncoded()));
        return null;
    }

}
