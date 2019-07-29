package com.xuanfeng.weather.mvvm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * MVVM模式，RecyclerView的通用adapter
 */

public class RecyclerViewBaseAdapter<T> extends RecyclerView.Adapter<RecyclerViewBaseAdapter.BaseViewHolder> {


    protected Context mContext;
    protected List<T> mList;//列表数据
    protected int mLayoutId;//item布局id
    protected int mVariableId;//mvvm绑定的viewModel引用

    public RecyclerViewBaseAdapter(Context context, List<T> list, int layoutId, int variableId) {
        mContext = context;
        mList = list;
        mLayoutId = layoutId;
        mVariableId = variableId;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {//ViewHolder持有：根布局，binding。
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), mLayoutId, parent, false);
        BaseViewHolder viewHolder = new BaseViewHolder(binding.getRoot());
        viewHolder.setBinding(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewBaseAdapter.BaseViewHolder holder, int position) {//绑定数据，通过binding来操作
        holder.binding.setVariable(mVariableId, mList.get(position));
        holder.binding.executePendingBindings();
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }

        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }
}
