package com.example.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.buyer_history_card, viewGroup, false);
        return new SellerHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder( SellerHistoryViewHolder sellerorderViewHolder, int i) {
        OrderData data = SellerHistoryList.get(i);
        try {
            sellerorderViewHolder.buyerName.setText(data.buyerName);
            sellerorderViewHolder.orderTime.setText("Order Time:" + data.orderTime);
            sellerorderViewHolder.addReview.setEnabled(false);
            sellerorderViewHolder.addReview.setVisibility(View.INVISIBLE);
            sellerorderViewHolder.addReview.setClickable(false);
            sellerorderViewHolder.collectionTime.setText("Collected Time: " + data.collectionTime);
            sellerorderViewHolder.dishName.setText(data.dishName);
            sellerorderViewHolder.price.setText('$' + data.price);
            sellerorderViewHolder.orderDate.setText("Order date: " + data.orderDate);
        }catch (Exception exception){

        }

    }

    public static class SellerHistoryViewHolder extends RecyclerView.ViewHolder{

        protected static TextView buyerName;
        protected static TextView orderTime;
        protected static TextView collectionTime;
        protected static TextView dishName;
        protected static TextView price;
        protected static ImageButton addReview;
        protected static ImageView historyPhoto;
        protected static TextView orderDate;

        public SellerHistoryViewHolder(View view){
            super(view);

            price = view.findViewById(R.id.buyer_history_price);
            orderDate = view.findViewById(R.id.buyer_history_order_date);
            buyerName = view.findViewById(R.id.buyer_history_restaurant);
            collectionTime = view.findViewById(R.id.buyer_history_collection_time);
            orderTime = view.findViewById(R.id.buyer_history_order_time);
            dishName = view.findViewById(R.id.buyer_history_dish_name);
            addReview = view.findViewById(R.id.add_review);
            historyPhoto = view.findViewById(R.id.history_photo);
        }
    }
}
