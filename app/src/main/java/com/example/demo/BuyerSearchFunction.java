package com.example.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BuyerSearchFunction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_search_function);
        Button Search = (Button) findViewById(R.id.search_button);
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View) {
            }
        });
    }
}
