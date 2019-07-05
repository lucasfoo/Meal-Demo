package com.example.demo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CartDataAdapter extends RecyclerView.Adapter<CartDataAdapter.EditorViewHolder> {
    private List<CartItem> CartList;

    public  CartDataAdapter(List<CartItem> CartList){
        this.CartList = CartList;
    }

    @Override
    public int getItemCount(){
        return CartList.size();
    }

    @NonNull
    @Override
    public CartDataAdapter.EditorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_single_item, viewGroup, false);
        return new CartDataAdapter.EditorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartDataAdapter.EditorViewHolder cartViewHolder, final int i){
        final CartItem item = CartList.get(i);
        CartDataAdapter.EditorViewHolder.rName.setText(item.restaurantName);
        CartDataAdapter.EditorViewHolder.mName.setText(item.itemName);
        CartDataAdapter.EditorViewHolder.mPrice.setText(item.price);
        CartDataAdapter.EditorViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cartItemID = item.cartItemID;
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("buyers")
                        .child(user.getUid()).child("cart").child(cartItemID);
                databaseReference.removeValue();
            }
        });

    }

    public static class  EditorViewHolder extends RecyclerView.ViewHolder{
        protected static TextView rName;
        protected static TextView mName;
        protected static TextView mPrice;
        protected static View cardView;
        protected static ImageButton delete;

        public EditorViewHolder(View view){
            super(view);
            rName = view.findViewById(R.id.cart_restaurant_name);
            mName = view.findViewById(R.id.item_name);
            mPrice = view.findViewById(R.id.item_price);
            cardView = view.findViewById((R.id.cart_view));
            delete = view.findViewById(R.id.delete_from_cart);
        }
    }
}
