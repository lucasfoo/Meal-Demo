package com.example.demo;

import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReviewDataAdapter extends RecyclerView.Adapter<ReviewDataAdapter.ReviewViewHolder>{
    private List<ReviewData> ReviewList;


    public ReviewDataAdapter(List<ReviewData> ReviewList){
        this.ReviewList = ReviewList;

    }

    @Override
    public int getItemCount(){
        return ReviewList.size();
    }

    @NonNull
    @Override
    public ReviewDataAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_card, viewGroup, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder reviewViewHolder, int i){
        final ReviewData review = ReviewList.get(i);
        ReviewViewHolder.mName.setText(review.username);
        ReviewViewHolder.mRestaurantRating.setRating(review.reviewScore);
        ReviewViewHolder.mReviewContent.setText("\"" + review.reviewContent + "\"");

    }

    public static class  ReviewViewHolder extends RecyclerView.ViewHolder{
        protected static CardView cardView;
        protected static TextView mName;
        protected static TextView mReviewContent;
        protected  static RatingBar mRestaurantRating;

        public ReviewViewHolder(View view){
            super(view);
            cardView = view.findViewById(R.id.review_page);
            mName = view.findViewById(R.id.review_name);
            mReviewContent = view.findViewById(R.id.review_content);
            mRestaurantRating = view.findViewById(R.id.review_ratingBar);
        }
    }

}