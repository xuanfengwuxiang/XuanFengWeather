package com.xuanfeng.xuanfengweather.module.menu;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xuanfeng.mylibrary.mvp.BaseFragment;
import com.xuanfeng.xuanfengweather.R;
import com.xuanfeng.xuanfengweather.module.loseweight.LoseWeightCalculatorActivity;
import com.xuanfeng.xuanfengweather.module.test.activity.TestActivity;

import butterknife.OnClick;

//侧滑界面
public class MenuFragment extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_menu;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData(Bundle bundle) {

    }


    @OnClick({R.id.tv_lose_weight, R.id.tv_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_lose_weight:
                Intent intent = new Intent(getContext(), LoseWeightCalculatorActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                break;
            case R.id.tv_test:
                intent = new Intent(getContext(), TestActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                break;
        }
    }
}
