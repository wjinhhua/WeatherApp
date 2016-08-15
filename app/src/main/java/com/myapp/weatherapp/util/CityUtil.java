package com.myapp.weatherapp.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.myapp.weatherapp.domain.CityBean;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 版权: ft626 版权所有(c) 2016
 * 作者: wjh
 * 版本: 1.0
 * 创建日期: 2016/6/21.20:40
 * 描述:
 **/
public class CityUtil {
    private static DBUtil instance;
    private static PinYinUtil pinyin;
    private static  CharacterUtil util;
    private static SQLiteDatabase db;

    public CityUtil(Context context){
        DBUtil.initDB(context);
        instance =  DBUtil.getDB();
        pinyin = new PinYinUtil();
        CharacterUtil.initCharacter();
        util = CharacterUtil.getCharacter();

    }


    public List<String> getProv(){
        List<String> provcn_list =  new ArrayList<>();
        instance.openDatabase();
        db =  DBUtil.getDatabase();
        String sql = "select * from citys";
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            String provcn = null;
            try {
                provcn = new String(cursor.getString(cursor.getColumnIndex("prov")).getBytes(),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (!provcn_list.contains(provcn)) {
                provcn_list.add(provcn);
            }
        }
        instance.closeDatabase();
        db.close();
        cursor.close();
        return  provcn_list;
    }

    public List<String> getAreaByProv(String prov){
        List<String> districtcn_list =  new ArrayList<>();
        instance.openDatabase();
        db =  DBUtil.getDatabase();
        String sql = "select * from citys where prov=?";
        Cursor cursor = db.rawQuery(sql, new String[]{ prov });
        while(cursor.moveToNext()){
            String districtcn = null;
            try {
                districtcn = new String(cursor.getString(cursor.getColumnIndex("area")).getBytes(),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (!districtcn_list.contains(districtcn)) {
                districtcn_list.add(districtcn);
            }
        }
        instance.closeDatabase();
        db.close();
        cursor.close();
        return  districtcn_list;
    }

    public List<String> getCityCNByArea(String area){
        List<String> namecn_list =  new ArrayList<>();
        instance.openDatabase();
        db =  DBUtil.getDatabase();
        String sql = "select * from citys where area=?";
        Cursor cursor = db.rawQuery(sql, new String[]{ area });
        while(cursor.moveToNext()){
            String namecn = null;
            try {
                namecn = new String(cursor.getString(cursor.getColumnIndex("citycn")).getBytes(),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (!namecn_list.contains(namecn)) {
                namecn_list.add(namecn);
            }
        }
        instance.closeDatabase();
        db.close();
        cursor.close();
        return  namecn_list;
    }

    public List<String> getCityCNByProv(String prov){
        List<String> namecn_list =  new ArrayList<>();
        instance.openDatabase();
        db =  DBUtil.getDatabase();
        String sql = "select * from citys where prov=?";
        Cursor cursor = db.rawQuery(sql, new String[]{ prov });
        while(cursor.moveToNext()){
            String namecn = null;
            try {
                namecn = new String(cursor.getString(cursor.getColumnIndex("citycn")).getBytes(),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (!namecn_list.contains(namecn)) {
                namecn_list.add(namecn);
            }
        }
        instance.closeDatabase();
        db.close();
        cursor.close();
        return  namecn_list;
    }
    public String getIdByCityCN(String citycn) {
        String areaid = null;
        instance.openDatabase();
        db =  DBUtil.getDatabase();
        String sql = "select id from citys where citycn=?";
        Cursor cursor = db.rawQuery(sql, new String[]{ citycn});
        if (cursor.moveToNext()){
            areaid = cursor.getString(cursor.getColumnIndex("id"));
        }
        instance.closeDatabase();
        db.close();
        cursor.close();
        return  areaid;
    }

    public List<CityBean> toSort(List<String> list){
        List<CityBean> current = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            CityBean bean = new CityBean();
            bean.city = list.get(i);
            String pinyin = util.getSelling(bean.city);
            if (bean.city.equals("重庆")){
                 pinyin = "chongqing";
            }
            String sort = pinyin.substring(0, 1).toUpperCase();

            if (sort.matches("[A-Z]")) {
               bean.firstPinYin = sort.toUpperCase();
            } else {
                bean.firstPinYin = "#";
            }
            current.add(bean);
        }
        Collections.sort(current, pinyin);
        return current;
    }

    public List<String> getSort(List<CityBean> list){
        List<String> sortList = new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            String sort = list.get(i).city;
            sortList.add(sort);
        }
      return sortList;

    }

}
