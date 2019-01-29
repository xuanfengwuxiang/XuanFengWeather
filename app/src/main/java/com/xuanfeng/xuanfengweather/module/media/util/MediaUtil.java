package com.xuanfeng.xuanfengweather.module.media.util;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xuanfeng.mylibrary.magicindicator.MagicBean;
import com.xuanfeng.mylibrary.magicindicator.MagicListener;
import com.xuanfeng.mylibrary.magicindicator.MagicUtil;
import com.xuanfeng.xuanfengweather.R;
import com.xuanfeng.xuanfengweather.ViewPagerAdapter;
import com.xuanfeng.xuanfengweather.module.media.fragment.ChatFragment;
import com.xuanfeng.xuanfengweather.module.media.fragment.FaceDetectorFragment;
import com.xuanfeng.xuanfengweather.module.media.fragment.VideoFragment;
import com.xuanfeng.xuanfengweather.module.media.fragment.PhotoShowFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuanfengwuxiang on 2018/8/20.
 */

public class MediaUtil {
    //初始化设置ViewPager的适配器指示器
    public static void initMagicIndicator(Context context, MagicIndicator mMagicIndicator, final ViewPager mViewPager) {
        List<MagicBean> titleList = new ArrayList<>();
        titleList.add(new MagicBean("视频", ""));
        titleList.add(new MagicBean("图片", ""));
        titleList.add(new MagicBean("人脸识别", ""));
        titleList.add(new MagicBean("AI智能聊天", ""));
        int baseThemeColor = context.getResources().getColor(R.color.baseThemeColor);
        MagicUtil.setMagicAdapter(context, titleList, mMagicIndicator, baseThemeColor, baseThemeColor, mViewPager, new MagicListener() {
            @Override
            public void onClick(int position, String key) {
                mViewPager.setCurrentItem(position);
            }
        });
    }

    //设置ViewPager的适配器
    public static void setViewPagerAdapter(Fragment fragment, ViewPager viewPager) {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new VideoFragment());
        fragmentList.add(new PhotoShowFragment());
        fragmentList.add(new FaceDetectorFragment());
        fragmentList.add(new ChatFragment());
        FragmentPagerAdapter fragmentPagerAdapter = new ViewPagerAdapter(fragment.getChildFragmentManager(), fragmentList);
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
    }

    public static void setTittle(Context context, ImageView mIvLeft, RelativeLayout mRlHeader, TextView mTvTittle) {
        mIvLeft.setVisibility(View.GONE);
        mRlHeader.setBackgroundColor(context.getResources().getColor(R.color.baseThemeColor));
        mTvTittle.setText("多媒体");
    }
}