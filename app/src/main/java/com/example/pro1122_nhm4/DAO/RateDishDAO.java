package com.example.pro1122_nhm4.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pro1122_nhm4.DbHelper.DbHelper;
import com.example.pro1122_nhm4.Model.RateDish;
import com.example.pro1122_nhm4.Model.RateOrder;

import java.util.ArrayList;
import java.util.List;

public class RateDishDAO {
    private SQLiteDatabase db;
    private DbHelper dbHelper;
    public RateDishDAO (Context context){
        dbHelper = new DbHelper(context);
    }
    public void open(){
        db = dbHelper.getWritableDatabase();
    }
    public void close(){
        dbHelper.close();
    }
    public long insert(RateDish rateDish){
        open();
        ContentValues values = new ContentValues();
        values.put(RateDish.COL_Ma_User, rateDish.getUser_id());
        values.put(RateDish.COL_Ma_Dish, rateDish.getDish_id());
        values.put(RateDish.COL_Rate, rateDish.getRating());
        values.put(RateDish.COL_Comment, rateDish.getComment());
        values.put(RateDish.COL_Date, rateDish.getDate_rate());
        long newRowId = db.insert(RateDish.TB_NAME, null, values);
        close();
        return newRowId;
    }
    public List<RateDish> getAllRateDishes(){
        open();
        List<RateDish> rateDishes = new ArrayList<>();
        Cursor cursor = db.query(RateDish.TB_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                RateDish rateDish = new RateDish();
                rateDish.setRate_dish_id(cursor.getInt(0));
                rateDish.setUser_id(cursor.getInt(1));
                rateDish.setDish_id(cursor.getInt(2));
                rateDish.setRating(cursor.getInt(3));
                rateDish.setComment(cursor.getString(4));
                rateDish.setDate_rate(cursor.getString(5));
                rateDishes.add(rateDish);
                cursor.moveToNext();
            }
        }
        cursor.close();
        close();
        return rateDishes;
    }

    public List<RateDish> getRateDishByDishID(Integer id) {
        open();
        List<RateDish> rateDishes = new ArrayList<>();
        String selection = RateDish.COL_Ma_Dish + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(RateDish.TB_NAME, null, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                RateDish rateDish = new RateDish();
                rateDish.setRate_dish_id(cursor.getInt(0));
                rateDish.setUser_id(cursor.getInt(1));
                rateDish.setDish_id(cursor.getInt(2));
                rateDish.setRating(cursor.getInt(3));
                rateDish.setComment(cursor.getString(4));
                rateDish.setDate_rate(cursor.getString(5));
                rateDishes.add(rateDish);
                cursor.moveToNext();
                }
        }
        cursor.close();
        close();
        return rateDishes;
    }
}
