package com.example.demo;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.seller_restaurant_editor_page, viewGroup, false);
        return new EditorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EditorViewHolder restaurantViewHolder, int i){
        final Dish dish = dishList.get(i);
        EditorViewHolder.mName.setText(dish.DishName);
        EditorViewHolder.mPrice.setText(dish.DishPrice);

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
        protected static TextView mName;
        protected static TextView mPrice;
        public static View cardView;

        public EditorViewHolder(View view){
            super(view);
            cardView = view.findViewById(R.id.editor_container);
            mName = view.findViewById(R.id.editor_dish_name);
            mPrice = view.findViewById(R.id.editor_price);

        }
    }


}
