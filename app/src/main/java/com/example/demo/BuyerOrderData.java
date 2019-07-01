package com.example.demo;

public class BuyerOrderData{
    public String sellerOrderID;
    public String restaurantID;
    public String itemID;
    public String price;
    public String itemName;


    public BuyerOrderData(){}

    public BuyerOrderData(String sellerOrderID, String RestaurantID, String ItemID, String price, String itemName) {
        this.restaurantID = RestaurantID;
        this.itemID = ItemID;
        this.price = price;
        this.itemName = itemName;
        this.sellerOrderID = sellerOrderID;
    }
}
