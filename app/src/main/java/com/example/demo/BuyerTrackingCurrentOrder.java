package com.example.demo;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BuyerTrackingCurrentOrder extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_tracking_current_order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        DrawerLayout drawer = findViewById(R.id.buyer_tracking_current_order_drawer_layout);
//        NavigationView navigationView = findViewById(R.id.buyer_tracking_current_order_nav);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//        navigationView.setNavigationItemSelectedListener(this);


        RecyclerView recList = findViewById(R.id.buyer_tracking_current_order_view);
        recList.setHasFixedSize(true);
        recList.setClickable(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recList.setLayoutManager(linearLayoutManager);
        BuyerTrackingCurrentOrderDataAdapter buyerTrackingDataAdapter = new BuyerTrackingCurrentOrderDataAdapter(CreateList(10));
        recList.setAdapter(buyerTrackingDataAdapter);
    }

    public List<OrderData> CreateList(int a){
        List<OrderData> items = new ArrayList<OrderData>();
        for(int b = 0; b < a; b++){
            OrderData item = new OrderData();
            item.dishName="dish name "+ b;
            item.collectionTime = b+""+b;
            item.price = b + "";
            item.restaurantName = "sp "+ b;
            items.add(item);
        }
        return items;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
