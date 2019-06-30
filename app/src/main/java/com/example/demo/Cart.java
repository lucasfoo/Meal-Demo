package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("buyers")
                .child(user.getUid()).child("cart");
        databaseReference.addValueEventListener(new ValueEventListener() {
            List<CartData> cartDataList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    final CartData cartData = new CartData();
                    Cart_item cart_item = dataSnapshot1.getValue(Cart_item.class);
                    cartData.restaurantID = cart_item.restaurantID;
                    cartData.itemID = cart_item.itemID;
                    DatabaseReference itemRef = FirebaseDatabase.getInstance().getReference("sellers").child(cartData.restaurantID).child("Dishes").child(cartData.itemID);
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
                    cartDataList.add(cartData);
                }
                RecyclerView cart_List = findViewById(R.id.cart_view);
                cart_List.setHasFixedSize(true);
                cart_List.setClickable(true);


                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                cart_List.setLayoutManager(linearLayoutManager);

                CartDataAdapter editorDataAdapter = new CartDataAdapter(cartDataList);
                cart_List.setAdapter(editorDataAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        Button button = (Button) findViewById(R.id.checkout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cart.this, Checkout.class);
                startActivity(intent);
            }
        });

    }

    private List<CartData> createList(int size) {
        List<CartData> items = new ArrayList<>();
        for (int i = 1; i <= size; ++i) {
            CartData data = new CartData();
            data.cost = "5";
            data.name = "dish name "+ i;
            items.add(data);
        }
        return  items;
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
