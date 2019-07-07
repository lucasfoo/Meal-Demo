package com.example.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BuyerProfileEditor extends AppCompatActivity {
    DatePicker picker;
    Button btnGet;
    TextView tvw;
    EditText FirstName;
    EditText LastName;
    EditText Password;
    Button Save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_profile_editor);


        FirstName = (EditText)findViewById(R.id.buyer_profile_editor_change_first_name);
        LastName = (EditText)findViewById(R.id.buyer_profile_editor_change_last_name);
        Password = (EditText)findViewById(R.id.buyer_profile_editor_change_password);
        Save = (Button)findViewById(R.id.buyer_profile_editor_save_button);

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
}
