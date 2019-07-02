package com.example.demo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class BuyerRestaurantItemListAdapter extends RecyclerView.Adapter<BuyerRestaurantItemListAdapter.ItemListViewHolder>{
    private List<BuyerRestaurantItemData> ItemList;

    public BuyerRestaurantItemListAdapter(List<BuyerRestaurantItemData> ItemList){
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
        final BuyerRestaurantItemData buyerRestaurantItemData = ItemList.get(i);
        ItemListViewHolder.mName.setText(buyerRestaurantItemData.ItemName);
        String price = "$" + buyerRestaurantItemData.Price;
        ItemListViewHolder.mPrice.setText(price);
        ItemListViewHolder.mDishID = buyerRestaurantItemData.ItemID;
        ItemListViewHolder.mRestaurantID = buyerRestaurantItemData.RestaurantID;
        BuyerRestaurantItemListAdapter.ItemListViewHolder.cardView.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BuyerDishDetail.class);
                intent.putExtra("DishID", buyerRestaurantItemData.ItemID);
                intent.putExtra("RestaurantID", buyerRestaurantItemData.RestaurantID);
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
