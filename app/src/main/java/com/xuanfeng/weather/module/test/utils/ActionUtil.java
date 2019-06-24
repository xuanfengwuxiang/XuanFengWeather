package com.xuanfeng.weather.module.test.utils;

public class ActionUtil {

    public static String getActionName(int code) {
        switch (code) {
            case 0:
                return "ACTION_DOWN";
             case 1:
                return "ACTION_UP";

             case 2:
                return "ACTION_MOVE";

             case 3:
                return "ACTION_CANCEL";

             case 4:
                return "ACTION_OUTSIDE";

        }
        return "其他action";
    }
}
