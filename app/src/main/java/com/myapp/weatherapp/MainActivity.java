package com.myapp.weatherapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.weatherapp.domain.Weather;
import com.myapp.weatherapp.pager.BasePager;
import com.myapp.weatherapp.pager.FuturePager;
import com.myapp.weatherapp.pager.TodayPager;
import com.myapp.weatherapp.service.WeatherService;
import com.myapp.weatherapp.util.CityUtil;
import com.myapp.weatherapp.util.CommonUtil;
import com.myapp.weatherapp.util.SharePrefUtil;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView tv_action_bar_city;
    private String city;
    private WeatherService weatherService;
    private boolean isSuccess = false;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLocationClient = new LocationClient(MainActivity.this);
        initLocation();
        mLocationClient.registerLocationListener(myListener );
        mLocationClient.start();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        viewPager = (ViewPager) findViewById(R.id.viewPage);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        initData();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(false);
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            city = bdLocation.getAddress().city;
            city =  city.substring(0,city.indexOf("市"));
            CityUtil util = new CityUtil(MainActivity.this);
            String areaId = util.getIdByCityCN(city);
            if (areaId == null || areaId.equals("")){
                if (tv_action_bar_city != null)
                    tv_action_bar_city.setText("定位失败");
                areaId="CN101280101";
            }

            SharePrefUtil.saveString(MainActivity.this, "id", areaId);
            if (tv_action_bar_city != null)
                tv_action_bar_city.setText("当前城市："+city);

            initWeatherService(areaId);

        }
    }
    private List<WeatherObserver> mObservers = new ArrayList<>();
    public void registerObserver(WeatherObserver o){
        synchronized (o) {
            mObservers.add(o);
        }
    }
    public void unregisterObserver(WeatherObserver o){
        synchronized (o) {
            mObservers.remove(o);
        }
    }
    public interface WeatherObserver{
        void notifyWeatherChange(Weather weather);
    }

    public void notifyWeatherChange(Weather weather){
        synchronized (mObservers) {
            for (WeatherObserver o : mObservers) {
                o.notifyWeatherChange(weather);
            }
        }
    }
    protected void initWeatherService(String areaId) {
        Intent intent = new Intent(MainActivity.this, WeatherService.class);
      //  intent.putExtra("areaId",areaId);
        startService(intent);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            weatherService = ((WeatherService.WeatherServiceBinder) service).getService();
            weatherService.setCallBack(new WeatherService.WeatherCallBack() {
                @Override
                public void onSuccess(Weather w) {
                    isSuccess = true;
                    if (isSuccess) {
                        notifyWeatherChange(w);
                    }
                }

                @Override
                public void onFailure(String failure) {
                    isSuccess = false;
                }
            });

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isSuccess = false;
            weatherService.cancleCallBack();
        }
    };

    @Override
    public void onDestroy() {
       unbindService(connection);
        mLocationClient.stop();
        super.onDestroy();
    }
    Date date =  new Date();
    Date first =  new Date(date.getTime());
    Date second =  new Date(date.getTime() + 1 * 24 * 60 * 60 * 1000);
    Date three =  new Date(date.getTime() + 2 * 24 * 60 * 60 * 1000);
    Date four =  new Date(date.getTime() + 3 * 24 * 60 * 60 * 1000);
    Date five =  new Date(date.getTime() + 4 * 24 * 60 * 60 * 1000);
    String[] titles = {"今天","明天",CommonUtil.getWeekOfDate(first),CommonUtil.getWeekOfDate(second),CommonUtil.getWeekOfDate(three),CommonUtil.getWeekOfDate(four),CommonUtil.getWeekOfDate(five)};
    List<BasePager> pagers = new ArrayList<>();
    private void initData() {
        for (int i = 0; i < titles.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(titles[i]));
        }
        initPager();
        MainAdapter adapter = new MainAdapter();
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);
    }

    private void initPager() {
        pagers.add(new TodayPager(MainActivity.this));
        pagers.add(new FuturePager(MainActivity.this, 1));
        pagers.add(new FuturePager(MainActivity.this, 2));
        pagers.add(new FuturePager(MainActivity.this, 3));
        pagers.add(new FuturePager(MainActivity.this, 4));
        pagers.add(new FuturePager(MainActivity.this, 5));
        pagers.add(new FuturePager(MainActivity.this, 6));
    }

    class MainAdapter extends PagerAdapter{
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return pagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pagers.get(position).getContentView(), 0);
            return pagers.get(position).getContentView();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if(position >= pagers.size()) return;
            container.removeView(pagers.get(position).getContentView());
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        tv_action_bar_city = (TextView) menu.findItem(R.id.action_bar_city).getActionView();
        tv_action_bar_city.setText("定位中..");
        tv_action_bar_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelectCityActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        searchView.setOnQueryTextListener(null);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            city = data.getStringExtra("city");
            tv_action_bar_city.setText("当前城市：" + city);
            CityUtil util = new CityUtil(MainActivity.this);
            String areaId = util.getIdByCityCN(city);
            SharePrefUtil.saveString(MainActivity.this, "id", areaId);
            weatherService.getWeather(areaId);


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_bar_city) {
            Toast.makeText(this, "11", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_search) {
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
