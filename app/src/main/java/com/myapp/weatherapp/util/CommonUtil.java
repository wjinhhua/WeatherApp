package com.myapp.weatherapp.util;

import com.myapp.weatherapp.R;

import java.util.Calendar;
import java.util.Date;

/**
 * 版权: ft626 版权所有(c) 2016
 * 作者: wjh
 * 版本: 1.0
 * 创建日期: 2016/6/21.21:37
 * 描述:
 **/
public class CommonUtil {

    public static String getWeekOfDate(Date date) {
        String[] weekDays = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        if (w>4)
            w= w-7;
        return weekDays[w+2];
    }
   public static int getImageId(String code){
        if (code.equals("100")){
            return R.mipmap.ic_100;
        }
       if (code.equals("101")){
           return R.mipmap.ic_101;
       }
       if (code.equals("102")){
           return R.mipmap.ic_102;
       }
       if (code.equals("103")){
           return R.mipmap.ic_103;
       }
       if (code.equals("104")){
           return R.mipmap.ic_104;
       }
       if (code.equals("200")){
           return R.mipmap.ic_200;
       }
       if (code.equals("201")){
           return R.mipmap.ic_201;
       }
       if (code.equals("202")){
           return R.mipmap.ic_202;
       }
       if (code.equals("203")){
           return R.mipmap.ic_203;
       }
       if (code.equals("204")){
           return R.mipmap.ic_204;
       }
       if (code.equals("205")){
           return R.mipmap.ic_205;
       }
       if (code.equals("206")){
           return R.mipmap.ic_206;
       }
       if (code.equals("207")){
           return R.mipmap.ic_207;
       }
       if (code.equals("208")){
           return R.mipmap.ic_208;
       }
       if (code.equals("209")){
           return R.mipmap.ic_209;
       }
       if (code.equals("210")){
           return R.mipmap.ic_210;
       }
       if (code.equals("211")){
           return R.mipmap.ic_211;
       }
       if (code.equals("212")){
           return R.mipmap.ic_212;
       }
       if (code.equals("213")){
           return R.mipmap.ic_213;
       }
       if (code.equals("300")){
           return R.mipmap.ic_300;
       }
       if (code.equals("301")){
           return R.mipmap.ic_301;
       }
       if (code.equals("302")){
           return R.mipmap.ic_302;
       }
       if (code.equals("303")){
           return R.mipmap.ic_303;
       }
       if (code.equals("304")){
           return R.mipmap.ic_304;
       }
       if (code.equals("305")){
           return R.mipmap.ic_305;
       }
       if (code.equals("306")){
           return R.mipmap.ic_306;
       }
       if (code.equals("307")){
           return R.mipmap.ic_307;
       }
       if (code.equals("308")){
           return R.mipmap.ic_308;
       }
       if (code.equals("309")){
           return R.mipmap.ic_309;
       }
       if (code.equals("310")){
           return R.mipmap.ic_310;
       }
       if (code.equals("311")){
           return R.mipmap.ic_311;
       }
       if (code.equals("312")){
           return R.mipmap.ic_312;
       }
       if (code.equals("313")){
           return R.mipmap.ic_313;
       }
       if (code.equals("400")){
           return R.mipmap.ic_400;
       }
       if (code.equals("401")){
           return R.mipmap.ic_401;
       }
       if (code.equals("402")){
           return R.mipmap.ic_402;
       }
       if (code.equals("403")){
           return R.mipmap.ic_403;
       }
       if (code.equals("404")){
           return R.mipmap.ic_404;
       }
       if (code.equals("405")){
           return R.mipmap.ic_405;
       }
       if (code.equals("406")){
           return R.mipmap.ic_406;
       }
       if (code.equals("407")){
           return R.mipmap.ic_407;
       }
       if (code.equals("500")){
           return R.mipmap.ic_500;
       }
       if (code.equals("501")){
           return R.mipmap.ic_501;
       }
       if (code.equals("502")){
           return R.mipmap.ic_502;
       }
       if (code.equals("503")){
           return R.mipmap.ic_503;
       }
       if (code.equals("504")){
           return R.mipmap.ic_504;
       }
       if (code.equals("507")){
           return R.mipmap.ic_507;
       }
       if (code.equals("508")){
           return R.mipmap.ic_508;
       }
       if (code.equals("900")){
           return R.mipmap.ic_900;
       }
       if (code.equals("901")){
           return R.mipmap.ic_901;
       }
       return R.mipmap.ic_999;
    }
}
