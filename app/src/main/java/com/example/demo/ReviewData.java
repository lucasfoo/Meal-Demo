package com.example.demo;

import android.widget.RatingBar;

public class ReviewData {
    public String username;
    public String reviewContent;
    public Float reviewScore;

    public ReviewData(){
    }

    public  ReviewData(String username, String reviewContent, Float reviewScore){
        this.reviewContent = reviewContent;
        this.username = username;
        this.reviewScore= reviewScore;
    }

}
