package com.xuanfeng.xuanfengweather.module.media.model;


import com.xuanfeng.mylibrary.http.HttpResponse;
import com.xuanfeng.mylibrary.mvp.BaseModel;
import com.xuanfeng.mylibrary.mvp.BaseView;
import com.xuanfeng.xuanfengweather.constant.HttpConstant;

import java.util.LinkedHashMap;

/**
 * Created by xuanfengwuxiang on 2018/8/20.
 */

public class ChatModel extends BaseModel {

    public ChatModel(BaseView baseView) {
        super(baseView);
    }

    public void getReply(String msg, HttpResponse httpResponse) {
        String url = HttpConstant.CHAT_URL;
        url = url.replace("##content##", msg);
        get(url, new LinkedHashMap<String, String>(), httpResponse);
    }
}
