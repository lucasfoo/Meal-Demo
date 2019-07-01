package com.example.demo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class OrderDataAdapter extends RecyclerView.Adapter<OrderDataAdapter.OrderViewHolder> {
    private List<OrderData> OrderList;

    public  OrderDataAdapter(List<OrderData> OrderList){
        this.OrderList = OrderList;
    }

    @Override
    public int getItemCount(){
        return OrderList.size();
    }

    @NonNull
    @Override
    public OrderDataAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_order, viewGroup, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder orderViewHolder, int i){
        final OrderData orderData = OrderList.get(i);
        orderViewHolder.buyersName.setText(orderData.buyerName);
        orderViewHolder.Dish_name.setText(orderData.dishName);
        orderViewHolder.Order_num.setText("Order number: " + orderData.orderID);
        orderViewHolder.Collection_time.setText("Order time: " + orderData.orderTime);

        OrderDataAdapter.OrderViewHolder.Collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("sellers").child(user.getUid())
                        .child("orders").child(orderData.orderID);
                orderRef.removeValue();
            }
        });





        OrderDataAdapter.OrderViewHolder.cardView.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Seller_Order_Detail.class);
                intent.putExtra("orderID",orderData.orderID);
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
        public OrderViewHolder(View view) {
            super(view);
            buyersName = view.findViewById(R.id.buyers_name);
            cardView = view.findViewById(R.id.single_order);
            Dish_name = view.findViewById(R.id.dish_name);
            Order_num = view.findViewById(R.id.dish_num);
            Collection_time = view.findViewById(R.id.collection_time);
            Collect = view.findViewById(R.id.collectButton);

        }
    }

}
