package com.xuanfeng.xflibrary.utils;

import android.content.Context;

import java.io.File;

/**
 * App 工具
 */
public class AppUtil {

    private AppUtil() {
    }

    private static final String DIR_TEMP = "temp";


    //创建app 工作目录
    public static void initAppWorkPath(Context context) {

        FileUtil.createFolder(getAppRootPath(context));//创建跟目录
        FileUtil.createFolder(getAppTempPath(context));//创建temp目录
    }

    //获取 app 根目录
    public static String getAppRootPath(Context context) {
        return FileUtil.getSDPath(context);
    }

    //获取 app temp文件夹
    public static String getAppTempPath(Context context) {
        return getAppRootPath(context) + File.separator + DIR_TEMP;
    }
}
