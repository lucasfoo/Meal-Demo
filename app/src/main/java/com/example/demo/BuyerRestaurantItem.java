package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BuyerRestaurantItem extends AppCompatActivity {
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_restaurant_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Bundle extras = getIntent().getExtras();
        final String restaurantID = extras.getString("rID");
        String rname = extras.getString("rname");
        String raddress = extras.getString("raddress");
        TextView mName = findViewById(R.id.Restaurant_Name);
        TextView mAddress = findViewById(R.id.Restaurant_Address);
        mName.setText(rname);
        mAddress.setText(raddress);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("sellers").child(restaurantID).child("Dishes");
        mDatabase.addValueEventListener(new ValueEventListener() {
            List<BuyerRestaurantItemData> buyerRestaurantItemDataList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    BuyerRestaurantItemData buyerRestaurantItemData = new BuyerRestaurantItemData();
                    Dish dish = dataSnapshot1.getValue(Dish.class);
                    buyerRestaurantItemData.RestaurantID = restaurantID;
                    buyerRestaurantItemData.ItemName = dish.DishName;
                    buyerRestaurantItemData.Price = dish.DishPrice;
                    buyerRestaurantItemData.ItemID = dataSnapshot1.getKey();
                    buyerRestaurantItemDataList.add(buyerRestaurantItemData);
                }

                RecyclerView ItemList = findViewById(R.id.ItemRecyclerView);
                ItemList.setHasFixedSize(true);
                ItemList.setClickable(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                ItemList.setLayoutManager(linearLayoutManager);
                BuyerRestaurantItemListAdapter buyerRestaurantItemListAdapter = new BuyerRestaurantItemListAdapter(buyerRestaurantItemDataList);
                ItemList.setAdapter(buyerRestaurantItemListAdapter);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        View one_item = (View) findViewById(R.id.ItemRecyclerView);
        one_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuyerRestaurantItem.this, BuyerDishDetail.class);
                startActivity(intent);
            }
        });

        FloatingActionButton edit_menu = (FloatingActionButton) findViewById(R.id.cart2);
        edit_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuyerRestaurantItem.this, Cart.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }



}
