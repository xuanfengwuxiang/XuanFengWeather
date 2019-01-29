package com.xuanfeng.xuanfengweather.module.media.model;


import android.arch.lifecycle.LifecycleOwner;

import com.xuanfeng.mylibrary.http.HttpResponse;
import com.xuanfeng.mylibrary.mvp.BaseModel;
import com.xuanfeng.mylibrary.mvp.BaseView;
import com.xuanfeng.xuanfengweather.constant.HttpConstant;

import java.util.LinkedHashMap;

/**
 * Created by xuanfengwuxiang on 2018/8/20.
 * 聊天网络请求
 */

public class ChatModel extends BaseModel {

    public ChatModel(BaseView baseView) {
        super(baseView);
    }

    public void getReply(LifecycleOwner lifecycleOwner, String msg, HttpResponse httpResponse) {
        String url = HttpConstant.CHAT_URL;
        url = url.replace("##content##", msg);
        getJsonObjectByGet(lifecycleOwner, url, new LinkedHashMap<String, String>(), httpResponse);
    }
}