package com.xuanfeng.xuanfengweather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.xuanfeng.mylibrary.widget.NoScrollViewPager;
import com.xuanfeng.xuanfengweather.module.media.fragment.MediaFragment;
import com.xuanfeng.xuanfengweather.module.news.NewsFragment;
import com.xuanfeng.xuanfengweather.module.weather.fragment.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuanfengwuxiang on 2018/8/1.
 */

public class MainUtil {

    private static long mExitTime = 0;

    //退出app
    public static void exitApp(Activity activity) {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(activity, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            activity.finish();
            try {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                Process.killProcess(Process.myPid());//从操作系统中结束掉当前程序的进程
                System.exit(0);//退出JVM
            } catch (Exception e) {
                Process.killProcess(Process.myPid());
                e.printStackTrace();
            }
        }
    }

    //设置导航按钮监听
    public static void setCheckListener(RadioGroup mRbParent, final Context context, final NoScrollViewPager mVpMain,
                                        final LinearLayout mActivityMain) {
        mRbParent.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_one:
                        mVpMain.setCurrentItem(0, false);
                        mActivityMain.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_sunny_day));
                        break;
                    case R.id.rb_two:
                        mVpMain.setCurrentItem(1, false);
                        mActivityMain.setBackgroundColor(context.getResources().getColor(R.color.baseThemeColor));
                        break;
                    case R.id.rb_three:
                        mVpMain.setCurrentItem(2, false);
                        mActivityMain.setBackgroundColor(context.getResources().getColor(R.color.baseThemeColor));
                        break;
                }
            }
        });
    }

    //设置viewpager的适配器
    public static void setViewPagerAdapter(AppCompatActivity activity, NoScrollViewPager mVpMain) {
        List<Fragment> list = new ArrayList<>();
        list.add(new WeatherFragment());
        list.add(new MediaFragment());
        list.add(new NewsFragment());
        FragmentPagerAdapter fragmentPagerAdapter = new ViewPagerAdapter(activity.getSupportFragmentManager(), list);
        mVpMain.setAdapter(fragmentPagerAdapter);
        mVpMain.setOffscreenPageLimit(2);
    }

}
