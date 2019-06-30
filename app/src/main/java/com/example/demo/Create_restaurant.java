package com.example.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Create_restaurant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_restaurant);

        Button create = (Button) findViewById(R.id.Create_restaurant);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText restaurantName = (EditText) findViewById(R.id.enter_restaurant_name);
                EditText restaurantStreet = findViewById(R.id.enter_street);
                EditText restaurantBlk = findViewById(R.id.enter_blk);
                EditText restaurantPostcode= findViewById(R.id.enter_postcode);
                String Name = restaurantName.getText().toString();
                String Street = restaurantStreet.getText().toString();
                String Blk = restaurantBlk.getText().toString();
                String Postcode = restaurantPostcode.getText().toString();
                if(Name.isEmpty() || Street.isEmpty() || Blk.isEmpty() || Postcode.isEmpty()){
                    // do something here
                }else{
                    Intent intent = new Intent(Create_restaurant.this, SellerP1.class);
                    startActivity(intent);
                }
            }
        });

    }
}
