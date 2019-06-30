package com.example.demo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder>{
    private List<ItemData> ItemList;

    public ItemListAdapter(List<ItemData> ItemList){
        this.ItemList = ItemList;
    }

    @Override
    public int getItemCount(){
        return ItemList.size();
    }

    @NonNull
    @Override
    public ItemListAdapter.ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card, viewGroup, false);
        return new ItemListViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ItemListViewHolder itemListViewHolder, int i) {
        final ItemData itemData = ItemList.get(i);
        ItemListViewHolder.mName.setText(itemData.ItemName);
        String price = "$" + itemData.Price;
        ItemListViewHolder.mPrice.setText(price);
        ItemListViewHolder.mDishID = itemData.ItemID;
        ItemListViewHolder.mRestaurantID = itemData.RestaurantID;
        ItemListAdapter.ItemListViewHolder.cardView.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Buyer_dish_detail.class);
                intent.putExtra("DishID",itemData.ItemID);
                intent.putExtra("RestaurantID", itemData.RestaurantID);
                view.getContext().startActivity(intent);
            }
        });
    }

    public static class ItemListViewHolder extends RecyclerView.ViewHolder{
        public static View cardView;
        protected static TextView mName;
        protected static TextView mPrice;
        protected static String mDishID;
        protected static String mRestaurantID;

        public ItemListViewHolder(View view){
            super(view);
            cardView = view.findViewById(R.id.item_card);
            mName = view.findViewById(R.id.item_name);
            mPrice = view.findViewById(R.id.item_price);

      
        }
    }
}
