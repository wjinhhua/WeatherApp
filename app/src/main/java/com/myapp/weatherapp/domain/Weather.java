package com.myapp.weatherapp.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 版权: ft626 版权所有(c) 2016
 * 作者: wjh
 * 版本: 1.0
 * 创建日期: 2016/6/21.20:47
 * 描述:
 **/
public class Weather {
    public AQI aqi;
    public Basic basic;
    public List<Daily_forecast> daily_forecast = new ArrayList<>();
    public List<Hourly_forecast> hourly_forecast = new ArrayList<>();
    public NOW now;
    public String status;
    public Suggestion suggestion;
    //--------
    public class AQI{
        public City city;
    }
    public class Basic{
        public String city;
        public String cnty;
        public String id;
        public String lat;
        public String lon;
        public Update update;
    }
    public class Daily_forecast{
        public Astro astro;
        public Cond cond;
        public String date;
        public String hum;
        public String pcpn;
        public String pop;
        public String pres;
        public Tmp tmp;
        public String vis;
        public Wind wind;
    }
    public class Hourly_forecast{
        public String date;
        public String hum;
        public String pop;
        public String pres;
        public String tmp;
        public Wind wind;
    }
    public class NOW{
        public CondN cond;
        public String fl;
        public String hum;
        public String pcpn;
        public String pres;
        public String tmp;
        public String vis;
        public Wind wind;
    }
    public class Suggestion{
        public Sugg comf;
        public Sugg cw;
        public Sugg drsg;
        public Sugg flu;
        public Sugg sport;
        public Sugg trav;
        public Sugg uv;
    }
    //------ --------
    public class City{
        public String aqi;
        public String co;
        public String no2;
        public String o3;
        public String pm10;
        public String pm25;
        public String qlty;
        public String so2;
    }
    public class Update{
        public String loc;
        public String utc;
    }
    public class Astro{
        public String sr;
        public String ss;
    }
    public class Cond{
        public String code_d;
        public String code_n;
        public String txt_d;
        public String txt_n;
    }
    public class Tmp{
        public String max;
        public String min;
    }
    public class Wind{
        public String deg;
        public String dir;
        public String sc;
        public String spd;
    }
    public class CondN{
        public String code;
        public String txt;
    }
    public class Sugg {
       public String brf;
        public String txt;
    }

}
