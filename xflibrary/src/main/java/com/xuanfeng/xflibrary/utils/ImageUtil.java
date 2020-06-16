package com.xuanfeng.xflibrary.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

/**
 * Created by xuanfengwuxiang on 2018/3/12.
 * 图片工具类
 */

public class ImageUtil {
    private static final String TAG = "ImageUtil";

    private ImageUtil() {
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
            if (!file.exists()) {
                file.createNewFile();
            }
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

    //从url获取bitmap
    public static Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            int length = http.getContentLength();

            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    /**
     * 调用拍照
     */
    public static void takePhoto(Activity activity, Uri outUri, int requestCode) {

        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);//将拍取的照片保存到指定URI
        activity.startActivityForResult(intent, requestCode);
    }


    /**
     * 选 相册图片
     */
    public static void selectFromGallery(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, requestCode);

    }

    //相册返回的uri转成Bitmap
    public static Bitmap getBitmapFromUri(Context context, Uri uri) {

        InputStream inputStream = null;
        try {
            inputStream = context.getContentResolver().openInputStream(uri);
            return BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //相册返回的uri转测path
    public static String getPathFromUri(Context context, Uri uri) {

        String scheme = uri.getScheme();
        if (ContentResolver.SCHEME_FILE.equals(scheme)) {//file:///开头
            return uri.getPath();
        }

        if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {//content://开头
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
            if (cursor == null) {
                return "";
            }
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String path = cursor.getString(columnIndex);  //获取照片路径
            cursor.close();
            return path;
        }
        return "";

    }

    /**
     * 系统裁剪
     *
     * @param inUri   输入路径
     * @param outUri  输出路径
     * @param outputX 输出宽
     * @param outputY 输出高
     * @param aspectX 输出X比
     * @param aspectY 输出Y比
     */
    public static void cropFromGallery(Activity activity, int requestCode, Uri inUri, Uri outUri, int outputX, int outputY, int aspectX, int aspectY) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(inUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);//裁剪后Uri路径
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//不加会出现无法加载此图片的错误
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);// 这两句是在7.0以上版本当targeVersion大于23时需要
        intent = Intent.createChooser(intent, "裁剪图片");
        activity.startActivityForResult(intent, requestCode);
    }
}
