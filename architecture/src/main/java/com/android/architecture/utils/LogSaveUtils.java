package com.android.architecture.utils;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogSaveUtils {
    private static final String TAG = LogSaveUtils.class.getSimpleName();
    private static final int DEBUG_LEVER = 0;
    private static final int INFO_LEVER = 1;
    private static final int WARING_LEVER = 2;
    private static final int ERROR_LEVER = 3;

    private static final String LOG_DIR_NAME = "LightArchitectureAppLog";
    private static final int SAVE_LOG_DAY = 7;
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
                    if (files != null) {
                        if (files.length > SAVE_LOG_DAY) {
                            deleteLogFile();
                        }
                    }
                }

                String filePath = logSavePath + getLogFileName();
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

    private static String getLogSavePath() {
        if (!TextUtils.isEmpty(LOG_DIR_PATH)) {
            return LOG_DIR_PATH;
        }
        try {
            if (Environment.MEDIA_MOUNTED.equals(Environment
                    .getExternalStorageState())) {
                String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                String logPath = sdPath + "/" + LOG_DIR_NAME + "/" + AppUtils.getApp().getPackageName() + "/";
                File file = new File(logPath);
                boolean mkdirs = false;
                if (!file.exists()) {
                    mkdirs = file.mkdirs();
                    Log.e(TAG, "创建目录: " + logPath + "; 结果：" + mkdirs);
                }
                if (mkdirs) {
                    LOG_DIR_PATH = logPath;
                    return logPath;
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
        return logPath;
    }

    private static String getLogFileName() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        return LOG_PREFIX + sf.format(System.currentTimeMillis()) + LOG_SUFFIX;
    }

    private static void deleteLogFile() {
        String logSavePath = getLogSavePath();
        File dirFile = new File(logSavePath);
        if (!dirFile.exists()) {
            return;
        }
        if (!dirFile.isDirectory()) {
            return;
        }
        List<String> excludeNames = getExcludeDeleteLogName();
        File[] files = dirFile.listFiles();
        if (files == null || files.length <= 0) {
            return;
        }
        for (File file : files) {
            if (excludeNames.contains(file.getName())) {
                continue;
            }
            boolean delete = file.delete();
            LogSaveUtils.debug(TAG, file.getName() + " delete:" + delete);
        }
    }

    private static List<String> getExcludeDeleteLogName() {
        List<String> dates = new ArrayList<>();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < SAVE_LOG_DAY; i++) {
            String date = sf.format(calendar.getTime());
            dates.add(LOG_PREFIX + date + LOG_SUFFIX);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        }
        return dates;
    }

}
