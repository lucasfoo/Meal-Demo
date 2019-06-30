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
        ItemData itemData = ItemList.get(i);
        ItemListViewHolder.mName.setText(itemData.ItemName);
        String price = "$" + itemData.Price;
        ItemListViewHolder.mPrice.setText(price);


    }

    public static class ItemListViewHolder extends RecyclerView.ViewHolder{
        protected static TextView mName;
        protected static TextView mPrice;

        public ItemListViewHolder(View view){
            super(view);
            mName = view.findViewById(R.id.item_name);
            mPrice = view.findViewById(R.id.item_price);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(),Buyer_dish_detail.class);

                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
