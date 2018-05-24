package com.xuanfeng.xuanfengweather.module.news;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xuanfeng.xuanfengweather.R;
import com.xuanfeng.xuanfengweather.base.BaseFragment;
import com.xuanfeng.mylibrary.rxbus.RxBean;
import com.xuanfeng.xuanfengweather.constant.RxConstant;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import rx.functions.Action1;


public class NewsFragment extends BaseFragment {

    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.webView)
    WebView webView;
    Unbinder unbinder;
    @BindView(R.id.ptr_frame_layout)
    PtrFrameLayout mPtrFrameLayout;
    private List<String> mTitleDataList;
    private List<String> mUrlList;
    private WebSettings mWebSettings;
    private String mCurrentUrl;
    private View mHeaderVeiw;


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
        initWebView();
        mHeaderVeiw = View.inflate(mContext, R.layout.layout_ptr_header,null);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initData() {
        initPtrFrameLayout();
        initMagicIndicator();
        initUrls();
        setWebviewUrl();
        registerRxbus();
    }

    private void registerRxbus() {
        registRxBus(new Action1<RxBean>() {
            @Override
            public void call(RxBean rxBean) {
                if(rxBean==null){
                    return;
                }
                switch (rxBean.getKey()){
                    case RxConstant.NEWS_GO_BACK:
                        webViewGoBack();
                        break;
                }
            }
        });
    }
    //webview返回
    private void webViewGoBack() {
        if(webView.canGoBack()){
            webView.goBack();
        }
    }

    //初始化下拉刷新
    private void initPtrFrameLayout() {

        mPtrFrameLayout.setHeaderView(mHeaderVeiw);
        mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        webView.reload();
                        mPtrFrameLayout.refreshComplete();
                    }
                },1800);
            }
        });
    }

    //webview的初始化
    private void initWebView() {
        //声明WebSettings子类
        mWebSettings = webView.getSettings();
        // 如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        mWebSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        mWebSettings.setUseWideViewPort(true);
        //将图片调整到适合webview的大小
        mWebSettings.setLoadWithOverviewMode(true);
        // 缩放至屏幕的大小 //缩放操作
        mWebSettings.setSupportZoom(true);
        //支持缩放，默认为true。是下面那个的前提。
        mWebSettings.setBuiltInZoomControls(true);
        //设置内置的缩放控件。若为false，则该WebView不可缩放
        mWebSettings.setDisplayZoomControls(false);
        //隐藏原生的缩放控件 //其他细节操作
        mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //关闭webview中缓存
        mWebSettings.setAllowFileAccess(true);
        //设置可以访问文件
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //支持通过JS打开新窗口
        mWebSettings.setLoadsImagesAutomatically(true);
        //支持自动加载图片
        mWebSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mCurrentUrl = url;
                setWebviewUrl();
                return true;
            }
        });
    }

    //初始化设置WebView指示器
    private void initMagicIndicator() {
        //标题名字
        mTitleDataList = new ArrayList<>();
        mTitleDataList.add("Cnbeta");
        mTitleDataList.add("百度");
        mTitleDataList.add("腾讯");
        mTitleDataList.add("粒度科技");
        //设置滑动模式
        final CommonNavigator commonNavigator = new CommonNavigator(mContext);
        if (mTitleDataList.size() > 4) {//大于4个，可滑动模式
            commonNavigator.setFollowTouch(true);
        } else {//小于等于4，固定模式
            commonNavigator.setAdjustMode(true);
        }
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitleDataList == null ? 0 : mTitleDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
                colorTransitionPagerTitleView.setSelectedColor(mContext.getResources().getColor(R.color.baseThemeColor));
                colorTransitionPagerTitleView.setText(mTitleDataList.get(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mMagicIndicator.onPageSelected(index);
                        notifyDataSetChanged();
                        webView.clearHistory();
                        mCurrentUrl = mUrlList.get(index);
                        setWebviewUrl();
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(mContext.getResources().getColor(R.color.baseThemeColor));
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
    }

    private void initUrls() {
        mUrlList = new ArrayList<>();
        mUrlList.add("http://www.cnbeta.com");
        mUrlList.add("https://www.baidu.com");
        mUrlList.add("http://www.qq.com");
        mUrlList.add( "http://www.lidoogroup.com/");
        mCurrentUrl = mUrlList.get(0);
    }

    //设置新的URL
    private void setWebviewUrl() {
        webView.loadUrl(mCurrentUrl);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

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


}
