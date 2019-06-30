package com.example.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Seller_history extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_history);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        RecyclerView recList = findViewById(R.id.seller_history_view);
        recList.setHasFixedSize(true);
        recList.setClickable(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recList.setLayoutManager(linearLayoutManager);
        SellerHistoryDataAdapter sellerHistoryDataAdapter = new SellerHistoryDataAdapter(createList(10));
        recList.setAdapter(sellerHistoryDataAdapter);
    }


    private List<SellerHistoryData> createList(int size) {
        List<SellerHistoryData> items = new ArrayList<>();
        for (int i = 1; i <= size; ++i) {
            SellerHistoryData data = new SellerHistoryData();
            data.Date = i+""+i+i+i+i+i+i+i+i;
            data.CollectorName = "shaopeng "+ i;
            data.DishName = "dish name "+i;
            data.price = "5."+ i;
            items.add(data);
        }
        return  items;
    }
}
