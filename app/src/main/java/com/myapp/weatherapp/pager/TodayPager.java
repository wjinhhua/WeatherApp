package com.myapp.weatherapp.pager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapp.weatherapp.R;
import com.myapp.weatherapp.domain.Weather;
import com.myapp.weatherapp.util.CommonUtil;

/**
 * 版权: ft626 版权所有(c) 2016
 * 作者: wjh
 * 版本: 1.0
 * 创建日期: 2016/7/6.22:58
 * 描述:
 **/
public class TodayPager extends BasePager {
    private TextView tv_today_shishi, tv_today_weather, tv_today_tem, tv_today_wind_shishi,
            tv_today_fl, tv_today_hum, tv_today_pres, tv_today_vis, tv_today_pcpn;
    private ImageView iv_today_weather, iv_today_weather_allday_d, iv_today_weather_allday_n;
    private TextView tv_today_weather_allday_d, tv_today_weather_allday_n, tv_today_tem_allday,
            tv_today_astro_allday, tv_today_wind_allday, tv_today_hum_allday, tv_today_pres_allday,
            tv_today_pcpn_allday, tv_today_vis_allday;
    private TextView tv_today_qlty, tv_today_aqi, tv_today_co, tv_today_no2, tv_today_o3, tv_today_so2, tv_today_pm25;
    private TextView tv_today_comf_brf, tv_today_comf_txt, tv_today_cw_brf, tv_today_cw_txt,
            tv_today_drsg_brf, tv_today_drsg_txt, tv_today_flu_brf, tv_today_flu_txt, tv_today_sport_brf, tv_today_sport_txt,
            tv_today_trav_brf, tv_today_trav_txt, tv_today_uv_brf, tv_today_uv_txt;

    public TodayPager(Context ct) {
        super(ct);
    }

