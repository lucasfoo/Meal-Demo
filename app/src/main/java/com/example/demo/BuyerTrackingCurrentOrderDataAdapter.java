package com.example.demo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class BuyerTrackingCurrentOrderDataAdapter extends RecyclerView.Adapter<BuyerTrackingCurrentOrderDataAdapter.BuyerTrackingViewHolder>{
    private List<OrderData> BuyerTrackingList;

    public BuyerTrackingCurrentOrderDataAdapter(List<OrderData> BuyerTrackingList){
        this.BuyerTrackingList = BuyerTrackingList;
    }

    @Override
    public int getItemCount(){
        return BuyerTrackingList.size();
    }

    @NonNull
    @Override
    public BuyerTrackingCurrentOrderDataAdapter.BuyerTrackingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.buyer_tracking_current_order_card, viewGroup, false);
        return new BuyerTrackingViewHolder(view);
    }

    @Override
    public void onBindViewHolder( BuyerTrackingViewHolder buyerTrackingViewHolder, int i) {
        OrderData data = BuyerTrackingList.get(i);
        try {
            buyerTrackingViewHolder.restaurantName.setText(data.restaurantName);
            buyerTrackingViewHolder.collectionTime.setText(data.collectionTime);
            buyerTrackingViewHolder.dishName.setText(data.dishName);
            buyerTrackingViewHolder.price.setText(data.price);
        }catch (Exception exception){

        }

    }

    public static class BuyerTrackingViewHolder extends RecyclerView.ViewHolder{

        protected static TextView restaurantName;
        protected static TextView collectionTime;
        protected static TextView dishName;
        protected static TextView price;

        public BuyerTrackingViewHolder(View view){
            super(view);

            price = view.findViewById(R.id.buyer_tracking_current_order_price);
            restaurantName = view.findViewById(R.id.buyer_tracking_current_order_restaurant);
            collectionTime = view.findViewById(R.id.buyer_tracking_current_order_collection_time);
            dishName = view.findViewById(R.id.buyer_tracking_current_order_dish_name);
        }
    }
}
