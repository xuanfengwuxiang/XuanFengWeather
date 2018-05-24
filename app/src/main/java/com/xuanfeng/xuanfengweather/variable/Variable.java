package com.xuanfeng.xuanfengweather.variable;

/**
 * Created by zhujh on 2017/7/25.
 * 描述：全局变量
 */

public class Variable {
    //是否保存crash信息到文件
    public static boolean CRASH_2_FILE = false;
    //APP崩溃是否重启
    public static boolean IS_RELOAD_APP = false;
    //用户数据库版本（xf_user.sqlite）
    public static int USER_DATABASE_VERSION = 1;
    //base数据库版本（xf_base.sqlite）
    public static int BASE_DATABASE_VERSION = 1;
    //base库
    public static String DATABASE_NAME_BASE = "xf_base.sqlite";
    //user库
    public static String DATABASE_NAME_USER = "xf_user.sqlite";
    //文件缓存到手机中路径
    public static String FILE_PATH = "";
    //log缓存到手机中路径
    public static String LOG_PATH = "";
    //cache缓存到手机中路径
    public static String CACHE_PATH = "";
    //图片缓存目录
    public static String IMAGE_PATH = "";
    //应用程序包名
    public static String PACKAGE_NAME = "";
    //该应用程序的version_code
    public static String APP_VERSION_CODE = "0";
    //该应用程序的版本
    public static String APP_VERSION_NAME = "0.0.0";
    //APP渠道
    public static String APP_CHANNEL;

}
