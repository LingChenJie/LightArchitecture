package com.android.architecture.utils;

import com.android.architecture.helper.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/20
 * Modify date: 2022/10/20
 * Version: 1
 */
public class ZipUtils {

    private static final String TAG = ZipUtils.class.getSimpleName();
    private static final int BUFFER_SIZE = 1024 * 4;

    /**
     * 压缩文件
     *
     * @param srcPath  要压缩路径
     * @param destPath 输出zip文件路径
     */
    public static boolean zip(String srcPath, String destPath) {
        ZipOutputStream out = null;
        try {
            File outFile = new File(destPath);
            if (outFile.exists()) outFile.delete();
            File srcFile = new File(srcPath);
            if (!srcFile.exists()) {
                return false;
            }
            out = new ZipOutputStream(new FileOutputStream(outFile));
            if (srcFile.isFile()) {
                zipFileOrDirectory(out, srcFile, "");
            } else {
                File[] entries = srcFile.listFiles();
                if (entries != null) {
                    for (File entry : entries) {
                        zipFileOrDirectory(out, entry, "");
                    }
                }
            }
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            CloseableUtils.close(out);
        }
        return false;
    }

    /**
     * 压缩文件或目录
     *
     * @param out          压缩输出流
     * @param file         要压缩的文件或目录
     * @param relativePath 相对路径，根目录传空
     * @throws IOException
     */
    private static void zipFileOrDirectory(ZipOutputStream out, File file, String relativePath) {
        FileInputStream input = null;
        try {
            if (file.isFile()) {
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytes_read;
                input = new FileInputStream(file);
                ZipEntry entry = new ZipEntry(relativePath + file.getName());
                out.putNextEntry(entry);
                while ((bytes_read = input.read(buffer)) != -1) {
                    out.write(buffer, 0, bytes_read);
                }
                out.closeEntry();
            } else {
                File[] entries = file.listFiles();
                if (entries != null) {
                    for (File entry : entries) {
                        zipFileOrDirectory(out, entry, relativePath + file.getName() + File.separator);
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            // throw ex;
        } finally {
            CloseableUtils.close(input);
        }
    }


    /**
     * 解压文件
     *
     * @param zipFile   被解压的文件
     * @param unzipPath 解压的路径
     */
    public static boolean unzip(File zipFile, String unzipPath) {
        if (zipFile == null) {
            Logger.w(TAG, "[解压失败]" + zipFile);
            return false;
        } else {
            InputStream input = null;
            OutputStream output = null;
            try {
                ZipFile zip = new ZipFile(zipFile);
                Enumeration<? extends ZipEntry> entries = zip.entries();
                String outPath = null;
                do {
                    if (!entries.hasMoreElements()) {
                        break;
                    }
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    String zipEntryName = entry.getName();
                    input = new BufferedInputStream(zip.getInputStream(entry));
                    outPath = (unzipPath + File.separator + zipEntryName).replaceAll("\\*", "/");
                    File file = new File(outPath.substring(0, outPath.lastIndexOf(47)));
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                } while ((new File(outPath)).isDirectory());

                output = new BufferedOutputStream(new FileOutputStream(outPath));
                byte[] buffer = new byte[BUFFER_SIZE];
                int len;
                while ((len = input.read(buffer)) != -1) {
                    output.write(buffer, 0, len);
                    output.flush();
                }
                Logger.w(TAG, "[解压完成]");
                return true;
            } catch (IOException var21) {
                Logger.w(TAG, "[解压失败] " + zipFile);
                var21.printStackTrace();
            } finally {
                try {
                    if (input != null) {
                        input.close();
                    }
                    if (output != null) {
                        output.close();
                    }
                } catch (IOException var20) {
                    Logger.w(TAG, "[解压失败] " + zipFile);
                    var20.printStackTrace();
                }
            }
            return false;
        }
    }
}
