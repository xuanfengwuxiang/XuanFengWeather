package com.xuanfeng.mylibrary.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;

/**
 * Created by xuanfengwuxiang on 2018/3/15.
 */

public class ScreenUtil {
    private ScreenUtil() {
    }

    private static volatile PowerManager.WakeLock mWakeLock = null;

    //设置屏幕常亮
    public static void setScreenOn(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService(context.POWER_SERVICE);
        mWakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Lock");
        mWakeLock.acquire();
    }

    //取消屏幕常亮
    public static void cancelScreenOn() {
        if (mWakeLock != null) {
            mWakeLock.release();
            mWakeLock = null;
        }
    }

    //如果需要实现亮度调节，首先需要设置屏幕亮度调节模式为手动模式。
    public static void setScrennManualMode(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        try {
            int mode = Settings.System.getInt(contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE);
            if (mode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
                Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }

    //获取屏幕亮度值
    public static int getScreenBrightness(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        int defVal = 125;
        return Settings.System.getInt(contentResolver,
                Settings.System.SCREEN_BRIGHTNESS, defVal);
    }

    //设置系统屏幕亮度值
    public static void saveScreenBrightness(Context context,int value) {
        setScrennManualMode(context);
        ContentResolver contentResolver = context.getContentResolver();
        Settings.System.putInt(contentResolver,
                Settings.System.SCREEN_BRIGHTNESS, value);
    }

    //view截图
    public static Bitmap getBitmap(View v) {
        if (v == null) {
            return null;
        }
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        Bitmap bitmap = v.getDrawingCache();
        return bitmap;
    }

    //微信小程序分享-专用图片裁剪 5：4
    public static Bitmap getCropForMiniProgram(Bitmap bitmap) {
        // 得到图片的宽，高
        int w = bitmap.getWidth();
        int h = (int) (w * (4.0f / 5.0f));
        int retY = (int) (w * (1.0f / 10.0f));
        if (retY + h > bitmap.getHeight()) {
            return bitmap;
        }
        return Bitmap.createBitmap(bitmap, 0, retY, w, h, null, false);
    }

}
