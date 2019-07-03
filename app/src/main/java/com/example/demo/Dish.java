package com.example.demo;

import android.widget.ImageView;

public class Dish {
    public String DishName;
    public String DishDescription;
//    public String DishPreparationDuration;
    public String DishPrice;
    public String DishID;

    public Dish(){}


    //    public ImageView DishPhoto;
    public Dish(String DishName, String DishDescription, String DishPrice, String DishID){
        this.DishName = DishName;
        this.DishDescription = DishDescription;
        this.DishPrice = DishPrice;
        this.DishID = DishID;
//        this.DishPhoto = DishPhoto;
    }
}
