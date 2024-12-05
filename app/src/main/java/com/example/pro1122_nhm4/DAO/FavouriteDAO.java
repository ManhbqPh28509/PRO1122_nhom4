package com.example.pro1122_nhm4.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pro1122_nhm4.DbHelper.DbHelper;
import com.example.pro1122_nhm4.Model.Favourite;

import java.util.ArrayList;
import java.util.List;

public class FavouriteDAO {
    private SQLiteDatabase db;
    private DbHelper dbHelper;
    public FavouriteDAO(Context context){
        dbHelper = new DbHelper(context);
    }
    public void open(){
        db = dbHelper.getWritableDatabase();
    }
    public void close(){
        dbHelper.close();
    }
    public List<Favourite> getAllFavouriteByUserId(int userId){
        open();
        List<Favourite> fravouriteList = new ArrayList<>();
        String selection = "user_id = ?";
        String[] selectionArgs = {String.valueOf(userId)};
        Cursor cursor = db.query(Favourite.TB_NAME, null, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Favourite fravourite = new Favourite();
                fravourite.setFavourite_id(cursor.getInt(0));
                fravourite.setUser_id(cursor.getInt(1));
                fravourite.setDish_id(cursor.getInt(2));
                fravouriteList.add(fravourite);
                cursor.moveToNext();
            }
        }
        cursor.close();
        close();
        return fravouriteList;

        }

    public long addFavourite(int dishId, int userId){
        open();
        ContentValues values = new ContentValues();
        values.put(Favourite.COL_User_ID, userId);
        values.put(Favourite.COL_Dish_ID, dishId);
        long newRowId =  db.insert(Favourite.TB_NAME, null, values);
        close();
        return newRowId;

        }
    public long deleteFavourite(int userId, int dishId){
        String query = "DELETE FROM Fravoutite WHERE user_id = ? AND dish_id = ?";
        open();
        int rowsAffected = db.delete(Favourite.TB_NAME, "user_id = ? AND dish_id = ?", new String[]{String.valueOf(userId), String.valueOf(dishId)});
        close();
        return rowsAffected;
    }
    public boolean isFavourite(int userId, int dishId) {
        String query = "SELECT * FROM Favourite WHERE user_id = ? AND dish_id = ?";
        open();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), String.valueOf(dishId)});
        boolean isFavourite = cursor.getCount() > 0;
        cursor.close();
        close();
        return isFavourite;
    }

}
