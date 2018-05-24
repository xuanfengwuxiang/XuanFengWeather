package com.xuanfeng.xuanfengweather.module.gallery.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.xuanfeng.xuanfengweather.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuanfengwuxiang on 2017/11/12.
 */

public class ViewPagerPhotoAdapter extends PagerAdapter {
    private Context mContext;
    private List<String> mUrls;
    private List<String> mTittleList;

    public ViewPagerPhotoAdapter(Context Context, List<String> urls,List<String> tittleList) {
        mContext = Context;
        mUrls = urls;
        mTittleList = tittleList;
    }

    @Override
    public int getCount() {
        return mUrls == null ? 0 : mUrls.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_photo, container, false);
        ImageView mIvPhoto = (ImageView) view.findViewById(R.id.iv_photo);
        Picasso.with(mContext).load(mUrls.get(position)).into(mIvPhoto);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {//必须实现，销毁
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTittleList.get(position);
    }
}
