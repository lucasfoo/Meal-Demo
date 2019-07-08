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
    private ImageView Logo;
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

        Logo = findViewById(R.id.app_logo);


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
