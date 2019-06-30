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
                EditText restaurantAddress = findViewById(R.id.enter_restaurant_address);
                String Name = restaurantName.getText().toString();
                String Address = restaurantAddress.getText().toString();
                if(Name.isEmpty() || Address.isEmpty()){

                }else{
                    Intent intent = new Intent(Create_restaurant.this, SellerP1.class);
                    startActivity(intent);
                }
            }
        });

    }
}
