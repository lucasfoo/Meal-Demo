package com.example.demo;

import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Checkout extends AppCompatActivity {
    private double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        totalPrice = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("buyers")
                .child(user.getUid()).child("cart");
        databaseReference.addValueEventListener(new ValueEventListener() {
            List<CartData> cartDataList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    final CartData cartData = new CartData();
                    Cart_item cart_item = dataSnapshot1.getValue(Cart_item.class);
                    DatabaseReference itemRef = FirebaseDatabase.getInstance().getReference().child("sellers")
                            .child(cart_item.restaurantID).child("Dishes").child(cart_item.itemID);
                    cartData.restaurantID = cart_item.restaurantID;
                    cartData.itemID = cart_item.itemID;
                    itemRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                            Dish dish = dataSnapshot2.getValue(Dish.class);
                            cartData.name = dish.DishName;
                            cartData.cost = dish.DishPrice;
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }


                    });
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
