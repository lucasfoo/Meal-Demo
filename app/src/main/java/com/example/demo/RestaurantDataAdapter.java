package com.example.demo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RestaurantDataAdapter extends RecyclerView.Adapter<RestaurantDataAdapter.RestaurantViewHolder> {
    private List<RestaurantData> RestaurantList;

    public  RestaurantDataAdapter(List<RestaurantData> RestaurantList){
        this.RestaurantList = RestaurantList;
    }

    @Override
    public int getItemCount(){
        return RestaurantList.size();
    }

    @NonNull
    @Override
    public RestaurantDataAdapter.RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_menu_card, viewGroup, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder restaurantViewHolder, int i){
        RestaurantData restaurantData = RestaurantList.get(i);
        RestaurantViewHolder.mName.setText(restaurantData.name);
    }

    public static class  RestaurantViewHolder extends RecyclerView.ViewHolder {
        protected static TextView mName;

        public RestaurantViewHolder(View view) {
            super(view);
            mName = view.findViewById(R.id.restaurant_name);
        }
    }

}
