package com.example.demo;

public class OrderData {
    public String buyerName;
    public String buyerID;
    public String orderDate;
    public String orderTime;
    public String price;
    public String dishName;
    public String itemID;
    public String collectionTime;
    public String sellerOrderID;
    public String buyerOrderID;
    public String sellerID;
    public String restaurantName;
    public String status;
    public String imageRef;


    public OrderData(){}

    public OrderData(String buyerName, String buyerID, String orderDate , String orderTime, String price, String dishName,String itemID, String sellerOrderID, String buyerOrderID, String sellerID, String restaurantName, String status, String imageRef){
        this.buyerName = buyerName;
        this.buyerID = buyerID;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.price = price;
        this.dishName = dishName;
        this.itemID = itemID;
        this.sellerOrderID = sellerOrderID;
        this.buyerOrderID = buyerOrderID;
        this.sellerID = sellerID;
        this.restaurantName = restaurantName;
        this.status = status;
        this.imageRef = imageRef;
    }

}