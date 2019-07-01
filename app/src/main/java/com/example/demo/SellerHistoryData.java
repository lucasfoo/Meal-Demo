package com.example.demo;

public class SellerHistoryData {
    public String buyerName;
    public String buyerID;
    public String orderDate;
    public String orderTime;
    public String price;
    public String dishName;

    public  SellerHistoryData(){}

    public  SellerHistoryData(String buyerName, String buyerID, String orderDate ,String orderTime, String price, String dishName){
        this.buyerName = buyerName;
        this.buyerID = buyerID;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.price = price;
        this.dishName = dishName;
    }
}
