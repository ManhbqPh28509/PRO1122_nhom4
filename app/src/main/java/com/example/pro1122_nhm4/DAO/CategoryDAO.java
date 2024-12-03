package com.example.pro1122_nhm4.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.fragment.app.FragmentActivity;

import com.example.pro1122_nhm4.DbHelper.DbHelper;
import com.example.pro1122_nhm4.Model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    SQLiteDatabase db;
    DbHelper dbHelper;
    Context context;
    public CategoryDAO(Context context) {
        dbHelper = new DbHelper(context);
    }
    public void open(){db = dbHelper.getWritableDatabase();}
    public void close(){ dbHelper.close();}
    public List<Category> getAll() {
        List<Category> list = new ArrayList<>();
        String[] ds_cot = new String[]{"*"};
        Cursor cursor = db.query(Category.TB_NAME, ds_cot, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                Category category = new Category();
                category.setCategory_id(cursor.getInt(0));
                category.setName(cursor.getString(1));
                category.setImg(cursor.getString(2));
                list.add(category);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }
}
