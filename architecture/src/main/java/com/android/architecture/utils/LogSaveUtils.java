package com.android.architecture.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogSaveUtils {
    private static final String TAG = LogSaveUtils.class.getSimpleName();
    private static final int DEBUG_LEVER = 0;
    private static final int INFO_LEVER = 1;
    private static final int WARING_LEVER = 2;
    private static final int ERROR_LEVER = 3;

    private static String LOG_DIR_NAME = "APP_LOG";
    private static int SAVE_LOG_COUNT = 10;// 日志保存的数量，默认为7
    private static final String LOG_PREFIX = "app_";
    private static final String LOG_SUFFIX = ".log";

    private static String LOG_DIR_PATH;
    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();

    /**
     * 日志打印的级别 默认是debug级别
     */
    private static int mLogLever = DEBUG_LEVER;

    /**
     * 设置日志的级别
     *
     * @param logLever 0：debug,1:info,2:waring,3:error
     */
    public static void setLogLever(int logLever) {
        if (logLever < DEBUG_LEVER || logLever > ERROR_LEVER) {
            mLogLever = DEBUG_LEVER;
        } else {
            mLogLever = logLever;
        }
    }

    /**
     * 设置保存日志的目录
     *
     * @param dirName
     */
    public static void setSaveLogDir(String dirName) {
        LOG_DIR_NAME = dirName;
    }


    /**
     * 设置保存日志的数量
     *
     * @param count
     */
    public static void setSaveLogCount(int count) {
        SAVE_LOG_COUNT = count;
    }

    public static void debug(String tag, String log) {
        if (mLogLever > DEBUG_LEVER) {
            return;
        }
        saveFile("DEBUG [" + tag + "]", log);
    }

    public static void info(String tag, String log) {
        if (mLogLever > INFO_LEVER) {
            return;
        }
        saveFile("INFO [" + tag + "]", log);
    }

    public static void warn(String tag, String log) {
        if (mLogLever > WARING_LEVER) {
            return;
        }
        saveFile("WARN [" + tag + "]", log);
    }

    public static void error(String tag, String log) {
        if (mLogLever > ERROR_LEVER) {
            return;
        }
        saveFile("ERROR [" + tag + "]", log);
    }

    private static void saveFile(String key, String value) {
        EXECUTOR.execute(() -> {
            String logSavePath = getLogSavePath();
            File dirFile = new File(logSavePath);
            try {
                if (!dirFile.exists()) {
                    boolean result = dirFile.mkdirs();
                    Log.e(TAG, "创建目录:" + logSavePath + "; 结果:" + result);
                } else {
                    File[] files = dirFile.listFiles();
                    if (files != null && files.length > SAVE_LOG_COUNT) {
                        deleteLogFile();
                    }
                }
                String filePath = logSavePath + getCurrentLogFileName();
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                String time = format.format(System.currentTimeMillis());

                String content = time + " " + key + ": " + value + "\n";
                OkioUtils.writeString(filePath, content, true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 设置日志保存的路径
     *
     * @return
     */
    public static String getLogSavePath() {
        if (!TextUtils.isEmpty(LOG_DIR_PATH)) {
            return LOG_DIR_PATH;
        }
        try {
            if (Environment.MEDIA_MOUNTED.equals(Environment
                    .getExternalStorageState())) {
                String sdPath = FileUtils.getSDCardRootPath();
                String logPath = sdPath + "/" + LOG_DIR_NAME + "/" + AppUtils.getApp().getPackageName() + "/";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {// Android10.0以上系统
                    sdPath = AppUtils.getApp().getExternalFilesDir(null).getAbsolutePath();
                    logPath = sdPath + "/" + "Log" + "/";
                }
                File file = new File(logPath);
                boolean mkdirs = true;
                if (!file.exists()) {
                    mkdirs = file.mkdirs();
                    Log.e(TAG, "创建目录: " + logPath + "; 结果：" + mkdirs);
                }
                if (mkdirs) {
                    LOG_DIR_PATH = logPath;
                    return LOG_DIR_PATH;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String cachePath = AppUtils.getApp().getCacheDir().getAbsolutePath();
        String logPath = cachePath + "/" + LOG_DIR_NAME + "/" + AppUtils.getApp().getPackageName() + "/";
        File file = new File(logPath);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
            Log.e(TAG, "创建目录: " + logPath + "; 结果：" + mkdirs);
        }
        LOG_DIR_PATH = logPath;
        return LOG_DIR_PATH;
    }

    /**
     * 获取当前日志的文件名
     *
     * @return
     */
    public static String getCurrentLogFileName() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        return LOG_PREFIX + sf.format(System.currentTimeMillis()) + LOG_SUFFIX;
    }

    /**
     * 删除多余的日志
     */
    private static void deleteLogFile() {
        String logSavePath = getLogSavePath();
        File dirFile = new File(logSavePath);
        if (!dirFile.exists()) {
            return;
        }
        if (!dirFile.isDirectory()) {
            return;
        }
        File[] files = dirFile.listFiles();
        if (files == null || files.length <= 0) {
            return;
        }
        List<File> list = Arrays.asList(files);
        // 按最晚修改的文件时间倒排序
        Collections.sort(list, new Comparator<File>() {
            @Override
            public int compare(File file, File newFile) {
                return Long.compare(newFile.lastModified(), file.lastModified());
            }
        });
        for (int i = 0; i < list.size(); i++) {
            if (i < SAVE_LOG_COUNT) {
                continue;
            }
            File file = list.get(i);
            boolean delete = file.delete();
            LogSaveUtils.debug(TAG, file.getName() + " delete:" + delete);
        }
    }

}
