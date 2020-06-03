package com.xuanfeng.mylibrary.contacts;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xuanfeng.mylibrary.R;

import java.util.ArrayList;
import java.util.List;

public class ContactRecyclerView extends RecyclerView {

    private Context mContext;
    private List<ContactBean> mList;
    private ContactAdapter mContactAdapter;

    public ContactRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public ContactRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContactRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    private void init() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(VERTICAL);
        setLayoutManager(manager);

        mList = new ArrayList<>();
        mContactAdapter = new ContactAdapter(mContext, mList);
        setAdapter(mContactAdapter);
    }


    public void setData(List<ContactBean> list) {
        mList.clear();
        mList.addAll(list);
        mContactAdapter.notifyDataSetChanged();
    }


    private class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {

        private List<ContactBean> mList;
        private Context mContext;

        public ContactAdapter(Context context, List<ContactBean> list) {
            mList = list;
            mContext = context;
        }

        @NonNull
        @Override
        public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_contact_recyclerview, parent, false);
            return new ContactHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactHolder holder, int position) {

            if (mList.isEmpty()) {
                return;
            }

            ContactBean bean = mList.get(position);
            holder.mTvChar.setText(bean.getFirstChar());
            holder.mTvName.setText(bean.getName());
            holder.mTvTel.setText(bean.getPhoneNum());
            holder.mTvChar.setVisibility(bean.isFirst() ? VISIBLE : GONE);

        }

        @Override
        public int getItemCount() {
            return mList != null ? mList.size() : 0;
        }

        class ContactHolder extends RecyclerView.ViewHolder {

            private final TextView mTvChar;
            private final TextView mTvName;
            private final TextView mTvTel;

            public ContactHolder(@NonNull View itemView) {
                super(itemView);
                mTvChar = itemView.findViewById(R.id.tv_char);
                mTvName = itemView.findViewById(R.id.tv_name);
                mTvTel = itemView.findViewById(R.id.tv_tel);
            }
        }
    }

    public static class ContactBean {
        private String name;//姓名
        private String phoneNum;//手机号
        private String firstChar;//姓名首字母
        private boolean isFirst;//是否字母组第一个

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public String getFirstChar() {
            return firstChar;
        }

        public void setFirstChar(String firstChar) {
            this.firstChar = firstChar;
        }

        public boolean isFirst() {
            return isFirst;
        }

        public void setFirst(boolean first) {
            isFirst = first;
        }
    }
}
