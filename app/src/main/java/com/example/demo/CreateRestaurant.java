package com.example.demo;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class CreateRestaurant extends AppCompatActivity {
    private DatabaseReference mDatabase;
    protected static RangeTimePickerDialog picker;
    TextView restaurantOpeningHour;
    TextView restaurantClosingHour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_restaurant);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        restaurantOpeningHour = findViewById(R.id.enter_opening_hour);
        restaurantClosingHour = findViewById(R.id.enter_closing_hour);


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




        Button create = (Button) findViewById(R.id.Create_restaurant);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText restaurantName = (EditText) findViewById(R.id.enter_restaurant_name);
                EditText restaurantAddress = findViewById(R.id.enter_address);
                EditText restaurantApt = findViewById(R.id.enter_apt);
                EditText restaurantPostcode= findViewById(R.id.enter_postcode);




                String Name = restaurantName.getText().toString();
                String Address = restaurantAddress.getText().toString();
                String Apt = restaurantApt.getText().toString();
                String Postcode = restaurantPostcode.getText().toString();
                String OpeningHour = restaurantOpeningHour.getText().toString();
                String ClosingHour = restaurantClosingHour.getText().toString();
                if(Name.isEmpty() || Address.isEmpty() || Apt.isEmpty() || Postcode.isEmpty() || OpeningHour.isEmpty() || ClosingHour.isEmpty()){
                    // do something here
                }else{
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String userID = user.getUid();
                    String name = restaurantName.getText().toString();
                    String email = user.getEmail();
                    Seller seller = new Seller(email, name, Address, Apt, Postcode, OpeningHour, ClosingHour);
                    mDatabase.child("sellers").child(userID).setValue(seller);
                    Intent intent = new Intent(CreateRestaurant.this, InitialActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
