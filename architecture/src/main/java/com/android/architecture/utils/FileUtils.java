package com.android.architecture.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.text.DecimalFormat;

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

    /**
     * 拷贝文件到对应的路径
     *
     * @param file
     * @param destPath
     */
    public static boolean copyFile(File file, String destPath) {
        if ((file == null) || (destPath == null)) {
            return false;
        }
        File destFile = new File(destPath);
        if (destFile.exists()) {
            boolean delete = destFile.delete();// delete file
            System.out.println("删除旧文件：" + delete);
        }
        try {
            boolean newFile = destFile.createNewFile();
            System.out.println("createNewFile：" + newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            fileOutputStream = new FileOutputStream(destFile);
            copy(fileInputStream.getChannel(), fileOutputStream.getChannel());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseableUtils.close(fileInputStream);
            CloseableUtils.close(fileOutputStream);
        }
        return true;
    }

    public static void copy(@NonNull ReadableByteChannel input, @NonNull FileChannel output) throws IOException {
        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                output.transferFrom(input, 0, Long.MAX_VALUE);
            } else {
                InputStream inputStream = Channels.newInputStream(input);
                OutputStream outputStream = Channels.newOutputStream(output);
                int length;
                byte[] buffer = new byte[1024 * 4];
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            }
            output.force(false);
        } finally {
            input.close();
            output.close();
        }
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

}
