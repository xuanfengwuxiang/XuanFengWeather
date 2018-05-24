package com.xuanfeng.mylibrary.widget.popupmenu;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.xuanfeng.mylibrary.R;
import com.xuanfeng.mylibrary.widget.popupmenu.adapter.PopupMenuAdapter;

import java.util.List;

/**
 * Created by xuanfengwuxiang on 2017/12/4.
 * 这是一个向下弹出的弹窗菜单
 */

public class PopupMenu extends PopupWindow{

    private Context mContext;
    private RecyclerView mRecyclerView;
    private PopupMenuAdapter mPopupMenuAdapter;
    private List<String> mMenuList;
    private View mContentView;//弹窗总布局

    public PopupMenu(Context context, int width, int height, List<String> list) {
        mContext = context;
        mMenuList = list;
        initView(context);
        setAdapter();
        initPopupWindow(width, height);
    }

    private void initView(Context context) {
        mContentView = LayoutInflater.from(context).inflate(R.layout.popupmenu_layout, null);
        mRecyclerView = (RecyclerView) mContentView.findViewById(R.id.recyclerView);
    }

    private void setAdapter() {
        mPopupMenuAdapter = new PopupMenuAdapter(mContext, mMenuList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mPopupMenuAdapter);
    }

    private void initPopupWindow(int width, int height) {
        setContentView(mContentView);
        setWidth(width);
        setHeight(height);
        setFocusable(true);//解决现象：点击按钮，每次弹窗都先消失再显示。理想效果：点击显示，再点消失。
        setBackgroundDrawable(new BitmapDrawable()); //点击外部，弹窗消失
        setOutsideTouchable(true);
    }

    public void notifyDataSetChanged() {
        mPopupMenuAdapter.notifyDataSetChanged();
    }

    public void setOnItemClickListener(PopupMenuAdapter.OnItemClickListener listener) {
        mPopupMenuAdapter.setOnItemClickListener(listener);
    }


}
