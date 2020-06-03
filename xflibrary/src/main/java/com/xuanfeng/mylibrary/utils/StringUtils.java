package com.xuanfeng.mylibrary.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhujh on 2017/7/21.
 * 描述：字符串处理
 */

public class StringUtils {
    private static final String TAG = "StringUtils";

    private StringUtils() {
    }

    //将json转化为对象.
    public static Object fromJson(String json, Class<?> clazz) {
        Object obj = null;
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            obj = gson.fromJson(json, clazz);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return obj;
    }

    //将json转化为列表1.
    public static <T> List<T> fromJson(Object json, TypeToken<T> typeToken) {
        if (json == null) {
            return new ArrayList<>();
        }
        return fromJson(json.toString(), typeToken);
    }

    //将json转化为列表2(1调用).
    public static <T> List<T> fromJson(String json, TypeToken<T> typeToken) {
        List<T> list = null;
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            Type type = typeToken.getType();
            list = gson.fromJson(json, type);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return list;
    }

    //将对象转化为json.
    public static String toJson(Object src) {
        String json = null;
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            json = gson.toJson(src);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return json;
    }

    //判断给定字符串是否空白串 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str) || "null".equals(str);
    }

}
