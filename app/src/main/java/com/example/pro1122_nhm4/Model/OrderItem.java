package com.example.pro1122_nhm4.Model;

public class OrderItem {
    private int order_item_id;
    private int order_id;
    private int dish_id;
    private int price;
    private int quantity;

    public static final String TB_NAME = "OrderItem";
    public static final String COL_Ma_Order_Item = "order_item_id";
    public static final String COL_Ma_Order = "order_id";
    public static final String COL_Ma_Dish = "dish_id";
    public static final String COL_Price = "price";
    public static final String COL_Quantity = "quantity";

    public OrderItem() {
    }

    public OrderItem(int order_item_id, int order_id, int dish_id, int price, int quantity) {
        this.order_item_id = order_item_id;
        this.order_id = order_id;
        this.dish_id = dish_id;
        this.price = price;
        this.quantity = quantity;
    }


    public int getOrder_item_id() {
        return order_item_id;
    }

    public void setOrder_item_id(int order_item_id) {
        this.order_item_id = order_item_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getDish_id() {
        return dish_id;
    }

    public void setDish_id(int dish_id) {
        this.dish_id = dish_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
