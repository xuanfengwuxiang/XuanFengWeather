package com.xuanfeng.weather.module.weather.presenter

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.gson.Gson
import com.xuanfeng.weather.constant.HttpConstant
import com.xuanfeng.weather.module.weather.view.WeatherView
import com.xuanfeng.weather.module.weather.widget.WeatherRecyclerView.WeatherBean
import com.xuanfeng.xflibrary.http.httpmgr.HttpManager.Companion.instance
import com.xuanfeng.xflibrary.mvp.BasePresenter

/**
 * Created by xuanfengwuxiang on 2017/12/13.
 */
class WeatherPresenter : BasePresenter<WeatherView?>, DefaultLifecycleObserver {
    private var mWeatherView: WeatherView? = null

    override fun attachView(weatherView: WeatherView?) {
        mWeatherView = weatherView
    }

    override fun detachView() {}
    fun getWeather(lifecycleOwner: LifecycleOwner?, city: String?) {
        if (TextUtils.isEmpty(city)) {
            return
        }
        val params = LinkedHashMap<String?, Any?>()
        params["city"] = city
        mWeatherView!!.showProgress()
        instance.getJO(HttpConstant.WEATHER_URL, params) {
            onSuccess {
                mWeatherView!!.hideProgress()
                val weatherBean = Gson().fromJson(it.toString(), WeatherBean::class.java)
                val list = weatherBean?.data?.forecast
                if (list != null && list.isNotEmpty()) {
                    mWeatherView!!.onGetWeatherSuccess(list)
                }
            }

            onError {
                mWeatherView!!.hideProgress()
            }
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        Log.i("WeatherPresenter", "onResume了")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        Log.i("WeatherPresenter", "onDestroy走了")
    }
}