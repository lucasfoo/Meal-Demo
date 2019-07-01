package com.example.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
        final Button button = findViewById(R.id.add_to_cart);

        Bundle extras = getIntent().getExtras();
        final String dishID = extras.getString("DishID");
        final String restaurantID = extras.getString("RestaurantID");
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("buyers").child(user.getUid()).child("cart");
                Cart_item cart_item = new Cart_item(restaurantID, dishID, mDishPrice.getText().toString(), mDishName.getText().toString());
                cartRef = cartRef.push();
                cartRef.setValue(cart_item);
                finish();
            }
        });


    }

}
