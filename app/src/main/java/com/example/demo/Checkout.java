package com.example.demo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        final List<CartData> cartDataList = new ArrayList<>();
        totalPrice = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Button pay = findViewById(R.id.pay);

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
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    CartData cartData = new CartData();
                    CartItem cart_item = dataSnapshot1.getValue(CartItem.class);
                    cartData.restaurantID = cart_item.restaurantID;
                    cartData.itemID = cart_item.itemID;
                    cartData.cost = cart_item.price;
                    cartData.name = cart_item.itemName;
                    if(cartData.cost != null){
                        totalPrice += Double.valueOf(cartData.cost);
                    }
                    cartDataList.add(cartData);
                }

                RecyclerView cart_List = findViewById(R.id.checkout_view);
                cart_List.setHasFixedSize(true);
                cart_List.setClickable(true);


                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                cart_List.setLayoutManager(linearLayoutManager);

                CartDataAdapter editorDataAdapter = new CartDataAdapter(cartDataList);
                cart_List.setAdapter(editorDataAdapter);
                TextView mTotalPrice = findViewById(R.id.show_price);
                DecimalFormat decimalFormat = new DecimalFormat("$0.00");
                mTotalPrice.append(" " + decimalFormat.format(totalPrice));
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
                for(CartData cartData : cartDataList){
                    String restaurantID = cartData.restaurantID;
                    String itemID = cartData.itemID;
                    String itemName = cartData.name;
                    String itemCost = cartData.cost;
                    String buyerName = user.getDisplayName();
                    String buyerID = user.getUid();
                    DateFormat df = new SimpleDateFormat("MMM d, ''yyyy");
                    String date = df.format(Calendar.getInstance().getTime());
                    df = new SimpleDateFormat("h:mm a");
                    String time = df.format(Calendar.getInstance().getTime());
                    SellerOrderDetailData sellerOrderDetailData = new SellerOrderDetailData(buyerName, buyerID, date, time, itemCost, itemName);
                    DatabaseReference sellerOrderRef = sellerRef.child(restaurantID).child("orders").push();
                    sellerOrderRef.setValue(sellerOrderDetailData);
                    String orderID = sellerOrderRef.getKey();
                    BuyerOrderData buyerOrderData = new BuyerOrderData(orderID, restaurantID, itemID,itemCost, itemName);
                    DatabaseReference buyerOrderRef = buyerRef.child("orders").push();
                    buyerOrderRef.setValue(buyerOrderData);
                    buyerRef.child("cart").removeValue();
                }
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
