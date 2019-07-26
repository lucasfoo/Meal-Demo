package com.example.demo;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null) {
            Intent intent = new Intent(Login.this, BuyerViewRestaurant.class);
            startActivity(intent);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton SignIn = findViewById(R.id.SignIn);


        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               EditText emailEditText = (EditText) findViewById(R.id.ev_user_id);
               EditText passwordEditText = findViewById(R.id.ev_password);
               String email = emailEditText.getText().toString();
               String password = passwordEditText.getText().toString();
               if(!email.matches("") && ! password.matches("")) {
                   mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {
                               Intent intent = new Intent(Login.this, InitialActivity.class);
                               finish();
                               startActivity(intent);
                           } else {
                               Toast.makeText(Login.this, "Authentication failed.",
                                       Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
               }else{
                   Toast.makeText(Login.this, "Invalid Email or Password",
                           Toast.LENGTH_SHORT).show();
               }
            }
        });

        //SIGN UP BUTTON
        register = (Button) findViewById(R.id.Register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }
}
