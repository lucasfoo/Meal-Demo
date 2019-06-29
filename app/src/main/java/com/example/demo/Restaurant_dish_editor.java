package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Restaurant_dish_editor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_dish_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.new_dish);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Restaurant_dish_editor.this, Insert_new_dish.class);
                startActivity(intent);
            }
        });



        RecyclerView editor_List = findViewById(R.id.restaurant_editor_list);
        editor_List.setHasFixedSize(true);
        editor_List.setClickable(true);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        editor_List.setLayoutManager(linearLayoutManager);

        RestaurantEditorDataAdapter editorDataAdapter = new RestaurantEditorDataAdapter(createList(10));
        editor_List.setAdapter(editorDataAdapter);

    }

    private List<RestaurantEditorData> createList(int size) {
        List<RestaurantEditorData> items = new ArrayList<>();
        for (int i = 1; i <= size; ++i) {
            RestaurantEditorData data = new RestaurantEditorData();
            data.cost = "5";
            data.dish_name = "dish name "+ i;
            items.add(data);
        }
        return  items;

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

}
