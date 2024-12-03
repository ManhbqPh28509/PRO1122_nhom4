package com.example.pro1122_nhm4.Model;

public class Cart {
    private Integer cart_id, user_id, dish_id, quantity,sum;
    public static final String TB_NAME = "Cart";
    public static final String COL_Ma_Cart = "cart_id";
    public static final String COL_Ma_User_Cart = "user_id";
    public static final String COL_Ma_Dish_Cart = "dish_id";
    public static final String COL_Quantity = "quantity";
    public static final String COL_Sum = "sum";
    public Cart() {
    }

    public Cart(Integer cart_id, Integer user_id, Integer dish_id, Integer quantity, Integer sum) {
        this.cart_id = cart_id;
        this.user_id = user_id;
        this.dish_id = dish_id;
        this.quantity = quantity;
        this.sum = sum;
    }

    public Integer getCart_id() {
        return cart_id;
    }

    public void setCart_id(Integer cart_id) {
        this.cart_id = cart_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getDish_id() {
        return dish_id;
    }

    public void setDish_id(Integer dish_id) {
        this.dish_id = dish_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }


}
