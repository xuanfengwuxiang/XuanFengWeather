/**
 * Android Jungle-MediaPlayer framework project.
 *
 * Copyright 2016 Arno Zhang <zyfgood12@163.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jungle.mediaplayer.widgets;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.jungle.mediaplayer.R;
import com.jungle.mediaplayer.base.BaseMediaPlayerInterface;
import com.jungle.mediaplayer.base.BaseMediaPlayerListener;
import com.jungle.mediaplayer.base.MediaPlayerUtils;
import com.jungle.mediaplayer.base.MediaSize;
import com.jungle.mediaplayer.base.ScreenOrientationSwitcher;
import com.jungle.mediaplayer.widgets.control.MediaPlayerGestureController;
import com.jungle.mediaplayer.widgets.control.PlayerBottomControl;
import com.jungle.mediaplayer.widgets.control.PlayerLoadingControl;
import com.jungle.mediaplayer.widgets.control.PlayerPreviewControl;
import com.jungle.mediaplayer.widgets.control.PlayerTopControl;
import com.jungle.mediaplayer.widgets.panel.LockOrientationPanel;

public abstract class MediaPlayerFrame extends FrameLayout
        implements PlayerTopControl.Listener, PlayerPreviewControl.Listener, BaseMediaPlayerInterface {

    private static final String TAG = "MediaPlayerFrame";


    public static interface FrameHandler {
        boolean handleSingleTap();
    }

    protected FrameLayout mRootView;
    protected ScreenOrientationSwitcher mScreenOrientationSwitcher;
    protected MediaPlayerGestureController mGestureController;
    protected LockOrientationPanel mLockOrientationPanel;
    protected PlayerTopControl mTopControl;
    protected PlayerBottomControl mBottomControl;
    protected PlayerLoadingControl mLoadingControl;
    protected PlayerPreviewControl mPreviewControl;
    protected Point mInitializedScreenSize = new Point();
    protected boolean mIsFullscreen = false;
    protected boolean mShowTitleBar = true;
    protected boolean mUpdateMediaSizeDisabled = false;
    protected boolean mIsShowingToolView = true;
    private Animation mAnimEntryTop;
    private Animation mAnimLeaveTop;
    private Animation mAnimEntryBottom;
    private Animation mAnimLeaveBottom;

    @Nullable
    protected FrameHandler mFrameHandler;


    public MediaPlayerFrame(Context context) {
        super(context);
        init(context);
    }

    public MediaPlayerFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MediaPlayerFrame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.layout_jungle_media_player_frame, this);

        mAnimEntryTop = AnimationUtils.loadAnimation(context, R.anim.control_entry_from_top);
        mAnimLeaveTop = AnimationUtils.loadAnimation(context, R.anim.control_leave_from_top);
        mAnimEntryBottom = AnimationUtils.loadAnimation(context, R.anim.control_entry_from_bottom);
        mAnimLeaveBottom = AnimationUtils.loadAnimation(context, R.anim.control_leave_from_bottom);

        initView();
        initGestureController();
        initOrientationSwitcher();
        scheduleHideTitleBar();
    }

    public FrameLayout getMediaRootLayout() {
        return (FrameLayout) findViewById(R.id.media_root);
    }

    private void initView() {
        MediaSize size = MediaPlayerUtils.getScreenSize(getContext());
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mInitializedScreenSize.set(size.mHeight, size.mWidth);
        } else {
            mInitializedScreenSize.set(size.mWidth, size.mHeight);
        }

        mTopControl = (PlayerTopControl) findViewById(R.id.player_top_control);
        mBottomControl = (PlayerBottomControl) findViewById(R.id.player_bottom_control);
        mLoadingControl = (PlayerLoadingControl) findViewById(R.id.player_loading_control);
        mLockOrientationPanel = (LockOrientationPanel) findViewById(R.id.lock_orientation_panel);
        mPreviewControl = (PlayerPreviewControl) findViewById(R.id.player_preview_control);

        mTopControl.setListener(this);
        mBottomControl.setOperationHelper(new PlayerBottomControl.OperationHelper() {
            @Override
            public void switchPlayOrPause() {
                if (isPlaying()) {
                    pause();
                } else {
                    resume();
                }
            }

            @Override
            public void switchFullScreen() {
                MediaPlayerFrame.this.switchFullScreen(!mIsFullscreen);
            }
        });

        mPreviewControl.setListener(this);
    }

    private void initGestureController() {
        mRootView = (FrameLayout) findViewById(R.id.video_container);
        mGestureController = new MediaPlayerGestureController(
                getContext(), mRootView, mGestureHelper);
        mGestureController.setProgressAdjustPanelContainer(mRootView);
        mGestureController.setAdjustPanelContainer(mRootView);
    }

    private void initOrientationSwitcher() {
        mScreenOrientationSwitcher = new ScreenOrientationSwitcher(
                getContext(), SensorManager.SENSOR_DELAY_NORMAL);

        mScreenOrientationSwitcher.setChangeListener(mOrientationChangeListener);
        if (mScreenOrientationSwitcher.canDetectOrientation()) {
            mScreenOrientationSwitcher.enable();
        }

        mLockOrientationPanel.setLockChangedListener(
                new LockOrientationPanel.OnLockChangedListener() {
                    @Override
                    public void onChanged(boolean isLocked) {
                        if (isLocked) {
                            hideMenu();
                            mLockOrientationPanel.showLockPanel();
                            mGestureController.setGestureFilterType(
                                    MediaPlayerGestureController.GestureFilterType.ExceptSingleTap);
                        } else {
                            showMenu();
                            mGestureController.setGestureFilterType(
                                    MediaPlayerGestureController.GestureFilterType.None);
                        }
//                        if (isLocked) {
//                            return;
//                        }
//                        int requestOrientation = mScreenOrientationSwitcher.getCurrOrientation();
//                        switchFullScreenInternal(requestOrientation);
                    }
                });
    }

    public void setAdjustPanelContainer(FrameLayout container) {
        mGestureController.setAdjustPanelContainer(container);
    }

    public void setFrameHandler(@Nullable FrameHandler handler) {
        mFrameHandler = handler;
    }

    public Bitmap captureMedia() {
        return MediaPlayerUtils.takeViewScreenshot(getMediaRootLayout());
    }

    @Override
    public void destroy() {
        Log.e(TAG, "MediaPlayer Will **Destroy**!!");

        unScheduleAuditionCheck();
        unScheduleHideTitleBar();

        mTopControl.doDestroy();
        mBottomControl.doDestroy();
        mScreenOrientationSwitcher.disable();
        mLockOrientationPanel.doDestroy();
    }

    public abstract void addPlayerListener(BaseMediaPlayerListener listener);

    protected abstract void toggleFullScreen(boolean isFullscreen, boolean reverseOrientation);

    protected abstract void updateMediaSize(int width, int height);

    protected abstract boolean checkAudition();

    protected abstract void handleDragProgress(int startDragPos, float distanceX);

    protected abstract void handleEndDragProgress(int dragPosition, float totalDistanceX);

    protected void handleSingleTap() {

        if (mLockOrientationPanel.isLocked()) {
            if (mIsFullscreen) {
                mLockOrientationPanel.showAndHide();
            }
            return;
        }
        showAndHideMenu();
    }

    protected void showAndHideMenu() {
        if (mIsShowingToolView) {
            hideMenu();
        } else {
           showMenu();
        }
    }

    protected void showMenu() {
        mIsShowingToolView = true;
        if (!isLoading()) {
            mBottomControl.clearAnimation();
            mBottomControl.setVisibility(View.VISIBLE);
            mBottomControl.startAnimation(mAnimEntryBottom);
        }
        showTitleBar();
        scheduleHideTitleBar();
        //全屏
        if (mIsFullscreen) {
            mLockOrientationPanel.showLockPanel();
        }
    }

    protected void hideMenu() {
        mIsShowingToolView = false;
        mBottomControl.clearAnimation();
        mBottomControl.setVisibility(View.GONE);
        mBottomControl.startAnimation(mAnimLeaveBottom);
        hideTitleBar();
        if (mIsFullscreen) {
            mLockOrientationPanel.hideLockPanel();
        }
    }

    protected void showPreview(Bitmap bitmap) {
        mPreviewControl.show();
        mPreviewControl.setPreviewImage(bitmap);
        mTopControl.setVisibility(VISIBLE);
    }

    protected void hidePreview() {
        mPreviewControl.hide();
    }

    public void setTitle(String title) {
        mTopControl.setTitle(title);
    }

    public void showError(String msg) {
        mLoadingControl.showError(msg);
    }

    public void showError(boolean show) {
        mLoadingControl.showError(show);
    }

    public void showLoading(boolean hasBackground) {
        mLoadingControl.showLoading(hasBackground);
        mLoadingControl.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        mLoadingControl.setVisibility(View.GONE);
    }

    public PlayerTopControl getTitleBar() {
        return mTopControl;
    }

    public PlayerBottomControl getBottomBar() {
        return mBottomControl;
    }

    public void setEnableAutoRotation(boolean enable) {
        mScreenOrientationSwitcher.setEnableAutoRotation(enable);
    }

    protected void showTitleBar() {
        if (!mIsFullscreen && !mShowTitleBar) {
            return;
        }

        mTopControl.show(mAnimEntryTop);
    }

    protected void hideTitleBar() {
        mTopControl.hide(mAnimLeaveTop);
    }

    public void setShowTitleBar(boolean show) {
        mShowTitleBar = show;
        if (mShowTitleBar) {
            showTitleBar();
        } else {
            hideTitleBar();
        }
    }

    public boolean isFullscreen() {
        return mIsFullscreen;
    }

    public boolean isLocked() {
        return mLockOrientationPanel.isLocked();
    }

    /**
     * 自动解锁
     */
    public void autoUnLock() {
        mLockOrientationPanel.unLocked();
        mLockOrientationPanel.hideLockPanel();
    }

    public void scheduleHideTitleBar() {
        unScheduleHideTitleBar();
        postDelayed(mHideTitleBarRunnable, 3000);//3s后自动隐藏菜单
    }

    protected void unScheduleHideTitleBar() {
        removeCallbacks(mHideTitleBarRunnable);
    }

    private Runnable mHideTitleBarRunnable = new Runnable() {
        @Override
        public void run() {
            if (mPreviewControl.getVisibility() == VISIBLE) {
                return;
            }
            if (mIsShowingToolView) {
                handleSingleTap();
            }
        }
    };

    protected void scheduleAuditionCheck() {
        postDelayed(mAuditionCheckRunnable, 5 * 1000);
    }

    protected void unScheduleAuditionCheck() {
        removeCallbacks(mAuditionCheckRunnable);
    }

    private Runnable mAuditionCheckRunnable = new Runnable() {
        @Override
        public void run() {
            if (!checkAudition()) {
                scheduleAuditionCheck();
            }
        }
    };

    private MediaPlayerGestureController.GestureOperationHelper mGestureHelper =
            new MediaPlayerGestureController.GestureOperationHelper() {
                @Override
                public void onSingleTap() {//单击
                    if (mFrameHandler != null && mFrameHandler.handleSingleTap()) {
                        return;
                    }
                    handleSingleTap();
                }

                @Override
                public void onDoubleTap() {//双击
                    if (!isLoading()) {
                        if (mLockOrientationPanel.isLocked()) {
                            return;
                        }
                        if (isPlaying()) {
                            pause();
                        } else {
                            resume();
                        }
                    }
                }

                @Override
                public void onStartDragProgress() {
                    mBottomControl.startDragProgress();
                }

                @Override
                public void onDragProgress(int startDragPos, float distanceX) {
                    handleDragProgress(startDragPos, distanceX);
                }

                @Override
                public void onEndDragProgress(int dragPosition, float totalDistanceX) {
                    handleEndDragProgress(dragPosition, totalDistanceX);
                    mBottomControl.endDragProgress();
                }

                @Override
                public boolean onCanHandleGesture() {
                    return !isLoading() && !isLoadingFailed();
                }

                @Override
                public int getCurrentPosition() {
                    return MediaPlayerFrame.this.getCurrentPosition();
                }
            };

    public boolean onTouchEvent(MotionEvent ev) {
        mGestureController.handleTouchEvent(ev);
        return true;
    }

    private ScreenOrientationSwitcher.OnChangeListener mOrientationChangeListener =
            new ScreenOrientationSwitcher.OnChangeListener() {
                @Override
                public void onChanged(int requestOrientation) {
                    if (!(isLoading() || isPlaying())) {
                        return;
                    }
                    if (mLockOrientationPanel.isLocked()) {
                        return;
                    }
                    switchFullScreenInternal(requestOrientation);
                }
            };

    private void switchFullScreenInternal(int requestOrientation) {
        Activity activity = (Activity) getContext();
        int currOrientation = activity.getRequestedOrientation();
        if (currOrientation == requestOrientation) {
            return;
        }

        if (requestOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            switchFullScreen(true);
        } else if (requestOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
            switchFullScreen(true, true);
        } else if (requestOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            switchFullScreen(false);
        }
    }

    public void switchFullScreen(boolean isFullscreen) {
        switchFullScreen(isFullscreen, false);
    }

    public void switchFullScreen(boolean isFullscreen, boolean reverseOrientation) {
        mIsFullscreen = isFullscreen;
        if (!mIsFullscreen) {
            hideTitleBar();
            mLockOrientationPanel.hideLockPanel();
        }

        mUpdateMediaSizeDisabled = false;
        if (mIsFullscreen) {
            if (mBottomControl.getVisibility() == View.VISIBLE) {
                mLockOrientationPanel.showLockPanel();
                showTitleBar();
            } else {
                hideTitleBar();
            }
            updateMediaSize(mInitializedScreenSize.y, mInitializedScreenSize.x);
            mUpdateMediaSizeDisabled = true;
        }

        switchScreenOrientation(mIsFullscreen, reverseOrientation);

        toggleFullScreen(mIsFullscreen, reverseOrientation);
        mBottomControl.switchViewState(mIsFullscreen);
        mTopControl.switchViewState(mIsFullscreen);
        requestLayout();
    }

    private void switchScreenOrientation(boolean fullScreen, boolean reverseOrientation) {
        Activity activity = (Activity) getContext();
        if (fullScreen) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            if (reverseOrientation) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            } else {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        } else {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            if (reverseOrientation) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
            } else {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!mUpdateMediaSizeDisabled) {
            updateMediaSize(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT: {
                int position = getCurrentPosition();
                seekTo(position - 10000);
                return true;
            }

            case KeyEvent.KEYCODE_DPAD_RIGHT: {
                int position = getCurrentPosition();
                seekTo(position + 10000);
                return true;
            }

            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                if (isPlaying()) {
                    pause();
                } else {
                    resume();
                }
                return true;

            case KeyEvent.KEYCODE_VOLUME_UP: {
                AudioManager manager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
                manager.adjustStreamVolume(3, 1, 5);
                return true;
            }

            case KeyEvent.KEYCODE_VOLUME_DOWN: {
                AudioManager manager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
                manager.adjustStreamVolume(3, -1, 5);
                return true;
            }

            default:
                break;
        }

        return super.onKeyDown(keyCode, event);
    }
}
