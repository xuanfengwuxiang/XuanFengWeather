package com.xuanfeng.weather.module.media.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.databinding.ViewDataBinding;

import com.xuanfeng.customtextviewlib.CustomTextView;
import com.xuanfeng.mylibrary.mvp.BaseActivity;
import com.xuanfeng.mylibrary.mvp.BaseFragment;
import com.xuanfeng.mylibrary.mvp.BasePresenter;
import com.xuanfeng.weather.R;
import com.xuanfeng.weather.module.media.model.ChatModel;
import com.xuanfeng.weather.module.media.presenter.ChatPresenter;
import com.xuanfeng.weather.module.media.view.ChatView;
import com.xuanfeng.weather.module.media.widget.ChatRecyclerView;
import com.xuanfeng.weather.module.media.widget.ChatRecyclerView.ChatBean;

import butterknife.BindView;
import butterknife.OnClick;

//聊天界面
public class ChatFragment extends BaseFragment<ChatPresenter, ViewDataBinding> implements ChatView {


    @BindView(R.id.rv_chat)
    ChatRecyclerView mRvChat;

    @BindView(R.id.et_input)
    EditText mEtInput;

    @BindView(R.id.tv_send)
    CustomTextView mTvSend;
    private ChatPresenter mChatPresenter;


    @OnClick(R.id.tv_send)
    public void onViewClicked() {
        String content = mEtInput.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(getContext(), "请输入聊天内容", Toast.LENGTH_SHORT).show();
            return;
        }
        mRvChat.setData(new ChatBean("me", content));
        mEtInput.getText().clear();
        mChatPresenter.getReply(this, content);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    public BasePresenter initPresenter() {
        mChatPresenter = new ChatPresenter(this, new ChatModel(this));
        return mChatPresenter;
    }

    @Override
    public void initData(Bundle bundle) {
        mRvChat.setData(new ChatBean("you", "嗨，我是陪聊师##__##"));
    }

    @Override
    public void onGetReply(String content) {
        if (!TextUtils.isEmpty(content)) {
            mRvChat.setData(new ChatBean("you", content));
        }
    }


}
