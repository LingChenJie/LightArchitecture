package com.android.architecture.utils;

import java.nio.charset.Charset;

public final class ByteUtil {
    private ByteUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void copy(byte[] source, byte[] dest, int len) {
        System.arraycopy(source, 0, dest, 0, len);
    }

    public static String bytes2HexString(byte[] var0) {
        byte[] var1 = new byte[var0.length * 2];
        int var2 = 0;

        int var5;
        for (int var3 = 0; var2 < var0.length; var3 = var5) {
            byte var4;
            byte var10001 = var4 = var0[var2];
            var1[var3++] = (byte) int2HexChar(var4 >> 4 & 15);
            var5 = var3 + 1;
            var1[var3] = (byte) int2HexChar(var10001 & 15);
            ++var2;
        }

        return new String(var1);
    }

    public static byte[] hexString2Bytes(String var0) {
        if (var0.length() % 2 != 0) {
            throw new RuntimeException("The hexString length has to be a multiple of 2.");
        } else {
            int var5;
            byte[] var1 = new byte[var5 = var0.length() / 2];
            char[] var2 = var0.toCharArray();

            for (int var3 = 0; var3 < var5; ++var3) {
                int var10003 = var3 * 2;
                byte var4 = (byte) var2[var3 * 2];
                var1[var3] = (byte) (hexChar2Int(var4) << 4 | hexChar2Int((byte) var2[var10003 + 1]));
            }

            return var1;
        }
    }

    public static String bcdBytes2String(byte[] var0) {
        char[] var1 = new char[var0.length * 2];
        int var2 = 0;
        int var3 = var0.length;

        int var6;
        for (int var4 = 0; var4 < var3; var2 = var6) {
            byte var5;
            boolean var10000 = (var6 = ((var5 = var0[var4]) & 240) >> 4) > 9;
            int var7 = var6 + 55;
            var6 += 48;
            int var8 = var2 + 1;
            if (!var10000) {
                var7 = var6;
            }

            var1[var2] = (char) var7;
            var10000 = (var2 = var5 & 15) > 9;
            int var9 = var2 + 55;
            var2 += 48;
            var6 = var8 + 1;
            if (!var10000) {
                var9 = var2;
            }

            var1[var8] = (char) var9;
            ++var4;
        }

        return new String(var1);
    }

    public static byte[] asciiBytes2BcdBytes(byte[] var0) {
        int var1;
        byte[] var2 = new byte[var1 = var0.length / 2];

        for (int var3 = 0; var3 < var1; ++var3) {
            int var10003 = var3 * 2;
            byte var4 = var0[var3 * 2];
            var2[var3] = (byte) (hexChar2Int(var4) << 4 | hexChar2Int(var0[var10003 + 1]));
        }

        return var2;
    }

    public static byte[] bcdBytes2AsciiBytes(byte[] var0) {
        byte[] var1 = new byte[var0.length * 2];

        for (int var2 = 0; var2 < var0.length; ++var2) {
            int var3 = var2 * 2;
            var1[var3] = (byte) int2HexChar(var0[var2] >> 4 & 15);
            ++var3;
            var1[var3] = (byte) int2HexChar(var0[var2] & 15);
        }

        return var1;
    }

    public static byte[] binaryBytes2Bytes(boolean[] var0) {
        int var10000 = var0.length - 1;
        int var1 = (var0.length - 1) / 8;
        if (var10000 % 8 != 0) {
            ++var1;
        }

        StringBuilder var5 = new StringBuilder();
        byte[] var2 = new byte[var1];
        int var3;
        for (var3 = 1; var3 < var0.length; ++var3) {
            if (var0[var3]) {
                var5.append("1");
            } else {
                var5.append("0");
            }
        }

        int var4 = 0;

        for (var3 = 0; var3 < var5.length(); ++var4) {
            var2[var4] = binaryString2Byte(var5.substring(var3, var3 += 8));
        }

        return var2;
    }

    public static boolean[] bytes2BinaryBytes(byte[] var0) {
        String var1 = "";
        boolean[] var2 = new boolean[var0.length * 8 + 1];
        int var3 = var0.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            var1 = var1 + getBinaryLengthStringByByte(var0[var4]);
        }

        for (int var5 = 0; var5 < var1.length(); var2[var5] = var1.substring(var5, ++var5).equalsIgnoreCase("1")) {
        }

