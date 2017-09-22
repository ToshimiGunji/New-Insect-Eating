package com.example.b1016121.newinsecteating;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.b1016121.newinsecteating.model.Restaurant;
import com.example.b1016121.newinsecteating.view.RestaurantView;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;
/**
 * Created by keita on 2017/09/23.
 */

public class RestaurantListAdapter extends BaseAdapter {
    private Context context;
    List<Restaurant> restaurants = new ArrayList<>();

    public RestaurantListAdapter(Context context) {
        this.context = context;
    }

    public void setRestaurants(List<Restaurant> restaurants){
        this.restaurants = restaurants;
    }

    public Restaurant getRestaurant(int pos) {
        return restaurants.get(pos);
    }

    @Override
    public int getCount() {
        return restaurants.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            RestaurantView r = new RestaurantView(context);
            r.setRestaurant(restaurants.get(position));
            return r;
        }else{
            RestaurantView r = (RestaurantView)convertView;
            r.setRestaurant(restaurants.get(position));
            return r;
        }
    }



}