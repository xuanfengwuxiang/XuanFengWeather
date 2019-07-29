package com.xuanfeng.weather.module.media.fragment;

import android.os.Bundle;

import com.xuanfeng.mylibrary.mvp.BaseFragment;
import com.xuanfeng.mylibrary.mvp.BasePresenter;
import com.xuanfeng.weather.R;
import com.xuanfeng.weather.databinding.FragmentMediaBinding;
import com.xuanfeng.weather.module.media.util.MediaUtil;

//第二个模块的总fragment
public class MediaFragment extends BaseFragment<BasePresenter, FragmentMediaBinding> {


    public int getLayoutId() {
        return R.layout.fragment_media;
    }

    public BasePresenter initPresenter() {
        mBinding.setMediaFragment(this);
        return null;
    }

    public void initData(Bundle bundle) {
        MediaUtil.setTittle(getContext(), mBinding.includeTittle.ivLeft, mBinding.includeTittle.rlHeader, mBinding.includeTittle.tvTittle);
        MediaUtil.setViewPagerAdapter(this, mBinding.viewPager);
        MediaUtil.initMagicIndicator(getContext(), mBinding.magicIndicator, mBinding.viewPager);
    }

    @Override
    public int getStatusBarColorResId() {
        return -1;
    }

    @Override
    public void showProgress() {
        //do nothing
    }

    @Override
    public void hideProgress() {
        //do nothing
    }

}
