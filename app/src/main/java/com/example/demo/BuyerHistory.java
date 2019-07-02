package com.example.demo;

import android.provider.ContactsContract;
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

public class BuyerHistory extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer__history);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.buyer_history_drawer_layout);
        NavigationView navigationView = findViewById(R.id.buyer_history_nav);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference buyerOrdersRef = FirebaseDatabase.getInstance().getReference("buyers").child(user.getUid()).child("orders");

        buyerOrdersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<BuyerHistoryData> buyerHistoryDataList = new ArrayList<>();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    BuyerHistoryData buyerHistoryData = new BuyerHistoryData();
                    BuyerOrderData buyerOrderData = dataSnapshot1.getValue(BuyerOrderData.class);
                    buyerHistoryData.DishName = buyerOrderData.itemName;
                    buyerHistoryData.Date = buyerOrderData.sellerOrderID;
                    buyerHistoryData.restaurantName = buyerOrderData.restaurantID;
                    buyerHistoryData.price = buyerOrderData.price;
                    buyerHistoryDataList.add(buyerHistoryData);
                }

                RecyclerView recList = findViewById(R.id.buyer_history_view);
                recList.setHasFixedSize(true);
                recList.setClickable(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                recList.setLayoutManager(linearLayoutManager);
                BuyerHistoryDataAdapter buyerHistoryDataAdapter = new BuyerHistoryDataAdapter(buyerHistoryDataList);
                recList.setAdapter(buyerHistoryDataAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private List<BuyerHistoryData> createList(int size) {
        List<BuyerHistoryData> items = new ArrayList<>();
        for (int i = 1; i <= size; ++i) {
            BuyerHistoryData data = new BuyerHistoryData();
            data.Date = i+""+i+i+i+i+i+i+i+i;
            data.restaurantName = "shaopeng's restaurant "+ i;
            data.DishName = "dish name "+i;
            data.price = "5."+ i;
            items.add(data);
        }
        return  items;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return true;
    }

}
