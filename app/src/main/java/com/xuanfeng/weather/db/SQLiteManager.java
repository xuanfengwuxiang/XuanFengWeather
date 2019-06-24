package com.xuanfeng.weather.db;

import android.content.Context;
import android.database.Cursor;

import com.xuanfeng.weather.XuanFengApplication;
import com.xuanfeng.weather.greendao.AppKv;
import com.xuanfeng.mylibrary.utils.StringUtils;

/**
 * Created by zhujh on 2017/8/2.
 * 描述：
 */

public class SQLiteManager {

    private volatile static SQLiteManager instance;
    private Context mContext;

    public SQLiteManager() {
        mContext = XuanFengApplication.getApplication();
    }

    public static SQLiteManager instance() {
        if (instance == null) {
            synchronized (SQLiteManager.class) {
                if (instance == null) {
                    instance = new SQLiteManager();
                }
            }
        }
        return instance;
    }

    /**
     * 保存或更新AppKv数据
     *
     * @param appKv
     */
    public void insertOrUpdateAppKvData(AppKv appKv) {
        StringBuffer sql = new StringBuffer();
        sql.append("select _id from APP_KV ");
        sql.append(" where APP_KEY='" + appKv.getAppKey() + "'");
        int id = -1;
        Cursor cursor = GreenDao.getDaoSession(mContext).getDatabase().rawQuery(sql.toString
                (), null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getInt(0);
        }
        cursor.close();
        if (id == -1) {
            String sqlExec = "insert into APP_KV (APP_KEY,APP_VAL) values(?,?)";
            Object[] obj = {appKv.getAppKey(), appKv.getAppVal()};
            GreenDao.getDaoSession(mContext).getDatabase().execSQL(sqlExec, obj);
        } else {
            String sqlExec = "update APP_KV set APP_VAL = ?where" + " _id = ?";
            Object[] obj = {appKv.getAppVal(), id};
            GreenDao.getDaoSession(mContext).getDatabase().execSQL(sqlExec, obj);
        }
    }

    public AppKv getAppKvDataSQL(String key) {
        AppKv appKv = null;
        if (!StringUtils.isEmpty(key)) {
            StringBuilder sql = new StringBuilder();
            sql.append("select * from APP_KV where APP_KEY = '" + key + "'");
            Cursor cursor = GreenDao.getDaoSession(mContext).getDatabase().rawQuery(sql.toString(), null);
            if (cursor != null) {
                try {
                    if(cursor.moveToFirst()){
                        long id = cursor.getLong(cursor.getColumnIndex("_id"));
                        String appVal = cursor.getString(cursor.getColumnIndex("APP_VAL"));
                        appKv = new AppKv();
                        appKv.setId(id);
                        appKv.setAppKey(key);
                        appKv.setAppVal(appVal);

                    }
                } finally {
                    cursor.close();
                }
            }
        }
        return appKv;
    }
}
