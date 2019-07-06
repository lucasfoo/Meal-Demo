package com.example.demo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class BuyerViewRestaurantDataAdapter extends RecyclerView.Adapter<BuyerViewRestaurantDataAdapter.RestaurantViewHolder>{
    private List<Seller> RestaurantList;


    public BuyerViewRestaurantDataAdapter(List<Seller> RestaurantList){
        this.RestaurantList = RestaurantList;

    }

    @Override
    public int getItemCount(){
        return RestaurantList.size();
    }

    @NonNull
    @Override
    public BuyerViewRestaurantDataAdapter.RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.buyer_view_restaurant_card, viewGroup, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder restaurantViewHolder, int i){
        final Seller seller = RestaurantList.get(i);
        RestaurantViewHolder.mName.setText(seller.name);
        RestaurantViewHolder.mAddress.setText(seller.address + ' ' + seller.apt + ' ' + seller.postalCode );
        RestaurantViewHolder.mRestaurantID = seller.sellerID;
        RestaurantViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BuyerRestaurantItem.class);
                intent.putExtra("rname", seller.name );
                intent.putExtra("raddress", seller.address);
                intent.putExtra("rID", seller.sellerID);
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
        }
    }

}