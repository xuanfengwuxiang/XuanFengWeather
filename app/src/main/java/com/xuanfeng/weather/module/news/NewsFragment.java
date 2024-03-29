package com.xuanfeng.weather.module.news;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xuanfeng.weather.R;
import com.xuanfeng.weather.databinding.FragmentNewsBinding;
import com.xuanfeng.xflibrary.magicindicator.MagicListener;
import com.xuanfeng.xflibrary.mvp.BaseFragment;
import com.xuanfeng.xflibrary.mvp.BasePresenter;

//新闻界面
public class NewsFragment extends BaseFragment<BasePresenter, FragmentNewsBinding> implements View.OnClickListener {


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


    MagicListener mMagicListener = (int position, String key) -> {
        mBinding.magicIndicator.onPageSelected(position);
        mBinding.webView.clearHistory();
        mCurrentUrl = key;
        setWebViewUrl();
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
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public BasePresenter initPresenter() {
        mBinding.setFragment(this);
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        NewsUtil.setTittleBar(getContext(), mBinding.includeTittle.tvTittle, mBinding.includeTittle.ivLeft);
        mWebSettings = NewsUtil.initWebViewSettings(mBinding.webView);
        NewsUtil.initPtrFrameLayout(getContext(), mBinding.ptrFrameLayout, mBinding.webView);
        mBinding.webView.setWebViewClient(mWebViewClient);
        mBinding.webView.addJavascriptInterface(new JSNativeInterface(), "dialing");
        mCurrentUrl = NewsUtil.initMagicIndicator(getContext(), mBinding.magicIndicator, mMagicListener);
        setWebViewUrl();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                NewsUtil.webViewGoBack(mBinding.webView);
                break;
            default:
                break;
        }
    }


    final class JSNativeInterface {
        /**
         * 错误提示
         */
        @JavascriptInterface
        public void showError(String errorMsg) {
            if (!TextUtils.isEmpty(errorMsg)) {

            }
        }

        /**
         * js->native返回首页接口（退出H5页面）
         */
        @JavascriptInterface
        public void goHome() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onBack();
                }
            });
        }
    }

    /**
     * 返回上一级
     */

    private void onBack() {
        if (mBinding.webView.canGoBack()) {
            mBinding.webView.goBack();
        } else {
//            finish();
//            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
    }
}
