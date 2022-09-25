package com.android.architecture.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.os.StatFs;

import com.android.architecture.helper.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by SuQi on 2022/9/25.
 * Describe:
 */
public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();
    public static final long KB = 1024L;
    public static final long MB = 1048576L;
    public static final long GB = 1073741824L;
    public static final long TB = 1099511627776L;
    public static final long PB = 1125899906842624L;
    public static final long EB = 1152921504606846976L;

    public static String formatSize(long fileSize) {
        if (fileSize < 0L) {
            return "0B";
        } else {
            DecimalFormat df = new DecimalFormat("0.00");
            String formatSize;
            if (fileSize < 1024L) {
                formatSize = df.format((double) fileSize) + "B";
            } else if (fileSize < 1048576L) {
                formatSize = df.format((double) fileSize / 1024.0D) + "KB";
            } else if (fileSize < 1073741824L) {
                formatSize = df.format((double) fileSize / 1048576.0D) + "MB";
            } else if (fileSize < 1099511627776L) {
                formatSize = df.format((double) fileSize / 1.073741824E9D) + "GB";
            } else if (fileSize < 1125899906842624L) {
                formatSize = df.format((double) fileSize / 1.099511627776E12D) + "TB";
            } else if (fileSize < 1152921504606846976L) {
                formatSize = df.format((double) fileSize / 1.125899906842624E15D) + "PB";
            } else {
                formatSize = df.format((double) fileSize / 1.15292150460684698E18D) + "EB";
            }

            if ("0.00B".equals(formatSize)) {
                formatSize = "0B";
            }

            return formatSize;
        }
    }

    public static boolean hasSDCard() {
        String sDCardStatus = Environment.getExternalStorageState();
        boolean status;
        if (sDCardStatus.equals("mounted")) {
            status = true;
        } else {
            status = false;
        }

        return status;
    }

    public static String getSDCardRootPath() {
        return hasSDCard() ? Environment.getExternalStorageDirectory().getAbsolutePath() : "";
    }

    public static String getPhoneStorageRootPath() {
        return Environment.getDataDirectory().getAbsolutePath();
    }

    public static String getFileName(String filePath) {
        return filePath != null && !"".equals(filePath) ? filePath.substring(filePath.lastIndexOf(File.separator) + 1) : "";
    }

    public static long getFileSize(String filePath) {
        long size = 0L;
        File file = new File(filePath);
        if (file != null && file.exists()) {
            size = file.length();
        }
        return size;
    }

    public static long getFreeDiskSpace() {
        String status = Environment.getExternalStorageState();
        long freeSpace = 0L;
        if (status.equals("mounted")) {
            try {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = (long) stat.getBlockSize();
                long availableBlocks = (long) stat.getAvailableBlocks();
                freeSpace = availableBlocks * blockSize;
            } catch (Exception var9) {
                var9.printStackTrace();
            }

            return freeSpace;
        } else {
            return -1L;
        }
    }

    public static long getUsedDiskSpace() {
        String status = Environment.getExternalStorageState();
        long usedSpace = 0L;
        if (status.equals("mounted")) {
            try {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = (long) stat.getBlockSize();
                long availableBlocks = (long) stat.getAvailableBlocks();
                long totalBlocks = (long) stat.getBlockCount();
                usedSpace = blockSize * (totalBlocks - availableBlocks);
            } catch (Exception var11) {
                var11.printStackTrace();
            }

            return usedSpace;
        } else {
            return -1L;
        }
    }

    public static boolean renamePath(String oldName, String newName) {
        File f = new File(oldName);
        return f.renameTo(new File(newName));
    }

    public static boolean deleteFileWithPath(String filePath) {
        SecurityManager checker = new SecurityManager();
        File f = new File(filePath);
        checker.checkDelete(filePath);
        if (f.isFile()) {
            f.delete();
            return true;
        } else {
            return false;
        }
    }


    public static synchronized void deleteAllFiles(String dir) {
        File file = new File(dir);
        if (file.exists() && file.isDirectory()) {
            File[] childFiles = file.listFiles();
            for (int i = 0; i < childFiles.length; ++i) {
                File f = childFiles[i];
                if (f.isFile()) {
                    f.delete();
                }
            }
        }
    }

    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0L;
        } else if (!dir.isDirectory()) {
            return 0L;
        } else {
            long dirSize = 0L;
            File[] files = dir.listFiles();
            File[] var4 = files;
            int var5 = files.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                File file = var4[var6];
                if (file.isFile()) {
                    dirSize += file.length();
                } else if (file.isDirectory()) {
                    dirSize += file.length();
                    dirSize += getDirSize(file);
                }
            }

            return dirSize;
        }
    }

    public static void write(Context context, String fileName, String content) {
        if (content == null) {
            content = "";
        }
        try {
            FileOutputStream fos = context.openFileOutput(fileName, 0);
            fos.write(content.getBytes());
            fos.close();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static void unzip(File zipFile, String unzipPath, FileUtils.ZipResultCallback callback) {
        if (zipFile == null) {
            Logger.w(TAG, "[解压失败] " + zipFile.toString());
            if (callback != null) {
                callback.onUnZipFailed();
            }

        } else {
            InputStream in = null;
            FileOutputStream out = null;

            try {
                ZipFile zip = new ZipFile(zipFile);
                Enumeration entries = zip.entries();

                while (true) {
                    String outPath;
                    do {
                        if (!entries.hasMoreElements()) {
                            return;
                        }

                        ZipEntry entry = (ZipEntry) entries.nextElement();
                        String zipEntryName = entry.getName();
                        in = zip.getInputStream(entry);
                        outPath = (unzipPath + File.separator + zipEntryName).replaceAll("\\*", "/");
                        File file = new File(outPath.substring(0, outPath.lastIndexOf(47)));
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                    } while ((new File(outPath)).isDirectory());

                    out = new FileOutputStream(outPath);
                    byte[] buf1 = new byte[1024];

                    int len;
                    while ((len = in.read(buf1)) > 0) {
                        out.write(buf1, 0, len);
                    }
                }
            } catch (IOException var21) {
                zipFile.delete();
                Logger.w(TAG, "[解压失败] " + zipFile.toString());
                var21.printStackTrace();
                if (callback != null) {
                    callback.onUnZipFailed();
                }
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }

                    if (out != null) {
                        out.close();
                    }
                } catch (IOException var20) {
                    Logger.w(TAG, "[解压失败] " + zipFile.toString());
                    var20.printStackTrace();
                    zipFile.delete();
                    if (callback != null) {
                        callback.onUnZipFailed();
                    }
                }

                if (callback != null) {
                    Logger.w(TAG, "[解压完成] " + zipFile.toString());
                    callback.onUnZipFinished(unzipPath);
                }
            }

        }
    }

    public static synchronized void writeCache(Context context, String fileName, String content) {
        String dir = getCacheDir(context);
        File file = new File(dir + File.separator + fileName);
        FileWriter writer = null;
        BufferedWriter bufW = null;

        try {
            writer = new FileWriter(file);
            bufW = new BufferedWriter(writer);
            bufW.write(content);
        } catch (IOException var74) {
            var74.printStackTrace();
        } finally {
            if (bufW != null) {
                try {
                    bufW.close();
                } catch (IOException var73) {
                    var73.printStackTrace();
                } finally {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (IOException var72) {
                            var72.printStackTrace();
                        }
                    }
                }
            }
        }

    }

    public static synchronized String readCache(Context context, String fileName) {
        String dir = getCacheDir(context);
        File file = new File(dir + File.separator + fileName);
        FileReader reader = null;
        BufferedReader bufR = null;
        StringBuilder sBuilder = new StringBuilder();

        try {
            reader = new FileReader(file);
            bufR = new BufferedReader(reader);

            String line;
            while ((line = bufR.readLine()) != null) {
                sBuilder.append(line);
            }
        } catch (IOException var75) {
            var75.printStackTrace();
            sBuilder = null;
        } finally {
            if (bufR != null) {
                try {
                    bufR.close();
                } catch (IOException var73) {
                    var73.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException var72) {
                            var72.printStackTrace();
                        }
                    }

                }
            }

        }

        return sBuilder == null ? null : sBuilder.toString();
    }

    public static String readAssetsText(AssetManager manager, String fileName) {
        StringBuilder sBuilder = new StringBuilder();

        try {
            InputStream is = manager.open(fileName);
            byte[] buffer = new byte[5120];

            int len;
            while ((len = is.read(buffer)) != -1) {
                byte[] temp = new byte[len];
                System.arraycopy(buffer, 0, temp, 0, len);
                sBuilder.append(new String(temp));
            }
        } catch (IOException var7) {
            var7.printStackTrace();
        }

        return sBuilder.toString();
    }

    public static String getCacheDir(Context context) {
        if (context == null) {
            return null;
        } else {
            return hasSDCard() ? getSDCacheDir(context) : getDataCacheDir(context);
        }
    }

    public static String getDataCacheDir(Context context) {
        return context == null ? null : context.getCacheDir().toString();
    }

    public static String getSDCacheDir(Context context) {
        return context == null ? null : context.getExternalCacheDir().toString();
    }

    public static void test(Context context) {
        System.out.println("getDataDirectory== " + Environment.getDataDirectory());
        System.out.println("getExternalStorageState== " + Environment.getExternalStorageState());
        System.out.println("getDownloadCacheDirectory== " + Environment.getDownloadCacheDirectory());
        System.out.println("context.getCacheDir()== " + context.getCacheDir());
        System.out.println("getRootDirectory== " + Environment.getRootDirectory());
        System.out.println("getExternalStorageDirectory== " + Environment.getExternalStorageDirectory());
        String sdPath = getSDCardRootPath();
        System.out.println("SD路径== " + sdPath);
        String sdUsedSize = formatSize(getUsedDiskSpace());
        System.out.println("SD卡已用空间== " + sdUsedSize);
        String sdFreeSize = formatSize(getFreeDiskSpace());
        System.out.println("SD卡剩余空间== " + sdFreeSize);
        String phonePath = getPhoneStorageRootPath();
        System.out.println("内置存储路径== " + phonePath);
    }

    public interface ZipResultCallback {
        void onUnZipFailed();

        void onUnZipFinished(String var1);
    }

}