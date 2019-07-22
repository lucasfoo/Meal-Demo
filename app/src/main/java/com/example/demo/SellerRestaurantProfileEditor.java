package com.example.demo;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

public class SellerRestaurantProfileEditor extends AppCompatActivity {
    EditText rAddress;
    EditText rApt;
    EditText rPostCode;
    EditText rName;

    Button Save;
    Seller seller;
    ImageView restaurantPhoto;
    TextView imagePrompt;
    private Uri imageUri;

    String openingTime;
    String closingTime;


    protected static RangeTimePickerDialog picker;
    TextView restaurantOpeningHour;
    TextView restaurantClosingHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_restaurant);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference sellerRef = FirebaseDatabase.getInstance().getReference("sellers").child(user.getUid());
        rName = (EditText) findViewById(R.id.enter_restaurant_name);
        rAddress = (EditText) findViewById(R.id.enter_address);
        rApt = (EditText) findViewById(R.id.enter_apt);
        rPostCode = (EditText) findViewById(R.id.enter_postcode);
        restaurantOpeningHour = findViewById(R.id.enter_opening_hour);
        restaurantClosingHour =  findViewById(R.id.enter_closing_hour);
        restaurantPhoto = findViewById(R.id.create_restaurant_photo);
        imagePrompt = findViewById(R.id.ImagePrompt);


        restaurantPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (restaurantPhoto.getDrawable() != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SellerRestaurantProfileEditor.this);
                    builder.setTitle("Alert")
                            .setMessage("Do you want to delete the photo?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    restaurantPhoto.setImageDrawable(null);
                                    Toast.makeText(SellerRestaurantProfileEditor.this, "Photo Deleted", Toast.LENGTH_SHORT).show();
                                    restaurantPhoto.setBackgroundResource(R.drawable.present_to_all);
                                    imagePrompt.setText("Tap to add photo");

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //meant to be empty
                                }
                            });
                    //Creating dialog box
                    AlertDialog dialog = builder.create();
                    dialog.show();


                }else{
                    CropImage.activity()
                            .setFixAspectRatio(true)
                            .setAspectRatio(1,1)
                            .setMinCropResultSize(128,128)
                            .setInitialCropWindowPaddingRatio(0)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setCropMenuCropButtonTitle("Submit")
                            .start(SellerRestaurantProfileEditor.this);

                }
            }
        });




        restaurantOpeningHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog

                picker = new RangeTimePickerDialog(v.getContext(), R.style.Theme_AppCompat_Light_Dialog_Alert,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                String AM_PM;
                                if (sHour < 12) {
                                    AM_PM = "AM";
                                } else {
                                    AM_PM = "PM";
                                }
                                DecimalFormat formatter = new DecimalFormat("00");
                                String formattedMinutes = formatter.format(sMinute);
                                restaurantOpeningHour.setText(sHour + ":" + formattedMinutes + ' ' + AM_PM);
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
                final int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog

                picker = new RangeTimePickerDialog(v.getContext(), R.style.Theme_AppCompat_Light_Dialog_Alert,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                String AM_PM;
                                if (sHour < 12) {
                                    AM_PM = "AM";
                                } else {
                                    AM_PM = "PM";
                                    sHour -= 12;
                                }
                                DecimalFormat formatter = new DecimalFormat("00");
                                String formattedMinutes = formatter.format(sMinute);
                                restaurantClosingHour.setText(sHour + ":" + formattedMinutes + ' ' + AM_PM);
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
                openingTime = seller.openingTime;
                closingTime = seller.closingTime;
                rName.setText(seller.name);
                rAddress.setText(seller.address);
                rApt.setText(seller.apt);
                rPostCode.setText(seller.postalCode);
                int openingMinutes = Integer.parseInt(openingTime) % 100;
                int openingHour = Integer.parseInt(openingTime) / 100;
                String openingAM_PM;
                if (openingHour < 12) {
                    openingAM_PM = "AM";
                } else {
                    openingAM_PM = "PM";
                    openingHour -= 12;
                }
                DecimalFormat formatter = new DecimalFormat("00");
                String formattedOpeningMinutes = formatter.format(openingMinutes);
                int closingMinutes = Integer.parseInt(closingTime) % 100;
                int closingHour = Integer.parseInt(closingTime) / 100;
                String closingAM_PM;
                if (openingHour < 12) {
                    closingAM_PM = "AM";
                } else {
                    closingAM_PM = "PM";
                    closingHour -= 12;
                }
                String formattedClosingMinutes = formatter.format(closingMinutes);
                restaurantOpeningHour.setText(String.valueOf(openingHour) + ":" + formattedOpeningMinutes + ' ' + openingAM_PM);
                restaurantClosingHour.setText(String.valueOf(closingHour) + ":" + formattedClosingMinutes + ' ' + closingAM_PM);
                StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("restaurant_images/" + seller.sellerID);
                GlideApp.with(getApplicationContext() /* context */)
                        .load(storageRef)
                        .into(restaurantPhoto);
                restaurantPhoto.setBackgroundResource(0);
                imagePrompt.setText("Tap to change");
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
                String Name = rName.getText().toString();
                String Address = rAddress.getText().toString();
                String Apt = rApt.getText().toString();
                String Postcode = rPostCode.getText().toString();
                String OpeningHour = restaurantOpeningHour.getText().toString();
                String ClosingHour = restaurantClosingHour.getText().toString();
                if(Name.isEmpty() || Address.isEmpty() || Apt.isEmpty() || Postcode.isEmpty() || OpeningHour.isEmpty() || ClosingHour.isEmpty()){
                    // do something here
                    Toast.makeText(SellerRestaurantProfileEditor.this, "Please enter all fields!", Toast.LENGTH_LONG).show();
                }else{
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    if(imageUri != null) {
                        String imageRef = "restaurant_images/" + seller.sellerID;
                        StorageReference imageStorageReference = FirebaseStorage.getInstance().getReference(imageRef);
                        imageStorageReference.delete();
                        imageStorageReference.putFile(imageUri);
                    }
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    seller.name = rName.getText().toString();
                    seller.address = rAddress.getText().toString();
                    seller.apt = rApt.getText().toString();
                    seller.postalCode = rApt.getText().toString();
                    seller.openingTime = openingTime;
                    seller.closingTime = closingTime;
                    mDatabase.child("sellers").child(user.getUid()).setValue(seller);
                    finish();
                }
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                try {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (resultCode == RESULT_OK) {
                        imageUri = result.getUri();

                        // get the cropped bitmap
                        Bitmap thePic = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        restaurantPhoto.setImageBitmap(thePic);
                        restaurantPhoto.setBackgroundResource(0);
                        imagePrompt.setText("Tap to change");
                    }
                }catch (Exception e) {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                            .show();
                }

        }

    }
}
