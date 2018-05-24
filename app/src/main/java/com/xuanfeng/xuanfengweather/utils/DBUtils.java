package com.xuanfeng.xuanfengweather.utils;

import com.xuanfeng.mylibrary.utils.StringUtils;
import com.google.gson.reflect.TypeToken;
import com.xuanfeng.xuanfengweather.db.SQLiteManager;
import com.xuanfeng.xuanfengweather.greendao.AppKv;

/**
 * Created by zhujh on 2017/8/2.
 * 描述：
 */

public class DBUtils {
    /**
     * 保存或更新AppKv数据
     *
     * @param key
     * @param obj 自定义类型数据
     */
    public static void insertOrUpdateAppKvData(String key, Object obj) {
        String jsonStr = StringUtils.toJson(obj);
        insertOrUpdateAppKvData(key, jsonStr);
    }

    /**
     * 保存或更新AppKv数据
     *
     * @param key
     * @param value 数据
     */
    public static void insertOrUpdateAppKvData(String key, String value) {
        AppKv appKv = new AppKv();
        appKv.setAppKey(key);
        appKv.setAppVal(value);
        SQLiteManager.instance().insertOrUpdateAppKvData(appKv);
    }

    public static Object getAppKvDataValue(String key, Class css) {
        AppKv appKv = SQLiteManager.instance().getAppKvDataSQL(key);
        if (appKv != null && !StringUtils.isEmpty(appKv.getAppVal())) {
            return StringUtils.fromJson(appKv.getAppVal(), css);
        }
        return null;
    }

    public static Object getAppKvDataValue(String key, TypeToken<?> typeToken) {
        AppKv appKv = SQLiteManager.instance().getAppKvDataSQL(key);
        if (appKv != null && !StringUtils.isEmpty(appKv.getAppVal())) {
            return StringUtils.fromJson(appKv.getAppVal(),typeToken);
        }
        return null;
    }
}
