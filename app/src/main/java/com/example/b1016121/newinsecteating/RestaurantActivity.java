package com.example.b1016121.newinsecteating;

/**
 * Created by keita on 2017/09/23.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.b1016121.newinsecteating.model.Restaurant;

public class RestaurantActivity extends AppCompatActivity {
    public static Intent intent(Context context, Restaurant restaurant) {
        Intent intent = new Intent(context,RestaurantActivity.class);
        return intent.putExtra("restaurant",restaurant);
    }



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        TextView nameView = (TextView)findViewById(R.id.name);
        TextView descriptionView = (TextView)findViewById(R.id.description);

        Restaurant restaurant = getIntent().getParcelableExtra("restaurant");
        nameView.setText(restaurant.getName());
        descriptionView.setText(restaurant.getDescription());
    }

}
