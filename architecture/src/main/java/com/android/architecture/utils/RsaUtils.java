package com.android.architecture.utils;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
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
    public static final String NONE_OAEP_PADDING = "RSA/NONE/OAEPPadding";
    private static final String SIGNATURE_ALGORITHM_SHA1 = "Sha1WithRSA";
    private static final String SIGNATURE_ALGORITHM_SHA256 = "Sha256WithRSA";
    private static final String SIGNATURE_ALGORITHM_SHA512 = "Sha512WithRSA";
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

    public static byte[] getPublicKeyEncoded(KeyPair keyPair) {
        RSAPublicKey key = (RSAPublicKey) keyPair.getPublic();
        System.out.println("publicKey modules(N):" + key.getModulus().toString(16).toUpperCase());
        System.out.println("publicKey exponent(E):" + key.getPublicExponent().toString(16).toUpperCase());
        System.out.println("publicKey format:" + key.getFormat());
        return key.getEncoded();
    }

    public static byte[] getPrivateKeyEncoded(KeyPair keyPair) {
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
    public static byte[] getPublicKeyEncoded(String modulus, String publicExponent) {
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
    public static byte[] getPrivateKeyEncoded(String modulus, String privateExponent) {
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
     * @param privateKeyEncoded 私钥
     * @return
     */
    public static byte[] getPublicKeyEncoded(byte[] privateKeyEncoded) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyEncoded);
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

    /**
     * getPublicKey
     *
     * @param publicKeyEncoded 公钥编码
     * @return
     */
    public static RSAPublicKey getPublicKey(byte[] publicKeyEncoded) {
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyEncoded);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PublicKey key = keyFactory.generatePublic(keySpec);
            return (RSAPublicKey) key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * getPrivateKey
     *
     * @param privateKeyEncoded 私钥编码
     * @return
     */
    public static RSAPrivateKey getPrivateKey(byte[] privateKeyEncoded) {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyEncoded);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PrivateKey key = keyFactory.generatePrivate(keySpec);
            return (RSAPrivateKey) key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公钥加密
     *
     * @param publicKeyEncoded
     * @param data
     * @return
     */
    public static byte[] encryptByPublicKey(byte[] publicKeyEncoded, byte[] data) {
        try {
            RSAPublicKey key = getPublicKey(publicKeyEncoded);
            Cipher cipher = Cipher.getInstance(NONE_OAEP_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 私钥解密
     *
     * @param privateKeyEncoded
     * @param data
     * @return
     */
    public static byte[] decryptByPrivateKey(byte[] privateKeyEncoded, byte[] data) {
        try {
            RSAPrivateKey key = getPrivateKey(privateKeyEncoded);
            Cipher cipher = Cipher.getInstance(NONE_OAEP_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 私钥签名
     *
     * @param privateKeyEncoded
     * @param data
     * @return
     */
    public static byte[] signByPrivateKey(byte[] privateKeyEncoded, byte[] data) {
        try {
            RSAPrivateKey key = getPrivateKey(privateKeyEncoded);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM_SHA256);
            signature.initSign(key);
            signature.update(data);
            return signature.sign();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 公钥验签
     *
     * @param publicKeyEncoded
     * @param data
     * @param signInfo
     * @return
     */
    public static boolean verifyByPublicKey(byte[] publicKeyEncoded, byte[] data, byte[] signInfo) {
        try {
            RSAPublicKey key = getPublicKey(publicKeyEncoded);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM_SHA256);
            signature.initVerify(key);
            signature.update(data);
            return signature.verify(signInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void main(String[] args) {
        try {
//        test();
//        test2();
//        test3();

//            test4();
//            test5();
//            test6();
//            test7();
            test8();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void test() {
        KeyPair keyPair = generateRSAKeyPair(DEFAULT_KEY_SIZE);
        byte[] privateKey = getPrivateKeyEncoded(keyPair);
        byte[] publicKey = getPublicKeyEncoded(keyPair);
        System.out.println("------------------");
        System.out.println("------------------");
        System.out.println("privateKey(HEX):" + ByteUtil.bytes2HexStr(privateKey));
        System.out.println("publicKey(HEX):" + ByteUtil.bytes2HexStr(publicKey));

        byte[] data = ByteUtil.hexStr2Bytes("11223344556677881122334455667788");
        byte[] encryptResult = encryptByPublicKey(publicKey, data);
        System.out.println("------------------");
        System.out.println("encryptResult(HEX):" + ByteUtil.bytes2HexStr(encryptResult));

        byte[] decryptResult = decryptByPrivateKey(privateKey, encryptResult);
        System.out.println("------------------");
        System.out.println("decryptResult(HEX):" + ByteUtil.bytes2HexStr(decryptResult));
    }

    public static void test2() {
        String module = "c0301ed3994f78d29b5282230ca43b493d4a55f0daf5944369d4d5d2add167bc86b812b4c11a1b17a1ceaee39530ca05e67ef12bce88859d217311fff2db06684fe7aa400fbf4dfdb6ede6780ac277c01751a41d4c558ee9259236520e84d9a2f89530cd74c00f974c2ba3a1f85cb225e1792d81cee7af6f76d6b3edb5d9cc7fcc0d39000a7d997077b054ed1d6f753191804c3e8e0b26a64c7b9449f275e89248d917e788ee8f63bae94c235f85ac976c90e4a8894934bb721b04c2dff53dae9a2b42b642f9db2015ef2fc67412a7a48333c19d4f62ca6ed90de35db1249cac3a197d762c7ba4a0384a950e5f73ae472c7bd72ac4bf51276e4f5bef3b278b07";
        String exponent = "300f9895e7dff4e20e0f9b15b2c29b44dc7beb73f8fdb2df0ce739b59edc202a08329e12efbb5c8d135cc1658e588e1cdda05f254a57dfba04b4670aefab9035a3cdd64880e7a154525bb9e7c9210a9b51f98ba5fc01e5642e8e1ae1785a06a6f7ad2f7021c329e2f71c6ea6419ac4be2e5d8460d2e223802762d7ca1950e70f9acf9a9ae12206180553f8837e9cffa212bde1e209f49ed793cc2028bee76e5ce035f10dcf9f6ec3fea98fd2213883780a88847c7892a69b5382425bc1aef627c35b83d0a39902c9b89816b7c28cfbdf41625e57e74fbeb417425020d18eddca0ccb1f3740529e59adee1d38d3d93293665c9e7485f24c47bba7c2cd2ab52591";
        byte[] privateKey = getPrivateKeyEncoded(module, exponent);

        byte[] en = ByteUtil.hexStr2Bytes("124DCD2654F46B4296BE416D1B63568AF74A196DD2A2FB993AA6E3D024C6C95AD74F423FA9D48381C09A9807A285F4A37E8C9ABC3974FEF29294FD66563B3DAB8067FB20B3FA4326E806A009D879ED319AB63EDD0D2D8219AD17CF2BB8311A58EEDB29089352531AB93D85D1B7031352D2B5870C2179BE89A2412076E3C4B14492194DA0E838BC46FD4E8FB5139AD22FBB7B90C6C371B78B203448956D682AA8793D8C78FF61CCEB408DC1E516909D5C0F6AC5DD5CDC19687B58B7794003E1CE2118902633823C4EB320E8BA5AC2C48F0841FEB7FB171B59F73D7808E91E5FB59DD606CC215ECB269A91202A4401D91C256504BF260DDFF4C01524A43C1586C6");
        byte[] bytes = decryptByPrivateKey(privateKey, en);
        System.out.println("bytes:" + ByteUtil.bytes2HexStr(bytes));
    }

    public static void test3() {
        String publicKey = "30820122300D06092A864886F70D01010105000382010F003082010A0282010100F231050B587D6829370BC3A826E4B93A025BE7D068852CB8AE1E9BA828C69F8D082F6657F53DF393742576F71A2A4725275BCA6A010261484BB03057C09B1E8C30A3BD59A70717621AC9CE1E070737C6A0DE318961127D03EF076436EF4FB98E5C48BFAB12357BFE7340FAFCE4E80346E8CEEA616D36E51B31EDFFC95701A0DE833C615E55B38B7FAFA059AECBF4A74C1DC8337E4DA251EF1C14B0070D140E37778CCD5896BA4442FB2203650D638A5BD84E9181DC7E68F01D4A9C03BABC6682B123A54D9D6401A4DD37415E4EB3D3A163E085360C1F5F0225AB80DB62CC8E5EC0BA2FE400D393E1C722823C8553ABFAD0DEF72DED0A9CFFFF1CB21D498A45C50203010001";
        byte[] data = ByteUtil.hexStr2Bytes("11223344556677881122334455667799");
        byte[] encryptResult = encryptByPublicKey(ByteUtil.hexStr2Bytes(publicKey), data);
        System.out.println("encryptResult:" + ByteUtil.bytes2HexStr(encryptResult));
    }

    public static void test4() throws CertificateException {
//        String certificateBase64Str = "MIIC0DCCAbigAwIBAgIIAJvBVAAAEpMwDQYJKoZIhvcNAQELBQAwLjELMAkGA1UE" +
//                "BhMCUFQxHzAdBgNVBAMMFk1ORlN1YkNBIERldmljZSBTaWduZXIwHhcNMTgwNjE1" +
//                "MDAwMDAwWhcNMTkwNjE1MDAwMDAwWjAiMQswCQYDVQQGEwJQVDETMBEGA1UEAwwK" +
//                "RGV2S2V5UGFpcjCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAL0mq7LY" +
//                "Vj1cyKwKQlWUvIHcLg/i9mgn/V8CNcXfqoeRTlo4n2Rqwor1wDxPe2xDQauq8Y1z" +
//                "HKGPXuECULlyokr8GA7BKGPru4bsX+WjI1kR1uiBIVbUGVEBTS/BL8k1MVRL6ON7" +
//                "Vo/v9rixg8Rb0lOhZH9wr8Lr0kzBo7PcU1kR9hkuapL0oe6AVODGWnwEnBn7mDGC" +
//                "17cgX3QinWnOUQNkrYaMtu4FcSL30omXZjO8wknfwiu6I40zsaSgJmpznf8jeI+4" +
//                "cSg+loIyp+E4PYM57QvoJu9govIR9F4sVAblpsJdrB5X9v0TO+9SXS7bhG4BODMQ" +
//                "ssCDwYbaGw4f4iECAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAi4NjsZAEHXLK6mOh" +
//                "GgkI7dFYKQN8l+N7IwwqAwaj0QumXd4e6V8OxAD5QbnIP+JNaVeinRCYfeEsg+bx" +
//                "mRmKIOEjLzZwkZo6BoF7JiCfSKpoi0+1gWSr2E+zu4YMu3KzyhQGio/oZg9jDhTn" +
//                "9uXShDtnQs3slqbsjqTSBoxpO1449IOnsXJPZbupCM3LUhtEUjNscO0nQlW2Z4nC" +
//                "+rFzOssOJwfmsIl5TGtM1Fg8hla6/VBORvSi8eZMLeGfelOLaGAYAqDd4olXyBQP" +
//                "cZw7+Beq0rWCxEQ0CVrujnKWPJu0OyMMhiQHtapDPi52s6tI4abSdqGHwuNJvRyy" +
//                "NyWaWA==";

        String certificateBase64Str = "" +
                "MIIDejCCAmICCQDxmreWEtPOsTANBgkqhkiG9w0BAQsFADB/MQswCQYDVQQGEwJD\n" +
                "TjERMA8GA1UECAwIc2hhbmdoYWkxETAPBgNVBAcMCHNoYW5naGFpMQ0wCwYDVQQK\n" +
                "DARTdW1pMQ0wCwYDVQQLDARTdW1pMQ0wCwYDVQQDDARTdW1pMR0wGwYJKoZIhvcN\n" +
                "AQkBFg5xaS5zdUBzdW1pLmNvbTAeFw0yMzExMDEwNzMwNTBaFw0yNDEwMzEwNzMw\n" +
                "NTBaMH8xCzAJBgNVBAYTAkNOMREwDwYDVQQIDAhzaGFuZ2hhaTERMA8GA1UEBwwI\n" +
                "c2hhbmdoYWkxDTALBgNVBAoMBFN1bWkxDTALBgNVBAsMBFN1bWkxDTALBgNVBAMM\n" +
                "BFN1bWkxHTAbBgkqhkiG9w0BCQEWDnFpLnN1QHN1bWkuY29tMIIBIjANBgkqhkiG\n" +
                "9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzxCBDMD62C+jFVUEI6dnTIEVUT3T+0LcCapa\n" +
                "nJFwROVAjgNFkyBZ/qz64Ys9wTFyAvzBO9znmEIq8557cOEVrGR5+hzSN+imz3Xa\n" +
                "ZdsSI9Q4XH8drNn2y0euM02TszwH1YO9hKQ3HU/TqiZutPojUd+dVSBKz8OQ0lL6\n" +
                "u0Gn+XGCTz9ln0HEGa1sBS/5/t2PXhCgfYG5c5hjWgteylp0c3tkEW+Yo4PZH/Kx\n" +
                "Y3iU5rKYf1YcFToOXjpNhLUy2+qnSVpQ9GLMIpPQNdyBOEB2i/ZfgMOOWLbLzKYP\n" +
                "PfwqApHu9Z84PHLWuUZJADeKnFCjZDqub0l/mMTDY/4SJag4YQIDAQABMA0GCSqG\n" +
                "SIb3DQEBCwUAA4IBAQAHOX+r/aj4PhRkEn/sisxl5xxLAxdMmTs0D+KJW0R1Do9B\n" +
                "jU47lkUAa92xCrhURTe0OvajRQfCfFaSJ+QVKfMHA5EY0DGjoN4xIc37D4uwNmvJ\n" +
                "od7Tu0Cs1kD3acTEYngxl+Y2pWRSb7A83T7nG2WBFG8M+zidSpB16YtvG4wPdb3u\n" +
                "BWttXCHBv5I8sOCBumvr8NFL1KtGOp+c6JeYoXTCfUXy49vy8inUgO1W4KTHlSdV\n" +
                "RGGtvZV9/I6ywbDNXRsSi4iQAPDIyC9gF6NBJqe9iFcu1G4MT0DWTUcpkw9w7UUH\n" +
                "6njFcIM4lWqG+vr5EFWsLxVFLBMo+7HGGj583eRA";
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Certificate certificate = certificateFactory.generateCertificate(new ByteArrayInputStream(Base64Utils.decode(certificateBase64Str)));
        X509Certificate x509Certificate = (X509Certificate) certificate;
        System.out.println("x509Certificate : " + x509Certificate.toString());

        String publicKeyBase64Str = Base64Utils.encode(x509Certificate.getPublicKey().getEncoded());
        System.out.println("publicKeyBase64: " + publicKeyBase64Str);

        byte[] data = ByteUtil.hexStr2Bytes("A75AA4576271BEB57EC1B845FCCF1AC4");
        byte[] encryptResult = encryptByPublicKey(x509Certificate.getPublicKey().getEncoded(), data);
        System.out.println("encryptResult:" + ByteUtil.bytes2HexStr(encryptResult));
    }

    public static void test5() {
        String enData = "BA64D05DB" +
                "BF2E1E5A8D98DF53B8DDDE28C630A59662DE05BD83F1EBB54744710E96F6B0AD9046D0CBA23086122" +
                "D05DFD4D46FCFE4D5C0A1D06214615920C1B3848D2F7B05E88F2FE7C00598445E1037D9F033497F7F" +
                "232316BFC783507D16A727FBF1BA75EA26D8306F8654A514B6319DABD1633FB4C50945C97323F30F7" +
                "9D7296864A5DD7FEEBA0BAAB3A609064162E656A3CFA126C6D397DC868E35553398CFE55E942F480E" +
                "8DBF93EC29C9F2CFC0CBA1CCC93C5CD714B614A2902BCAF5BDF8DDACC8482FCE236D4A89DE903BD21" +
                "700061A778CD802EA527F7DF0637AD5567FDF97D0B50B9DD2DE9695B81F22994A118B0B98877CDFF6" +
                "1C1C44257071B64D9";
//        String enData = "4AE90D70EE1F37771E5E6786694376DAAAF88684502C51028024C8DDDB102C9ED824F99A01B9FFBA0ADD57649E97D0150A8C843080511F230F37B4917858596C75A9350E044762D3512091C348B8B9D0B34B5FDE1A73D903AD65066C9D49A4E0DBE7C237CE97E71EB6D90D0DD24822E0744229EB89D256BC7998A07BC4F38549309E20BB5E2EFA06991BABBF10F90E67DCFC1F997C8073E92A66B000F73C4C32337817E60913E780F45B41F520A1F34AA4CC480C8585304931B32ECB409691F5A57287A7E1AB7245F7B5FFF5F578E4209F4F1DF8977F644B245B4442CB1D8B60253FBEB5EBDFCC78BA6304449274B037CCA3E0ACFB5D134529015B37D98BBE6E";
        String privateKeyBase64Str =
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC9Jquy2FY9XMis" +
                        "CkJVlLyB3C4P4vZoJ/1fAjXF36qHkU5aOJ9kasKK9cA8T3tsQ0GrqvGNcxyhj17h" +
                        "AlC5cqJK/BgOwShj67uG7F/loyNZEdbogSFW1BlRAU0vwS/JNTFUS+jje1aP7/a4" +
                        "sYPEW9JToWR/cK/C69JMwaOz3FNZEfYZLmqS9KHugFTgxlp8BJwZ+5gxgte3IF90" +
                        "Ip1pzlEDZK2GjLbuBXEi99KJl2YzvMJJ38IruiONM7GkoCZqc53/I3iPuHEoPpaC" +
                        "MqfhOD2DOe0L6CbvYKLyEfReLFQG5abCXaweV/b9EzvvUl0u24RuATgzELLAg8GG" +
                        "2hsOH+IhAgMBAAECggEBAJuBClF7R9Mkz5mYyZZANIXydS/8YKWaktQkJf8qdbEE" +
                        "hczolinhF1VU2pj6ozaLSJcQb4vhoh00mEUWOTVtB/3rqP+gT0tuhvpSpDhPWYUl" +
                        "hHAbkUQoFTQihMmI4ndhss9hpPI3+R9WoZiP4AtzjcPRgKTBCM6QP5F49NOuhBtJ" +
                        "nIQTYYFYl2Cu2wt0bvprclbVQthCrkPnZywF5fOzk7wTiByhGeYA52ZMRO4WHg6r" +
                        "XVBU/xb8VBZbTjhEzgIeGwMf1pixHf8UbuvsSbQ1V3b8t3CHt9xtbZUGTYt4LIoI" +
                        "rcMHKg8e2YpO6qmu91YZp4qw2xxqfh0R0mFlaSgGV9ECgYEA8cHQWGUAQouhiQ28" +
                        "5dACbOmA4BMq6ez5meJ02EgGbQFwTLIwafLa35GBP8TTGSpWf3XEQ86v2S3HX20o" +
                        "M2ICBI9yVIyKTrpJv9P6N7UqaxSGJ3cMGooIrUiSFZ7BORFnkQ5b8ASlHF2WahJN" +
                        "W30z1oPly6IBso5XAzKaOMbptqUCgYEAyEtzk+ml7uoxhh2DPcLFOaVWPo1DGd2R" +
                        "M9UdadwVv+Ok7LWpK7/Z1MVYW7BYWUXF+RuCgG4tLMN7ERT5LlcVWvWO6bq5EQij" +
                        "+YfAkmrwOidV5YiMR7/gCg/6W7D4guUCSJn1w/7vPHohR5mSFHicIWP7qrCSWbDB" +
                        "vXabmiBUIM0CgYBnpWEuVHbtELH7cTFYEXrIuL8w0ebnDVrhV44in5Zpq2E68HYD" +
                        "JkQh717LORYLxvP4h0PSkk0fvhmo1sKSbOVSkTFCAFLXd9Rgcn/m3DvIVq9BQi+l" +
                        "PSKFMAS5UiuizMxrCw3tdABZjeILrbcOjznnMPsW92jk5VN2on4t81GFpQKBgCzn" +
                        "pZjiyD3hKYs88KCXGyqKY+SQPRv+bcBmJjsGiaXEvsQHEk9pqsemGuIrjhMtrm3j" +
                        "+gUbLmubw+qXfioigforlYfXQgiMnF6kTctFyGfxS7OzQmgPn4YCAQovifemqjVm" +
                        "tw/jBvXTF8T6rCKEy9Q8mz6waY9MLpNwlQAgAVx9AoGAI47mJnDj0Doo59WXjIXT" +
                        "eB2SE/0YoWZDvTvUxFQOm5n3Ed6mq9nVcl1RSH8bhkrZQRiFsrrLz6iJR4BmjRgw" +
                        "67WlKYG8mX7HXvEjYf5yAsuxdh0N5xnYlyeXhSciyij6YE4caQoja+VRBgMPzlYv" +
                        "IDkIK7tutsTEsyrc7SHy9j4=";

        byte[] dataBytes = ByteUtil.hexStr2Bytes(enData);
        byte[] decryptResult = decryptByPrivateKey(Base64Utils.decode(privateKeyBase64Str), dataBytes);
        System.out.println("decryptResult:" + ByteUtil.bytes2HexStr(decryptResult));
    }

    public static void test6() throws CertificateException {
        String certificateBase64Str =
                "MIICzTCCAbWgAwIBAgIIAJvBVAAAEpQwDQYJKoZIhvcNAQELBQAwKzELMAkGA1UE\n" +
                        "BhMCUFQxHDAaBgNVBAMME01ORlN1YkNBIEtEQyBTaWduZXIwHhcNMTgwNjE1MDAw\n" +
                        "MDAwWhcNMTkwNjE1MDAwMDAwWjAiMQswCQYDVQQGEwJQVDETMBEGA1UEAwwKS0RD\n" +
                        "S2V5UGFpcjCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBALxDC+lZUoO8\n" +
                        "zIod49pqw5DsegAdBpyYTgN07uhaiOhibU78z7VpMRJ+aJd2GNsDPbmYSoSUsbE4\n" +
                        "FLrIH9Zc7Xm2AL1fUgEe7gkEMbqNunqfMJUO8BRNJNww2BpbASiaVZtFYzF7BuOl\n" +
                        "6l4+yMQNZb/bteXQ3dhRefGB7+IFyGlpTtWTmQkHWVGEH+7OwYoIAVHUVXy8XJlp\n" +
                        "olqVgsMIkk1NZs4GrzHOvYTfwpFg98tIO6gt4y3mv4Jwd7ZnZ5o+Bz/zfmGiKtVe\n" +
                        "mUTNnRUOXz9C0bVJJ9acd2HZZb3rEVXiM5NbbUFBuYuO1eCsGwqvH54fSYcidHj1\n" +
                        "j3heLA3yKzcCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAeKPA3qRuKhgI3Oo09FQ7\n" +
                        "bG5sdqp9IMc/zNSYGSOUyn13eYrpLOhTq7jZ2oYP6TlcT5HNv8ubTfFGqxqw5mCp\n" +
                        "lM7BTmyQ1jw94QtVmHCwBzYAwNhtLbor3CKIl1qL6G9B2lJXGjTDROoYPYhngJrM\n" +
                        "Py2vrXZukT3IvMCzxil7GswV68H5Nau4w1fIUoSGFK9BQWnmBCv4Q9gW/xkeuph9\n" +
                        "Shy1vIgJhyTWxD+zRW/f5girW0YvMHCHSXRm8lLjjFC0J41hsc48M3vUbjiRyoXY\n" +
                        "lVuL93Z6MAY1Q70Lur14XZJPbdmtFBOrU8MP7uE6eEoyIdh3QA/vwMr7uFkpCaGh\n" +
                        "jQ==";
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Certificate certificate = certificateFactory.generateCertificate(new ByteArrayInputStream(Base64Utils.decode(certificateBase64Str)));
        X509Certificate x509Certificate = (X509Certificate) certificate;

//        String data =
//                "DE7436D0556F31AD154C1AF340DF9648B0120B1TX01S0100KS18FF041112345678E00000BA64D05DB" +
//                "BF2E1E5A8D98DF53B8DDDE28C630A59662DE05BD83F1EBB54744710E96F6B0AD9046D0CBA23086122" +
//                "D05DFD4D46FCFE4D5C0A1D06214615920C1B3848D2F7B05E88F2FE7C00598445E1037D9F033497F7F" +
//                "232316BFC783507D16A727FBF1BA75EA26D8306F8654A514B6319DABD1633FB4C50945C97323F30F7" +
//                "9D7296864A5DD7FEEBA0BAAB3A609064162E656A3CFA126C6D397DC868E35553398CFE55E942F480E" +
//                "8DBF93EC29C9F2CFC0CBA1CCC93C5CD714B614A2902BCAF5BDF8DDACC8482FCE236D4A89DE903BD21" +
//                "700061A778CD802EA527F7DF0637AD5567FDF97D0B50B9DD2DE9695B81F22994A118B0B98877CDFF6" +
//                "1C1C44257071B64D9CB6A00AAE574E0DAEE1FAA67E0906C11EADD8CEB799042E16BBF053D36131517" +
//                "95C27EBFF26BE494";
        String data = "DE7436D0556F31AD154C1AF340DF9648B0120B1TX01S0100KS18FF041112345678E00000BA64D05DB" +
                "BF2E1E5A8D98DF53B8DDDE28C630A59662DE05BD83F1EBB54744710E96F6B0AD9046D0CBA23086122" +
                "D05DFD4D46FCFE4D5C0A1D06214615920C1B3848D2F7B05E88F2FE7C00598445E1037D9F033497F7F" +
                "232316BFC783507D16A727FBF1BA75EA26D8306F8654A514B6319DABD1633FB4C50945C97323F30F7" +
                "9D7296864A5DD7FEEBA0BAAB3A609064162E656A3CFA126C6D397DC868E35553398CFE55E942F480E" +
                "8DBF93EC29C9F2CFC0CBA1CCC93C5CD714B614A2902BCAF5BDF8DDACC8482FCE236D4A89DE903BD21" +
                "700061A778CD802EA527F7DF0637AD5567FDF97D0B50B9DD2DE9695B81F22994A118B0B98877CDFF6" +
                "1C1C44257071B64D9D8FF0E717302DBEFE0FC7C3C65E0335D863449592349638AAFE6D5A064AD221E" +
                "9A2E674CBD5828B7";
        String signInfo = "2741A8EE3B14585D805F2A3B68B49805211D2C97D83745AC3ECFA00494C1F993D" +
                "97C68F4B0D379CBF340951F893BA126CEC065AAE4CFD177DE096C3850A17A6CBC3C48DDBA9C74CAC9" +
                "75D5DEE444D39E102B79D2FD5938A4C558924CE3D478AF22FD146A4E941AAF577CD3E0B2B217566BB" +
                "3A31FBC1EDF7F8F4CF384512DEC212F85C4B277FBECB10D439856A92E0C02C1BC65325810AC10E1D6" +
                "EF1287C034540528034E5DDAB38797335F3DF70F8843270C148E227CA9B6ED64D376CDC4D40A33506" +
                "BBDEBFC40242E0BB00A135360373DA61A8D5EA7FBFEFA3D911D17F74600E370E3BD3A367B3FC6ACA4" +
                "F025C43E49ED485AA7C66CED90940019EA6984B5F3";
//        String signInfo = "457BCD2F17776FB186FC9AFC88B0B3242BFC1FDF5A6FAA780D5A32F785B42D1FA816E18980DD8DE1EBA85972B0DA05ED4B997F9B924EC06A27D29E5E0EF7AE9AA4F460E5C1567AFA83BFA4A279462E51C9673EC0025DF5EA90464A098360C6276B6814EC81DC7FCFB06C947E06836082B2930DBAFD92D8C5D5C8A9B5E7EE79C88B6C7DC89EC018AAED02B8FACF07DFDB597DD1D47C8608CDAEC9725BC87C76B90D17C728E4B91AF743236A1069CBFDF58DAEF4F7B24104A1EE20B93E70D05D3EE60D276032D2BE307509C7E3277D25849C38B5B85B10021C4A99C3BE83193B835926F3F077EB12869BA1970CD97F14CA656E59BCC9B042BF096FF1F248C07CDC";

        boolean verifyResult = verifyByPublicKey(x509Certificate.getPublicKey().getEncoded(), ByteUtil.hexStr2Bytes(data), ByteUtil.hexStr2Bytes(signInfo));
        System.out.println("publicKey verifyResult:" + verifyResult);
    }

    public static void test7() {
        String data = "DE7436D0556F31AD154C1AF340DF9648"+
                "423031323042315458303153303130304B533138464" +
                        "6303431313132333435363738453030303030"+
                "BA64D05DB" +
                "BF2E1E5A8D98DF53B8DDDE28C630A59662DE05BD83F1EBB54744710E96F6B0AD9046D0CBA23086122" +
                "D05DFD4D46FCFE4D5C0A1D06214615920C1B3848D2F7B05E88F2FE7C00598445E1037D9F033497F7F" +
                "232316BFC783507D16A727FBF1BA75EA26D8306F8654A514B6319DABD1633FB4C50945C97323F30F7" +
                "9D7296864A5DD7FEEBA0BAAB3A609064162E656A3CFA126C6D397DC868E35553398CFE55E942F480E" +
                "8DBF93EC29C9F2CFC0CBA1CCC93C5CD714B614A2902BCAF5BDF8DDACC8482FCE236D4A89DE903BD21" +
                "700061A778CD802EA527F7DF0637AD5567FDF97D0B50B9DD2DE9695B81F22994A118B0B98877CDFF6" +
                "1C1C44257071B64D9CB6A00AAE574E0DAEE1FAA67E0906C11EADD8CEB799042E16BBF053D36131517" +
                "95C27EBFF26BE494";
        String privateKeyBase64Str =
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC8QwvpWVKDvMyK\n" +
                "HePaasOQ7HoAHQacmE4DdO7oWojoYm1O/M+1aTESfmiXdhjbAz25mEqElLGxOBS6\n" +
                "yB/WXO15tgC9X1IBHu4JBDG6jbp6nzCVDvAUTSTcMNgaWwEomlWbRWMxewbjpepe\n" +
                "PsjEDWW/27Xl0N3YUXnxge/iBchpaU7Vk5kJB1lRhB/uzsGKCAFR1FV8vFyZaaJa\n" +
                "lYLDCJJNTWbOBq8xzr2E38KRYPfLSDuoLeMt5r+CcHe2Z2eaPgc/835hoirVXplE\n" +
                "zZ0VDl8/QtG1SSfWnHdh2WW96xFV4jOTW21BQbmLjtXgrBsKrx+eH0mHInR49Y94\n" +
                "XiwN8is3AgMBAAECggEAPq1nutG1KgziYB1hbadb4tUILR98wwfl+TqmC4+QwyV5\n" +
                "1PFQScpyvwB82+hCkC/9DdxI+ujyW9ka9bCJAPpBSeMxTdr9V0gv4aqWz9A9xHNZ\n" +
                "Xlbx0SB55CES4Xto4Fa5p48rXh7ihUoIxEXM25DGTmDcp12pbkp5+dDTx4E7XvRR\n" +
                "NF6IN1rp/m8dgCkdPQ/Mi1nh5w0exdFh7ZU9FO1zYsS3fG9wKME4CuMbfOziutFw\n" +
                "572yfUJZKWzytf6oLOMSVz7lp9tk4yCPlzFEzOs3IE9CyCE9j6FkiGay59MG8RA5\n" +
                "KUh3Hnk8yhn8X1t6/DCI36l+NZEGjlvCSoapYcagGQKBgQDkG+Y9Q5BMpXDz51CD\n" +
                "6H9KD+jNFstgghlA7yNwCk956BIFgcMeRI3wwuPSbu55NyaiLsWOBVYwXdl7ZqCK\n" +
                "mAEBR3kgS4CCitOtc91x+q7Ahr9o0NpKE+T+Z/B+9MFm7NoNrR8uM0uCiGXzJdfx\n" +
                "W+9J0mDdNm/2wysiTlsNlnGgtQKBgQDTR+IIRQkGrC3gd1jp6iwE1WjESJ7eR0y5\n" +
                "DZecT68VXaO6vGhmgpW/lJl7oGU9hfG1hoSSLobi5R0RR2suNKnX3kyTQEaDbpq0\n" +
                "iJ5F2hC6PW5EF+eu+KpCaLBkbV2KHsu+ldWOKI5aK/4pH4Wtb2W5zIOPiWNH5ghe\n" +
                "VJRlkyILuwKBgQCVZfxuxZB90qFe7XIbYqvzquXTZQmAxNSbZcR0/hu9gRREjQ9p\n" +
                "BIdeV5Z03XjM5SUldbKn6LDhbqYhHz8oZpPJ7bDUSP77AMfQw6EWpJPNvrMgiCFg\n" +
                "ARWq1hJ6Y/1DVv8dk/RnAuuCq6jyDigXiRn45JPSbnP4Ty0yA9W42X7EPQKBgDWf\n" +
                "Q82nf7/yyP30LFvJs/xSEXHnoHJ3lBBPU9A3JQ9+IYTONoz1zX2/ACkxLyCrMwpI\n" +
                "JrNXHaLOVU9CWgBzGzwPTk38/Sh0VM1G8yx1SNOLrcWYb5OnxsZt/X6vhxL79+fZ\n" +
                "7UZUIrTd/3Z+apX29NFwB1/RlaH0zuDqBHO3IOfrAoGAJPt5TO14aeEdhKEGhBXS\n" +
                "KtNG8SpIJ80d41WhPQQNBrwZcwqyOe67qHZDRSMhb+zJsdvwkboZArP8xRjZTcvj\n" +
                "ue7HBtEukzAimp6QX8uolqNFGCpCgrLrB3djLVSEPgFW2emI7xjqVvEXjxY3Hh1M\n" +
                "P5U5PLVtAn9prWsVYrwqfqw=";

        byte[] bytes = signByPrivateKey(Base64Utils.decode(privateKeyBase64Str), ByteUtil.hexStr2Bytes(data));
        System.out.println("PrivateKey signInfo:" + ByteUtil.bytes2HexStr(bytes));

    }

    public static void test8() {
        String hexStr = "30820122300D06092A864886F70D01010105000382010F003082010A0282010100BD21D29F76EC537EB643A8237B89EDC59C379F45F62E89DA72F02701EE20385CF576C0B8B1D70C7A391729FD241DF37C0CC61D2603E891D7078EDAAC2EA01B954576BB48AE6D06AA9733F4BB0562BB9B7D830624D6685B3DF1FB34437D2187A27E984407E99076A0613927126735507DA510466BE08CE4DD4B93436C7E9AF18B8DCDC00CAFE8DD3AE9581086C5A0EB9941D8003F54EB4832842066E03BFB63F6BD01A665E61A59656677A10BE0C044F5491170E3CC96FBB30137D143C60A520B98B61E9482DECF3F0598F85AC00BFFC9393D5831128075E5B5F285B66EF3FC4E16E88F1F674F5113BD2E5EAA8B164221F3F8A8A69E529B08451550CB5C2FB2870203010001";
        byte[] bytes = encryptByPublicKey(ByteUtil.hexStr2Bytes(hexStr), ByteUtil.hexStr2Bytes("9B75B9EC8CA2DA7534DA4546F2FE34832C32204F102FD943"));
        System.out.println("Encrypted:" + ByteUtil.bytes2HexStr(bytes));
    }


}
