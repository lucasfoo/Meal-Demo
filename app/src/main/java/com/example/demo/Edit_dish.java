package com.example.demo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Edit_dish extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dish);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        Bundle extras = getIntent().getExtras();
//        String dish_name = extras.getString("dish_name");
//        String price = extras.getString("price");
//        String description = extras.getString("description");
//        TextView mDish = findViewById(R.id.edit_dish_name);
//        TextView mPrice = findViewById(R.id.edit_price);
//        TextView mDesc = findViewById(R.id.edit_description);

    }

}
