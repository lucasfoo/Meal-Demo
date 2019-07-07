package com.example.demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SellerOrderDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_order_detail);









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
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        orderDetail_List.setLayoutManager(linearLayoutManager);

        SellerOrderDetailDataAdapter OrderDetailDataAdapter = new SellerOrderDetailDataAdapter(createList(10));
        orderDetail_List.setAdapter(OrderDetailDataAdapter);


    }

    private List<SellerOrderDetailData> createList(int size) {
        List<SellerOrderDetailData> items = new ArrayList<>();
        for (int i = 1; i <= size; ++i) {
            SellerOrderDetailData data = new SellerOrderDetailData();
            data.orderTime = "5pm";
            data.buyerID = "shaopeng "+ i;
            items.add(data);
        }
        return  items;
    }


}
