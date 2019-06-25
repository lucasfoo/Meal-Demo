package com.example.demo;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Seller {
    public String email;


    public Seller(String email){
        this.email = email;
    }
}
