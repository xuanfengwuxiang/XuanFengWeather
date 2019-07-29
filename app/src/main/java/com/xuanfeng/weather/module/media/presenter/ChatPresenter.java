package com.xuanfeng.weather.module.media.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.google.gson.JsonObject;
import com.xuanfeng.mylibrary.http.HttpResponse;
import com.xuanfeng.mylibrary.mvp.BasePresenter;
import com.xuanfeng.mylibrary.utils.StringUtils;
import com.xuanfeng.weather.module.media.model.ChatModel;
import com.xuanfeng.weather.module.media.view.ChatView;
import com.xuanfeng.weather.module.media.widget.ChatRecyclerView.ResponseBean;

/**
 * Created by xuanfengwuxiang on 2018/8/20.
 */

public class ChatPresenter extends BasePresenter<ChatView, ChatModel> {

    public ChatPresenter(ChatView view, ChatModel model) {
        super(view, model);
    }

    public void getReply(LifecycleOwner lifecycleOwner, String msg) {
        mModel.getReply(lifecycleOwner,msg, new HttpResponse<JsonObject>() {
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
