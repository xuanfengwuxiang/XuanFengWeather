package com.xuanfeng.xuanfengweather.db;

import android.content.Context;

import com.xuanfeng.xuanfengweather.greendao.DaoMaster;
import com.xuanfeng.xuanfengweather.greendao.DaoSession;

/**
 * Created by zhujh on 2017/8/2.
 * 描述：DaoMaster/DaoSession的工厂类
 */

public class GreenDao {
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "APP_KV", null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
}
