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

package com.jungle.mediaplayer.widgets.control;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jungle.mediaplayer.R;

public class PlayerPreviewControl extends FrameLayout {

    public interface Listener {
        void onPlayBtnClicked();
    }

    private RelativeLayout mPreviewContainer;
    private ImageView mPreviewImage;
    private Listener mListener;

    public PlayerPreviewControl(Context context) {
        super(context);
        initLayout(context);
    }

    public PlayerPreviewControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public PlayerPreviewControl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }

    private void initLayout(Context context) {
    }

    public void createDefault() {
        create(R.layout.layout_default_player_preview_control);
    }

    public void create(int resId) {
        View.inflate(getContext(), resId, this);
        mPreviewContainer = (RelativeLayout) findViewById(R.id.preview_container);
        mPreviewImage = (ImageView) findViewById(R.id.preview_image);
        mPreviewImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        findViewById(R.id.preview_play).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onPlayBtnClicked();
            }
        });
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public void setPreviewImage(Bitmap bitmap) {
        if (bitmap != null) {
            mPreviewImage.setImageBitmap(bitmap);
        } else {
            mPreviewImage.setImageResource(R.drawable.preview_default);
        }
    }

    public void show() {
        setVisibility(View.VISIBLE);
    }

    public void hide() {
        setVisibility(View.GONE);
    }
}