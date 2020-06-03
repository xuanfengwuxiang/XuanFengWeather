package com.xuanfeng.weather.module.media.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xuanfeng.xflibrary.mvp.BaseActivity;
import com.xuanfeng.xflibrary.mvp.BasePresenter;
import com.xuanfeng.xflibrary.utils.FileUtil;
import com.xuanfeng.xflibrary.utils.ImageUtil;
import com.xuanfeng.weather.R;
import com.xuanfeng.weather.databinding.ActivityImageviewDoodleBinding;


public class ImageViewDoodleActivity extends BaseActivity<BasePresenter, ActivityImageviewDoodleBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_imageview_doodle;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }


    @Override
    public void initData(Bundle bundle) {
        mBinding.setListener(this);
        Glide.with(this).asDrawable().load(R.drawable.ic_scenery1).into(mBinding.doodleImageView);

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
            default:
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
