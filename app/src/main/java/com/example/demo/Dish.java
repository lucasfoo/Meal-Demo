package com.example.demo;

import android.widget.ImageView;

public class Dish {
    public String DishName;
    public String DishDescription;
    public String DishPrice;

    public Dish(){}


    //    public ImageView DishPhoto;
    public Dish(String DishName, String DishDescription, String DishPrice){
        this.DishName = DishName;
        this.DishDescription = DishDescription;
        this.DishPrice = DishPrice;
//        this.DishPhoto = DishPhoto;
    }
}
