package com.example.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SellerRestaurantProfileEditor extends AppCompatActivity {
    EditText Address;
    EditText Apt;
    EditText PostCode;
    EditText rName;
    EditText OpeningHour;
    EditText ClosingHour;
    Button Save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_restaurant_profile_editor);
        rName = (EditText) findViewById(R.id.seller_restaurant_profile_enter_restaurant_name);
        Address = (EditText) findViewById(R.id.seller_restaurant_profile_enter_address);
        Apt = (EditText) findViewById(R.id.seller_restaurant_profile_enter_apt);
        PostCode = (EditText) findViewById(R.id.seller_restaurant_profile_enter_postcode);
        OpeningHour = (EditText) findViewById(R.id.seller_restaurant_profile_enter_opening_hour);
        ClosingHour = (EditText) findViewById(R.id.seller_restaurant_profile_enter_closing_hour);
        Save = (Button) findViewById((R.id.seller_restaurant_profile_save));
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}
