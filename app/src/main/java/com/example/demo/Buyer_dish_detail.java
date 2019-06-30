package com.example.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Buyer_dish_detail extends AppCompatActivity {
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_dish_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView mDishName = findViewById(R.id.tv_dish_name);
        final TextView mDishPrice = findViewById(R.id.view_price);
        final TextView mDishDesc = findViewById(R.id.tv_alldescription);

        Bundle extras = getIntent().getExtras();
        String dishID = extras.getString("DishID");
        String restaurantID = extras.getString("RestaurantID");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("sellers").child(restaurantID)
                .child("Dishes").child(dishID);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Dish dish = dataSnapshot.getValue(Dish.class);
                mDishName.setText(dish.DishName);
                mDishPrice.setText(dish.DishPrice);
                mDishDesc.setText(dish.DishDescription);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

}
