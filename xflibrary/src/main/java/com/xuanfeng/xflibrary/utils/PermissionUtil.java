package com.xuanfeng.xflibrary.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

/**
 * 权限工具
 */
public class PermissionUtil {


    //判断是否缺少权限
    public boolean lacksPermissions(Context context, String[] permissions) {
        for (String permission : permissions) {
            if (lacksPermission(context, permission)) {
                return true;
            }
        }

        return false;
    }

    //判断是否缺少权限
    private static boolean lacksPermission(Context mContexts, String permission) {
        return ContextCompat.checkSelfPermission(mContexts, permission) == PackageManager.PERMISSION_DENIED;
    }

}
