package com.android.architecture.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/19
 * Modify date: 2022/7/19
 * Version: 1
 */
public class CloseableUtils {

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
