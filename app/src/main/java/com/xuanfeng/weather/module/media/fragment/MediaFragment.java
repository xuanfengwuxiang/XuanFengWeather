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

    public int getStatusBarColorResId() {
        return -1;
    }

    public void showProgress() {

    }

    public void hideProgress() {

    }

/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initData(null);
        return rootView;
    }*/

   /* @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }*/
}
