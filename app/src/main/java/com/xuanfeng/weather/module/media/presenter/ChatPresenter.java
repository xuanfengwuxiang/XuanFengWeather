package com.xuanfeng.weather.module.media.presenter;

import com.google.gson.JsonObject;
import com.xuanfeng.weather.constant.HttpConstant;
import com.xuanfeng.weather.module.media.view.ChatView;
import com.xuanfeng.weather.module.media.widget.ChatRecyclerView.ResponseBean;
import com.xuanfeng.xflibrary.http.HttpResponse;
import com.xuanfeng.xflibrary.http.httpmgr.HttpManager;
import com.xuanfeng.xflibrary.mvp.BasePresenter;
import com.xuanfeng.xflibrary.utils.StringUtils;

import java.util.LinkedHashMap;

/**
 * Created by xuanfengwuxiang on 2018/8/20.
 */

public class ChatPresenter extends BasePresenter<ChatView> {

    public ChatPresenter(ChatView view) {
        super(view);
    }

    public void getReply(String msg) {
        String url = HttpConstant.CHAT_URL;
        url = url.replace("##content##", msg);
        HttpManager.getInstance().getJO(url, new LinkedHashMap<String, String>(), new HttpResponse<JsonObject>() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                if (jsonObject == null) {
                    return;
                }
                ResponseBean responseBean = (ResponseBean) StringUtils.fromJson(jsonObject.toString(), ResponseBean.class);
                if (responseBean != null) {
                    ResponseBean.ResultBean resultBean = responseBean.getResult();
                    if (resultBean != null) {
                        String response = resultBean.getContent();
                        mView.onGetReply(response);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                //do nothing
            }

            @Override
            public void onComplete() {
                //do nothing
            }
        });
    }
}