    @Override
    protected View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_today, null);
        tv_today_shishi = (TextView) view.findViewById(R.id.tv_today_shishi);
        tv_today_weather = (TextView) view.findViewById(R.id.tv_today_weather);
        tv_today_tem = (TextView) view.findViewById(R.id.tv_today_tem);
        tv_today_wind_shishi = (TextView) view.findViewById(R.id.tv_today_wind_shishi);
        tv_today_fl = (TextView) view.findViewById(R.id.tv_today_fl);
        tv_today_hum = (TextView) view.findViewById(R.id.tv_today_hum);
        tv_today_pres = (TextView) view.findViewById(R.id.tv_today_pres);
        tv_today_vis = (TextView) view.findViewById(R.id.tv_today_vis);
        tv_today_pcpn = (TextView) view.findViewById(R.id.tv_today_pcpn);
        iv_today_weather = (ImageView) view.findViewById(R.id.iv_today_weather);
        iv_today_weather_allday_d = (ImageView) view.findViewById(R.id.iv_today_weather_allday_d);
        iv_today_weather_allday_n = (ImageView) view.findViewById(R.id.iv_today_weather_allday_n);
        tv_today_weather_allday_d = (TextView) view.findViewById(R.id.tv_today_weather_allday_d);
        tv_today_weather_allday_n = (TextView) view.findViewById(R.id.tv_today_weather_allday_n);
        tv_today_tem_allday = (TextView) view.findViewById(R.id.tv_today_tem_allday);
        tv_today_astro_allday = (TextView) view.findViewById(R.id.tv_today_astro_allday);
        tv_today_wind_allday = (TextView) view.findViewById(R.id.tv_today_wind_allday);
        tv_today_hum_allday = (TextView) view.findViewById(R.id.tv_today_hum_allday);
        tv_today_pres_allday = (TextView) view.findViewById(R.id.tv_today_pres_allday);
        tv_today_pcpn_allday = (TextView) view.findViewById(R.id.tv_today_pcpn_allday);
        tv_today_vis_allday = (TextView) view.findViewById(R.id.tv_today_vis_allday);
        tv_today_qlty = (TextView) view.findViewById(R.id.tv_today_qlty);
        tv_today_aqi = (TextView) view.findViewById(R.id.tv_today_aqi);
        tv_today_co = (TextView) view.findViewById(R.id.tv_today_co);
        tv_today_no2 = (TextView) view.findViewById(R.id.tv_today_no2);
        tv_today_o3 = (TextView) view.findViewById(R.id.tv_today_o3);
        tv_today_so2 = (TextView) view.findViewById(R.id.tv_today_so2);
        tv_today_pm25 = (TextView) view.findViewById(R.id.tv_today_pm25);
        tv_today_comf_brf = (TextView) view.findViewById(R.id.tv_today_comf_brf);
        tv_today_comf_txt = (TextView) view.findViewById(R.id.tv_today_comf_txt);
        tv_today_cw_brf = (TextView) view.findViewById(R.id.tv_today_cw_brf);
        tv_today_cw_txt = (TextView) view.findViewById(R.id.tv_today_cw_txt);
        tv_today_drsg_brf = (TextView) view.findViewById(R.id.tv_today_drsg_brf);
        tv_today_drsg_txt = (TextView) view.findViewById(R.id.tv_today_drsg_txt);
        tv_today_flu_brf = (TextView) view.findViewById(R.id.tv_today_flu_brf);
        tv_today_flu_txt = (TextView) view.findViewById(R.id.tv_today_flu_txt);
        tv_today_sport_brf = (TextView) view.findViewById(R.id.tv_today_sport_brf);
        tv_today_sport_txt = (TextView) view.findViewById(R.id.tv_today_sport_txt);
        tv_today_trav_brf = (TextView) view.findViewById(R.id.tv_today_trav_brf);
        tv_today_trav_txt = (TextView) view.findViewById(R.id.tv_today_trav_txt);
        tv_today_uv_brf = (TextView) view.findViewById(R.id.tv_today_uv_brf);
        tv_today_uv_txt = (TextView) view.findViewById(R.id.tv_today_uv_txt);
        return view;
    }
    private void init(Weather weather) {
        Weather.Hourly_forecast h = null;
        if (weather.hourly_forecast.size() != 0) {
            h = weather.hourly_forecast.get(0);
        }
        Weather.NOW n = weather.now;
        Weather.Daily_forecast d = weather.daily_forecast.get(0);
        Weather.AQI aqi= weather.aqi;
        Weather.Suggestion s = weather.suggestion;
        iv_today_weather.setImageResource(CommonUtil.getImageId(n.cond.code));
        iv_today_weather_allday_d.setImageResource(CommonUtil.getImageId(d.cond.code_d));
        iv_today_weather_allday_n.setImageResource(CommonUtil.getImageId(d.cond.code_n));
        tv_today_weather.setText(n.cond.txt);
        if (h != null) {
            tv_today_shishi.setText(h.date);
            tv_today_tem.setText(h.tmp + "℃");
            tv_today_wind_shishi.setText(h.wind.dir + "" + h.wind.sc);
            tv_today_hum.setText("湿度：" + h.hum + "%");
            tv_today_pres.setText("气压：" + h.pres + "atm");
        } else {
            tv_today_shishi.setText("暂无实时天气");
            tv_today_tem.setText(n.tmp + "℃");
            tv_today_wind_shishi.setText(n.wind.dir + "" + n.wind.sc);
            tv_today_hum.setText("湿度：" + n.hum + "%");
            tv_today_pres.setText("气压：" + n.pres + "atm");
        }
        tv_today_fl.setText("体感温度：" + n.fl + "℃");
        tv_today_vis.setText("能见度：" + n.vis + "km");
        tv_today_pcpn.setText("降雨量" + n.pcpn + "mm");
        tv_today_weather_allday_d.setText(d.cond.txt_d);
        tv_today_weather_allday_n.setText(d.cond.txt_n);
        tv_today_tem_allday.setText("温度：" + d.tmp.min + "-" + d.tmp.max + "℃");
        tv_today_astro_allday.setText("日出-日落:" + d.astro.sr + "-" + d.astro.ss);
        tv_today_wind_allday.setText(d.wind.sc + " " + d.wind.dir);
        tv_today_hum_allday.setText("湿度：" + d.hum + "%");
        tv_today_pres_allday.setText("气压：" + d.pres + "atm");
        tv_today_pcpn_allday.setText("降雨量：" + d.pcpn + "mm");
        tv_today_vis_allday.setText("能见度：" + d.vis + "km");
        if (aqi == null){
            tv_today_qlty.setText("空气质量：夜间暂无预报" );
            tv_today_aqi.setText("空气质量指数：夜间暂无预报" );
            tv_today_co.setText("co平均值(ug/m³)：夜间暂无预报");
            tv_today_no2.setText("no2平均值(ug/m³)：夜间暂无预报");
            tv_today_o3.setText("o3平均值(ug/m³)：夜间暂无预报" );
            tv_today_so2.setText("so2平均值(ug/m³)：夜间暂无预报");
            tv_today_pm25.setText("PM2.5平均值(ug/m³)：");
        }else {
            Weather.City c = aqi.city;
            tv_today_qlty.setText("空气质量：" + c.qlty);
            tv_today_aqi.setText("空气质量指数：" + c.aqi);
            tv_today_co.setText("co平均值(ug/m³)：" + c.co);
            tv_today_no2.setText("no2平均值(ug/m³)：" + c.no2);
            tv_today_o3.setText("o3平均值(ug/m³)：" + c.o3);
            tv_today_so2.setText("so2平均值(ug/m³)：" + c.so2);
            tv_today_pm25.setText("PM2.5平均值(ug/m³)：" + c.pm25);
        }
        tv_today_comf_brf.setText("舒适指数：" + s.comf.brf);
        tv_today_comf_txt.setText(s.comf.txt);
        tv_today_cw_brf.setText("洗车指数：" + s.cw.brf);
        tv_today_cw_txt.setText(s.cw.txt);
        tv_today_drsg_brf.setText("穿衣指数：" + s.drsg.brf);
        tv_today_drsg_txt.setText(s.drsg.txt);
        tv_today_flu_brf.setText("感冒指数：" + s.flu.brf);
        tv_today_flu_txt.setText(s.flu.txt);
        tv_today_sport_brf.setText("运动指数：" + s.sport.brf);
        tv_today_sport_txt.setText(s.sport.txt);
        tv_today_trav_brf.setText("旅游指数：" + s.trav.brf);
        tv_today_trav_txt.setText(s.trav.txt);
        tv_today_uv_brf.setText("紫外线指数：" + s.uv.brf);
        tv_today_uv_txt.setText(s.uv.txt);
    }

    @Override
    public void notifyWeatherChange(Weather weather) {
        init(weather);
    }
}
