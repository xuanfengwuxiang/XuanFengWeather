package com.xuanfeng.xuanfengweather.module.gallery.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuanfeng.xuanfengweather.R;
import com.xuanfeng.xuanfengweather.ViewPagerAdapter;
import com.xuanfeng.xuanfengweather.base.BaseFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

//第二个模块的总fragment
public class EntertainmentFragment extends BaseFragment {
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    Unbinder unbinder;
    private GalleryFragment mGalleryFragment;
    private List<String> mTitleDataList;
    private List<Fragment> mFragmentList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_entertainment, container, false);
        unbinder = ButterKnife.bind(this, mContentView);
        initViews();
        setListeners();
        initData();
        return mContentView;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initData() {
        setViewPagerAdapter();
        initMagicIndicator();
    }
    //设置ViewPager的适配器
    private void setViewPagerAdapter() {
        mFragmentList = new ArrayList<>();
        mGalleryFragment = new GalleryFragment();
        mFragmentList.add(mGalleryFragment);
        mFragmentList.add(new PhotoShowFragment());
        mFragmentList.add(new FaceDetectorFragment());
        FragmentPagerAdapter fragmentPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), mFragmentList);
        mViewPager.setAdapter(fragmentPagerAdapter);
        mViewPager.setOffscreenPageLimit(1);
    }
    //初始化设置ViewPager的适配器指示器
    private void initMagicIndicator() {
        mTitleDataList = new ArrayList<>();
        mTitleDataList.add("视频");
        mTitleDataList.add("图片");
        mTitleDataList.add("人脸识别");
        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        if (mTitleDataList.size() > 4) {//大于4个，可滑动模式
            commonNavigator.setFollowTouch(true);
        } else {//小于等于4，固定模式
            commonNavigator.setAdjustMode(true);
        }
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitleDataList == null ? 0 : mTitleDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
                colorTransitionPagerTitleView.setSelectedColor(mContext.getResources().getColor(R.color.baseThemeColor));
                colorTransitionPagerTitleView.setText(mTitleDataList.get(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(mContext.getResources().getColor(R.color.baseThemeColor));
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
