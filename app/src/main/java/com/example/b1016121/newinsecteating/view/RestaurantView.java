package com.example.b1016121.newinsecteating.view;

/**
 * Created by keita on 2017/09/23.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import com.bumptech.glide.Glide;

import com.example.b1016121.newinsecteating.R;
import com.example.b1016121.newinsecteating.model.Restaurant;

//import com.example.b1016121.newinsecteating.bindView;

import com.example.b1016121.newinsecteating.model.Restaurant;

import org.w3c.dom.Text;

public class RestaurantView extends FrameLayout {
    public RestaurantView(Context context){
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_restaurant, this);
        this.nameTextView = (TextView)findViewById(R.id.name_text_view);
        this.addressTextView = (TextView)findViewById(R.id.address_text_view);

    }
    public RestaurantView(Context context, AttributeSet attr){
        super(context,attr);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_restaurant, this);
        this.nameTextView = (TextView)findViewById(R.id.name_text_view);
        this.addressTextView = (TextView)findViewById(R.id.address_text_view);
    }
    public RestaurantView(Context context, AttributeSet attr,int defStyleAttr) {
        super(context, attr, defStyleAttr);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_restaurant, this);
        this.nameTextView = (TextView)findViewById(R.id.name_text_view);
        this.addressTextView = (TextView)findViewById(R.id.address_text_view);
    }



    //private ImageView profileImageView = findViewById(R.id.image_view);
    //private TextView nameTextView = (TextView)findViewById(R.id.name_text_view);
    //private TextView addressTextView = (TextView)findViewById(R.id.address_text_view);

    private TextView nameTextView = null;
    private TextView addressTextView = null;

    public void setRestaurant(Restaurant restaurant) {
        Log.v("API Log: ",restaurant.getName());
        nameTextView.setText(restaurant.getName());
        addressTextView.setText(restaurant.getAddress());

    }

}
