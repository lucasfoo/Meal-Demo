package com.example.demo;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import android.view.View;

import android.view.MenuItem;

import android.view.Menu;
import android.widget.ImageView;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class BuyerViewRestaurant extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String name = user.getDisplayName();
        String email = user.getEmail();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_view_restaurant);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        View view = navigationView.getHeaderView(0);
        TextView emailTextView = view.findViewById(R.id.tv_user_email);
        TextView nameTextView = view.findViewById(R.id.tv_user_id);
        emailTextView.setText(email);
        nameTextView.setText(name);

        //RecylcerView code below
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("sellers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            List<Seller> res = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                res.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Seller seller = dataSnapshot1.getValue(Seller.class);
                    seller.sellerID = dataSnapshot1.getKey();
                    res.add(seller);
                }


                RecyclerView recList = findViewById(R.id.cardList);
                recList.setHasFixedSize(true);
                recList.setClickable(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

                recList.setLayoutManager(linearLayoutManager);
                BuyerViewRestaurantDataAdapter buyerViewRestaurantDataAdapter = new BuyerViewRestaurantDataAdapter(res);
                recList.setAdapter(buyerViewRestaurantDataAdapter);
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
        getMenuInflater().inflate(R.menu.buyer_tracking_cart_search_app_bar, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_search) {
            Intent intent = new Intent(BuyerViewRestaurant.this, BuyerSearchFunction.class);
            startActivity(intent);
        }
        if (id == R.id.action_track) {
            Intent intent = new Intent(BuyerViewRestaurant.this, BuyerOngoingOrders.class);
            startActivity(intent);
        }

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_cart) {
            Intent intent = new Intent(BuyerViewRestaurant.this, Checkout.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent intent = new Intent(BuyerViewRestaurant.this, BuyerViewRestaurant.class);
            startActivity(intent);
            finish();        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(getApplicationContext(), BuyerHistory.class);
            startActivity(intent);
        } else if (id == R.id.nav_seller) {
            Intent intent = new Intent(BuyerViewRestaurant.this, CreateRestaurant.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(BuyerViewRestaurant.this, InitialActivity.class);
            finish();
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
