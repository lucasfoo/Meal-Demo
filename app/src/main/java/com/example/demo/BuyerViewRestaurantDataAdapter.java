package com.example.demo;

import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(seller.photoID);
        GlideApp.with(RestaurantViewHolder.mRestaurantPhoto.getContext() /* context */)
                .load(storageRef)
                .into(RestaurantViewHolder.mRestaurantPhoto);

//        RestaurantViewHolder.mRestaurantPhoto =


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
        protected static ImageView mRestaurantPhoto;
        protected  static RatingBar mRestaurantRating;

        public RestaurantViewHolder(View view){
            super(view);
            cardView = view.findViewById(R.id.main_menu_item);
            mName = view.findViewById(R.id.restaurant_name);
            mAddress = view.findViewById(R.id.restaurant_address);
            mRestaurantPhoto = view.findViewById(R.id.restaurant_photo);
            mRestaurantRating = view.findViewById(R.id.restaurant_rating);
        }
    }

}