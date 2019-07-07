package com.example.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                Toast.makeText(AddReview.this,
                        "Rating changed, current rating " + ratingBar.getRating(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
