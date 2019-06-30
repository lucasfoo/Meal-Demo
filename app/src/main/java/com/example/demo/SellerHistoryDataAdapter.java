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
    private List<SellerHistoryData> SellerHistoryList;

    public SellerHistoryDataAdapter(List<SellerHistoryData> SellerHistoryList){
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
        SellerHistoryData data = SellerHistoryList.get(i);
        sellerorderViewHolder.collector.setText(data.CollectorName);
        sellerorderViewHolder.collectionTime.setText(data.Date);
        sellerorderViewHolder.dishName.setText(data.DishName);
        sellerorderViewHolder.price.setText(data.price);

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
