package com.example.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class AddReview extends AppCompatActivity {
    protected RatingBar ratingBar;
    protected EditText ReviewRestaurant;
    protected EditText ReviewChef;
    protected Button Submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        ratingBar = (RatingBar) findViewById(R.id.review_rating);
        ReviewRestaurant = (EditText) findViewById(R.id.enter_review_for_restaurant);
        ReviewChef = (EditText) findViewById(R.id.enter_review_for_chef);
        Submit = (Button) findViewById(R.id.submit_review);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        ratingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rating = "Rating is :" + ratingBar.getRating();
                Toast.makeText(AddReview.this, rating, Toast.LENGTH_LONG).show();
            }
        });
    }
}
