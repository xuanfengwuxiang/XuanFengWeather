package com.xuanfeng.xuanfengweather.module.media.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xuanfeng.mylibrary.utils.FileUtil;
import com.xuanfeng.mylibrary.utils.ImageUtil;
import com.xuanfeng.xuanfengweather.R;
import com.xuanfeng.xuanfengweather.mvvm.BaseActivity;
import com.xuanfeng.xuanfengweather.widget.DoodleImageView;

import butterknife.BindView;
import butterknife.OnClick;

public class ImageViewDoodleActivity extends BaseActivity {

    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.doodle_image_view)
    DoodleImageView mDoodleImageView;
    @BindView(R.id.tv_save)
    TextView mTvSave;

    @Override
    public int getLayoutId() {
        return R.layout.activity_imageview;
    }

    @Override
    public void initViewModel() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData(Bundle bundle) {
        ImageUtil.loadImage(this, R.drawable.ic_scenery1, mDoodleImageView);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean isFullScreen() {
        return true;
    }


    @OnClick({R.id.tv_save, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                mDoodleImageView.unDo();
                break;
            case R.id.tv_save:
                Bitmap bitmap = mDoodleImageView.getBitmap();
                boolean issuccessful = ImageUtil.saveBitmapToSD(bitmap, FileUtil.getSDPath(this), "涂鸦.jpg");
                if (issuccessful) {
                    Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setEnterTransition(new Slide());
        super.onCreate(savedInstanceState);
    }
}
