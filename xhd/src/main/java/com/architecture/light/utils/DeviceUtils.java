package com.architecture.light.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.architecture.light.constant.Constant;

import java.lang.reflect.Method;

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/3
 * Modify date: 2022/9/3
 * Version: 1
 */
public class DeviceUtils {

    @SuppressLint("HardwareIds")
    public static String getDeviceSN() {
        String serial;
        try {
            @SuppressLint("PrivateApi")
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
        } catch (Exception e) {
            e.printStackTrace();
            serial = android.os.Build.SERIAL;
        }
        if (TextUtils.isEmpty(serial) && Constant.IS_DEBUG) {
            serial = "1234567890";
        }
        return serial;
    }

}
