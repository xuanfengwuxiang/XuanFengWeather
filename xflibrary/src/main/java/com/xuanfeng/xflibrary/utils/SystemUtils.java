package com.xuanfeng.xflibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * Created by zhujh on 2017/7/24.
 * 描述：安卓系统相关工具
 */

public class SystemUtils {
    public static final String TAG = "SystemUtils";

    private SystemUtils() {
    }

    //获取屏幕高度
    public static final float getHeightInPx(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    //获取屏幕宽度
    public static final float getWidthInPx(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    // 取得版本号
    public static int getVersionCode(Context context) {
        try {
            PackageInfo manager = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return manager.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    // 取得版本名
    public static String getVersionName(Context context) {
        try {
            PackageInfo manager = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return manager.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "Unknown";
        }
    }

    //由指定的路径创建文件夹
    public static File createFolder(String path) {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        try (BufferedReader reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));) {

            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            Log.e(TAG, throwable.toString());
        }
        return null;
    }

    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                return intIP2StringIP(wifiInfo.getIpAddress());
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }


    /**
     * 获取当前activity
     *
     * @return
     */
    public static Activity getCurrentActivity() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(
                    null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities = (Map) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Activity activity = (Activity) activityField.get(activityRecord);
                    return activity;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPhoneBrand() {
        String brand = Build.BRAND;
        return brand;
    }

    public static boolean isDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //（实际上就是“sort_key”字段） 出来是首字母
    private static final String PHONE_BOOK_LABEL = "phonebook_label";
    //需要查询的字段
    private static final String[] CONTACTOR_ION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            PHONE_BOOK_LABEL
    };


    public static List<ContactsBean> getAllContacts(Context context) {

        List<ContactsBean> contacts = new ArrayList<>();
        Uri uri = ContactsContract.CommonDataKinds.Contactables.CONTENT_URI;
        //获取联系人。按首字母排序
        Cursor cursor = context.getContentResolver().query(uri, CONTACTOR_ION, null, null, ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY);
        if (cursor != null) {

            while (cursor.moveToNext()) {
                ContactsBean info = new ContactsBean();
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String firstChar = cursor.getString(cursor.getColumnIndex(PHONE_BOOK_LABEL));
                info.setName(name);
                info.setFirstChar(firstChar);
                info.setPhoneNum(phoneNum);
                contacts.add(info);
            }
            cursor.close();

        }

        return contacts;
    }

    public static class ContactsBean {
        private String name;//姓名
        private String phoneNum;//手机号
        private String firstChar;//姓名首字母

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public String getFirstChar() {
            return firstChar;
        }

        public void setFirstChar(String firstChar) {
            this.firstChar = firstChar;
        }
    }
}
