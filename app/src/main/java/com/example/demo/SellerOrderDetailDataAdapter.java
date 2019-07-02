package com.example.demo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SellerOrderDetailDataAdapter extends RecyclerView.Adapter<SellerOrderDetailDataAdapter.SellerOrderViewHolder>{
    private List<SellerOrderDetailData> OrderDetailList;

    public SellerOrderDetailDataAdapter(List<SellerOrderDetailData> OrderDetailList){
        this.OrderDetailList = OrderDetailList;
    }

    @Override
    public int getItemCount(){
        return OrderDetailList.size();
    }

    @NonNull
    @Override
    public SellerOrderDetailDataAdapter.SellerOrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.seller_order_detail_card, viewGroup, false);
        return new SellerOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder( SellerOrderViewHolder sellerorderViewHolder, int i) {
        SellerOrderDetailData sellerOrderDetailData = OrderDetailList.get(i);
//        SellerOrderViewHolder.itemName.setText(sellerOrderDetailData.itemName);
//        SellerOrderViewHolder.collector.setText(data.Collector);
//        SellerOrderViewHolder.collectionTime.setText(sellerOrderDetailData.CollectionTime);

    }

    public static class SellerOrderViewHolder extends RecyclerView.ViewHolder{
        public static View cardView;
        protected static TextView collector;
        protected static TextView itemName;
        protected static TextView itemPrice;
        protected static TextView collectionTime;

        public SellerOrderViewHolder(View view){
            super(view);
            cardView = view.findViewById(R.id.seller_order_detail_view);
            collector = view.findViewById(R.id.seller_order_collector);
            itemName = view.findViewById(R.id.seller_order_detail_dish_name);
            itemPrice = view.findViewById(R.id.seller_order_detail_price);
            collectionTime = view.findViewById(R.id.seller_order_collection_time);


        }
    }
}
