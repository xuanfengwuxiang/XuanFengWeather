package com.xuanfeng.xuanfengweather.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.xuanfeng.mylibrary.utils.SystemUtils;
import com.xuanfeng.xuanfengweather.BuildConfig;
import com.xuanfeng.xuanfengweather.R;
import com.xuanfeng.xuanfengweather.variable.Variable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by zhujh on 2017/7/25.
 * 描述：系统配置信息工具
 */

public class SystemPropertyUtil {
    public static void initSystemProperties(Context context){
        InputStream is = context.getResources().openRawResource(R.raw.system);
        Properties properties = new Properties();
        try {
            properties.load(is);
            getBaseProperties(context,properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //将系统配置信息存入全局变量
    private static void getBaseProperties(Context context, Properties properties) {

        if(BuildConfig.DEBUG){//debug版本保存crash信息，重启APP
            Variable.CRASH_2_FILE = Boolean.parseBoolean(properties.getProperty("crash2file","false"));
            Variable.IS_RELOAD_APP = Boolean.parseBoolean(properties.getProperty("reloadapp","false"));
        }

        Variable.USER_DATABASE_VERSION = Integer.parseInt(properties.getProperty(
                "udbversion", "1"));
        Variable.BASE_DATABASE_VERSION = Integer.parseInt(properties.getProperty(
                "bdbversion", "1"));

        Variable.DATABASE_NAME_BASE = properties.getProperty("dbname1");
        Variable.DATABASE_NAME_USER = properties.getProperty("dbname2");

        try {
            Variable.FILE_PATH = Environment.getExternalStorageDirectory()
                    + properties.getProperty("filepath");
            Variable.LOG_PATH = Environment.getExternalStorageDirectory()
                    + properties.getProperty("logpath");
            Variable.CACHE_PATH = Environment.getExternalStorageDirectory()
                    + properties.getProperty("cachepath");
            Variable.IMAGE_PATH = Environment.getExternalStorageDirectory()
                    + properties.getProperty("imagepath");
        } catch (Exception e) {
            Variable.FILE_PATH = context.getCacheDir() + "/";
            Variable.LOG_PATH = context.getCacheDir() + "/";
            Variable.CACHE_PATH = context.getCacheDir() + "/";
            Variable.IMAGE_PATH = context.getCacheDir() + "/";
        }

        try {
            SystemUtils.createFolder(Variable.FILE_PATH);
            SystemUtils.createFolder(Variable.LOG_PATH);
            SystemUtils.createFolder(Variable.CACHE_PATH);
        } catch (Exception e) {

        }
        Variable.PACKAGE_NAME = context.getPackageName();
        Variable.APP_VERSION_CODE = String.valueOf(SystemUtils
                .getVersionCode(context));
        Variable.APP_VERSION_NAME = SystemUtils.getVersionName(context);
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(Variable.PACKAGE_NAME,
                    PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                Variable.APP_CHANNEL = appInfo.metaData.getString("APP_CHANNEL");
            } else {
                Variable.APP_CHANNEL = "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
