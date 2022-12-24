package com.android.architecture.utils;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/12/7
 * Modify date: 2022/12/7
 * Version: 1
 */
public class RsaUtils {

    private static final String TAG = RsaUtils.class.getSimpleName();

    public static final String RSA = "RSA";
    public static final int DEFAULT_KEY_SIZE = 2048;
    public static final String ECB_NO_PADDING = "RSA/ECB/NoPadding";
    public static final String ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";
    public static final String ECB_PKCS5_PADDING = "RSA/ECB/PKCS5Padding";
    public static final String ECB_PKCS7_PADDING = "RSA/ECB/PKCS7Padding";
    // 当前秘钥支持加密的最大字节数
    public static final int DEFAULT_BUFFER_SIZE = (DEFAULT_KEY_SIZE / 8) - 11;


    /**
     * generateRSAKeyPair
     *
     * @param keyLength 1028/2048
     * @return
     */
    public static KeyPair generateRSAKeyPair(int keyLength) {
        try {
            KeyPairGenerator generator;
            generator = KeyPairGenerator.getInstance(RSA);
            generator.initialize(keyLength);
            return generator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] getPublicKey(KeyPair keyPair) {
        RSAPublicKey key = (RSAPublicKey) keyPair.getPublic();
        System.out.println("publicKey modules(N):" + key.getModulus().toString(16).toUpperCase());
        System.out.println("publicKey exponent(E):" + key.getPublicExponent().toString(16).toUpperCase());
        System.out.println("publicKey format:" + key.getFormat());
        return key.getEncoded();
    }

    public static byte[] getPrivateKey(KeyPair keyPair) {
        RSAPrivateKey key = (RSAPrivateKey) keyPair.getPrivate();
        System.out.println("privateKey modules(N):" + key.getModulus().toString(16).toUpperCase());
        System.out.println("privateKey exponent(E):" + key.getPrivateExponent().toString(16).toUpperCase());
        System.out.println("privateKey format:" + key.getFormat());
        System.out.println("------------------");
        RSAPrivateCrtKey crtKey = (RSAPrivateCrtKey) key;
        System.out.println("privateKey P:" + crtKey.getPrimeP().toString(16).toUpperCase());
        System.out.println("privateKey Q:" + crtKey.getPrimeQ().toString(16).toUpperCase());
        System.out.println("privateKey D:" + crtKey.getPrivateExponent().toString(16).toUpperCase());
        System.out.println("privateKey DP:" + crtKey.getPrimeExponentP().toString(16).toUpperCase());
        System.out.println("privateKey DQ:" + crtKey.getPrimeExponentQ().toString(16).toUpperCase());
        System.out.println("privateKey QP:" + crtKey.getCrtCoefficient().toString(16).toUpperCase());
        System.out.println("------------------");
        return key.getEncoded();
    }

    /**
     * getPublicKey
     *
     * @param modulus        16进制
     * @param publicExponent 16进制
     * @return
     */
    public static byte[] getPublicKey(String modulus, String publicExponent) {
        try {
            BigInteger bigIntModulus = new BigInteger(modulus, 16);
            BigInteger bigIntPublicExponent = new BigInteger(publicExponent, 16);
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPublicExponent);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PublicKey key = keyFactory.generatePublic(keySpec);
            return key.getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * getPrivateKey
     *
     * @param modulus         16进制
     * @param privateExponent 16进制
     * @return
     */
    public static byte[] getPrivateKey(String modulus, String privateExponent) {
        try {
            BigInteger bigIntModulus = new BigInteger(modulus, 16);
            BigInteger bigIntPrivateExponent = new BigInteger(privateExponent, 16);
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(bigIntModulus, bigIntPrivateExponent);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PrivateKey key = keyFactory.generatePrivate(keySpec);
            return key.getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * getPublicKey
     *
     * @param privateKey 私钥
     * @return
     */
    public static byte[] getPublicKey(byte[] privateKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
            PrivateKey priKey = keyFactory.generatePrivate(keySpec);
            RSAPrivateKeySpec priv = keyFactory.getKeySpec(priKey, RSAPrivateKeySpec.class);
            RSAPublicKeySpec keySpec2 = new RSAPublicKeySpec(priv.getModulus(), BigInteger.valueOf(65537));
            PublicKey publicKey = keyFactory.generatePublic(keySpec2);
            return publicKey.getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] encryptByPublicKey(byte[] publicKey, byte[] data) {
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PublicKey key = keyFactory.generatePublic(keySpec);
            Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decryptByPrivateKey(byte[] privateKey, byte[] data) {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PrivateKey key = keyFactory.generatePrivate(keySpec);
            Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        KeyPair keyPair = generateRSAKeyPair(DEFAULT_KEY_SIZE);
        byte[] privateKey = getPrivateKey(keyPair);
        byte[] publicKey = getPublicKey(keyPair);
        System.out.println("privateKey:" + ByteUtil.bytes2HexStr(privateKey));
        System.out.println("publicKey:" + ByteUtil.bytes2HexStr(publicKey));

        byte[] data = ByteUtil.hexStr2Bytes("11223344556677881122334455667788");
        byte[] encryptResult = encryptByPublicKey(publicKey, data);
        System.out.println("encryptResult:" + ByteUtil.bytes2HexStr(encryptResult));

        byte[] decryptResult = decryptByPrivateKey(privateKey, encryptResult);
        System.out.println("decryptResult:" + ByteUtil.bytes2HexStr(decryptResult));


//        String module = "c0301ed3994f78d29b5282230ca43b493d4a55f0daf5944369d4d5d2add167bc86b812b4c11a1b17a1ceaee39530ca05e67ef12bce88859d217311fff2db06684fe7aa400fbf4dfdb6ede6780ac277c01751a41d4c558ee9259236520e84d9a2f89530cd74c00f974c2ba3a1f85cb225e1792d81cee7af6f76d6b3edb5d9cc7fcc0d39000a7d997077b054ed1d6f753191804c3e8e0b26a64c7b9449f275e89248d917e788ee8f63bae94c235f85ac976c90e4a8894934bb721b04c2dff53dae9a2b42b642f9db2015ef2fc67412a7a48333c19d4f62ca6ed90de35db1249cac3a197d762c7ba4a0384a950e5f73ae472c7bd72ac4bf51276e4f5bef3b278b07";
//        String exponent = "300f9895e7dff4e20e0f9b15b2c29b44dc7beb73f8fdb2df0ce739b59edc202a08329e12efbb5c8d135cc1658e588e1cdda05f254a57dfba04b4670aefab9035a3cdd64880e7a154525bb9e7c9210a9b51f98ba5fc01e5642e8e1ae1785a06a6f7ad2f7021c329e2f71c6ea6419ac4be2e5d8460d2e223802762d7ca1950e70f9acf9a9ae12206180553f8837e9cffa212bde1e209f49ed793cc2028bee76e5ce035f10dcf9f6ec3fea98fd2213883780a88847c7892a69b5382425bc1aef627c35b83d0a39902c9b89816b7c28cfbdf41625e57e74fbeb417425020d18eddca0ccb1f3740529e59adee1d38d3d93293665c9e7485f24c47bba7c2cd2ab52591";
//        byte[] privateKey = getPrivateKey(module, exponent);
//
//        byte[] en = ByteUtil.hexString2Bytes("124DCD2654F46B4296BE416D1B63568AF74A196DD2A2FB993AA6E3D024C6C95AD74F423FA9D48381C09A9807A285F4A37E8C9ABC3974FEF29294FD66563B3DAB8067FB20B3FA4326E806A009D879ED319AB63EDD0D2D8219AD17CF2BB8311A58EEDB29089352531AB93D85D1B7031352D2B5870C2179BE89A2412076E3C4B14492194DA0E838BC46FD4E8FB5139AD22FBB7B90C6C371B78B203448956D682AA8793D8C78FF61CCEB408DC1E516909D5C0F6AC5DD5CDC19687B58B7794003E1CE2118902633823C4EB320E8BA5AC2C48F0841FEB7FB171B59F73D7808E91E5FB59DD606CC215ECB269A91202A4401D91C256504BF260DDFF4C01524A43C1586C6");
//        byte[] bytes = decryptByPrivateKey(privateKey, en);
//        System.out.println("bytes:" + ByteUtil.bytes2HexString(bytes));
    }

}
