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
 * 创建日期: 2016/7/6.23:06
 * 描述:
 **/
public class FuturePager extends BasePager {
    private int position;
    private ImageView iv_future_weather_allday_d,iv_future_weather_allday_n;
    private TextView tv_future_weather_allday_d,tv_future_weather_allday_n,tv_future_tem_allday,
            tv_future_astro_allday,tv_future_wind_allday,tv_future_hum_allday,tv_future_pres_allday,
            tv_future_pcpn_allday,tv_future_vis_allday;

    public FuturePager(Context ct,  int position) {
        super(ct);
        this.position = position;
    }

    @Override
    protected View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_futrue, null);
        iv_future_weather_allday_d = (ImageView) view.findViewById(R.id.iv_future_weather_allday_d);
        iv_future_weather_allday_n = (ImageView) view.findViewById(R.id.iv_future_weather_allday_n);
        tv_future_weather_allday_d = (TextView) view.findViewById(R.id.tv_future_weather_allday_d);
        tv_future_weather_allday_n = (TextView) view.findViewById(R.id.tv_future_weather_allday_n);
        tv_future_tem_allday = (TextView) view.findViewById(R.id.tv_future_tem_allday);
        tv_future_astro_allday = (TextView) view.findViewById(R.id.tv_future_astro_allday);
        tv_future_wind_allday = (TextView) view.findViewById(R.id.tv_future_wind_allday);
        tv_future_hum_allday = (TextView) view.findViewById(R.id.tv_future_hum_allday);
        tv_future_pres_allday = (TextView) view.findViewById(R.id.tv_future_pres_allday);
        tv_future_pcpn_allday = (TextView) view.findViewById(R.id.tv_future_pcpn_allday);
        tv_future_vis_allday = (TextView) view.findViewById(R.id.tv_future_vis_allday);
        return view;
    }

    private void init(Weather weather) {
        Weather.Daily_forecast d = weather.daily_forecast.get(position);
        iv_future_weather_allday_d.setImageResource(CommonUtil.getImageId(d.cond.code_d));
        iv_future_weather_allday_n.setImageResource(CommonUtil.getImageId(d.cond.code_n));
        tv_future_weather_allday_d.setText(d.cond.txt_d);
        tv_future_weather_allday_n.setText(d.cond.txt_n);
        tv_future_tem_allday.setText("温度："+d.tmp.min+"-"+d.tmp.max+"℃");
        tv_future_astro_allday.setText("日出-日落:"+d.astro.sr+"-"+d.astro.ss);
        tv_future_wind_allday.setText(d.wind.sc+" "+d.wind.dir);
        tv_future_hum_allday.setText("湿度："+d.hum+"%");
        tv_future_pres_allday.setText("气压："+d.pres+"atm");
        tv_future_pcpn_allday.setText("降雨量："+d.pcpn+"mm");
        tv_future_vis_allday.setText("能见度："+d.vis+"km");
    }
    @Override
    public void notifyWeatherChange(Weather weather) {
        init(weather);
    }
}
