package com.example.b1016121.newinsecteating;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.*;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.google.gson.*;

import java.util.HashMap;
import java.util.Map;

import android.widget.AdapterView;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

import android.widget.TextView;

import android.widget.ListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.example.b1016121.newinsecteating.model.Restaurant;

import retrofit2.Retrofit;
import android.util.Log;
import com.example.b1016121.newinsecteating.client.RestaurantClient;


public class MainActivity extends AppCompatActivity implements LocationListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Fine か Coarseのいずれかのパーミッションが得られているかチェックする
        // 本来なら、Android6.0以上かそうでないかで実装を分ける必要がある

//        if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION) !=
//                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            /** fine location のリクエストコード（値は他のパーミッションと被らなければ、なんでも良い）*/
//            final int requestCode = 1;
//            // いずれも得られていない場合はパーミッションのリクエストを要求する
//
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, requestCode);
//            return;
//        }
//        // 位置情報を管理している LocationManager のインスタンスを生成する
//        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        String locationProvider = null;
//
//        // GPSが利用可能になっているかどうかをチェック
//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            locationProvider = LocationManager.GPS_PROVIDER;
//        }
//        // GPSプロバイダーが有効になっていない場合は基地局情報が利用可能になっているかをチェック
//        else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
//            locationProvider = LocationManager.NETWORK_PROVIDER;
//        }
//        // いずれも利用可能でない場合は、GPSを設定する画面に遷移する
//        else {
//            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(settingsIntent);
//            return;
//        }
//
//        /** 位置情報の通知するための最小時間間隔（ミリ秒） */
//        final long minTime = 500;
//        /** 位置情報を通知するための最小距離間隔（メートル）*/
//        final long minDistance = 1;
//
//        // 利用可能なロケーションプロバイダによる位置情報の取得の開始
//        // FIXME 本来であれば、リスナが複数回登録されないようにチェックする必要がある
//        locationManager.requestLocationUpdates(locationProvider, minTime, minDistance, this);
//        // 最新の位置情報
//        Location location = locationManager.getLastKnownLocation(locationProvider);

        //String lat = String.valueOf(location.getLatitude());
        //String lng = String.valueOf(location.getLongitude());


        //-----------------------------------------------------------------------------------
        ListView listView = (ListView)findViewById(R.id.list_view);
        Button searchButton = (Button)findViewById(R.id.search_button);
        EditText queryEditText = (EditText)findViewById(R.id.query_edit_text);
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        RestaurantListAdapter listAdapter = new RestaurantListAdapter(getApplicationContext());
        listView.setAdapter(listAdapter);

        // リストタップ時の処理（遷移）
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant res = listAdapter.getRestaurant(position);
                Intent intent = RestaurantActivity.intent(getApplicationContext(),res);
                startActivity(intent);
            }}

        );

        // API周りの処理
        GsonBuilder gb = new GsonBuilder();
        Gson gson = gb.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://insect-eating-api.herokuapp.com")//基本のurl設定
                .addConverterFactory(GsonConverterFactory.create(gson))//Gsonの使用
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        RestaurantClient restaurantClient = retrofit.create(RestaurantClient.class);

        Map<String,String> queryMap = new HashMap<String,String>();
        queryMap.put("lat","41.790648");
        queryMap.put("lng","140.751419");

        // アプリ起動時のWebAPI操作
        restaurantClient.search(queryMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    progressBar.setVisibility(View.GONE);
                }).subscribe(res -> {
                    listAdapter.setRestaurants(res);
                    listAdapter.notifyDataSetChanged();
                }, res -> {
                    Log.v("api error",res.toString());
                });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                restaurantClient.searchQue(queryEditText.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doAfterTerminate(() -> {
                            progressBar.setVisibility(View.GONE);
                        }).subscribe(res -> {
                    listAdapter.setRestaurants(res);
                    listAdapter.notifyDataSetChanged();
                }, res -> {
                    Log.v("api error",res.toString());
                });

            }
        });

    }

    //位置情報が通知されるたびにコールバックされるメソッド
    @Override
    public void onLocationChanged(Location location) {
        //TextView textView = (TextView) findViewById(R.id.lo);
        //textView.setText(String.valueOf("onLocationChanged() : " + location.getLatitude()) + ":" + String.valueOf(location.getLongitude()));
    }

    //ロケーションプロバイダが利用不可能になるとコールバックされるメソッド
    @Override
    public void onProviderDisabled(String provider) {
        //ロケーションプロバイダーが使われなくなったらリムーブする必要がある
    }

    //ロケーションプロバイダが利用可能になるとコールバックされるメソッド
    @Override
    public void onProviderEnabled(String provider) {
        //プロバイダが利用可能になったら呼ばれる
    }

    //ロケーションステータスが変わるとコールバックされるメソッド
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // 利用可能なプロバイダの利用状態が変化したときに呼ばれる
    }
}