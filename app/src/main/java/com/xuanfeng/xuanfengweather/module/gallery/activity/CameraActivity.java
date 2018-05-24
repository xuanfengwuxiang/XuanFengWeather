package com.xuanfeng.xuanfengweather.module.gallery.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.xuanfeng.mylibrary.widget.CustomToast;
import com.xuanfeng.xuanfengweather.MainActivity;
import com.xuanfeng.xuanfengweather.R;
import com.xuanfeng.xuanfengweather.databinding.ActivityCameraBinding;
import com.xuanfeng.xuanfengweather.module.gallery.callback.GoogleDetectListenerImpl;
import com.xuanfeng.xuanfengweather.mvvm.BaseActivity;
import com.xuanfeng.xuanfengweather.utils.ImageUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 人脸识别,相机界面
 */
public class CameraActivity extends BaseActivity<ActivityCameraBinding> {

    private SurfaceHolder mSurfaceHolder;
    public static int cameraId;
    public Camera mCamera;
    private SurfaceViewCallBack mSurfaceViewCallBack;
    private MainHandler mainHandler = new MainHandler();
    byte[] photoBytes;
    private boolean isBack = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_camera;
    }

    @Override
    public boolean isFullScreen() {
        return true;
    }

    @Override
    public void initViewModel() {

    }

    @Override
    public void initListener() {
        mBinding.setListener(this);
    }

    @Override
    public void initData(Bundle bundle) {
        initSurfaceView();
    }

    private void initSurfaceView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//保持屏幕常亮
        mSurfaceHolder = mBinding.surfaceView.getHolder();
        cameraId = ImageUtil.findBackCamera();
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mBinding.tvTurnCamera.setText("前置/后置------当前：后置");
        mSurfaceViewCallBack = new SurfaceViewCallBack(this);
        mSurfaceHolder.addCallback(mSurfaceViewCallBack);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_turn_camera://切换摄像头
                turnCamera();
                break;

            case R.id.tv_take_photo://拍照
                takePhoto();
                break;

            case R.id.tv_finish_photo://照片完成按钮
                takePhotoFinish();
                break;

            case R.id.tv_cancel_photo://照片取消按钮
                cancle();
                break;
        }
    }

    private class MainHandler extends Handler {

        @Override
        public void handleMessage(final Message msg) {
            int what = msg.what;
            switch (what) {
                case 1:
                    startGoogleDetect();
                    break;
                case 2:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Camera.Face[] faces = (Camera.Face[]) msg.obj;
                            mBinding.faceView.setFaces(faces);
                            CustomToast.getInstance(CameraActivity.this).showToast("收到人脸识别的信息");
                        }
                    });

                    break;
            }
            super.handleMessage(msg);
        }
    }

    public void takePhoto() {          //点击拍照的方法。
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean b, Camera camera) {
               if (b) {        //如果焦点获取成功，拍照
                    mCamera.takePicture(null, null, pictureCallBack);  //pictureCallBack 为拍照的回掉。
                }
            }
        });
    }

    private Camera.PictureCallback pictureCallBack = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {     //bytes 即是拍照回来的内容，将内容写入本地即可
            photoBytes = bytes;
            showPhoto(photoBytes);
        }
    };

    /**
     * 显示拍出的图片
     */
    private void showPhoto(byte[] photoBytes) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length);
        Matrix matrix = new Matrix();
        if (isBack) {  //后置摄像头
            matrix.setRotate(90);
        } else {
            matrix.setRotate(-90); //前置摄像头
        }
        Bitmap bit = bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        if (bit != null) {
            mBinding.ivPhotoResult.setImageBitmap(bit);
        }
        mBinding.rlPhotoResult.setVisibility(View.VISIBLE);
    }

    /**
     * 摄像头的切换
     */
    public void turnCamera() {
        if (isBack) {
            cameraId = ImageUtil.findFrontCamera();  //前置摄像头id
            mBinding.tvTurnCamera.setText("前置/后置------当前：前置");
        } else {
            cameraId = ImageUtil.findBackCamera();  //后置
            mBinding.tvTurnCamera.setText("前置/后置------当前：后置");
        }
        isBack = !isBack;
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release(); // 释放照相机
        }
        mCamera = Camera.open(cameraId);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        //ImageUtil.setCameraParams(mCamera, displayMetrics.widthPixels, displayMetrics.heightPixels);
        ImageUtil.setCameraParams(mCamera,1080,1920);
        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.setDisplayOrientation(90);
            mCamera.startPreview();
            mainHandler.sendEmptyMessage(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击完成保存图片，并将路径回传给上个页面
     */
    public void takePhotoFinish() {          //完成
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/test2.jpg");
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(photoBytes);
            fo.close();
            Toast.makeText(this, "已保存图片", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);                         //完成拍照，回到mainActivity,并将图片的路径回传
            intent.putExtra("picPath", Environment.getExternalStorageDirectory().getPath() + "/test2.jpg");
            intent.putExtra("isBack", isBack);
            setResult(RESULT_OK, intent);
            finish();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消，放弃该图片
     */
    public void cancle() {          //取消
        photoBytes = null;
        mBinding.rlPhotoResult.setVisibility(View.GONE);
        mCamera.startPreview();
    }

    //添加人脸监听器
    private void startGoogleDetect() {
        Camera.Parameters parameters =mCamera.getParameters();
        if (parameters.getMaxNumDetectedFaces() > 0) {
            if (mBinding.faceView != null) {
                mBinding.faceView.clearFaces();
                mBinding.faceView.setVisibility(View.VISIBLE);
            }
            mCamera.setFaceDetectionListener(new GoogleDetectListenerImpl(this, mainHandler));
            mCamera.startFaceDetection();
        }
    }

    public class SurfaceViewCallBack implements SurfaceHolder.Callback {
        private Context mContext;

        public SurfaceViewCallBack(Context context) {
            mContext = context;
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                if (mCamera == null) {
                    mCamera = Camera.open(cameraId);
                    mCamera.setPreviewDisplay(holder);
                    DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
                    //ImageUtil.setCameraParams(mCamera,displayMetrics.widthPixels, displayMetrics.heightPixels);
                    ImageUtil.setCameraParams(mCamera,1080,1920);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            mCamera.startPreview();
            mainHandler.sendEmptyMessage(1);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (mCamera != null) {
                mCamera.release(); // 释放照相机
                mCamera = null;
            }
        }
    }
}
