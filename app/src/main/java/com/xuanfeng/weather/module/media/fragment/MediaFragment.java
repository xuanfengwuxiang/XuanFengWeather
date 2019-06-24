package com.xuanfeng.weather.module.media.fragment;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xuanfeng.mylibrary.mvp.BaseFragment;
import com.xuanfeng.weather.R;
import com.xuanfeng.weather.module.media.util.MediaUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;

import butterknife.BindView;
import butterknife.Unbinder;

//第二个模块的总fragment
public class MediaFragment extends BaseFragment {

    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.iv_left)
    ImageView mIvLeft;

    @BindView(R.id.tv_tittle)
    TextView mTvTittle;

    @BindView(R.id.rl_header)
    RelativeLayout mRlHeader;
    @BindView(R.id.tv_left)
    TextView mTvLeft;
    Unbinder unbinder;

    public int getLayoutId() {
        return R.layout.fragment_entertainment;
    }

    public void initPresenter() {

    }

    public void initData(Bundle bundle) {
        MediaUtil.setTittle(getContext(), mIvLeft, mRlHeader, mTvTittle);
        MediaUtil.setViewPagerAdapter(this, mViewPager);
        MediaUtil.initMagicIndicator(getContext(), mMagicIndicator, mViewPager);
    }

    public int getStatusBarColorResId( ) {
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
