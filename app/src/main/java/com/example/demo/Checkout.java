package com.example.demo;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;


public class Checkout extends AppCompatActivity {
    private double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final List<CartItem> cartDataList = new ArrayList<>();
        totalPrice = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Button pay = findViewById(R.id.pay);
        ImageView dish_image = findViewById(R.id.item_photo);


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("buyers")
                .child(user.getUid()).child("cart");
        cartRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cartDataList.clear();
                totalPrice = 0;
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    CartItem cart_item = dataSnapshot1.getValue(CartItem.class);
                    if(cart_item.price != null){
                        totalPrice += Double.valueOf(cart_item.price);
                    }
                    cartDataList.add(cart_item);
                }

                RecyclerView cart_List = findViewById(R.id.checkout_view);
                cart_List.setHasFixedSize(true);
                cart_List.setClickable(true);


                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                cart_List.setLayoutManager(linearLayoutManager);

                CartDataAdapter editorDataAdapter = new CartDataAdapter(cartDataList);
                cart_List.setAdapter(editorDataAdapter);
                TextView mTotalPrice = findViewById(R.id.show_price);
                DecimalFormat decimalFormat = new DecimalFormat("$0.00");
                mTotalPrice.setText(" " + decimalFormat.format(totalPrice));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference sellerRef = FirebaseDatabase.getInstance().getReference("sellers");
                DatabaseReference buyerRef = FirebaseDatabase.getInstance().getReference("buyers").child(user.getUid());
                if(cartDataList.isEmpty()){
                    Toast.makeText(getApplicationContext(),"No items in cart, add in at least 1 item!",Toast.LENGTH_LONG).show();
                    return;
                }
                for(CartItem cartItem : cartDataList){
                    String restaurantID = cartItem.restaurantID;
                    String itemID = cartItem.itemID;
                    String itemName = cartItem.itemName;
                    String restaurantName = cartItem.restaurantName;
                    String itemCost = cartItem.price;
                    String buyerName = user.getDisplayName();
                    String buyerID = user.getUid();

                    DateFormat df = new SimpleDateFormat("MMM d, ''yyyy");
                    String date = df.format(Calendar.getInstance().getTime());
                    df = new SimpleDateFormat("h:mm a");
                    String time = df.format(Calendar.getInstance().getTime());
                    DatabaseReference sellerOrderRef = sellerRef.child(restaurantID).child("orders").push();
                    DatabaseReference buyerOrderRef = buyerRef.child("orders").push();
                    OrderData orderData = new OrderData(buyerName, buyerID, date, time, itemCost, itemName,itemID, sellerOrderRef.getKey(), buyerOrderRef.getKey(), restaurantID,restaurantName, "Uncollected");
                    orderData.sellerOrderID = sellerOrderRef.getKey();
                    orderData.buyerOrderID = buyerOrderRef.getKey();
                    sellerOrderRef.setValue(orderData);
                    buyerOrderRef.setValue(orderData);
                    buyerRef.child("cart").removeValue();
                }
                Intent intent = new Intent(Checkout.this, BuyerOngoingOrders.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Order placed, please pay cash upon collection!",Toast.LENGTH_LONG).show();
                finish();
            }

        });
    }
    /*
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    */
}
