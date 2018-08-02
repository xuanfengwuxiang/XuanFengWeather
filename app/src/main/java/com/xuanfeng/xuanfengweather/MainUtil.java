package com.xuanfeng.xuanfengweather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xuanfeng.mylibrary.rxbus.RxBean;
import com.xuanfeng.mylibrary.rxbus.RxBus;
import com.xuanfeng.mylibrary.utils.ImageUtil;
import com.xuanfeng.mylibrary.widget.NoScrollViewPager;
import com.xuanfeng.xuanfengweather.constant.RxConstant;
import com.xuanfeng.xuanfengweather.module.gallery.fragment.EntertainmentFragment;
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
    public static void setCheckListener(RadioGroup mRbParent, final Context context, final NoScrollViewPager mVpMain, final TextView mTvLeft,
                                        final ImageView mIvLeft, final LinearLayout mActivityMain, final RelativeLayout mRlHeader, final TextView mTvTitle, final RadioButton mRbOne
            , final RadioButton mRbTwo, final RadioButton mRbThree) {
        mRbParent.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_one:
                        mVpMain.setCurrentItem(0, false);
                        mTvLeft.setVisibility(View.VISIBLE);
                        mTvLeft.setText("南京市");
                        mTvLeft.setTextColor(Color.WHITE);
                        Drawable img = context.getResources().getDrawable(R.drawable.ic_add);
                        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                        mTvLeft.setCompoundDrawables(img, null, null, null);
                        mIvLeft.setVisibility(View.GONE);
                        mActivityMain.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_sunny_day));
                        mRlHeader.setBackgroundColor(context.getResources().getColor(R.color.color_transparent));
                        mRbOne.setChecked(true);
                        mTvTitle.setText("");
                        break;
                    case R.id.rb_two:
                        mVpMain.setCurrentItem(1, false);
                        mTvLeft.setVisibility(View.GONE);
                        mIvLeft.setVisibility(View.GONE);
                        mActivityMain.setBackgroundColor(context.getResources().getColor(R.color.baseThemeColor));
                        mRlHeader.setBackgroundColor(context.getResources().getColor(R.color.baseThemeColor));
                        mRbTwo.setChecked(true);
                        mTvTitle.setText("多媒体");
                        break;
                    case R.id.rb_three:
                        mVpMain.setCurrentItem(2, false);
                        mTvLeft.setVisibility(View.GONE);
                        mIvLeft.setVisibility(View.VISIBLE);
                        mActivityMain.setBackgroundColor(context.getResources().getColor(R.color.baseThemeColor));
                        mRlHeader.setBackgroundColor(context.getResources().getColor(R.color.baseThemeColor));
                        mRbThree.setChecked(true);
                        mTvTitle.setText("新闻");
                        mIvLeft.setImageBitmap(ImageUtil.getColorBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_back), 128f, 128f, 128f, 128f));
                        break;
                }
            }
        });
    }

    //设置viewpager的适配器
    public static void setViewPagerAdapter(AppCompatActivity activity, NoScrollViewPager mVpMain) {
        List<Fragment> list = new ArrayList<>();
        list.add(new WeatherFragment());
        list.add(new EntertainmentFragment());
        list.add(new NewsFragment());
        FragmentPagerAdapter fragmentPagerAdapter = new ViewPagerAdapter(activity.getSupportFragmentManager(), list);
        mVpMain.setAdapter(fragmentPagerAdapter);
        mVpMain.setOffscreenPageLimit(2);
    }

    //第3个界面，点击左边的返回按钮
    public static void doLeftClick(NoScrollViewPager mVpMain) {
        int currentIndex = mVpMain.getCurrentItem();
        if (2 == currentIndex) {
            RxBus.getInstance().send(new RxBean(RxConstant.NEWS_GO_BACK));
        }
    }
}
