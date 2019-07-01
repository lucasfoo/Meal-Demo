package com.example.demo;


public class OrderData {
    public String buyerName;
    public String buyerID;
    public String orderDate;
    public String orderTime;
    public String price;
    public String dishName;
    public String collectionTime;
    public String orderID;


    public OrderData(){}

    public OrderData(String buyerName, String buyerID, String orderDate ,String orderTime, String price, String dishName){
            this.buyerName = buyerName;
            this.buyerID = buyerID;
            this.orderDate = orderDate;
            this.orderTime = orderTime;
            this.price = price;
            this.dishName = dishName;
        }

}

