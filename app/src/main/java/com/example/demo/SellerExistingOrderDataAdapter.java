package com.example.demo;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class SellerExistingOrderDataAdapter extends RecyclerView.Adapter<SellerExistingOrderDataAdapter.OrderViewHolder> {
    private List<OrderData> OrderList;

    public SellerExistingOrderDataAdapter(List<OrderData> OrderList){
        this.OrderList = OrderList;
    }

    @Override
    public int getItemCount(){
        return OrderList.size();
    }

    @NonNull
    @Override
    public SellerExistingOrderDataAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.seller_existing_order_card, viewGroup, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder orderViewHolder, int i){
        final OrderData orderData = OrderList.get(i);
        orderViewHolder.buyersName.setText(orderData.buyerName);
        orderViewHolder.Dish_name.setText(orderData.dishName);
        orderViewHolder.Order_num.setText("Order number: " + orderData.sellerOrderID);
        orderViewHolder.Collection_time.setText("Order time: " + orderData.orderTime);

        SellerExistingOrderDataAdapter.OrderViewHolder.Collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference sellerOrderRef = FirebaseDatabase.getInstance().getReference("sellers").child(user.getUid())
                        .child("orders").child(orderData.sellerOrderID);
                DatabaseReference sellerCompletedRef =  FirebaseDatabase.getInstance().getReference("sellers").child(user.getUid())
                        .child("completed").push();
                DatabaseReference buyerOrderRef = FirebaseDatabase.getInstance().getReference("buyers").child(orderData.buyerID).
                        child("orders").child(orderData.buyerOrderID);
                DatabaseReference buyerCompletedRef = FirebaseDatabase.getInstance().getReference("buyers").child(orderData.buyerID)
                        .child("completed").push();
                orderData.status = "Collected";
                buyerCompletedRef.setValue(orderData);
                buyerOrderRef.removeValue();
                sellerCompletedRef.setValue(orderData);
                sellerOrderRef.removeValue();
            }
        });





        SellerExistingOrderDataAdapter.OrderViewHolder.cardView.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SellerOrderDetail.class);
                intent.putExtra("orderID", orderData.sellerOrderID);
                view.getContext().startActivity(intent);
            }
        });

    }

    public static class  OrderViewHolder extends RecyclerView.ViewHolder {
        protected static TextView buyersName;
        protected static TextView Dish_name;
        protected static TextView Order_num;
        protected static TextView Collection_time;
        protected static Button Collect;
        public static View cardView;
        protected static ImageView DishPhoto;

        public OrderViewHolder(View view) {
            super(view);
            buyersName = view.findViewById(R.id.buyers_name);
            cardView = view.findViewById(R.id.single_order);
            Dish_name = view.findViewById(R.id.dish_name);
            Order_num = view.findViewById(R.id.dish_num);
            Collection_time = view.findViewById(R.id.collection_time);
            Collect = view.findViewById(R.id.collectButton);
            DishPhoto = view.findViewById(R.id.exisiting_order_dish_photo);

        }
    }

}
