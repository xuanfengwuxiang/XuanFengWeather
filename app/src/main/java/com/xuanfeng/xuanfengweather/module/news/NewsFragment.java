package com.xuanfeng.xuanfengweather.module.news;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuanfeng.mylibrary.magicindicator.MagicListener;
import com.xuanfeng.mylibrary.mvp.BaseFragment;
import com.xuanfeng.xuanfengweather.R;

import net.lucode.hackware.magicindicator.MagicIndicator;

import butterknife.BindView;
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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData(Bundle bundle) {
        NewsUtil.setTittleBar(getContext(), mTvTittle, mIvLeft);
        mWebSettings = NewsUtil.initWebViewSettings(webView);
        NewsUtil.initPtrFrameLayout(getContext(), mPtrFrameLayout, webView);
        webView.setWebViewClient(mWebViewClient);
        mCurrentUrl = NewsUtil.initMagicIndicator(getContext(), mMagicIndicator, mMagicListener);
        setWebViewUrl();
    }
}
