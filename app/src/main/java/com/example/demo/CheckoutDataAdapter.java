package com.example.demo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CheckoutDataAdapter extends RecyclerView.Adapter<CheckoutDataAdapter.EditorViewHolder> {
    private List<CheckoutData> CheckoutList;
    private double net;

    public  CheckoutDataAdapter(List<CheckoutData> CheckoutList){
        this.CheckoutList = CheckoutList;
    }

    private void count_net(){
        for(int i = 0; i < CheckoutList.size(); i++){
            net += Double.parseDouble(CheckoutList.get(i).cost);
        }

    }

    @Override
    public int getItemCount(){
        return CheckoutList.size();
    }

    @NonNull
    @Override
    public CheckoutDataAdapter.EditorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.checkout_page, viewGroup, false);
        return new CheckoutDataAdapter.EditorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CheckoutDataAdapter.EditorViewHolder cartViewHolder, int i){
        CheckoutData item = CheckoutList.get(i);
        CheckoutDataAdapter.EditorViewHolder.mName.setText(item.name);
        CheckoutDataAdapter.EditorViewHolder.mPrice.setText(item.cost);
//        CheckoutDataAdapter.EditorViewHolder.mNet.setText(net+"");

    }

    public static class  EditorViewHolder extends RecyclerView.ViewHolder{
        protected static TextView mName;
        protected static TextView mPrice;
        protected static TextView mNet;

        public EditorViewHolder(View view){
            super(view);
            mName = view.findViewById(R.id.checkout_name);
            mPrice = view.findViewById(R.id.checkout_price);
            mNet = view.findViewById(R.id.net);


        }
    }
}
