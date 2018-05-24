/**
 * Android Jungle-MediaPlayer framework project.
 * <p>
 * Copyright 2016 Arno Zhang <zyfgood12@163.com>
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jungle.mediaplayer.widgets.control;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jungle.mediaplayer.R;
import com.jungle.mediaplayer.base.BaseMediaPlayerListener;
import com.jungle.mediaplayer.base.MediaPlayerUtils;
import com.jungle.mediaplayer.widgets.MediaPlayerFrame;

public class PlayerBottomControl extends FrameLayout {

    public interface OperationHelper {
        void switchPlayOrPause();

        void switchFullScreen();
    }

    private LinearLayout mBottomControlContainer;
    private View mVolumeView;
    private ImageView mPlayPauseBtn;
    private ImageView mFullScreenView;
    private TextView mCurrentProgressView;
    private TextView mTotalDurationView;//总时长
    private SeekBar mSeekBar;
    private OperationHelper mOperationHelper;
    private MediaPlayerFrame mMediaPlayer;
    private boolean mDisableVolume = false;
    private boolean mDragProgress = false;
    private int mPlayPosition = 0;
    private boolean mShowVolumeView = false;
    private boolean mIsFullScreen = false;//是否是全屏

    public PlayerBottomControl(Context context) {
        super(context);
        initLayout(context);
    }

    public PlayerBottomControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public PlayerBottomControl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }

    private void initLayout(Context context) {
    }

    public void createDefault() {
        create(R.layout.layout_default_player_bottom_control);
    }

    public void create(int resId) {
        View.inflate(getContext(), resId, this);
        mBottomControlContainer = (LinearLayout) findViewById(R.id.bottom_control_container);
        mCurrentProgressView = (TextView) findViewById(R.id.player_curr_progress);
        mTotalDurationView = (TextView) findViewById(R.id.player_total_duration);
        mSeekBar = (SeekBar) findViewById(R.id.player_progress);
        mPlayPauseBtn = (ImageView) findViewById(R.id.player_play_or_pause_btn);
        mFullScreenView = (ImageView) findViewById(R.id.player_switch_full_screen);
        mVolumeView = findViewById(R.id.player_volume_btn);
        mVolumeView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDisableVolume) {
                    mMediaPlayer.setVolume(1.0f);
                } else {
                    mMediaPlayer.setVolume(0.0f);
                }

                mDisableVolume = !mDisableVolume;
                mVolumeView.setBackgroundResource(mDisableVolume
                        ? R.drawable.volume_disable_icon : R.drawable.volume_icon);

                mMediaPlayer.scheduleHideTitleBar();//重置定时隐藏菜单任务
            }
        });

        mFullScreenView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOperationHelper.switchFullScreen();
                mMediaPlayer.scheduleHideTitleBar();//重置定时隐藏菜单任务
            }
        });

        mPlayPauseBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOperationHelper.switchPlayOrPause();
                mMediaPlayer.scheduleHideTitleBar();//重置定时隐藏菜单任务
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser) {
                    return;
                }

                updateSeek(progress, false);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mDragProgress = true;
                mMediaPlayer.scheduleHideTitleBar();//重置定时隐藏菜单任务

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mDragProgress = false;
                updateSeek(seekBar.getProgress(), true);
                mMediaPlayer.scheduleHideTitleBar();//重置定时隐藏菜单任务
            }

            private void updateSeek(int progress, boolean doSeek) {
                float percent = (float) progress / (float) mSeekBar.getMax();
                int duration = mMediaPlayer.getDuration();
                int seekToPosition = (int) (duration * percent);
                if (mIsFullScreen) {
                    mCurrentProgressView.setText(MediaPlayerUtils.formatTime(seekToPosition));
                } else {
                    mCurrentProgressView.setText(MediaPlayerUtils.formatTime(seekToPosition)
                            + "/" + MediaPlayerUtils.formatTime(duration));
                }

                if (doSeek) {
                    mMediaPlayer.seekTo(seekToPosition);
                }
            }
        });
    }

    public ViewGroup getRightExtraContainer() {
        return (ViewGroup) findViewById(R.id.player_bottom_control_right_extra);
    }

    public void setShowVolumeView(boolean show) {
        mShowVolumeView = show;
    }

    public void showSwitchFullScreenBtn(boolean show) {
        findViewById(R.id.player_switch_full_screen).setVisibility(
                show ? View.VISIBLE : View.GONE);
    }

    public void setOperationHelper(OperationHelper helper) {
        mOperationHelper = helper;
    }

    public void doDestroy() {
        clearUpdateProgress();
    }

    public int getPlayPosition() {
        return mPlayPosition;
    }

    public void prepareForPlay() {
        mDragProgress = false;
    }

    public void startDragProgress() {
        mDragProgress = true;
    }

    public void dragProgress(int progress) {
        if (!mDragProgress) {
            return;
        }

        int duration = mMediaPlayer.getDuration();
        updateProgressInternal(progress, duration);
    }

    public void endDragProgress() {
        mDragProgress = false;
    }

    private void clearUpdateProgress() {
        removeCallbacks(mUpdateProgressRunnable);
    }

    public void setMediaPlayer(MediaPlayerFrame player) {
        mMediaPlayer = player;
        mMediaPlayer.addPlayerListener(new BaseMediaPlayerListener() {
            @Override
            public void onLoading() {
                updateProgressInternal(0, 0);
            }

            @Override
            public void onLoadFailed() {
            }

            @Override
            public void onFinishLoading() {
            }

            @Override
            public void onError(int what, boolean canReload, String message) {
                clearUpdateProgress();
            }

            @Override
            public void onStartPlay() {
                mPlayPauseBtn.setImageResource(R.drawable.player_icon_media_pause);
                post(mUpdateProgressRunnable);
            }

            @Override
            public void onPlayComplete() {
                mPlayPauseBtn.setImageResource(R.drawable.player_icon_media_play);
                updateProgressInternal();
            }

            @Override
            public void onStartSeek() {
            }

            @Override
            public void onSeekComplete() {
            }

            @Override
            public void onResumed() {
                mPlayPauseBtn.setImageResource(R.drawable.player_icon_media_pause);
            }

            @Override
            public void onPaused() {
                mPlayPauseBtn.setImageResource(R.drawable.player_icon_media_play);
            }

            @Override
            public void onStopped() {
                mPlayPauseBtn.setImageResource(R.drawable.player_icon_media_play);
                clearUpdateProgress();
            }
        });
    }

    public void switchViewState(boolean isFullScreen) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mSeekBar.getLayoutParams();
        float left = 0f;
        float right = 0f;
        if (isFullScreen) {
            left = getContext().getResources().getDimension(R.dimen.full_screen_seek_bar_margin_left);
            right = getContext().getResources().getDimension(R.dimen.full_screen_seek_bar_margin_right);
            mVolumeView.setVisibility(mShowVolumeView ? View.VISIBLE : View.GONE);
            mFullScreenView.setVisibility(GONE);//全屏时隐藏“全屏半屏控制按钮”
//            mFullScreenView.setImageResource(R.drawable.player_icon_fullscreen_shrink);
            mTotalDurationView.setVisibility(VISIBLE);
        } else {
            left = getContext().getResources().getDimension(R.dimen.half_screen_seek_bar_margin_left);
            right = getContext().getResources().getDimension(R.dimen.half_screen_seek_bar_margin_right);
            mVolumeView.setVisibility(View.GONE);
            mFullScreenView.setVisibility(VISIBLE);
            mFullScreenView.setImageResource(R.drawable.player_icon_fullscreen_stretch);
            mTotalDurationView.setVisibility(GONE);
        }
        params.leftMargin = (int) left;
        params.rightMargin = (int) right;
        mSeekBar.setLayoutParams(params);
        mIsFullScreen = isFullScreen;
        updateProgressInternal();

    }

    private void updateProgress() {
        postDelayed(mUpdateProgressRunnable, 1000);
        if (mDragProgress || !mMediaPlayer.isPlaying()) {
            return;
        }

        updateProgressInternal();
    }

    private void updateProgressInternal() {
        int position = mMediaPlayer.getCurrentPosition();
        int duration = mMediaPlayer.getDuration();
        updateProgressInternal(position, duration);
    }

    private void updateProgressInternal(int position, int duration) {
        if (position < 0) {
            position = 0;
        }

        if (position >= duration) {
            position = duration;
        }

        mPlayPosition = position;
        if (duration == 0) {
            position = 0;
            mSeekBar.setSecondaryProgress(0);
            mSeekBar.setProgress(0);
        } else {
            mSeekBar.setSecondaryProgress((int) (mMediaPlayer.getBufferPercent() / 100.0f * duration));
            mSeekBar.setProgress(position);
            mSeekBar.setMax(duration);
        }

        if (mIsFullScreen) {//全屏
            mCurrentProgressView.setText(MediaPlayerUtils.formatTime(position));
            mTotalDurationView.setText(MediaPlayerUtils.formatTime(duration));
        } else {//半屏
            mCurrentProgressView.setText(MediaPlayerUtils.formatTime(position)
                    + "/" + MediaPlayerUtils.formatTime(duration));
        }
    }

    private Runnable mUpdateProgressRunnable = new Runnable() {
        @Override
        public void run() {
            updateProgress();
        }
    };
}
