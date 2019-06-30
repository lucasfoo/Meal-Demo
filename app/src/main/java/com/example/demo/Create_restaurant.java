package com.example.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Create_restaurant extends AppCompatActivity {
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_restaurant);
        mDatabase = FirebaseDatabase.getInstance().getReference();

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
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String userID = user.getUid();
                    String name = restaurantName.getText().toString();
                    String email = user.getEmail();
                    String address = restaurantAddress.getText().toString();
                    Seller seller = new Seller(email, name, address);
                    mDatabase.child("sellers").child(userID).setValue(seller);
                    Intent intent = new Intent(Create_restaurant.this, InitialActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
