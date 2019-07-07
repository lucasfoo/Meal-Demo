package com.example.demo;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class SellerRestaurantProfileEditor extends AppCompatActivity {
    EditText Address;
    EditText Apt;
    EditText PostCode;
    EditText rName;

    Button Save;
    Seller seller;


    protected static RangeTimePickerDialog picker;
    TextView restaurantOpeningHour;
    TextView restaurantClosingHour;

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
        restaurantOpeningHour = findViewById(R.id.enter_opening_hour);
        restaurantClosingHour =  findViewById(R.id.enter_closing_hour);


        restaurantOpeningHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog

                picker = new RangeTimePickerDialog(v.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                String AM_PM;
                                if (sHour < 12) {
                                    AM_PM = "AM";
                                } else {
                                    AM_PM = "PM";
                                }
                                restaurantOpeningHour.setText(sHour + ":" + sMinute + ' ' + AM_PM);
                                restaurantOpeningHour.setTextColor(Color.argb(255, 0, 0, 0));
                            }

                        }, hour, minutes, false);

                picker.show();
            }
        });


        restaurantClosingHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog

                picker = new RangeTimePickerDialog(v.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                String AM_PM;
                                if (sHour < 12) {
                                    AM_PM = "AM";
                                } else {
                                    AM_PM = "PM";
                                }
                                restaurantClosingHour.setText(sHour + ":" + sMinute + ' ' + AM_PM);
                                restaurantClosingHour.setTextColor(Color.argb(255, 0, 0, 0));
                            }

                        }, hour, minutes, false);

                picker.show();
            }
        });







        sellerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                seller = dataSnapshot.getValue(Seller.class);
                rName.setText(seller.name);
                Address.setText(seller.address);
                Apt.setText(seller.apt);
                PostCode.setText(seller.postalCode);
                restaurantOpeningHour.setText(seller.openingTime);
                restaurantClosingHour.setText(seller.closingTime);

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
