package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
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


        DrawerLayout drawer = findViewById(R.id.seller_existing_order_drawer_layout);
        NavigationView navigationView = findViewById(R.id.seller_nav);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        String email = user.getEmail();
        View view = navigationView.getHeaderView(0);
        TextView emailTextView = view.findViewById(R.id.tv_user_email);
        final TextView nameTextView = view.findViewById(R.id.tv_user_id);


        DatabaseReference nameRef = FirebaseDatabase.getInstance().getReference("sellers").child(user.getUid()).child("name");
        nameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                nameTextView.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        emailTextView.setText(email);
        int color = getResources().getColor(R.color.colorPrimary);;
        view.setBackgroundColor(color);


        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("sellers").child(user.getUid()).child("orders");

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<OrderData> orderDataList = new ArrayList<>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    OrderData item = dataSnapshot1.getValue(OrderData.class);
                    orderDataList.add(item);
                }
                RecyclerView recList = findViewById(R.id.orderList);
                recList.setHasFixedSize(true);
                recList.setClickable(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recList.setLayoutManager(linearLayoutManager);

                SellerExistingOrderDataAdapter sellerExistingOrderDataAdapter = new SellerExistingOrderDataAdapter(orderDataList);
                recList.setAdapter(sellerExistingOrderDataAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.seller_existing_order_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_restaurant_profile) {
            Intent intent = new Intent(SellerExistingOrder.this, SellerRestaurantProfileEditor.class);

            startActivity(intent);
        }else if (id == R.id.nav_seller_history) {
            Intent intent = new Intent(SellerExistingOrder.this, SellerHistory.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(SellerExistingOrder.this, InitialActivity.class);
            finish();
            startActivity(intent);
        }else if(id == R.id.nav_seller_edit_menu){
            Intent intent = new Intent(SellerExistingOrder.this, SellerRestaurantDishEditor.class);
            startActivity(intent);
        }else if(id == R.id.nav_seller_home){

        }


        DrawerLayout drawer = findViewById(R.id.seller_existing_order_drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

}

