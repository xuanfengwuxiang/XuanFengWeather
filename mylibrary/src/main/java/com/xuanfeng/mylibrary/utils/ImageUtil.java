package com.xuanfeng.mylibrary.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by xuanfengwuxiang on 2018/3/12.
 */

public class ImageUtil {

    //修改控件的图片颜色   R  G   B范围0-255
    public static Bitmap getColorBitmap(Bitmap bitmap, float R, float G, float B, float A) {
        Bitmap updateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(updateBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿的画笔
        paint.setAntiAlias(true);
        Matrix matrix = new Matrix();
        ColorMatrix cm = new ColorMatrix();
        cm.set(new float[]{
                R / 128f, 0, 0, 0, 0,// 红色值
                0, G / 128f, 0, 0, 0,// 绿色值
                0, 0, B / 128f, 0, 0,// 蓝色值
                0, 0, 0, A / 128f, 0 // 透明度
        });
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bitmap, matrix, paint);
        return updateBitmap;
    }

    //保存位图到SD卡
    public static boolean saveBitmapToSD(Bitmap bitmap, String path, String fileName) {
        boolean isSuccessful = false;
        if (bitmap == null) {
            return false;
        }
        File file = new File(path, fileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            isSuccessful = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccessful;
    }
}
