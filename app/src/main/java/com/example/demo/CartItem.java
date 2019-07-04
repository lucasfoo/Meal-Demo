package com.example.demo;

public class CartItem {
    public String restaurantName;
    public String restaurantID;
    public String itemID; // this should contain the itemID that is referenced by the seller.
    public String price;
    public String itemName;
    public String cartItemID; // this should hold the unique reference to the item in the buyer's cart int Firebase

    public CartItem(){};

    public CartItem(String RestaurantID, String ItemID, String price, String itemName, String cartItemID){
        this.restaurantID = RestaurantID;
        this.itemID = ItemID;
        this.price = price;
        this.itemName = itemName;
        this.cartItemID = cartItemID;
    }

}
