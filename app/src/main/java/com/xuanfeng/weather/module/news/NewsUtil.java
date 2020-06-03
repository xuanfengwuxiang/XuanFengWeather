package com.xuanfeng.weather.module.news;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuanfeng.xflibrary.magicindicator.MagicBean;
import com.xuanfeng.xflibrary.magicindicator.MagicListener;
import com.xuanfeng.xflibrary.magicindicator.MagicUtil;
import com.xuanfeng.xflibrary.utils.ImageUtil;
import com.xuanfeng.weather.R;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by xuanfengwuxiang on 2018/8/2.
 */

public class NewsUtil {
    private NewsUtil() {
    }

    //webView的功能设置
    public static WebSettings initWebViewSettings(WebView webView) {
        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setUseWideViewPort(true);//设置自适应屏幕，两者合用
        mWebSettings.setLoadWithOverviewMode(true);//将图片调整到适合webview的大小
        mWebSettings.setSupportZoom(true); //缩放操作
        mWebSettings.setBuiltInZoomControls(true);//支持缩放，默认为true。是下面那个的前提。
        mWebSettings.setDisplayZoomControls(false);//设置内置的缩放控件。若为false，则该WebView不可缩放
        mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        mWebSettings.setAllowFileAccess(true);//设置可以访问文件
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        mWebSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        mWebSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        mWebSettings.setDomStorageEnabled(true);//DOM缓存
        return mWebSettings;
    }

    public static String initMagicIndicator(Context context, MagicIndicator mMagicIndicator, MagicListener mTabListener) {
        String currentUrl = "";
        List<MagicBean> magicList = getMagicList();
        int baseThemeColor = context.getResources().getColor(R.color.baseThemeColor);
        MagicUtil.setMagicAdapter(context, magicList, mMagicIndicator, baseThemeColor, baseThemeColor, null, mTabListener);
        if (!magicList.isEmpty()) {
            currentUrl = magicList.get(0).getKey();
        }
        return currentUrl;
    }

    public static List<MagicBean> getMagicList() {
        List<MagicBean> magicList = new ArrayList<>();
        magicList.add(new MagicBean("Cnbeta", "http://www.cnbeta.com"));
        magicList.add(new MagicBean("百度", "https://www.baidu.com"));
        magicList.add(new MagicBean("腾讯", "http://www.qq.com"));
        magicList.add(new MagicBean("粒度科技", "http://www.lidoogroup.com/"));
        return magicList;
    }

    public static void initPtrFrameLayout(Context context, final PtrFrameLayout mPtrFrameLayout, final WebView webView) {
        mPtrFrameLayout.setHeaderView(View.inflate(context, R.layout.layout_ptr_header, null));
        mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(() -> {
                    webView.reload();
                    mPtrFrameLayout.refreshComplete();
                }, 1800);
            }
        });
    }

    //webView返回
    public static void webViewGoBack(WebView webView) {
        if (webView.canGoBack()) {
            webView.goBack();
        }
    }

    public static void setTittleBar(Context context, TextView mTvTittle, ImageView mIvLeft) {
        mTvTittle.setText("新闻");
        mIvLeft.setImageBitmap(ImageUtil.getColorBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_back), 128f, 128f, 128f, 128f));
    }
}
