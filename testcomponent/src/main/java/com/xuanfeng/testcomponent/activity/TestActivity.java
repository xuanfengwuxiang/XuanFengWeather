package com.xuanfeng.testcomponent.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.transition.Explode;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xuanfeng.mylibrary.mvp.BaseActivity;
import com.xuanfeng.mylibrary.mvp.BasePresenter;
import com.xuanfeng.mylibrary.utils.SoftKeyBoardUtil;
import com.xuanfeng.mylibrary.widget.popupmenu.PopupMenu;
import com.xuanfeng.mylibrary.widget.popupmenu.adapter.PopupMenuAdapter;
import com.xuanfeng.testcomponent.R;
import com.xuanfeng.testcomponent.databinding.ActivityTestBinding;
import com.xuanfeng.testcomponent.service.TestService;

import java.util.ArrayList;
import java.util.List;


@Route(path = "/testcomponent/TestActivity")
public class TestActivity extends BaseActivity<BasePresenter, ActivityTestBinding> {


    private PopupMenu mPopupMenu;
    private SoftKeyBoardUtil mSoftKeyBoardUtil;

    public void onClick(View view) {
        int i = view.getId();//测试自定义EditText
        if (i == R.id.tv_test_for_edittext) {
            Intent intent = new Intent(this, TestForEditTextActivity.class);
            startActivity(intent);
        } else if (i == R.id.tv_test_for_popupmenu) {
            if (mPopupMenu == null) {
                initPopupMenu();
            }
            if (!mPopupMenu.isShowing()) {
                mPopupMenu.showAsDropDown(mBinding.tvTestForPopupmenu);
            }
        } else if (i == R.id.tv_test_for_touch_dispatch) {
            Intent intent;
            intent = new Intent(this, TestTouchEventActivity.class);
            startActivity(intent);
        } else if (i == R.id.tv_test_for_service) {
            startService(service);
        } else if (i == R.id.tv_test_for_close_service) {
            stopService(service);
        } else if (i == R.id.tv_test_for_gallery) {
            Intent intent;
            intent = new Intent(this, TestForGalleryActivity.class);
            startActivity(intent);
        } else if (i == R.id.tv_aidl) {
            Intent intent;
            intent = new Intent(this, TestAIDLActivity.class);
            startActivity(intent);
        } else if (i == R.id.ll_test_share_anim) {
            Intent intent;
            intent = new Intent(this, TestShareAnimActivity.class);
            Pair pair = Pair.create(mBinding.tvShareAnim, "fab");
            Pair pair1 = Pair.create(mBinding.ivShareAnim, "pic");
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(TestActivity.this, pair, pair1);
            startActivity(intent, activityOptionsCompat.toBundle());
        } else if (i == R.id.iv_left) {
            finishAfterTransition();
        }
    }


    private void initPopupMenu() {
        final List<String> list = new ArrayList<>();
        list.add("苍井空");
        list.add("波多野结衣");
        list.add("小泽玛利亚");
        list.add("苍井空");
        list.add("波多野结衣");
        list.add("小泽玛利亚");
        list.add("苍井空");
        list.add("波多野结衣");
        list.add("小泽玛利亚");
        mPopupMenu = new PopupMenu(this, mBinding.tvTestForPopupmenu.getWidth(), 500, list);
        mPopupMenu.setOnItemClickListener(new PopupMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mPopupMenu.dismiss();
                Toast.makeText(TestActivity.this, list.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mSoftKeyBoardUtil.removeListener();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public BasePresenter initPresenter() {
        mBinding.setListener(this);
        return null;
    }

    Intent service;
    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void initData(Bundle bundle) {
        mBinding.tvTittle.setText("测试界面");
        mSoftKeyBoardUtil = SoftKeyBoardUtil.setListener(this, mKeyBoardListener);
        service = new Intent(this, TestService.class);
    }

    SoftKeyBoardUtil.KeyBoardListener mKeyBoardListener = new SoftKeyBoardUtil.KeyBoardListener() {
        @Override
        public void keyBoardShow(int height) {

        }

        @Override
        public void keyBoardHide(int height) {

        }
    };

    @Override
    public int getStatusBarColorResId() {
        return R.color.white;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setEnterTransition(new Explode());
        super.onCreate(savedInstanceState);
    }
}
