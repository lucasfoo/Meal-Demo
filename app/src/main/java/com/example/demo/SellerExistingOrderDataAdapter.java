package com.example.demo;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

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
        orderViewHolder.Collection_time.setText("Order time: " + orderData.orderTime);
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(orderData.imageRef);
        GlideApp.with(SellerExistingOrderDataAdapter.OrderViewHolder.DishPhoto.getContext()/* context */)
                .load(storageRef)
                .into(SellerExistingOrderDataAdapter.OrderViewHolder.DishPhoto);

        SellerExistingOrderDataAdapter.OrderViewHolder.Collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFormat df = new SimpleDateFormat("h:mm a");
                String date = df.format(Calendar.getInstance().getTime());
                orderData.collectionTime = date;
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

        /*
        SellerExistingOrderDataAdapter.OrderViewHolder.cardView.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SellerOrderDetail.class);
                intent.putExtra("orderID", orderData.sellerOrderID);
                view.getContext().startActivity(intent);
            }
        });
        */

    }

    public static class  OrderViewHolder extends RecyclerView.ViewHolder {
        protected static TextView buyersName;
        protected static TextView Dish_name;
        protected static TextView Collection_time;
        protected static Button Collect;
        public static View cardView;
        protected static ImageView DishPhoto;

        public OrderViewHolder(View view) {
            super(view);
            buyersName = view.findViewById(R.id.buyers_name);
            cardView = view.findViewById(R.id.single_order);
            Dish_name = view.findViewById(R.id.dish_name);
            Collection_time = view.findViewById(R.id.collection_time);
            Collect = view.findViewById(R.id.collectButton);
            DishPhoto = view.findViewById(R.id.exisiting_order_dish_photo);

        }
    }

}
