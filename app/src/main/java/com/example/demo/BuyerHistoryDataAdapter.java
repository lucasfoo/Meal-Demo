package com.example.demo;

import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class BuyerHistoryDataAdapter extends RecyclerView.Adapter<BuyerHistoryDataAdapter.BuyerHistoryViewHolder>{
    private List<OrderData> BuyerHistoryList;

    public BuyerHistoryDataAdapter(List<OrderData> BuyerHistoryList){
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
    public void onBindViewHolder( BuyerHistoryViewHolder buyerHistoryViewHolder, int i) {
        OrderData data = BuyerHistoryList.get(i);
        try {
            buyerHistoryViewHolder.restaurantName.setText(data.restaurantName);
            buyerHistoryViewHolder.collectionTime.setText(data.collectionTime);
            buyerHistoryViewHolder.dishName.setText(data.dishName);
            buyerHistoryViewHolder.price.setText(data.price);
            if(data.status.equalsIgnoreCase("Uncollected")){
                buyerHistoryViewHolder.addReview.setVisibility(View.GONE);
            }else{
                buyerHistoryViewHolder.addReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), AddReview.class);
                        view.getContext().startActivity(intent);
                    }
                });
            }

        }catch (Exception exception){

        }



    }

    public static class BuyerHistoryViewHolder extends RecyclerView.ViewHolder{

        protected static TextView restaurantName;
        protected static TextView collectionTime;
        protected static TextView dishName;
        protected static TextView price;
        protected static ImageButton addReview;

        public BuyerHistoryViewHolder(View view){
            super(view);

            price = view.findViewById(R.id.buyer_history_price);
            restaurantName = view.findViewById(R.id.buyer_history_restaurant);
            collectionTime = view.findViewById(R.id.buyer_history_time);
            dishName = view.findViewById(R.id.buyer_history_dish_name);
            addReview = view.findViewById(R.id.add_review);
        }
    }
}
