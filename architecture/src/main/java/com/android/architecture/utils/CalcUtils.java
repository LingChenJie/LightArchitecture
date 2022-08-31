package com.android.architecture.utils;

public class CalcUtils {

    /**
     * 计算报文长度
     *
     * @return
     */
    public static int getDataLen(byte[] data) {
        if (data.length < 2) {
            throw new RuntimeException("The data length must be greater than 2.");
        }
        int len = (((data[0] << 8) & 0xff00) | (data[1] & 0xff));
        return len;
    }

    /**
     * 获取报文长度的两字节
     *
     * @return
     */
    public static byte[] getLenData(int length) {
        byte[] data = new byte[2];
        data[0] = (byte) (length / 256);
        data[1] = (byte) (length % 256);
        return data;
    }

}
