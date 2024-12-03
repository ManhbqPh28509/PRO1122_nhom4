package com.example.pro1122_nhm4.Model;

import java.time.LocalDate;

public class RateDish {
    private int rate_dish_id;
    private int user_id;
    private int dish_id;
    private int rating;
    private String comment;
    private String date_rate;
    public static final String TB_NAME = "RateDish";
    public static final String COL_Ma_Rate_Dish = "rate_dish_id";
    public static final String COL_Ma_User = "user_id";
    public static final String COL_Ma_Dish = "dish_id";
    public static final String COL_Rate = "rating";
    public static final String COL_Comment = "comment";
    public static final String COL_Date = "date_rate";
    public RateDish() {
    }

    public RateDish(int rate_dish_id, int user_id, int dish_id, int rating, String comment, String date_rate) {
        this.rate_dish_id = rate_dish_id;
        this.user_id = user_id;
        this.dish_id = dish_id;
        this.rating = rating;
        this.comment = comment;
        this.date_rate = date_rate;
    }

    public int getRate_dish_id() {
        return rate_dish_id;
    }

    public void setRate_dish_id(int rate_dish_id) {
        this.rate_dish_id = rate_dish_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getDish_id() {
        return dish_id;
    }

    public void setDish_id(int dish_id) {
        this.dish_id = dish_id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate_rate() {
        return date_rate;
    }

    public void setDate_rate(String date_rate) {
        this.date_rate = date_rate;
    }
}
