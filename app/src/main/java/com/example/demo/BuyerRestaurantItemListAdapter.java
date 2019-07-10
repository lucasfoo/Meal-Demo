package com.example.demo;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class BuyerRestaurantItemListAdapter extends RecyclerView.Adapter<BuyerRestaurantItemListAdapter.ItemListViewHolder>{
    private List<Dish> ItemList;

    public BuyerRestaurantItemListAdapter(List<Dish> ItemList){
        this.ItemList = ItemList;
    }

    @Override
    public int getItemCount(){
        return ItemList.size();
    }

    @NonNull
    @Override
    public BuyerRestaurantItemListAdapter.ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.buyer_restaurant_item_card, viewGroup, false);
        return new ItemListViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ItemListViewHolder itemListViewHolder, int i) {
        final Dish dish = ItemList.get(i);
        ItemListViewHolder.mName.setText(dish.DishName);
        String price = "$" + dish.DishPrice;
        ItemListViewHolder.mPrice.setText(price);
        ItemListViewHolder.mDishID = dish.DishID;
        ItemListViewHolder.mRestaurantID = dish.restaurantID;
        String prepTime = dish.PrepDuration.equalsIgnoreCase("0") ? "Available to collect immediately!" : Integer.parseInt(dish.PrepDuration )/ 100 + " hour" + String.format("%02d", Integer.parseInt(dish.PrepDuration ) % 100) +  " minutes";
        ItemListViewHolder.mPreparationTime.setText(prepTime);
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(dish.imageUri);
        GlideApp.with(ItemListViewHolder.dishPhoto.getContext() /* context */)
                .load(storageRef)
                .into(ItemListViewHolder.dishPhoto);

        BuyerRestaurantItemListAdapter.ItemListViewHolder.cardView.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BuyerDishDetail.class);
                intent.putExtra("DishID",dish.DishID);
                intent.putExtra("RestaurantID", dish.restaurantID);
                view.getContext().startActivity(intent);
            }
        });
    }

    public static class ItemListViewHolder extends RecyclerView.ViewHolder{
        public static View cardView;
        protected static TextView mName;
        protected static TextView mPrice;
        protected static TextView mPreparationTime;
        protected static ImageView dishPhoto;
        protected static String mDishID;
        protected static String mRestaurantID;

        public ItemListViewHolder(View view){
            super(view);
            cardView = view.findViewById(R.id.item_card);
            mName = cardView.findViewById(R.id.item_name);
            mPrice = cardView.findViewById(R.id.item_price);
            dishPhoto = cardView.findViewById(R.id.item_photo);
            mPreparationTime = cardView.findViewById(R.id.item_prep_time);
        }
    }
}
