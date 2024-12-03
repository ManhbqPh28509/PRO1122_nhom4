package com.example.pro1122_nhm4.Model;

public class RateOrder {
    private int rate_order_id;
    private int user_id;
    private int order_id;
    private int rating;
    private String comment;
    private String date_rate;
    public static final String TB_NAME = "RateOrder";
    public static final String COL_Ma_Rate_Order = "rate_order_id";
    public static final String COL_Ma_User = "user_id";
    public static final String COL_Ma_Order = "order_id";
    public static final String COL_Rate = "rating";
    public static final String COL_Comment = "comment";
    public static final String COL_Date = "date_rate";
    public RateOrder() {
    }

    public RateOrder(int rate_order_id, int user_id, int order_id, int rating, String comment, String date_rate) {
        this.rate_order_id = rate_order_id;
        this.user_id = user_id;
        this.order_id = order_id;
        this.rating = rating;
        this.comment = comment;
        this.date_rate = date_rate;
    }

    public int getRate_order_id() {
        return rate_order_id;
    }

    public void setRate_order_id(int rate_order_id) {
        this.rate_order_id = rate_order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
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
