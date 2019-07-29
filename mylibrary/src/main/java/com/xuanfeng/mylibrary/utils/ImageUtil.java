package com.xuanfeng.mylibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

/**
 * Created by xuanfengwuxiang on 2018/3/12.
 */

public class ImageUtil {
    private static final String TAG = "ImageUtil";

    private ImageUtil() {
    }

    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context).asDrawable().load(url).into(imageView);
    }

    public static void loadImage(Context context, int resId, ImageView imageView) {
        Glide.with(context).asDrawable().load(resId).into(imageView);
    }

    public static void loadImage(Context context, int resId, View view) {

        int width = context.getResources().getDisplayMetrics().widthPixels;
        int height = context.getResources().getDisplayMetrics().heightPixels;
        Glide.with(context).asDrawable()
                .load(resId)
                .into(new SimpleTarget<Drawable>(width, height) {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                        view.setBackgroundDrawable(resource);
                    }
                });

    }

    //修改控件的图片颜色   R  G   B范围0-255
    public static Bitmap getColorBitmap(Bitmap bitmap, float r, float g, float b, float a) {
        Bitmap updateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(updateBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿的画笔
        paint.setAntiAlias(true);
        Matrix matrix = new Matrix();
        ColorMatrix cm = new ColorMatrix();
        cm.set(new float[]{
                r / 128f, 0, 0, 0, 0,// 红色值
                0, g / 128f, 0, 0, 0,// 绿色值
                0, 0, b / 128f, 0, 0,// 蓝色值
                0, 0, 0, a / 128f, 0 // 透明度
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
            Log.e(TAG, e.toString());
        }
        return isSuccessful;
    }

    //获取网络视频第一帧图片，待检验
    public static Bitmap getBitmap(Context context, String url, boolean isSD) {
        Bitmap bitmap = null;
        //MediaMetadataRetriever 是android中定义好的一个类，提供了统一
        //的接口，用于从输入的媒体文件中取得帧和元数据；
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (isSD) {
                //（）根据文件路径获取缩略图
                retriever.setDataSource(context, Uri.fromFile(new File(url)));
            } else {
                //根据网络路径获取缩略图
                retriever.setDataSource(url, new HashMap());
            }
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.toString());
        } finally {
            retriever.release();
        }
        return bitmap;
    }
}
