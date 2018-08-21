package com.xuanfeng.xuanfengweather.module.test.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.xuanfeng.mylibrary.mvp.BaseActivity;
import com.xuanfeng.xuanfengweather.R;
import com.xuanfeng.xuanfengweather.module.test.adapter.ViewPagerPhotoTestAdapter;
import com.xuanfeng.xuanfengweather.widget.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 此界面实现，ViewPager一屏展示3张图片
 */

public class TestForGalleryActivity extends BaseActivity {

    @BindView(R.id.vp_photo)
    ViewPager mVpPhoto;
    @BindView(R.id.container)
    RelativeLayout mContainer;


    private void setViewPager() {
        List<Integer> photoUrls = new ArrayList<>();
        photoUrls.add(R.drawable.ic_scenery1);
        photoUrls.add(R.drawable.ic_scenery2);
        photoUrls.add(R.drawable.ic_scenery3);
        photoUrls.add(R.drawable.ic_scenery1);
        photoUrls.add(R.drawable.ic_scenery2);
        photoUrls.add(R.drawable.ic_scenery3);
        PagerAdapter pagerAdapter = new ViewPagerPhotoTestAdapter(this, photoUrls);
        mVpPhoto.setAdapter(pagerAdapter);
        mVpPhoto.setPageMargin(40);//设置页与页之间的间距
        mVpPhoto.setPageTransformer(true, new ZoomOutPageTransformer(this));//设置缩放动画
        mVpPhoto.setOffscreenPageLimit(3);//设置幕后item的缓存数目。功能是预加载的那个图片可以直接缩小。不然的话，图片不会自动缩小
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test_for_gallery;
    }

    @Override
    public void initPresenter() {

    }

    public void initListener() {
        mContainer.setOnTouchListener(new View.OnTouchListener() {//将父类的touch事件分发至viewPager，否则只能滑动中间的一个view对象
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mVpPhoto.dispatchTouchEvent(event);
            }
        });
    }

    @Override
    public void initData(Bundle bundle) {
        initListener();
        setViewPager();
    }

    @Override
    public int getStatusBarColorResId() {
        return R.color.white;
    }


}
