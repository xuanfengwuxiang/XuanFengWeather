package com.xuanfeng.xuanfengweather.module.loseweight;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.xuanfeng.mylibrary.utils.ToastUtil;

//减肥计算器工具类
public class LoseWeightUtil {

    /**
     * 获取最佳有氧心率
     * 卡福能公式：针对身体素质较高的人群。
     * 目标心率=(220-年龄-静止心率)*(65%~85%)+静止心率
     */
    public static void getHeartRate(Context context, EditText etAge, EditText etStaticHeartRate, TextView tvBestHeartRate) {
        if (context == null || etAge == null || etStaticHeartRate == null || tvBestHeartRate == null) {
            return;
        }
        String age = etAge.getText().toString();
        String staticRate = etStaticHeartRate.getText().toString();
        if (TextUtils.isEmpty(age)) {
            ToastUtil.showToast(context, "请输入年龄");
            return;
        }
        if (TextUtils.isEmpty(staticRate)) {
            ToastUtil.showToast(context, "请输入静态心率");
            return;
        }
        try {
            int ageNum = Integer.parseInt(age);
            int staticRateNum = Integer.parseInt(staticRate);
            int low = (int) ((220 - ageNum - staticRateNum) * 0.5 + staticRateNum);
            int high = (int) ((220 - ageNum - staticRateNum) * 0.6 + staticRateNum);

            tvBestHeartRate.setText("最佳心率范围：" + low + "~" + high);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * (Resting Energy Expenditure，简称REE)
     * 女性 REE = (10 × 体重) +(6.25 × 身高) - (5 × 年龄) - 161
     * 男性 REE = (10 × 体重) +(6.25 × 身高) - (5 × 年龄) + 5
     */
    public static void getREE(Context context, EditText etSex, EditText etAge, EditText etWeight, EditText etHeight, TextView tvRee) {

        if (context == null || etSex == null || etAge == null || etWeight == null || etHeight == null || tvRee == null) {
            return;
        }
        String sex = etSex.getText().toString();
        String age = etAge.getText().toString();
        String weight = etWeight.getText().toString();
        String height = etHeight.getText().toString();
        if (TextUtils.isEmpty(sex)) {
            ToastUtil.showToast(context, "请输入性别");
            return;
        }
        if (TextUtils.isEmpty(age)) {
            ToastUtil.showToast(context, "请输入年龄");
            return;
        }
        if (TextUtils.isEmpty(weight)) {
            ToastUtil.showToast(context, "请输入体重");
            return;
        }
        if (TextUtils.isEmpty(height)) {
            ToastUtil.showToast(context, "请输入身高");
            return;
        }

        try {
            int ageNum = Integer.parseInt(age);
            int weightNum = Integer.parseInt(weight);
            int heightNum = Integer.parseInt(height);
            double ree = 10 * weightNum + 6.25 * heightNum - 5 * ageNum + (TextUtils.equals(sex, "男") ? 5 : (-161));
            tvRee.setText("REE值：" + (int) ree + "大卡");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 基础代谢率bmr
     * 男性: BMR = 66+(13.7× 体重(kg))+(5×身高(cm)) – (6.8×年龄(岁))
     * 女性: BMR = 655+(9.6×体重(kg))+(1.8×身高(cm))–(4.7×年龄(岁))
     */
    public static void getBmr(Context context, EditText etSex, EditText etAge, EditText etWeight, EditText etHeight, TextView tvBmr) {

        if (context == null || etSex == null || etAge == null || etWeight == null || etHeight == null || tvBmr == null) {
            return;
        }
        String sex = etSex.getText().toString();
        String age = etAge.getText().toString();
        String weight = etWeight.getText().toString();
        String height = etHeight.getText().toString();
        if (TextUtils.isEmpty(sex)) {
            ToastUtil.showToast(context, "请输入性别");
            return;
        }
        if (TextUtils.isEmpty(age)) {
            ToastUtil.showToast(context, "请输入年龄");
            return;
        }
        if (TextUtils.isEmpty(weight)) {
            ToastUtil.showToast(context, "请输入体重");
            return;
        }
        if (TextUtils.isEmpty(height)) {
            ToastUtil.showToast(context, "请输入身高");
            return;
        }

        try {
            int ageNum = Integer.parseInt(age);
            int weightNum = Integer.parseInt(weight);
            int heightNum = Integer.parseInt(height);
            double bmr = 0;
            if (TextUtils.equals("男", sex)) {
                bmr = 66 + 13.7 * weightNum + 5 * heightNum - 6.8 * ageNum;
            } else {
                bmr = 655 + 9.6 * weightNum + 1.8 * heightNum - 4.7 * ageNum;
            }
            tvBmr.setText("BMR值：" + (int) bmr + "大卡");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
