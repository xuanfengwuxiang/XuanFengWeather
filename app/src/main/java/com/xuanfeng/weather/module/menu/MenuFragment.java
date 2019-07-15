package com.xuanfeng.weather.module.menu;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xuanfeng.mylibrary.mvp.BaseFragment;
import com.xuanfeng.mylibrary.mvp.BasePresenter;
import com.xuanfeng.weather.R;
import com.xuanfeng.weather.databinding.FragmentMenuBinding;
import com.xuanfeng.weather.module.loseweight.LoseWeightCalculatorActivity;

import butterknife.OnClick;

//侧滑界面
public class MenuFragment extends BaseFragment<BasePresenter, FragmentMenuBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_menu;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        mBinding.setListener(this);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_lose_weight:
                Intent intent = new Intent(getContext(), LoseWeightCalculatorActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                break;
            case R.id.tv_test:
                ARouter.getInstance().build("/testcomponent/TestActivity").navigation();
                break;
        }
    }
}
