package com.xuanfeng.testcomponent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;


import com.xuanfeng.testcomponent.R;

import java.util.List;

/**
 * Created by xuanfengwuxiang on 2017/11/12.
 */

public class ViewPagerPhotoTestAdapter extends PagerAdapter {
    private Context mContext;
    private List<Integer> mUrls;

    public ViewPagerPhotoTestAdapter(Context Context, List<Integer> urls) {
        mContext = Context;
        mUrls = urls;
    }

    @Override
    public int getCount() {
        return mUrls == null ? 0 : mUrls.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_photo, container, false);
        ImageView mIvPhoto = (ImageView) view.findViewById(R.id.iv_photo);
        //Picasso.with(mContext).load(mUrls.get(position)).into(mIvPhoto);
        mIvPhoto.setImageResource(mUrls.get(position));
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

}
