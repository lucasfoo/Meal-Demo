package com.example.demo;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Set;

@IgnoreExtraProperties
public class Buyer {
    public String email;
    public boolean seller;
    public Buyer(){
        seller = false;
    }

    public Buyer(String email, boolean seller){
        this.email = email;
        this.seller = seller;
    }
}
