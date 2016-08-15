package com.myapp.weatherapp.util;


import com.myapp.weatherapp.domain.CityBean;

import java.util.Comparator;

/**
 * 版权: ft626 版权所有(c) 2016
 * 作者: wjh
 * 版本: 1.0
 * 创建日期: 2016/6/21.20:17
 * 描述:
 **/
public class PinYinUtil implements Comparator<CityBean> {
    @Override
    public int compare(CityBean lhs, CityBean rhs) {
        if (lhs.firstPinYin.equals("@")
                || rhs.firstPinYin.equals("#")) {
            return -1;
        } else if (lhs.firstPinYin.equals("#")
                || rhs.firstPinYin.equals("@")) {
            return 1;
        } else {
            return lhs.firstPinYin.compareTo(rhs.firstPinYin);
        }
    }
}
