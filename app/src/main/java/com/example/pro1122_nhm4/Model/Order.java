package com.example.pro1122_nhm4.Model;

import java.time.LocalDate;

public class Order {
    private  int order_id;
    private int user_id;
    private LocalDate order_date;
    private String status;
    private int total_amount;
    public static final String TB_NAME = "Order";
    public static final String COL_Ma_Order = "order_id";
    public static final String COL_Ma_User = "user_id";
    public static final String COL_Order_Date = "order_date";
    public static final String COL_Status = "status";
    public static final String COL_Total_Amount = "total_amount";

    public Order() {
    }

    public Order(int order_id, int user_id, LocalDate order_date, String status, int total_amount) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.order_date = order_date;
        this.status = status;
        this.total_amount = total_amount;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public LocalDate getOrder_date() {
        return order_date;
    }

    public void setOrder_date(LocalDate order_date) {
        this.order_date = order_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }
}
