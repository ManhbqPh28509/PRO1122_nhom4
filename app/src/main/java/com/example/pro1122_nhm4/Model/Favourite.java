package com.example.pro1122_nhm4.Model;

public class Favourite {
    private int favourite_id;
    private int user_id;
    private int dish_id;
    public static final String TB_NAME = "Favourite";
    public static final String COL_Favourite_ID = "favourite_id";
    public static final String COL_User_ID = "user_id";
    public static final String COL_Dish_ID = "dish_id";
    public Favourite() {
    }
    public Favourite(int favourite_id, int user_id, int dish_id) {
        this.favourite_id = favourite_id;
        this.user_id = user_id;
        this.dish_id = dish_id;
    }

    public int getFavourite_id() {
        return favourite_id;
    }

    public void setFavourite_id(int favourite_id) {
        this.favourite_id = favourite_id;
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
}
