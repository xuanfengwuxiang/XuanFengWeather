package com.xuanfeng.xflibrary.widget;

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

import com.xuanfeng.xflibrary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 底部弹窗列表
 */
public class BottomRecyclerView extends RecyclerView {

    private Context mContext;
    private List<String> mList;
    private BottomAdapter mBottomAdapter;
    private OnItemClickListener mListener;

    public BottomRecyclerView(@NonNull Context context) {
        this(context,null);
    }

    public BottomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BottomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    private void init() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext, VERTICAL, false);
        setLayoutManager(manager);
        mList = new ArrayList<>();
        mBottomAdapter = new BottomAdapter(mContext, mList);
        setAdapter(mBottomAdapter);
    }

    public void setData(List<String> list) {
        if (list == null) {
            return;
        }
        mList.addAll(list);
        mBottomAdapter.notifyDataSetChanged();
    }

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
        mBottomAdapter.setListener(listener);
    }


    public static class BottomAdapter extends RecyclerView.Adapter<BottomAdapter.BottomViewHolder> {

        Context mContext;
        List<String> mList;
        OnItemClickListener mListener;

        public BottomAdapter(Context context, List<String> list) {
            mContext = context;
            mList = list;
        }

        @NonNull
        @Override
        public BottomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_xf_bottom, parent, false);
            return new BottomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BottomViewHolder holder, int position) {
            if (mList == null) {
                return;
            }
            holder.mTvFunc.setText(mList.get(position));
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick(position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList != null ? mList.size() : 0;
        }

        public void setListener(OnItemClickListener listener) {
            mListener = listener;
        }

        public static class BottomViewHolder extends RecyclerView.ViewHolder {

            private final TextView mTvFunc;

            public BottomViewHolder(@NonNull View itemView) {
                super(itemView);
                mTvFunc = itemView.findViewById(R.id.tv_func);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
