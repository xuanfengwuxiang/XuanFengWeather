package com.xuanfeng.xuanfengweather.module.media.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import com.jungle.mediaplayer.base.VideoInfo;
import com.jungle.mediaplayer.widgets.JungleMediaPlayer;
import com.jungle.mediaplayer.widgets.SimpleJungleMediaPlayerListener;
import com.xuanfeng.mylibrary.mvp.BaseActivity;
import com.xuanfeng.mylibrary.mvp.BaseFragment;
import com.xuanfeng.mylibrary.utils.SystemUtils;
import com.xuanfeng.xuanfengweather.R;

import butterknife.BindView;
import butterknife.Unbinder;


public class GalleryFragment extends BaseFragment {

    @BindView(R.id.jmp_video)
    JungleMediaPlayer mJmpVideo;
    Unbinder unbinder;

    private String mVideoUrl;
    private boolean mIsFullScreenNow = false;
    private int mVideoZoneNormalHeight = 0;
    private float mScreenWidth;


    private void initMediaPlayer() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mVideoZoneNormalHeight = (int) (metrics.widthPixels / 1.77f);
        mScreenWidth = SystemUtils.getWidthInPx(getContext());
        mJmpVideo.setAutoReloadWhenError(false);
        mJmpVideo.setTitle("月半小夜曲");
        mJmpVideo.setAutoResume(true);
        mJmpVideo.setPlayerListener(new SimpleJungleMediaPlayerListener() {

            @Override
            public void onTitleBackClicked() {
                if (mJmpVideo.isFullscreen()) {
                    mJmpVideo.switchFullScreen(false);
                    return;
                }
                //返回按钮被点击
            }

            @Override
            public void onPreviewPlayClicked() {
                mJmpVideo.playMedia(new VideoInfo(mVideoUrl));
            }

            @Override
            public void onReplayMedia(int startMillSeconds) {
                mJmpVideo.playMedia(new VideoInfo(mVideoUrl));
            }
        });
        updateVideoZoneSize(false);
        //mVideoUrl = "http://200000594.vod.myqcloud.com/200000594_1617cc56708f11e596723b988fc18469.f20.mp4";
        mVideoUrl = "";
        if (!TextUtils.isEmpty(mVideoUrl)) {
            mJmpVideo.setPreviewDataSource(new VideoInfo(mVideoUrl));
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            switchVideoContainer(true);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            switchVideoContainer(false);
        }
    }

    private void switchVideoContainer(boolean fullScreen) {
        if (mIsFullScreenNow == fullScreen) {
            return;
        }

        mIsFullScreenNow = fullScreen;

        ActionBar actionBar = ((BaseActivity) getContext()).getSupportActionBar();
        if (actionBar != null) {
            if (mIsFullScreenNow) {
                actionBar.hide();
            } else {
                actionBar.show();
            }
        }
        updateVideoZoneSize(fullScreen);
    }

    private void updateVideoZoneSize(final boolean fullScreen) {
        ViewGroup.LayoutParams params = mJmpVideo.getLayoutParams();
        params.height = fullScreen
                ? (int) mScreenWidth
                : mVideoZoneNormalHeight;
        mJmpVideo.setLayoutParams(params);
    }

    @Override
    public void onStop() {
        super.onStop();
        mJmpVideo.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mJmpVideo != null) {
            mJmpVideo.destroy();
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_gallery;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData(Bundle bundle) {
        initMediaPlayer();
    }
}
