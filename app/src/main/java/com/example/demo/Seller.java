package com.example.demo;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Seller {
    public  String name;
    public String address;
    public String email;

    public Seller(){}


    public Seller(String email, String restaurant_name, String address){
        this.email = email;
        this.name = restaurant_name;
        this.address = address;

    }

    public void editAddress(String address){
        this.address = address;
    }
}
