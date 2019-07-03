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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SellerExistingOrder extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_existing_order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton edit_menu = (FloatingActionButton) findViewById(R.id.edit_menu_id);
        edit_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerExistingOrder.this, SellerRestaurantDishEditor.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.seller_existing_order_drawer_layout);
        NavigationView navigationView = findViewById(R.id.seller_nav);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);




        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("sellers").child(user.getUid()).child("orders");

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<SellerExistingOrderData> sellerExistingOrderDataList = new ArrayList<>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    SellerOrderDetailData item = dataSnapshot1.getValue(SellerOrderDetailData.class);
                    SellerExistingOrderData sellerExistingOrderData = new SellerExistingOrderData();
                    sellerExistingOrderData.dishName = item.dishName;
                    sellerExistingOrderData.buyerName = item.buyerName;
                    sellerExistingOrderData.orderTime = item.orderDate + " " + item.orderTime ;
                    sellerExistingOrderData.orderID = dataSnapshot1.getKey();
                    sellerExistingOrderData.collectionTime = "Soon";
                    sellerExistingOrderDataList.add(sellerExistingOrderData);
                }
                RecyclerView recList = findViewById(R.id.orderList);
                recList.setHasFixedSize(true);
                recList.setClickable(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recList.setLayoutManager(linearLayoutManager);

                SellerExistingOrderDataAdapter sellerExistingOrderDataAdapter = new SellerExistingOrderDataAdapter(sellerExistingOrderDataList);
                recList.setAdapter(sellerExistingOrderDataAdapter);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_history) {
            Intent intent = new Intent(SellerExistingOrder.this, SellerHistory.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_restaurant_profile) {
            Intent intent = new Intent(SellerExistingOrder.this, SellerRestaurantProfileEditor.class);

            startActivity(intent);

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(SellerExistingOrder.this, InitialActivity.class);
            finish();
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

