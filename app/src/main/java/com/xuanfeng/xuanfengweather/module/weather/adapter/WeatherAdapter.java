package com.xuanfeng.xuanfengweather.module.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuanfeng.xuanfengweather.R;
import com.xuanfeng.xuanfengweather.module.weather.bean.WeatherBean.DataBean.ForecastBean;

import java.util.List;

/**
 * Created by zhujh on 2017/7/21.
 * 描述：温度列表的适配器
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    private Context mContext;
    private List<ForecastBean> mList;

    public WeatherAdapter(Context context, List<ForecastBean> list) {
        mContext = context;
        mList = list;
    }


    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_weather, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(WeatherAdapter.ViewHolder holder, int position) {
        if (mList != null) {
            holder.mTvDate.setText(mList.get(position).getDate());
            holder.mTvLowTemp.setText(mList.get(position).getLow());
            holder.mTvHighTemp.setText(mList.get(position).getHigh());
            holder.mTvType.setText(mList.get(position).getType());
            holder.mTvWindOrientation.setText(mList.get(position).getFengxiang());
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTvDate;
        private final TextView mTvLowTemp;
        private final TextView mTvHighTemp;
        private final TextView mTvWindOrientation;
        private final TextView mTvType;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvDate = (TextView) itemView.findViewById(R.id.tv_date);
            mTvLowTemp = (TextView) itemView.findViewById(R.id.tv_low_temp);
            mTvHighTemp = (TextView) itemView.findViewById(R.id.tv_high_temp);
            mTvWindOrientation = (TextView) itemView.findViewById(R.id.tv_wind_orientation);
            mTvType = (TextView) itemView.findViewById(R.id.tv_type);
        }
    }
}
