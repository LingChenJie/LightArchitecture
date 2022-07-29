package com.android.architecture.utils;

import java.io.File;
import java.nio.charset.StandardCharsets;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/29
 * Modify date: 2022/7/29
 * Version: 1
 */
public class OkioUtils {

    public static void writeString(String path, String content, boolean append) {
        Sink sink = null;
        BufferedSink bufferedSink = null;
        try {
            if (append) {
                sink = Okio.appendingSink(new File(path));
            } else {
                sink = Okio.sink(new File(path));
            }
            bufferedSink = Okio.buffer(sink);
            bufferedSink.writeString(content, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseableUtils.close(bufferedSink);
            CloseableUtils.close(sink);
        }
    }

    public static String readString(String path) {
        Source source = null;
        BufferedSource bufferedSource = null;
        try {
            source = Okio.source(new File(path));
            bufferedSource = Okio.buffer(source);
            StringBuilder content = new StringBuilder();
            for (String line; (line = bufferedSource.readUtf8Line()) != null; ) {
                content.append(line);
            }
            return content.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseableUtils.close(bufferedSource);
            CloseableUtils.close(source);
        }
        return "";
    }

}
