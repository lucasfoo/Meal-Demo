package com.example.demo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SellerHistoryDataAdapter extends RecyclerView.Adapter<SellerHistoryDataAdapter.SellerHistoryViewHolder>{
    private List<OrderData> SellerHistoryList;

    public SellerHistoryDataAdapter(List<OrderData> SellerHistoryList){
        this.SellerHistoryList = SellerHistoryList;
    }

    @Override
    public int getItemCount(){
        return SellerHistoryList.size();
    }

    @NonNull
    @Override
    public SellerHistoryDataAdapter.SellerHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.seller_history_card, viewGroup, false);
        return new SellerHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder( SellerHistoryViewHolder sellerorderViewHolder, int i) {
        OrderData data = SellerHistoryList.get(i);
        try {
            sellerorderViewHolder.collector.setText(data.buyerName);
            sellerorderViewHolder.collectionTime.setText(data.orderDate);
            sellerorderViewHolder.dishName.setText(data.dishName);
            sellerorderViewHolder.price.setText(data.price);
        }catch (Exception exception){

        }

    }

    public static class SellerHistoryViewHolder extends RecyclerView.ViewHolder{

        protected static TextView collector;
        protected static TextView collectionTime;
        protected static TextView dishName;
        protected static TextView price;

        public SellerHistoryViewHolder(View view){
            super(view);

            collector = view.findViewById(R.id.seller_history_collector);
            collectionTime = view.findViewById(R.id.seller_history_time);
            dishName = view.findViewById(R.id.seller_history_dish_name);
            collectionTime = view.findViewById(R.id.seller_order_collection_time);


        }
    }
}
