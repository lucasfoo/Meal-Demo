package com.example.demo;

import android.widget.ImageView;

public class Dish {
    public String DishName;
    public String DishDescription;
//    public String DishPreparationDuration;
    public String DishPrice;
    public String DishID;
    public String imageUri;
    public String PrepDuration;
    public String restaurantID;
    public Dish(){}


    //    public ImageView DishPhoto;
    public Dish(String DishName, String DishDescription, String DishPrice, String DishID, String imageUri, String prepDuration){
        this.DishName = DishName;
        this.DishDescription = DishDescription;
        this.DishPrice = DishPrice;
        this.DishID = DishID;
        this.imageUri = imageUri;
        this.PrepDuration = prepDuration;
    }

    public Dish(String DishName, String DishDescription, String DishPrice, String DishID, String prepDuration){
        this.DishName = DishName;
        this.DishDescription = DishDescription;
        this.DishPrice = DishPrice;
        this.DishID = DishID;
        this.PrepDuration = prepDuration;
    }
}
