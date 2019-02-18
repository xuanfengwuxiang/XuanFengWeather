package com.xuanfeng.xuanfengweather.module.test.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xuanfeng.mylibrary.mvp.BaseActivity;
import com.xuanfeng.mylibrary.utils.SoftKeyBoardUtil;
import com.xuanfeng.mylibrary.widget.popupmenu.PopupMenu;
import com.xuanfeng.mylibrary.widget.popupmenu.adapter.PopupMenuAdapter;
import com.xuanfeng.xuanfengweather.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TestActivity extends BaseActivity {

    @BindView(R.id.tv_test_for_edittext)
    TextView tvTestForEdittext;
    @BindView(R.id.tv_test_for_popupmenu)
    TextView mTvTestForPopupmenu;
    @BindView(R.id.tv_test_for_pad_send)
    TextView mTvTestForPadSend;
    @BindView(R.id.tv_test_for_tv_start)
    TextView mTvTestForTvStart;
    @BindView(R.id.tv_test_for_tv_send)
    TextView mTvTestForTvSend;
    @BindView(R.id.tv_test_for_gallery)
    TextView mTvTestForGallery;
    @BindView(R.id.tv_aidl)
    TextView mTvTestForPlugin;
    @BindView(R.id.tv_share_anim)
    TextView mTvTestShareAnim;
    @BindView(R.id.iv_share_anim)
    ImageView ivShareAnim;
    private PopupMenu mPopupMenu;
    private SoftKeyBoardUtil mSoftKeyBoardUtil;


    @OnClick({R.id.tv_test_for_edittext, R.id.tv_test_for_popupmenu, R.id.tv_test_for_keyboard, R.id.tv_test_for_pad_send, R.id.tv_test_for_tv_start,
            R.id.tv_test_for_tv_send, R.id.tv_test_for_gallery, R.id.tv_aidl, R.id.ll_test_share_anim})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_test_for_edittext://测试自定义EditText
                Intent intent = new Intent(this, TestForEditTextActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_test_for_popupmenu://测试自定义popupWindow
                if (mPopupMenu == null) {
                    initPopupMenu();
                }
                if (!mPopupMenu.isShowing()) {
                    mPopupMenu.showAsDropDown(mTvTestForPopupmenu);
                }
                break;
            case R.id.tv_test_for_pad_send:
                break;
            case R.id.tv_test_for_tv_start:
                break;
            case R.id.tv_test_for_tv_send:
                break;
            case R.id.tv_test_for_gallery:
                intent = new Intent(this, TestForGalleryActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_aidl://AIDL通信
                intent = new Intent(this, TestAIDLActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_test_share_anim://share转场动画
                intent = new Intent(this, TestShareAnimActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(mTvTestShareAnim, "fab"), Pair.create(ivShareAnim, "pic")).toBundle());
                break;

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
        mPopupMenu = new PopupMenu(this, mTvTestForPopupmenu.getWidth(), 500, list);
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
    public void initPresenter() {

    }


    @Override
    public void initData(Bundle bundle) {
        mSoftKeyBoardUtil = SoftKeyBoardUtil.setListener(this, mKeyBoardListener);
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
