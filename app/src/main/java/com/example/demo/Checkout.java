package com.example.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Checkout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        RecyclerView checkout_List = findViewById(R.id.checkout_view);
        checkout_List.setHasFixedSize(true);
        checkout_List.setClickable(true);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        checkout_List.setLayoutManager(linearLayoutManager);

        CheckoutDataAdapter editorDataAdapter = new CheckoutDataAdapter(createList(10));
        checkout_List.setAdapter(editorDataAdapter);


    }


    private List<CheckoutData> createList(int size) {
        List<CheckoutData> items = new ArrayList<>();
        for (int i = 1; i <= size; ++i) {
            CheckoutData data = new CheckoutData();
            data.cost = "5.00";
            data.name = "dish name "+ i;
            items.add(data);
        }
        return  items;
    }
}
