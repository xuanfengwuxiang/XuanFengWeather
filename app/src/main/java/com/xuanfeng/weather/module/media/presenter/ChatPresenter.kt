package com.xuanfeng.weather.module.media.presenter

import com.google.gson.Gson
import com.xuanfeng.weather.constant.HttpConstant
import com.xuanfeng.weather.module.media.view.ChatView
import com.xuanfeng.weather.module.media.widget.ChatRecyclerView.ResponseBean
import com.xuanfeng.xflibrary.http.httpmgr.HttpManager.Companion.instance
import com.xuanfeng.xflibrary.mvp.BasePresenter

/**
 * Created by xuanfengwuxiang on 2018/8/20.
 */
class ChatPresenter : BasePresenter<ChatView?> {
    var mView: ChatView? = null
    fun getReply(msg: String?) {
        var url = HttpConstant.CHAT_URL
        url = url.replace("##content##", msg!!)
        instance.getJO(url, LinkedHashMap()) {
            onSuccess {

                val responseBean = Gson().fromJson(it?.toString(), ResponseBean::class.java)
                if (responseBean != null) {
                    val resultBean = responseBean.result
                    if (resultBean != null) {
                        val response = resultBean.content
                        mView!!.onGetReply(response)
                    }
                }
            }

        }

    }

    override fun attachView(chatView: ChatView?) {
        mView = chatView
    }

    override fun detachView() {}
}