package com.example.demo;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class AddReview extends AppCompatActivity {
    protected RatingBar ratingBar;
    protected EditText ReviewRestaurant;
    protected EditText ReviewChef;
    protected Button Submit;
    protected Seller seller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        ratingBar = (RatingBar) findViewById(R.id.review_rating);
        ReviewRestaurant = (EditText) findViewById(R.id.enter_review_for_restaurant);
        Submit = (Button) findViewById(R.id.submit_review);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String reviewerName = user.getDisplayName();
                String reviewText = ReviewRestaurant.getText().toString();
                final Float rating = ratingBar.getRating();
                Bundle extras = getIntent().getExtras();
                String sellerID = extras.getString("sellerID");
                final DatabaseReference sellerRef = FirebaseDatabase.getInstance().getReference("sellers/").child(sellerID);
                ReviewData review = new ReviewData(reviewerName, reviewText, rating);
                sellerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        seller = dataSnapshot.getValue(Seller.class);
                        if(dataSnapshot.hasChild("review_count")){
                            seller.review_count += 1;
                            seller.total_score += rating;
                            finish();
                        }else {
                            seller.review_count = 1;
                            seller.total_score = rating;
                            finish();
                        }
                        sellerRef.child("total_score").setValue(seller.total_score);
                        sellerRef.child("review_count").setValue(seller.review_count);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                DatabaseReference reviewRef = sellerRef.child("reviews").child(user.getUid());
                reviewRef.setValue(review);
                finish();
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
