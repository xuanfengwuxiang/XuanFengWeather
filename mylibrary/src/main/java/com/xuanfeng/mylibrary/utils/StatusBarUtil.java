package com.xuanfeng.mylibrary.utils;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by zhujh on 2017/7/21.
 * 描述：状态栏的工具
 */

public class StatusBarUtil {


    public static void setStatusBarColor(Activity activity, int colorResId, Boolean isIconDark) {
        if (activity == null) {
            return;
        }
        switch (SystemUtils.getPhoneBrand()) {
            case "Meizu"://魅族
                setStatusBarTransparent(activity);
                FlymeSetStatusBarLightMode(activity.getWindow(), isIconDark);
            case "Xiaomi"://小米
                setStatusBarTransparent(activity);
                MIUISetStatusBarLightMode(activity.getWindow(), isIconDark);
                break;
            default://其他
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0以上
                    setStatusBarColorForM(activity, colorResId);
                    setStatusBarIconColorForM(activity, isIconDark);
                } else {//6.0以下
                    setStatusBarTransparent(activity);
                }
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)//5.0才有状态栏颜色修改
    private static void setStatusBarColorForM(Activity activity, int colorResId) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.setStatusBarColor(activity.getResources().getColor(colorResId));
    }

    //6.0才有的设置图标颜色
    private static void setStatusBarIconColorForM(Activity activity, Boolean isIconDark) {
       /* if (isIconDark) {//黑图标
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {//白图标
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_VISIBLE);
        }*/
        if (isIconDark) {//黑图标
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {//白图标
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

    //4.4以上有的透明状态栏
    public static void setStatusBarTransparent(Activity activity) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            if (mChildView instanceof DrawerLayout) {//DrawerLayout比较奇葩，只能设置其子孩子
                ViewCompat.setFitsSystemWindows(mChildView, false);
                if (mChildView instanceof ViewGroup) {
                    View v = ((ViewGroup) mChildView).getChildAt(0);
                    if (v != null) {
                        ViewCompat.setFitsSystemWindows(v, true);
                    }
                }
            } else {
                ViewCompat.setFitsSystemWindows(mChildView, true);            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
            }
        }
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window     需要设置的窗口
     * @param isIconDark 状态栏字体及图标是否深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean isIconDark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (isIconDark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                if (dark) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                } else {
                    /*int flag = window.getDecorView().getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                    window.getDecorView().setSystemUiVisibility(flag);​*/

                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_VISIBLE);
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }


}
