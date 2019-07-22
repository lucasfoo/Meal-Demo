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

public class SellerRestaurantDishEditorDataAdapter extends RecyclerView.Adapter<SellerRestaurantDishEditorDataAdapter.EditorViewHolder>{
    private List<Dish> dishList;

    public SellerRestaurantDishEditorDataAdapter(List<Dish> dishList){
        this.dishList = dishList;
    }

    @Override
    public int getItemCount(){
        return dishList.size();
    }

    @NonNull
    @Override
    public SellerRestaurantDishEditorDataAdapter.EditorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.buyer_restaurant_item_card, viewGroup, false);
        return new EditorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EditorViewHolder restaurantViewHolder, int i){
        final Dish dish = dishList.get(i);
        EditorViewHolder.mName.setText(dish.DishName);
        EditorViewHolder.mPrice.setText(dish.DishPrice);
        String price = "$" + dish.DishPrice;
        EditorViewHolder.mPrice.setText(price);
        EditorViewHolder.mDishID = dish.DishID;
        EditorViewHolder.mRestaurantID = dish.restaurantID;
        String prepTime = dish.PrepDuration.equalsIgnoreCase("0") ? "Available to collect immediately!" : ((Integer.parseInt(dish.PrepDuration )/ 100 != 0 ? Integer.parseInt(dish.PrepDuration )/ 100  + " hour " : ""))+ String.format("%02d", Integer.parseInt(dish.PrepDuration ) % 100) +  " minutes";
        EditorViewHolder.mPreparationTime.setText(prepTime);
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(dish.imageUri);
        GlideApp.with(EditorViewHolder.dishPhoto.getContext() /* context */)
                .load(storageRef)
                .into(EditorViewHolder.dishPhoto);

//        EditorViewHolder.mDishPhoto=

        SellerRestaurantDishEditorDataAdapter.EditorViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), InsertNewDish.class);
                intent.putExtra("dishID", dish.DishID);
                view.getContext().startActivity(intent);

            }
        });
    }

    public static class  EditorViewHolder extends RecyclerView.ViewHolder{
        public static View cardView;
        protected static TextView mName;
        protected static TextView mPrice;
        protected static TextView mPreparationTime;
        protected static ImageView dishPhoto;
        protected static String mDishID;
        protected static String mRestaurantID;


        public EditorViewHolder(View view){
            super(view);
            cardView = view.findViewById(R.id.item_card);
            mName = cardView.findViewById(R.id.item_name);
            mPrice = cardView.findViewById(R.id.item_price);
            dishPhoto = cardView.findViewById(R.id.item_photo);
            mPreparationTime = cardView.findViewById(R.id.item_prep_time);
        }
    }


}