        return var2;
    }

    public static byte[] string2Bytes(String var0, String var1) {
        try {
            return var0.getBytes(Charset.forName(var1));
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static byte[] asciiString2Bytes(String var0) {
        try {
            return var0.getBytes(Charset.forName("US-ASCII"));
        } catch (Exception var1) {
            var1.printStackTrace();
            return null;
        }
    }

    public static String hexStringToBinaryString(String var0) {
        if (var0.length() == 0) {
            return "";
        } else {
            StringBuilder var1;
            var1 = new StringBuilder();
            int var2 = 0;

            while (var2 < var0.length()) {
                String var3;
                for (var3 = Integer.toBinaryString(Integer.parseInt(var0.substring(var2, ++var2), 16)); var3.length() < 4; var3 = "0" + var3) {
                }

                var1.append(var3);
            }

            return var1.toString();
        }
    }

    public static String hexString2String(String var0) {
        Exception var10000;
        label29:
        {
            boolean var10001;
            byte[] var6;
            try {
                var6 = hexString2Bytes(var0);
            } catch (Exception var4) {
                var10000 = var4;
                var10001 = false;
                break label29;
            }

            byte[] var5 = var6;

            Charset var7;
            try {
                var7 = Charset.forName("UTF-8");
            } catch (Exception var3) {
                var10000 = var3;
                var10001 = false;
                break label29;
            }

            Charset var1 = var7;

            try {
                return new String(var5, var1);
            } catch (Exception var2) {
                var10000 = var2;
                var10001 = false;
            }
        }

        var10000.printStackTrace();
        return null;
    }

    public static String hexString2AsciiString(String var0) {
        Exception var10000;
        label29:
        {
            boolean var10001;
            byte[] var6;
            try {
                var6 = hexString2Bytes(var0);
            } catch (Exception var4) {
                var10000 = var4;
                var10001 = false;
                break label29;
            }

            byte[] var5 = var6;

            Charset var7;
            try {
                var7 = Charset.forName("US-ASCII");
            } catch (Exception var3) {
                var10000 = var3;
                var10001 = false;
                break label29;
            }

            Charset var1 = var7;

            try {
                return new String(var5, var1);
            } catch (Exception var2) {
                var10000 = var2;
                var10001 = false;
            }
        }

        var10000.printStackTrace();
        return null;
    }

    public static String hexString2GBKString(String var0) {
        Exception var10000;
        label29:
        {
            boolean var10001;
            String var6;
            Charset var8;
            try {
                var6 = var0;
                var8 = Charset.forName("GBK");
            } catch (Exception var4) {
                var10000 = var4;
                var10001 = false;
                break label29;
            }

            Charset var5 = var8;

            byte[] var7;
            try {
                var7 = hexString2Bytes(var6);
            } catch (Exception var3) {
                var10000 = var3;
                var10001 = false;
                break label29;
            }

            byte[] var1 = var7;

            try {
                return new String(var1, var5);
            } catch (Exception var2) {
                var10000 = var2;
                var10001 = false;
            }
        }

        var10000.printStackTrace();
        return null;
    }

    public static String string2HexString(String var0) {
        try {
            return bytes2HexString(var0.getBytes(Charset.forName("US-ASCII")));
        } catch (Exception var1) {
            var1.printStackTrace();
            return null;
        }
    }

    public static String getBinaryLengthStringByByte(int var0) {
        String var10000 = Integer.toBinaryString(var0 | 256);
        return var10000.substring((var0 = var10000.length()) - 8, var0);
    }

    public static byte hexString2Byte(String var0) {
        return (byte) Integer.parseInt(var0, 16);
    }

    public static byte binaryString2Byte(String var0) {
        return var0.substring(0, 1).equalsIgnoreCase("1") ? (byte) (Byte.valueOf("0" + var0.substring(1), 2) | 128) : Byte.valueOf(var0, 2);
    }

    public static char int2HexChar(int var0) {
        return var0 > 9 ? (char) (var0 - 10 + 65) : (char) (var0 + 48);
    }

    public static int hexChar2Int(byte var0) {
        if (var0 >= 97) {
            return var0 - 97 + 10 & 15;
        } else {
            return var0 >= 65 ? var0 - 65 + 10 & 15 : var0 - 48 & 15;
        }
    }

    public static int bcd2Int(byte[] var0) {
        String var1 = "";
        int var2 = var0.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            byte var10000 = var0[var3];
            int var4 = (var0[var3] & 240) >> 4;
            int var5 = var10000 & 15;
            var1 = var1 + (char) (var4 + 48);
            var1 = var1 + (char) (var5 + 48);
        }

        return Integer.parseInt(var1);
    }
}
