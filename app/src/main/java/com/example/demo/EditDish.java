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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditDish extends AppCompatActivity {
    private ImageView imageCapture1;
    private ImageView imageCapture2;
    private ImageView imageCapture3;
    private Button upload;
    String imgDecodableString;
    FloatingActionButton takePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dish);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button Save = (Button) findViewById(R.id.Save);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        takePhoto = findViewById(R.id.edit_take_photo);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(cameraIntent,0);
            }
        });

        imageCapture1 = (ImageView) findViewById(R.id.edit_photo1);
        imageCapture2 = (ImageView) findViewById(R.id.edit_photo2);
        imageCapture3 = (ImageView) findViewById(R.id.edit_photo3);
        imageCapture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageCapture1.getDrawable() != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditDish.this);
                    builder.setTitle("Alert")
                            .setMessage("Do you want to delete the photo?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    imageCapture1.setImageDrawable(null);
                                    Toast.makeText(EditDish.this, "Photo Deleted", Toast.LENGTH_SHORT).show();
                                    Reorder(imageCapture1,imageCapture2,imageCapture3);
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

                }
            }
        });

        imageCapture2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageCapture2.getDrawable() != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditDish.this);
                    builder.setTitle("Alert")
                            .setMessage("Do you want to delete the photo?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    imageCapture2.setImageDrawable(null);
                                    Toast.makeText(EditDish.this, "Photo Deleted", Toast.LENGTH_SHORT).show();
                                    Reorder(imageCapture1,imageCapture2,imageCapture3);
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

                }
            }
        });

        imageCapture3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageCapture3.getDrawable() != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditDish.this);
                    builder.setTitle("Alert")
                            .setMessage("Do you want to delete the photo?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    imageCapture3.setImageDrawable(null);
                                    Toast.makeText(EditDish.this, "Photo Deleted", Toast.LENGTH_SHORT).show();
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

                }
            }
        });

        upload = findViewById(R.id.edit_chooseFile);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 0:
                if (requestCode == 0) {
                    if (resultCode == RESULT_OK) {
                        Bitmap bp = (Bitmap) data.getExtras().get("data");
                        if(imageCapture1.getDrawable() == null){
                            imageCapture1.setImageBitmap(bp);
                        }else if(imageCapture2.getDrawable() == null) {
                            imageCapture2.setImageBitmap(bp);
                        } else if(imageCapture3.getDrawable() == null){
                            imageCapture3.setImageBitmap(bp);
                        }else{
                            Toast.makeText(this, "Only 3 photo are allowed", Toast.LENGTH_LONG).show();
                        }
                    } else if (resultCode == RESULT_CANCELED) {
                        Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case 1:
                try {
                    // When an Image is picked
                    if (requestCode == 1 && resultCode == RESULT_OK
                            && data != null) {


                        Uri selectedImage = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage);
//

                        if(imageCapture1.getDrawable() == null){
                            imageCapture1.setImageBitmap(bitmap);
                            imageCapture1.setVisibility(View.VISIBLE);

                        }else if(imageCapture2.getDrawable() == null) {

                            imageCapture2.setImageBitmap(bitmap);
                            imageCapture2.setVisibility(View.VISIBLE);
                        }else if(imageCapture3.getDrawable() == null){

                            imageCapture3.setImageBitmap(bitmap);
                            imageCapture3.setVisibility(View.VISIBLE);
                        }else {
                            Toast.makeText(this, "At most 3 pictures",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(this, "You haven't picked Image",
                                Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                            .show();
                }
        }

    }


    private void Reorder(ImageView a, ImageView b, ImageView c){
        if(a.getDrawable() == null){
            if(b.getDrawable()!=null){
                a.setImageDrawable(b.getDrawable());
                if(c.getDrawable() != null){
                    b.setImageDrawable(c.getDrawable());
                    c.setImageDrawable(null);
                }else{
                    b.setImageDrawable(null);
                }
            }
        }else if(b.getDrawable() == null){
            if(c.getDrawable() != null){
                b.setImageDrawable(c.getDrawable());
                c.setImageDrawable(null);
            }
        }
    }

}
