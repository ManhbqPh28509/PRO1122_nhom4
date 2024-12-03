package com.example.pro1122_nhm4.Model;

public class Category {
    private Integer category_id;
    private String name,img;
    public static final String TB_NAME = "Category";
    public static final String COL_Ma_Category = "category_id";
    public static final String COL_Name = "name";
    public static final String COL_IMG = "img";
    public Category() {
    }
    public Category(Integer category_id, String name, String img) {
        this.category_id = category_id;
        this.name = name;
        this.img = img;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
