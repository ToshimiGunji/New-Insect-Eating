package com.example.b1016121.newinsecteating.client;

/**
 * Created by keita on 2017/09/23.
 */


import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Query;
import rx.Observable;
import java.util.List;
import java.util.Map;
import com.example.b1016121.newinsecteating.model.Restaurant;

public interface RestaurantClient {
    @GET("/api/")
    Observable<List<Restaurant>> search(@QueryMap Map<String,String> queryMap);

    @GET("/api/search/")
    Observable<List<Restaurant>> searchQue(@Query("query") String query);
}