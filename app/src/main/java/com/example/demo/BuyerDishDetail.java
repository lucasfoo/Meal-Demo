package com.example.demo;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class BuyerDishDetail extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private String restaurantName;
    private Dish dish;
    private Seller seller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_dish_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView mDishName = findViewById(R.id.tv_dish_name);
        final TextView mPreparationTime = findViewById(R.id.buyer_dish_detail_preparation_duration);
        final TextView mDishPrice = findViewById(R.id.view_price);
        final TextView mDishDesc = findViewById(R.id.tv_alldescription);
        final Button AddToCart = findViewById(R.id.add_to_cart);
        final ImageView mDishImage = findViewById(R.id.view_photo);

        Bundle extras = getIntent().getExtras();
        final String dishID = extras.getString("DishID");
        final String restaurantID = extras.getString("RestaurantID");
        DatabaseReference restaurantRef = FirebaseDatabase.getInstance().getReference("sellers").child(restaurantID);
        restaurantRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                seller = dataSnapshot.getValue(Seller.class);
                restaurantName = seller.name;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference().child("sellers").child(restaurantID)
                .child("Dishes").child(dishID);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dish = dataSnapshot.getValue(Dish.class);
                String prepTime = dish.PrepDuration.equalsIgnoreCase("0") ? "Available to collect immediately!" : ((Integer.parseInt(dish.PrepDuration )/ 100 != 0 ? Integer.parseInt(dish.PrepDuration )/ 100  + " hour " : ""))+ String.format("%02d", Integer.parseInt(dish.PrepDuration ) % 100) +  " minutes";
                mDishName.setText(dish.DishName);
                mDishPrice.setText("$" +  dish.DishPrice);
                mDishDesc.setText(dish.DishDescription);
                mPreparationTime.setText(prepTime);
                StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(dish.imageUri);
                if(dish.imageUri != null) {
                    GlideApp.with(BuyerDishDetail.this.getApplicationContext() /* context */)
                            .load(storageRef)
                            .into(mDishImage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("buyers").child(user.getUid()).child("cart");
                cartRef = cartRef.push();
                CartItem cart_item = new CartItem(restaurantID, restaurantName ,dishID, dish.DishPrice, dish.DishName, cartRef.getKey(), seller.openingTime, seller.closingTime, dish.imageUri, dish.PrepDuration);
                cartRef.setValue(cart_item);
                finish();
            }
        });


    }

}
