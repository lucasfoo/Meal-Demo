package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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

public class BuyerRestaurantItem extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_restaurant_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.buyer_restaurant_item_drawer_layout);
        NavigationView navigationView = findViewById(R.id.buyer_restaurant_item_nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        // add back arrow to toolbar

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





    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.history_cart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Intent intent = new Intent(BuyerRestaurantItem.this, BuyerSearchFunction.class);
            startActivity(intent);
        }
        if (id == R.id.action_history) {
            Intent intent = new Intent(BuyerRestaurantItem.this, BuyerHistory.class);
            startActivity(intent);
        }
        if (id == R.id.action_cart) {
            Intent intent = new Intent(BuyerRestaurantItem.this, Cart.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.nav_profile){
            Intent intent = new Intent(BuyerRestaurantItem.this, BuyerProfileEditor.class);
            startActivity(intent);
        }

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_orders) {
            Intent intent = new Intent(getApplicationContext(), BuyerHistory.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_seller) {
//
            Intent intent = new Intent(BuyerRestaurantItem.this, CreateRestaurant.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(BuyerRestaurantItem.this, InitialActivity.class);
            finish();
            startActivity(intent);

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}
