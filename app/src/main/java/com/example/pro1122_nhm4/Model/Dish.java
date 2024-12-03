package com.example.pro1122_nhm4.Model;

public class Dish {
    private int dish_id;
    private String name;
    private int price;
    private String description;
    private String availability;
    private int category_id;
    private String img;
    public static final String TB_NAME = "Dish";
    public static final String COL_Ma_Dish= "dish_id";
    public static final String COL_Name = "name";
    public static final String COL_Price = "price";
    public static final String COL_Description = "description";
    public static final String COL_Availability = "availability";
    public static final String COL_Category_ID = "category_id";
    public static final String COL_IMG = "img";
    public Dish() {
    }

    public Dish(int dish_id, String name, int price, String decsription, String availability, int category_id, String img) {
        this.dish_id = dish_id;
        this.name = name;
        this.price = price;
        this.description = decsription;
        this.availability = availability;
        this.category_id = category_id;
        this.img = img;
    }

    public int getDish_id() {
        return dish_id;
    }

    public void setDish_id(int dish_id) {
        this.dish_id = dish_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
