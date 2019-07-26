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

public class SellerHistory extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_history);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.seller_history_drawer_layout);
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
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("sellers").child(user.getUid())
                .child("completed");

        orderRef.addValueEventListener(new ValueEventListener() {
            List<OrderData> orderDataList = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    OrderData orderData = dataSnapshot1.getValue(OrderData.class);
                    orderDataList.add(orderData);
                }

                RecyclerView recList = findViewById(R.id.seller_history_view);
                recList.setHasFixedSize(true);
                recList.setClickable(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

                recList.setLayoutManager(linearLayoutManager);
                SellerHistoryDataAdapter sellerHistoryDataAdapter = new SellerHistoryDataAdapter(orderDataList);
                recList.setAdapter(sellerHistoryDataAdapter);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_restaurant_profile) {
            Intent intent = new Intent(SellerHistory.this, SellerRestaurantProfileEditor.class);

            startActivity(intent);
        }else if (id == R.id.nav_seller_history) {
            Intent intent = new Intent(SellerHistory.this, SellerHistory.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(SellerHistory.this, InitialActivity.class);
            finish();
            startActivity(intent);
        }else if(id == R.id.nav_seller_edit_menu){
            Intent intent = new Intent(SellerHistory.this, SellerRestaurantDishEditor.class);
            startActivity(intent);
        }else if(id == R.id.nav_seller_home){
            Intent intent = new Intent(SellerHistory.this, SellerExistingOrder.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.seller_history_drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

}
