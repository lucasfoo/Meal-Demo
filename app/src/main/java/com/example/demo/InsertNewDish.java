package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class InsertNewDish extends AppCompatActivity implements View.OnClickListener {
    private EditText dishName;
    private EditText dishPrice;
    private EditText dishDescription;
    private EditText dishPreparationDuration;
    private ImageView DPhoto;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_dish);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();


        dishName = (EditText) findViewById(R.id.enter_dish_name);
        dishPrice = (EditText) findViewById((R.id.enter_price));
        dishDescription = (EditText) findViewById(R.id.enter_description) ;
        dishPreparationDuration = (EditText) findViewById(R.id.enter_preparation_duration);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.Post).setOnClickListener(this);


    }
    private boolean validateInputs(String name, String price, String desc,String preparationDuration) {
        if (name.isEmpty()) {
            dishName.setError("Name required");
            dishName.requestFocus();
            return true;
        }

        if (price.isEmpty()) {
            dishPrice.setError("Price required");
            dishPrice.requestFocus();
            return true;
        }

        if (desc.isEmpty()) {
            dishDescription.setError("Description required");
            dishDescription.requestFocus();
            return true;
        }


        if (preparationDuration.isEmpty()) {
            dishPreparationDuration.setError("Preparation duration required");
            dishPreparationDuration.requestFocus();
            return true;
        }
        return false;
    }


    public void onClick(View v) {

        String name = dishName.getText().toString().trim();
        String price = dishPrice.getText().toString().trim();
        String desc = dishDescription.getText().toString().trim();
        String preparationDuration = dishPreparationDuration.getText().toString().trim();
//        ImageView photo = DPhoto;


        if (!validateInputs(name, price, desc,preparationDuration)) {



           FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
           String userID = user.getUid();
           DatabaseReference DishRef = mDatabase.child("sellers").child(userID).child("Dishes").push();
            Dish dish = new Dish(name,desc,price, DishRef.getKey());
           DishRef.setValue(dish);
           finish();
//            FSdish.add(dish)
//                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                        @Override
//                        public void onSuccess(DocumentReference documentReference) {
//                            Toast.makeText(InsertNewDish.this, "Dish Added", Toast.LENGTH_LONG).show();
//
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(InsertNewDish.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    });

        }

    }

}
