package com.example.demo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

public class RestaurantDataAdapter extends RecyclerView.Adapter<RestaurantDataAdapter.RestaurantViewHolder>{
    private List<RestaurantData> RestaurantList;

    public interface OnRestaurantClickListener{
        void onRestaurantClick(RestaurantData restaurantData);
    }


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
        final RestaurantData restaurantData = RestaurantList.get(i);
        RestaurantViewHolder.mName.setText(restaurantData.name);
        RestaurantViewHolder.mAddress.setText(restaurantData.address);
        RestaurantViewHolder.mRestaurantID = restaurantData.restaurantID;
        RestaurantViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Rdetail.class);
                intent.putExtra("rname",restaurantData.name );
                intent.putExtra("raddress", restaurantData.address);
                intent.putExtra("rID", restaurantData.restaurantID);
                v.getContext().startActivity(intent);
            }
        });
    }

    public static class  RestaurantViewHolder extends RecyclerView.ViewHolder{
        protected static CardView cardView;
        protected static TextView mName;
        protected static TextView mAddress;
        protected static String mRestaurantID;

        public RestaurantViewHolder(View view){
            super(view);
            cardView = view.findViewById(R.id.main_menu_item);
            mName = view.findViewById(R.id.restaurant_name);
            mAddress = view.findViewById(R.id.restaurant_address);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

}