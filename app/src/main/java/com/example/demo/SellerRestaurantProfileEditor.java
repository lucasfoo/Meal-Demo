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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Calendar;

public class SellerRestaurantProfileEditor extends AppCompatActivity {
    EditText Address;
    EditText Apt;
    EditText PostCode;
    EditText rName;

    Button Save;
    Seller seller;
    ImageView restaurantPhoto;
    TextView imagePrompt;
    private Uri imageUri;


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
                        imagePrompt.setText("Tap to delete");

                    }
                }catch (Exception e) {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                            .show();
                }

        }

    }
}
