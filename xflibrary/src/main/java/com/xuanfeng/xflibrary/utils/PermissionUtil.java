package com.xuanfeng.xflibrary.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

/**
 * 权限工具
 */
public class PermissionUtil {


    //判断是否缺少权限
    public static boolean lacksPermissions(Context context, String[] permissions) {
        for (String permission : permissions) {
            if (lacksPermission(context, permission)) {
                return true;
            }
        }

        return false;
    }

    //判断是否缺少权限
    public static boolean lacksPermission(Context mContexts, String permission) {
        return ContextCompat.checkSelfPermission(mContexts, permission) == PackageManager.PERMISSION_DENIED;
    }

    //权限是否同意
    public static boolean isGranted(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * @param grantResults onRequestPermissionsResult方法中的参数
     * @return 所有权限 是否同意了
     */
    public static boolean isAllGranted(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
