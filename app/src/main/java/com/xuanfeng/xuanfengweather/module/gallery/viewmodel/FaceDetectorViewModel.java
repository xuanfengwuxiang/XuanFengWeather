package com.xuanfeng.xuanfengweather.module.gallery.viewmodel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.xuanfeng.xuanfengweather.R;
import com.xuanfeng.xuanfengweather.module.gallery.view.FaceDetectorView;

/**
 * Created by xuanfengwuxiang on 2017/12/21.
 * 人脸识别界面的数据绑定类
 */

public class FaceDetectorViewModel {
    private Context mContext;
    private FaceDetectorView mFaceDetectorView;

    public FaceDetectorViewModel(Context context, FaceDetectorView faceDetectorView) {
        mContext = context;
        mFaceDetectorView = faceDetectorView;
    }

    public Bitmap getBitmap(){
        return  BitmapFactory.decodeResource(mContext.getResources(),R.drawable.ic_cgx);
    }
}
