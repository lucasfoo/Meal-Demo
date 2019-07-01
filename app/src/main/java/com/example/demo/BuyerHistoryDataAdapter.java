package com.example.demo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class BuyerHistoryDataAdapter extends RecyclerView.Adapter<BuyerHistoryDataAdapter.BuyerHistoryViewHolder>{
    private List<BuyerHistoryData> BuyerHistoryList;

    public BuyerHistoryDataAdapter(List<BuyerHistoryData> BuyerHistoryList){
        this.BuyerHistoryList = BuyerHistoryList;
    }

    @Override
    public int getItemCount(){
        return BuyerHistoryList.size();
    }

    @NonNull
    @Override
    public BuyerHistoryDataAdapter.BuyerHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.buyer_history_card, viewGroup, false);
        return new BuyerHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder( BuyerHistoryViewHolder buyerorderViewHolder, int i) {
        BuyerHistoryData data = BuyerHistoryList.get(i);
        try {
            buyerorderViewHolder.restaurantName.setText(data.restaurantName);
            buyerorderViewHolder.collectionTime.setText(data.Date);
            buyerorderViewHolder.dishName.setText(data.DishName);
            buyerorderViewHolder.price.setText(data.price);
        }catch (Exception exception){

        }

    }

    public static class BuyerHistoryViewHolder extends RecyclerView.ViewHolder{

        protected static TextView restaurantName;
        protected static TextView collectionTime;
        protected static TextView dishName;
        protected static TextView price;

        public BuyerHistoryViewHolder(View view){
            super(view);

            restaurantName = view.findViewById(R.id.buyer_history_restaurant);
            collectionTime = view.findViewById(R.id.buyer_history_time);
            dishName = view.findViewById(R.id.buyer_history_dish_name);
            collectionTime = view.findViewById(R.id.buyer_history_time);


        }
    }
}
