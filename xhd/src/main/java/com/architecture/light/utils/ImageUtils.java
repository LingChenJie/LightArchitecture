package com.architecture.light.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;

/**
 * File describe:
 * Author: SuQi
 * Create date: 9/9/22
 * Modify date: 9/9/22
 * Version: 1
 */
public class ImageUtils {

    public static void saveImage(View view, String filePath) {
        Bitmap bitmap = loadBitmapFromView(view);
        FileOutputStream fos;
        try {
            File file = new File(filePath);
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        v.layout(0, 0, w, h);
        v.draw(c);
        return bmp;
    }

    // 为图片target添加水印
    private static Bitmap createWatermarkBitmap(Bitmap target, String str) {
        int w = target.getWidth();
        int h = target.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint p = new Paint();
        // 水印的颜色
        p.setColor(Color.RED);
        // 水印的字体大小
        p.setTextSize(16);
        p.setAntiAlias(true);// 去锯齿
        canvas.drawBitmap(target, 0, 0, p);
        // 在中间位置开始添加水印
        canvas.drawText(str, w / 2, h / 2, p);
        canvas.save();
        canvas.restore();
        return bmp;
    }

}
