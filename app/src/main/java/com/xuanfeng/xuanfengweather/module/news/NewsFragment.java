package com.xuanfeng.xuanfengweather.module.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuanfeng.mylibrary.magicindicator.MagicListener;
import com.xuanfeng.xuanfengweather.R;
import com.xuanfeng.xuanfengweather.base.BaseFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrFrameLayout;

//新闻界面
public class NewsFragment extends BaseFragment {

    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.webView)
    WebView webView;
    Unbinder unbinder;
    @BindView(R.id.ptr_frame_layout)
    PtrFrameLayout mPtrFrameLayout;
    @BindView(R.id.iv_left)
    ImageView mIvLeft;


    @BindView(R.id.tv_tittle)
    TextView mTvTittle;

    private WebSettings mWebSettings;
    private String mCurrentUrl;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_personal, container, false);
        unbinder = ButterKnife.bind(this, mContentView);
        initViews();
        setListeners();
        initData();
        return mContentView;
    }

    @Override
    protected void initViews() {
        NewsUtil.setTittleBar(getContext(), mTvTittle, mIvLeft);
        mWebSettings = NewsUtil.initWebViewSettings(webView);
        NewsUtil.initPtrFrameLayout(getContext(), mPtrFrameLayout, webView);
        webView.setWebViewClient(mWebViewClient);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initData() {
        mCurrentUrl = NewsUtil.initMagicIndicator(getContext(), mMagicIndicator, mMagicListener);
        setWebViewUrl();
    }


    //设置新的URL
    private void setWebViewUrl() {
        webView.loadUrl(mCurrentUrl);
    }

    WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            mCurrentUrl = url;
            setWebViewUrl();
            return true;
        }
    };


    MagicListener mMagicListener = new MagicListener() {
        @Override
        public void onClick(int position, String key) {
            mMagicIndicator.onPageSelected(position);
            webView.clearHistory();
            mCurrentUrl = key;
            setWebViewUrl();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        mWebSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        mWebSettings.setJavaScriptEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }


    @OnClick({R.id.iv_left, R.id.tv_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                NewsUtil.webViewGoBack(webView);
                break;
            case R.id.tv_left:
                break;
        }
    }
}
