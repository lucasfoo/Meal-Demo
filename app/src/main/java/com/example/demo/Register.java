package com.example.demo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Register extends AppCompatActivity {
    private Button button_back;
    private Button button_create;
    private FirebaseAuth mAuth;
    private ToggleButton accType;
    private boolean is_buyer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        button_back  = (Button) findViewById(R.id.back_to_login_page);
        accType = (ToggleButton) findViewById(R.id.accType);
        is_buyer = true;
        button_create = findViewById(R.id.btn_signup);

        accType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    is_buyer = false;
                } else {
                    is_buyer = true;
                }
            }
        });

        button_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.input_name);
                EditText editText1= findViewById(R.id.input_email);
                EditText editText2 = findViewById(R.id.input_password);
                final String name = editText.getText().toString();
                String email = editText1.getText().toString();
                String password = editText2.getText().toString();
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                           if(user != null){
                               if(is_buyer == true) {
                                   user.getUid() = "buyer";
                               } else{

                               }
                           }
                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name).build();
                            user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this, "Account created",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            Intent intent = new Intent(Register.this, MainMenu.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
