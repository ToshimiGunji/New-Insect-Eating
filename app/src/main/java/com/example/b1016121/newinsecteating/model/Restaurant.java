package com.example.b1016121.newinsecteating.model;

/**
 * Created by keita on 2017/09/22.
 */


import android.os.Parcel;
import android.os.Parcelable;


public class Restaurant implements Parcelable {

    private String name;
    private String description;
    private String address;
    private String image;

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Restaurant> CREATOR
            = new Parcelable.Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel source) {
            return new Restaurant(source);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(description);
        out.writeString(address);
        out.writeString(image);
    }

    public Restaurant(String name,String description, String address, String url){
        this.name = name;
        this.description = description;
        this.address = address;
        this.image = url;
    }

    private Restaurant(Parcel in){
        name = in.readString();
        description = in.readString();
        address = in.readString();
        image = in.readString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getAddress() {
        return this.address;
    }

    public String getUrl() { return this.image; }

}
