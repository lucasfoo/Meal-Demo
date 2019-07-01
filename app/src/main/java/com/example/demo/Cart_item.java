package com.example.demo;

public class Cart_item {
    public String restaurantID;
    public String itemID;
    public String price;
    public String itemName;

    public Cart_item(){};

    public Cart_item(String RestaurantID, String ItemID, String price, String itemName){
        this.restaurantID = RestaurantID;
        this.itemID = ItemID;
        this.price = price;
        this.itemName = itemName;
    }

}
