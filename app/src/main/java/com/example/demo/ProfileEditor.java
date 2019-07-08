package com.example.demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class ProfileEditor extends AppCompatActivity {
    DatePicker picker;
    Button btnGet;
    TextView tvw;
    EditText FirstName;
    EditText LastName;
    EditText Password;
    Button Save;
    ImageView DP;
    TextView imagePrompt;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_editor);


        FirstName = (EditText)findViewById(R.id.buyer_profile_editor_change_first_name);
        LastName = (EditText)findViewById(R.id.buyer_profile_editor_change_last_name);
        Password = (EditText)findViewById(R.id.buyer_profile_editor_change_password);
        Save = (Button)findViewById(R.id.buyer_profile_editor_save_button);
        DP = (ImageView) findViewById(R.id.profile_image);
        imagePrompt = (TextView) findViewById(R.id.profile_ImagePrompt);


        DP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DP.getDrawable() != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProfileEditor.this);
                    builder.setTitle("Alert")
                            .setMessage("Do you want to delete the photo?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DP.setImageDrawable(null);
                                    Toast.makeText(ProfileEditor.this, "Photo Deleted", Toast.LENGTH_SHORT).show();
                                    imagePrompt.setText("Tap to delete");

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
                            .start(ProfileEditor.this);

                }
            }
        });







        picker=(DatePicker)findViewById(R.id.buyer_profile_editor_choose_date);
        btnGet=(Button)findViewById(R.id.buyer_profile_get_date);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvw.setText("Birthday: "+ picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear());
            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }

    @Override
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
                        DP.setImageBitmap(thePic);
                        imagePrompt.setText("Tap to delete");
                    }
                }catch (Exception e) {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                            .show();
                }

        }

    }
}
