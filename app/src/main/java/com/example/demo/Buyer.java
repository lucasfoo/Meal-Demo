package com.example.demo;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Set;

@IgnoreExtraProperties
public class Buyer {
    public String email;
    public Buyer(){}

    public Buyer(String email, boolean seller){
        this.email = email;
    }
}
