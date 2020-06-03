package com.xuanfeng.xflibrary.widget.popupmenu.adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuanfeng.xflibrary.R;

import java.util.List;

/**
 * Created by xuanfengwuxiang on 2017/12/4.
 * PopupMenu适配器
 */

public class PopupMenuAdapter extends RecyclerView.Adapter<PopupMenuAdapter.PopMenuViewHolder>
        implements View.OnClickListener {

    private Context mContext;
    private List<String> mList;
    private OnItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }
    @FunctionalInterface//item点击监听
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public PopupMenuAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public PopMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_popupmenu, null);
        view.setOnClickListener(this);
        return new PopMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PopMenuViewHolder holder, int position) {
        if (mList != null) {
            holder.textView.setText(mList.get(position));
            holder.itemView.setTag(position);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class PopMenuViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public PopMenuViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_content);
        }
    }
}
