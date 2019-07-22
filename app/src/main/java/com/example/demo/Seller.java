package com.example.demo;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
    public class Seller {
    public  String name;
    public String address;
    public String apt;
    public String postalCode;
    public String email;
    public String openingTime;
    public String closingTime;
    public String sellerID;
    public String photoID;

    public Seller(){}


    public Seller(String email, String restaurant_name, String address, String apt, String postalCode, String openingTime, String closingTime, String sellerID){
        this.email = email;
        this.name = restaurant_name;
        this.address = address;
        this.apt = apt;
        this.postalCode = postalCode;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.sellerID = sellerID;
    }

    public void editAddress(String address){
        this.address = address;
    }
}
