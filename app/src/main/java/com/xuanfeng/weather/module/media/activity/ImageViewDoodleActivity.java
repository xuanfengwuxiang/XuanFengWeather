package com.xuanfeng.weather.module.media.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.widget.Toast;

import com.xuanfeng.mylibrary.mvp.BaseActivity;
import com.xuanfeng.mylibrary.mvp.BasePresenter;
import com.xuanfeng.mylibrary.utils.FileUtil;
import com.xuanfeng.mylibrary.utils.ImageUtil;
import com.xuanfeng.weather.R;
import com.xuanfeng.weather.databinding.ActivityImageviewBinding;

import butterknife.OnClick;

public class ImageViewDoodleActivity extends BaseActivity<BasePresenter, ActivityImageviewBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_imageview;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }


    @Override
    public void initData(Bundle bundle) {
        mBinding.setListener(this);
        ImageUtil.loadImage(this, R.drawable.ic_scenery1, mBinding.doodleImageView);
    }

    @Override
    public int getStatusBarColorResId() {
        return 0;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                mBinding.doodleImageView.unDo();
                break;
            case R.id.tv_save:
                Bitmap bitmap = mBinding.doodleImageView.getBitmap();
                boolean issuccessful = ImageUtil.saveBitmapToSD(bitmap, FileUtil.getSDPath(this), "涂鸦.jpg");
                if (issuccessful) {
                    Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public boolean isFullScreen() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setEnterTransition(new Slide());
        super.onCreate(savedInstanceState);
    }
}
