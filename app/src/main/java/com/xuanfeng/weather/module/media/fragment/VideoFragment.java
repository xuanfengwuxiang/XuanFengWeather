package com.xuanfeng.weather.module.media.fragment;

import android.os.Bundle;

import com.xuanfeng.weather.R;
import com.xuanfeng.weather.databinding.FragmentGalleryBinding;
import com.xuanfeng.xflibrary.mvp.BaseFragment;
import com.xuanfeng.xflibrary.mvp.BasePresenter;

//todo 换新的视频播放
public class VideoFragment extends BaseFragment<BasePresenter, FragmentGalleryBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_gallery;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        //do nothing
    }

}
