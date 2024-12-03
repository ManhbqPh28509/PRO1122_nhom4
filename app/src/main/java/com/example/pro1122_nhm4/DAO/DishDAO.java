package com.example.pro1122_nhm4.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.example.pro1122_nhm4.DbHelper.DbHelper;
import com.example.pro1122_nhm4.Model.Dish;
import com.example.pro1122_nhm4.Model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DishDAO {
    SQLiteDatabase db;
    DbHelper dbHelper;
    public  DishDAO(Context context){
        dbHelper = new DbHelper(context);
    }
    public void open(){
        db = dbHelper.getWritableDatabase();
    }
    public void close(){
        dbHelper.close();
    }
    public Dish getDishByID(int dish_id){
        String query = "SELECT * FROM Dish WHERE dish_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{dish_id + ""});

        if (cursor.moveToNext()) {
                Dish dish = new Dish();
                dish.setDish_id(cursor.getInt(0));
                dish.setName(cursor.getString(1));
                dish.setPrice(cursor.getInt(2));
                dish.setDescription(cursor.getString(3));
                dish.setAvailability(cursor.getString(4));
                dish.setCategory_id(cursor.getInt(5));
                dish.setImg(cursor.getString(6));

            return dish;
        }else {
            return null;
        }

    }
    public long addDish(Dish dish) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", dish.getName());
        contentValues.put("price", dish.getPrice());
        contentValues.put("description", dish.getDescription());
        contentValues.put("availability", dish.getAvailability());
        contentValues.put("category_id", dish.getCategory_id());
        contentValues.put("img", dish.getImg());
        long res = db.insert(Dish.TB_NAME, null, contentValues);
        return res;
    }
    public long updateDish(Dish dish) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", dish.getName());
        contentValues.put("price", dish.getPrice());
        contentValues.put("description", dish.getDescription());
        contentValues.put("availability", dish.getAvailability());
        contentValues.put("category_id", dish.getCategory_id());
        contentValues.put("img", dish.getImg());
        long res = db.update(Dish.TB_NAME, contentValues, "dish_id = ?", new String[]{dish.getDish_id() + ""});
        return res;
    }
    public long deleteDish(Dish dish) {
        String query = "DELETE FROM Dish WHERE dish_id = ?";
        return db.delete(Dish.TB_NAME, "dish_id = ?", new String[]{dish.getDish_id() + ""});
    }
    public List<Dish> getDishesByCategotyID(int category_id) {
        String query = "SELECT * FROM Dish WHERE category_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{category_id + ""});
        List<Dish> dishes = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Dish dish = new Dish();
                dish.setDish_id(cursor.getInt(0));
                dish.setName(cursor.getString(1));
                dish.setPrice(cursor.getInt(2));
                dish.setDescription(cursor.getString(3));
                dish.setAvailability(cursor.getString(4));
                dish.setCategory_id(cursor.getInt(5));
                dish.setImg(cursor.getString(6));
                dishes.add(dish);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dishes;
    }
    public List<Dish> getDishesByName(String name) {
        String query = "SELECT * FROM Dish WHERE name LIKE ?";
        Cursor cursor = db.rawQuery(query, new String[]{"%" + name + "%"});
        List<Dish> dishes = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Dish dish = new Dish();
                dish.setDish_id(cursor.getInt(0));
                dish.setName(cursor.getString(1));
                dish.setPrice(cursor.getInt(2));
                dish.setDescription(cursor.getString(3));
                dish.setAvailability(cursor.getString(4));
                dish.setCategory_id(cursor.getInt(5));
                dish.setImg(cursor.getString(6));
                dishes.add(dish);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dishes;
    }

    public ArrayList<Dish> getAll(){
        ArrayList<Dish> arrayList = new ArrayList<>();
        String[] ds_cot = new String[]{"*"};
        Cursor cursor = db.query(Dish.TB_NAME,ds_cot,null,null,null,null,null);
        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                Dish dish = new Dish();
                dish.setDish_id(cursor.getInt(0));
                dish.setName(cursor.getString(1));
                dish.setPrice(cursor.getInt(2));
                dish.setDescription(cursor.getString(3));
                dish.setAvailability(cursor.getString(4));
                dish.setCategory_id(cursor.getInt(5));
                dish.setImg(cursor.getString(6));
                arrayList.add(dish);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }


}
