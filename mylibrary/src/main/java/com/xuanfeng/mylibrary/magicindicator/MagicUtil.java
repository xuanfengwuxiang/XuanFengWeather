package com.xuanfeng.mylibrary.magicindicator;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.List;

/**
 * Created by xuanfengwuxiang on 2018/8/2.
 * MagicIndicator的工具类
 */

public class MagicUtil {

    //设置MagicIndicator的适配器
    public static void setMagicAdapter(Context context, final List<MagicBean> mTitleList, MagicIndicator indicator, final int textColor,
                                       final int indicatorColor, ViewPager viewPager, final MagicListener listener) {
        if (context == null || mTitleList == null || indicator == null || listener == null) {
            return;
        }
        //设置滑动模式
        final CommonNavigator commonNavigator = new CommonNavigator(context);
        if (mTitleList.size() > 4) {//大于4个，可滑动模式
            commonNavigator.setFollowTouch(true);
        } else {//小于等于4，固定模式
            commonNavigator.setAdjustMode(true);
        }

        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitleList == null ? 0 : mTitleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
                colorTransitionPagerTitleView.setSelectedColor(textColor);
                colorTransitionPagerTitleView.setText(mTitleList.get(index).getTittle());
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        notifyDataSetChanged();
                        listener.onClick(index, mTitleList.get(index).getKey());
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(indicatorColor);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;
            }
        });
        indicator.setNavigator(commonNavigator);
        if (viewPager != null) {
            ViewPagerHelper.bind(indicator, viewPager);
        }
    }
}
