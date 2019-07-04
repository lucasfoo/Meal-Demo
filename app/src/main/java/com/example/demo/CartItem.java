package com.example.demo;

public class CartItem {
    public String restaurantName;
    public String restaurantID;
    public String itemID;
    public String price;
    public String itemName;

    public CartItem(){};

    public CartItem(String RestaurantID, String ItemID, String price, String itemName){
        this.restaurantID = RestaurantID;
        this.itemID = ItemID;
        this.price = price;
        this.itemName = itemName;
    }

}
