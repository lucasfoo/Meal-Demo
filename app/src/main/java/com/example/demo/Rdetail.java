package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Rdetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdetail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Bundle extras = getIntent().getExtras();
        String rname = extras.getString("rname");
        String raddress = extras.getString("raddress");
        TextView mName = findViewById(R.id.Restaurant_Name);
        TextView mAddress = findViewById(R.id.Restaurant_Address);
        mName.setText(rname);
        mAddress.setText(raddress);

        View one_item = (View) findViewById(R.id.ItemRecyclerView);
        one_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Rdetail.this, Buyer_dish_detail.class);
                startActivity(intent);
            }
        });

        FloatingActionButton edit_menu = (FloatingActionButton) findViewById(R.id.cart2);
        edit_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Rdetail.this, Cart.class);
                startActivity(intent);
            }
        });



        //RecyclerVIew Code below
        RecyclerView ItemList = findViewById(R.id.ItemRecyclerView);
        ItemList.setHasFixedSize(true);
        ItemList.setClickable(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ItemList.setLayoutManager(linearLayoutManager);
        ItemListAdapter itemListAdapter = new ItemListAdapter(createList(5));
        ItemList.setAdapter(itemListAdapter);




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private List<ItemData> createList(int size) {
        List<ItemData> items = new ArrayList<>();
        for (int i = 1; i <= size; ++i) {
            ItemData itemData = new ItemData();
            itemData.dollars = "5";
            itemData.cents = "00";
            itemData.ItemName = "ITEM " + i;
            items.add(itemData);
        }
        return  items;
    }


}
