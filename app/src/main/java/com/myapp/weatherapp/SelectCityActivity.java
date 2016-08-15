package com.myapp.weatherapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.myapp.weatherapp.domain.CityBean;
import com.myapp.weatherapp.util.CityUtil;

import java.util.List;

/**
 * 版权: ft626 版权所有(c) 2016
 * 作者: wjh
 * 版本: 1.0
 * 创建日期: 2016/6/24.22:29
 * 描述:
 **/
public class SelectCityActivity extends Activity {
    private Spinner sp_prov;
    private Spinner sp_district;
    private Spinner sp_namecn;
    private Button bt_sure;
    private Button bt_cancel;

    private CityUtil util;
    private  String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectcity);
        initView();
        initData();
    }

    private void initView() {
        sp_prov= (Spinner) findViewById(R.id.sp_prov);
        sp_district= (Spinner) findViewById(R.id.sp_district);
        sp_namecn= (Spinner) findViewById(R.id.sp_namecn);
        bt_sure= (Button) findViewById(R.id.bt_sure);
        bt_cancel= (Button) findViewById(R.id.bt_cancel);
    }

    private void initData() {
        util = new CityUtil(SelectCityActivity.this);
        initProvSpinner();
        bt_sure.setOnClickListener(clickListener);
        bt_cancel.setOnClickListener(clickListener);
    }

    private void initProvSpinner(){
        List<String> prov = util.getProv();
        List<CityBean> list = util.toSort(prov);
        sp_prov.setAdapter(initAdapter(sp_prov, util.getSort(list)));
        sp_prov.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String prov = parent.getItemAtPosition(position).toString();
                initDistrictSpinner(prov);
                initNamecnSpinner2(prov);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void initDistrictSpinner(final String prov){
        List<String> district = util.getAreaByProv(prov);
        List<CityBean> list = util.toSort(district);
        sp_district.setAdapter(initAdapter(sp_district, util.getSort(list)));
        sp_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String district = parent.getItemAtPosition(position).toString();
                initNamecnSpinner(district);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
    private void initNamecnSpinner(String district){
        List<String> namecn = util.getCityCNByArea(district);
        List<CityBean> list = util.toSort(namecn);
        sp_namecn.setAdapter(initAdapter(sp_namecn, util.getSort(list)));
        sp_namecn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void initNamecnSpinner2(String provcn){
        List<String> namecn = util.getCityCNByProv(provcn);
        List<CityBean> list = util.toSort(namecn);
        sp_namecn.setAdapter(initAdapter(sp_namecn, util.getSort(list)));
    }
    private ArrayAdapter initAdapter(final Spinner spinner, final List<String> data){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelectCityActivity.this,
                R.layout.layout_spinner_text, data) {
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = View.inflate(SelectCityActivity.this, R.layout.layout_spinner_item,
                        null);
                TextView tv_spinner_text = (TextView) view
                        .findViewById(R.id.tv_spinner_text);
                ImageView ic_spinner = (ImageView) view
                        .findViewById(R.id.ic_spinner);
                tv_spinner_text.setText(data.get(position));
                if (spinner.getSelectedItemPosition() == position) {
                    view.setBackgroundResource(R.color.colorTextSelect);
                    ic_spinner.setImageResource(R.mipmap.ic_spinner_select);
                } else {
                    view.setBackgroundResource(R.color.colorTextNormal);
                    ic_spinner.setImageResource(R.mipmap.ic_spinner_normal);
                }

                return view;
            }

        };
        adapter.setDropDownViewResource(R.layout.layout_spinner_item);
        return adapter;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_cancel:
                    finish();
                    break;
                case R.id.bt_sure:
                    Intent intent = new Intent();
                    intent.putExtra("city", city);
                    setResult(1, intent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
}
