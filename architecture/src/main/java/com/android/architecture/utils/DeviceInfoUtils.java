package com.android.architecture.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * File describe:
 * Author: SuQi
 * Create date: 2024/2/2
 * Modify date: 2024/2/2
 * Version: 1
 */
public class DeviceInfoUtils {

    public static String getDeviceInfo() {
        DisplayMetrics displayMetrics = AppUtils.getApp().getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        float smallestWidth = Math.min(screenWidth, screenHeight) / displayMetrics.density;

        String targetResource = getTargetResource(displayMetrics);

        StringBuilder builder = new StringBuilder();
        builder.append("设备品牌：\t").append(Build.BRAND)
                .append("\n设备型号：\t").append(Build.MODEL)
                .append("\n设备类型：\t").append(isTablet() ? "平板" : "手机");

        builder.append("\n屏幕宽高：\t").append(screenWidth).append(" x ").append(screenHeight)
                .append("\n屏幕密度：\t").append(displayMetrics.densityDpi)
                .append("\n密度像素：\t").append(displayMetrics.density)
                .append("\n目标资源：\t").append(targetResource)
                .append("\n最小宽度：\t").append((int) smallestWidth);

        builder.append("\n安卓版本：\t").append(Build.VERSION.RELEASE)
                .append("\nAPI 版本：\t").append(Build.VERSION.SDK_INT)
                .append("\nCPU 架构：\t").append(Build.SUPPORTED_ABIS[0]);

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
            PackageInfo packageInfo = AppUtils.getApp().getPackageManager().getPackageInfo(AppUtils.getApp().getPackageName(), PackageManager.GET_PERMISSIONS);
            builder.append("\n首次安装：\t").append(dateFormat.format(new Date(packageInfo.firstInstallTime)))
                    .append("\n最近安装：\t").append(dateFormat.format(new Date(packageInfo.lastUpdateTime)))
                    .append("\n崩溃时间：\t").append(dateFormat.format(new Date()));

            List<String> permissions = Arrays.asList(packageInfo.requestedPermissions);

            if (permissions.contains(Permission.READ_EXTERNAL_STORAGE) || permissions.contains(Permission.WRITE_EXTERNAL_STORAGE)) {
                builder.append("\n存储权限：\t").append(XXPermissions.isGranted(AppUtils.getApp(), Permission.Group.STORAGE) ? "已获得" : "未获得");
            }

            if (permissions.contains(Permission.ACCESS_FINE_LOCATION) || permissions.contains(Permission.ACCESS_COARSE_LOCATION)) {
                builder.append("\n定位权限：\t");
                if (XXPermissions.isGranted(AppUtils.getApp(), Permission.ACCESS_FINE_LOCATION, Permission.ACCESS_COARSE_LOCATION)) {
                    builder.append("精确、粗略");
                } else {
                    if (XXPermissions.isGranted(AppUtils.getApp(), Permission.ACCESS_FINE_LOCATION)) {
                        builder.append("精确");
                    } else if (XXPermissions.isGranted(AppUtils.getApp(), Permission.ACCESS_COARSE_LOCATION)) {
                        builder.append("粗略");
                    } else {
                        builder.append("未获得");
                    }
                }
            }

            if (permissions.contains(Permission.CAMERA)) {
                builder.append("\n相机权限：\t").append(XXPermissions.isGranted(AppUtils.getApp(), Permission.CAMERA) ? "已获得" : "未获得");
            }

            if (permissions.contains(Permission.RECORD_AUDIO)) {
                builder.append("\n录音权限：\t").append(XXPermissions.isGranted(AppUtils.getApp(), Permission.RECORD_AUDIO) ? "已获得" : "未获得");
            }

            if (permissions.contains(Permission.SYSTEM_ALERT_WINDOW)) {
                builder.append("\n悬浮窗权限：\t").append(XXPermissions.isGranted(AppUtils.getApp(), Permission.SYSTEM_ALERT_WINDOW) ? "已获得" : "未获得");
            }

            if (permissions.contains(Permission.REQUEST_INSTALL_PACKAGES)) {
                builder.append("\n安装包权限：\t").append(XXPermissions.isGranted(AppUtils.getApp(), Permission.REQUEST_INSTALL_PACKAGES) ? "已获得" : "未获得");
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    @NonNull
    private static String getTargetResource(DisplayMetrics displayMetrics) {
        String targetResource;
        if (displayMetrics.densityDpi > 480) {
            targetResource = "xxxhdpi";
        } else if (displayMetrics.densityDpi > 320) {
            targetResource = "xxhdpi";
        } else if (displayMetrics.densityDpi > 240) {
            targetResource = "xhdpi";
        } else if (displayMetrics.densityDpi > 160) {
            targetResource = "hdpi";
        } else if (displayMetrics.densityDpi > 120) {
            targetResource = "mdpi";
        } else {
            targetResource = "ldpi";
        }
        return targetResource;
    }

    /**
     * 判断当前设备是否是平板
     */
    public static boolean isTablet() {
        return (AppUtils.getApp().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
