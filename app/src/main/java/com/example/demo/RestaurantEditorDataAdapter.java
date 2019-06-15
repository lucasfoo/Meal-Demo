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

public class RestaurantEditorDataAdapter extends RecyclerView.Adapter<RestaurantEditorDataAdapter.EditorViewHolder>{
    private List<RestaurantEditorData> dishList;

    public  RestaurantEditorDataAdapter(List<RestaurantEditorData> dishList){
        this.dishList = dishList;
    }

    @Override
    public int getItemCount(){
        return dishList.size();
    }

    @NonNull
    @Override
    public RestaurantEditorDataAdapter.EditorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.restaurant_editor_page, viewGroup, false);
        return new EditorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EditorViewHolder restaurantViewHolder, int i){
        RestaurantEditorData dish = dishList.get(i);
        EditorViewHolder.mName.setText(dish.dish_name);
        EditorViewHolder.mPrice.setText(dish.cost);
    }

    public static class  EditorViewHolder extends RecyclerView.ViewHolder{
        protected static TextView mName;
        protected static TextView mPrice;

        public EditorViewHolder(View view){
            super(view);
            mName = view.findViewById(R.id.editor_dish_name);
            mPrice = view.findViewById(R.id.editor_price);

        }
    }


}
