package com.xuanfeng.xuanfengweather.module.gallery.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuanfeng.xuanfengweather.R;
import com.xuanfeng.xuanfengweather.base.BaseFragment;
import com.xuanfeng.xuanfengweather.module.gallery.adapter.ViewPagerPhotoAdapter;
import com.xuanfeng.xuanfengweather.widget.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class PhotoShowFragment extends BaseFragment {

    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.vp_photo)
    ViewPager mVpPhoto;
    Unbinder unbinder;
    private List<String> mTittleList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_material_desgin, container, false);
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
        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab != null) {
                    mVpPhoto.setCurrentItem(tab.getPosition());
                }
            }
        });
    }

    @Override
    protected void initData() {
        setTablayout();
        setViewPager();

    }

    private void setViewPager() {
        List<String> photoUrls = new ArrayList<>();
        photoUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1511054491&di=4d9c293fbcc58cbf1b44a6fddca5ab30&imgtype=jpg&er=1&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F4bed2e738bd4b31c2fc3c19d8dd6277f9e2ff81d.jpg");
        photoUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1511066707&di=4e77bca150f6e00e073284c54e169eff&imgtype=jpg&er=1&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F3801213fb80e7beca9004ec5252eb9389b506b38.jpg");
        photoUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1511054750&di=6c1a46c779b7f237c7a65a80d01576c9&imgtype=jpg&er=1&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3D316e3f61a74bd11310c0bf7132c6ce7a%2F72f082025aafa40fb20015cfa164034f79f019da.jpg");
        PagerAdapter pagerAdapter = new ViewPagerPhotoAdapter(mContext, photoUrls,mTittleList);
        mVpPhoto.setAdapter(pagerAdapter);
        mVpPhoto.setPageMargin(40);
        mVpPhoto.setPageTransformer(true,new ZoomOutPageTransformer(mContext));
    }

    //设置指示器
    private void setTablayout() {
        mTittleList = new ArrayList<>();
        mTittleList.add("风景1");
        mTittleList.add("风景2");
        mTittleList.add("风景3");
        for (int i = 0; i < mTittleList.size(); i++) {
            mTablayout.addTab(mTablayout.newTab().setText(mTittleList.get(i)));
        }
        mTablayout.setupWithViewPager(mVpPhoto);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
