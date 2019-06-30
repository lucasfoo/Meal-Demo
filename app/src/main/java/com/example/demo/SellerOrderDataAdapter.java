package com.example.demo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SellerOrderDataAdapter extends RecyclerView.Adapter<SellerOrderDataAdapter.SellerOrderViewHolder>{
    private List<SellerOrderData> OrderDetailList;

    public SellerOrderDataAdapter(List<SellerOrderData> OrderDetailList){
        this.OrderDetailList = OrderDetailList;
    }

    @Override
    public int getItemCount(){
        return OrderDetailList.size();
    }

    @NonNull
    @Override
    public SellerOrderDataAdapter.SellerOrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.seller_order_detail_card, viewGroup, false);
        return new SellerOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder( SellerOrderViewHolder sellerorderViewHolder, int i) {
        SellerOrderData data = OrderDetailList.get(i);
//        SellerOrderViewHolder.collector.setText(data.Collector);
//        SellerOrderViewHolder.collectionTime.setText(data.CollectionTime);

    }

    public static class SellerOrderViewHolder extends RecyclerView.ViewHolder{
        public static View cardView;
        protected static TextView collector;
        protected static TextView collectionTime;

        public SellerOrderViewHolder(View view){
            super(view);
            cardView = view.findViewById(R.id.seller_order_detail_view);
            collector = view.findViewById(R.id.seller_order_collector);
            collectionTime = view.findViewById(R.id.seller_order_collection_time);


        }
    }
}
