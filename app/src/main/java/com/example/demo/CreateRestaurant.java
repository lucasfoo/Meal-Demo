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

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

public class CreateRestaurant extends AppCompatActivity {
    private DatabaseReference mDatabase;
    protected static RangeTimePickerDialog picker;
    TextView restaurantOpeningHour;
    TextView restaurantClosingHour;
    ImageView restaurantPhoto;
    TextView imagePrompt;
    private Uri imageUri;
    String openingTime;
    String closingTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_restaurant);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        restaurantOpeningHour = findViewById(R.id.enter_opening_hour);
        restaurantClosingHour = findViewById(R.id.enter_closing_hour);
        restaurantPhoto = findViewById(R.id.create_restaurant_photo);
        imagePrompt = findViewById(R.id.ImagePrompt);


        restaurantPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (restaurantPhoto.getDrawable() != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateRestaurant.this);
                    builder.setTitle("Alert")
                            .setMessage("Do you want to delete the photo?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    restaurantPhoto.setImageDrawable(null);
                                    Toast.makeText(CreateRestaurant.this, "Photo Deleted", Toast.LENGTH_SHORT).show();
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
                            .start(CreateRestaurant.this);

                }
            }
        });



        restaurantOpeningHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                final int hour = cldr.get(Calendar.HOUR_OF_DAY);
                final int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog

                picker = new RangeTimePickerDialog(v.getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                openingTime = String.valueOf(sHour * 100 + sMinute);
                                String AM_PM;
                                if (sHour < 12) {
                                    AM_PM = "AM";
                                } else {
                                    AM_PM = "PM";
                                    sHour -= 12;
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
                final int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog

                picker = new RangeTimePickerDialog(v.getContext(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                closingTime = String.valueOf(sHour * 100 + sMinute);
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
                if(Name.isEmpty() || Address.isEmpty() || Apt.isEmpty() || Postcode.isEmpty() || openingTime.isEmpty() || closingTime.isEmpty() || imageUri == null){
                    Toast.makeText(CreateRestaurant.this, "Please enter all fields!", Toast.LENGTH_LONG).show();
                }else{
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String userID = user.getUid();
                    String name = restaurantName.getText().toString();
                    String email = user.getEmail();
                    Seller seller = new Seller(email, name, Address, Apt, Postcode, openingTime, closingTime, userID);
                    String imageRef = "restaurant_images/" + UUID.randomUUID().toString();
                    StorageReference imageStorageReference = FirebaseStorage.getInstance().getReference().child(imageRef);
                    imageStorageReference.putFile(imageUri);
                    seller.photoID = imageRef;
                    seller.total_score = 0;
                    seller.review_count = 0;
                    mDatabase.child("sellers").child(userID).setValue(seller);
                    Intent intent = new Intent(CreateRestaurant.this, InitialActivity.class);
                    startActivity(intent);
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
