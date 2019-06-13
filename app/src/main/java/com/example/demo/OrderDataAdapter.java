package com.example.demo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_menu_card, viewGroup, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder OrderViewHolder, int i){
        OrderData orderData = OrderList.get(i);
        OrderViewHolder.Dish_name.setText(orderData.dish_name);
        OrderViewHolder.Order_num.setText(orderData.order_number);
        OrderViewHolder.Collection_time.setText(orderData.collection_time);
    }

    public static class  OrderViewHolder extends RecyclerView.ViewHolder {
        protected static TextView Dish_name;
        protected static TextView Order_num;
        protected static TextView Collection_time;

        public OrderViewHolder(View view) {
            super(view);
            Dish_name = view.findViewById(R.id.dish_name);
            Order_num = view.findViewById(R.id.dish_num);
            Collection_time = view.findViewById(R.id.collection_time);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.getContext().startActivity(new Intent(view.getContext(), Rdetail.class));
                }
            });
        }
    }

}
