package com.myapp.weatherapp.pager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.myapp.weatherapp.MainActivity;

/**
 * Created by wjh on 2015/10/29.
 */
public abstract class BasePager implements MainActivity.WeatherObserver{
    protected View contentView;
    protected Context context;

    public BasePager(Context ct) {
        context = ct;
        contentView = initView((LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        if(context instanceof MainActivity){
           ((MainActivity) context).registerObserver(BasePager.this);
        }
    }

    public View getContentView() {
        return contentView;
    }

    protected abstract View initView(LayoutInflater inflater);
}
