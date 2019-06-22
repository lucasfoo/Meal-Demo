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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Insert_new_dish extends AppCompatActivity implements View.OnClickListener {
    private EditText DN;
    private EditText DS;
    private EditText DP;
    private ImageView DPhoto;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference FS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_dish);

        FS = database.getReference("DISHES");
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();


        DN = (EditText) findViewById(R.id.enter_dish_name);
        DS = (EditText) findViewById((R.id.enter_price));
        DP = (EditText) findViewById(R.id.enter_description) ;
//        DPhoto = (ImageView) findViewById(R.id.enter_photo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.Post).setOnClickListener(this);


    }
    private boolean validateInputs(String name, String price, String desc) {
        if (name.isEmpty()) {
            DN.setError("Name required");
            DN.requestFocus();
            return true;
        }

        if (price.isEmpty()) {
            DP.setError("Price required");
            DP.requestFocus();
            return true;
        }

        if (desc.isEmpty()) {
            DS.setError("Description required");
            DS.requestFocus();
            return true;
        }
        return false;
    }


    public void onClick(View v) {

        String name = DN.getText().toString().trim();
        String price = DP.getText().toString().trim();
        String desc = DS.getText().toString().trim();
//        ImageView photo = DPhoto;


        if (!validateInputs(name, price, desc)) {



           Dish dish = new Dish(name,desc,price);
            DatabaseReference usersRef = FS;

            Map<Integer,Dish> dish_set = new HashMap<>();
            dish_set.put(1,dish);

            usersRef.setValue(dish_set);
            Intent intent = new Intent(Insert_new_dish.this, SellerP1.class);
            startActivity(intent);
//            FSdish.add(dish)
//                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                        @Override
//                        public void onSuccess(DocumentReference documentReference) {
//                            Toast.makeText(Insert_new_dish.this, "Dish Added", Toast.LENGTH_LONG).show();
//
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(Insert_new_dish.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    });

        }

    }

}
