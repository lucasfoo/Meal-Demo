package com.example.demo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SellerRestaurantProfileEditor extends AppCompatActivity {
    EditText Address;
    EditText Apt;
    EditText PostCode;
    EditText rName;
    EditText OpeningHour;
    EditText ClosingHour;
    Button Save;
    Seller seller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_restaurant);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference sellerRef = FirebaseDatabase.getInstance().getReference("sellers").child(user.getUid());
        rName = (EditText) findViewById(R.id.enter_restaurant_name);
        Address = (EditText) findViewById(R.id.enter_address);
        Apt = (EditText) findViewById(R.id.enter_apt);
        PostCode = (EditText) findViewById(R.id.enter_postcode);
        OpeningHour = (EditText) findViewById(R.id.enter_opening_hour);
        ClosingHour = (EditText) findViewById(R.id.enter_closing_hour);
        sellerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                seller = dataSnapshot.getValue(Seller.class);
                rName.setText(seller.name);
                Address.setText(seller.address);
                Apt.setText(seller.apt);
                PostCode.setText(seller.postalCode);
                OpeningHour.setText(seller.openingTime);
                OpeningHour.setText(seller.closingTime);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Save = (Button) findViewById((R.id.Create_restaurant));
        Save.setText("Save");
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}
