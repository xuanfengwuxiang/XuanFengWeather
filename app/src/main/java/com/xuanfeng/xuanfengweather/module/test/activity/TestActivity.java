package com.xuanfeng.xuanfengweather.module.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xuanfeng.mylibrary.widget.popupmenu.PopupMenu;
import com.xuanfeng.mylibrary.widget.popupmenu.adapter.PopupMenuAdapter;
import com.xuanfeng.xuanfengweather.R;
import com.xuanfeng.xuanfengweather.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends BaseActivity {

    @BindView(R.id.tv_test_for_edittext)
    TextView tvTestForEdittext;
    @BindView(R.id.tv_test_for_popupmenu)
    TextView mTvTestForPopupmenu;
    @BindView(R.id.tv_test_for_netty)
    TextView mTvTestForNetty;
    @BindView(R.id.tv_test_for_pad_send)
    TextView mTvTestForPadSend;
    @BindView(R.id.tv_test_for_tv_start)
    TextView mTvTestForTvStart;
    @BindView(R.id.tv_test_for_tv_send)
    TextView mTvTestForTvSend;
    @BindView(R.id.tv_test_for_gallery)
    TextView mTvTestForGallery;
    @BindView(R.id.tv_test_for_plugin)
    TextView mTvTestForPlugin;
    @BindView(R.id.tv_test_for_out_plugin)
    TextView mTvTestForOutPlugin;
    private PopupMenu mPopupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        initViews();
        setListeners();
        initData();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initData() {
    }


    @OnClick({R.id.tv_test_for_edittext, R.id.tv_test_for_popupmenu, R.id.tv_test_for_netty, R.id.tv_test_for_pad_send, R.id.tv_test_for_tv_start,
            R.id.tv_test_for_tv_send, R.id.tv_test_for_gallery, R.id.tv_test_for_plugin, R.id.tv_test_for_out_plugin})
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
            case R.id.tv_test_for_netty://测试netty的socket通信
                //connected();
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

            case R.id.tv_test_for_plugin://内置插件
                break;
            case R.id.tv_test_for_out_plugin://外置插件


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
    protected void onDestroy() {
        super.onDestroy();
    }


}
