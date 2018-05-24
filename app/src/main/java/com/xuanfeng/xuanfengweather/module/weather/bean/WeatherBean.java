package com.xuanfeng.xuanfengweather.module.weather.bean;

import android.view.View;
import android.widget.Toast;

import java.util.List;

/**
 * Created by zhujh on 2017/7/21.
 * 描述：
 */

public class WeatherBean {

    /**
     * yesterday : {"date":"20日星期四","high":"高温 37℃","fx":"西南风","low":"低温 28℃","fl":"4-5级","type":"多云"}
     * city : 南京
     * aqi : 60
     * forecast : [{"date":"21日星期五","high":"高温 38℃","fengli":"4-5级","low":"低温 29℃","fengxiang":"西南风","type":"晴"},
     * {"date":"22日星期六","high":"高温 39℃","fengli":"3-4级","low":"低温 29℃","fengxiang":"西南风","type":"晴"},
     * {"date":"23日星期天","high":"高温 39℃","fengli":"3-4级","low":"低温 29℃","fengxiang":"西南风","type":"晴"},
     * {"date":"24日星期一","high":"高温 39℃","fengli":"3-4级","low":"低温 30℃","fengxiang":"西南风","type":"多云"},
     * {"date":"25日星期二","high":"高温 39℃","fengli":"3-4级","low":"低温 30℃","fengxiang":"西风","type":"多云"}]
     * ganmao : 各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。
     * wendu : 32
     */

    public DataBean data;
    /**
     * data : {"yesterday":{"date":"20日星期四","high":"高温 37℃","fx":"西南风","low":"低温 28℃","fl":"4-5级","type":"多云"},
     * "city":"南京","aqi":"60","forecast":[{"date":"21日星期五","high":"高温 38℃","fengli":"4-5级","low":"低温 29℃",
     * "fengxiang":"西南风","type":"晴"},{"date":"22日星期六","high":"高温 39℃","fengli":"3-4级","low":"低温 29℃",
     * "fengxiang":"西南风","type":"晴"},{"date":"23日星期天","high":"高温 39℃","fengli":"3-4级","low":"低温 29℃",
     * "fengxiang":"西南风","type":"晴"},{"date":"24日星期一","high":"高温 39℃","fengli":"3-4级","low":"低温 30℃",
     * "fengxiang":"西南风","type":"多云"},{"date":"25日星期二","high":"高温 39℃","fengli":"3-4级","low":"低温 30℃",
     * "fengxiang":"西风","type":"多云"}],"ganmao":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。","wendu":"32"}
     * status : 1000
     * desc : OK
     */

    public int status;//获取的code

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String desc;//OK还是不OK

    public static class DataBean {
        public YesterdayBean getYesterday() {
            return yesterday;
        }

        public void setYesterday(YesterdayBean yesterday) {
            this.yesterday = yesterday;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public List<ForecastBean> getForecast() {
            return forecast;
        }

        public void setForecast(List<ForecastBean> forecast) {
            this.forecast = forecast;
        }

        /**
         * date : 20日星期四
         * high : 高温 37℃
         * fx : 西南风
         * low : 低温 28℃
         * fl : 4-5级
         * type : 多云
         */

        public YesterdayBean yesterday;//昨天
        public String city;
        public String aqi;
        public String ganmao;
        public String wendu;
        /**
         * date : 21日星期五
         * high : 高温 38℃
         * fengli : 4-5级
         * low : 低温 29℃
         * fengxiang : 西南风
         * type : 晴
         */

        public List<ForecastBean> forecast;

        public static class YesterdayBean {
            public String date;
            public String high;
            public String fx;
            public String low;
            public String fl;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String type;
        }

        public static class ForecastBean {
            public String date;
            public String high;
            public String fengli;
            public String low;
            public String fengxiang;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getFengli() {
                return fengli;
            }

            public void setFengli(String fengli) {
                this.fengli = fengli;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getFengxiang() {
                return fengxiang;
            }

            public void setFengxiang(String fengxiang) {
                this.fengxiang = fengxiang;
            }

            public String type;

            public void onItemClick(View view){//mvvm的点击事件
                Toast.makeText(view.getContext(), "日期："+date, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
