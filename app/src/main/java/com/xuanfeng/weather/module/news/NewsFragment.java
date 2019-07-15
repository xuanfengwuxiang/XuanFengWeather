package com.xuanfeng.weather.module.news;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuanfeng.mylibrary.magicindicator.MagicListener;
import com.xuanfeng.mylibrary.mvp.BaseFragment;
import com.xuanfeng.mylibrary.mvp.BasePresenter;
import com.xuanfeng.weather.R;
import com.xuanfeng.weather.databinding.FragmentNewsBinding;

import butterknife.BindView;
import butterknife.OnClick;

//新闻界面
public class NewsFragment extends BaseFragment<BasePresenter, FragmentNewsBinding> {


    @BindView(R.id.iv_left)
    ImageView mIvLeft;


    @BindView(R.id.tv_tittle)
    TextView mTvTittle;

    private WebSettings mWebSettings;
    private String mCurrentUrl;


    //设置新的URL
    private void setWebViewUrl() {
        mBinding.webView.loadUrl(mCurrentUrl);
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
            mBinding.magicIndicator.onPageSelected(position);
            mBinding.webView.clearHistory();
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
                NewsUtil.webViewGoBack(mBinding.webView);
                break;
            case R.id.tv_left:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        NewsUtil.setTittleBar(getContext(), mTvTittle, mIvLeft);
        mWebSettings = NewsUtil.initWebViewSettings(mBinding.webView);
        NewsUtil.initPtrFrameLayout(getContext(), mBinding.ptrFrameLayout, mBinding.webView);
        mBinding.webView.setWebViewClient(mWebViewClient);
        mCurrentUrl = NewsUtil.initMagicIndicator(getContext(), mBinding.magicIndicator, mMagicListener);
        setWebViewUrl();
    }
}
