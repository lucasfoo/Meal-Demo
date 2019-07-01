package com.example.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Seller_Order_Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller__order__detail);
        Bundle bundle = getIntent().getExtras();
        String orderID = bundle.getString("orderID");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("sellers").child(user.getUid()).child("orders").child(orderID);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        RecyclerView orderDetail_List = findViewById(R.id.seller_order_detail_view);
        orderDetail_List.setHasFixedSize(true);
        orderDetail_List.setClickable(true);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        orderDetail_List.setLayoutManager(linearLayoutManager);

        SellerOrderDataAdapter OrderDetailDataAdapter = new SellerOrderDataAdapter(createList(10));
        orderDetail_List.setAdapter(OrderDetailDataAdapter);


    }

    private List<SellerOrderData> createList(int size) {
        List<SellerOrderData> items = new ArrayList<>();
        for (int i = 1; i <= size; ++i) {
            SellerOrderData data = new SellerOrderData();
            data.orderTime = "5pm";
            data.buyerID = "shaopeng "+ i;
            items.add(data);
        }
        return  items;
    }


}
