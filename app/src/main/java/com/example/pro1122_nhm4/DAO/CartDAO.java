package com.example.pro1122_nhm4.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.pro1122_nhm4.DbHelper.DbHelper;
import com.example.pro1122_nhm4.Model.Cart;
import com.example.pro1122_nhm4.Model.Category;

import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    SQLiteDatabase db;
    DbHelper dbHelper;
    Context context;
    public  CartDAO(Context context){
        dbHelper = new DbHelper(context);
    }
    public void open(){
        db = dbHelper.getWritableDatabase();
    }
    public void close(){
        dbHelper.close();
    }
    public long insertNew(Cart cart){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Cart.COL_Ma_User_Cart,cart.getUser_id());
        contentValues.put(Cart.COL_Ma_Dish_Cart,cart.getDish_id());
        contentValues.put(Cart.COL_Quantity,cart.getQuantity());
        contentValues.put(Cart.COL_Sum,cart.getSum());
        long res = db.insert(Cart.TB_NAME,null,contentValues);
        return res;
    }
    public long deleteCart(int cart_id){
        long res = db.delete(Cart.TB_NAME,Cart.COL_Ma_Cart + "=?",new String[]{String.valueOf(cart_id)});
        return res;
    }
    public long deleteCartByUserID(int user_id){
        long res = db.delete(Cart.TB_NAME,Cart.COL_Ma_User_Cart + "=?",new String[]{String.valueOf(user_id)});
        return res;
    }
    public long updateCart(Cart cart){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Cart.COL_Ma_User_Cart,cart.getUser_id());
        contentValues.put(Cart.COL_Ma_Dish_Cart,cart.getDish_id());
        contentValues.put(Cart.COL_Quantity,cart.getQuantity());
        contentValues.put(Cart.COL_Sum,cart.getSum());
        long res = db.update(Cart.TB_NAME,contentValues,Cart.COL_Ma_Cart + "=?",new String[]{String.valueOf(cart.getCart_id())});
        return res;
    }
    public boolean isDishExists(int dish_id, int user_id){
        String query = "SELECT * FROM Cart WHERE dish_id = ? AND user_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{dish_id + "", user_id + ""});
        boolean exists = cursor.moveToFirst();
        Log.d("CartDAO", "isDishExists:"+exists + dish_id + user_id);
        cursor.close();
        return exists;
    }
    public List<Cart> getCartByUserID(int user_id) {
        List<Cart> carts = new ArrayList<>();
        String[] columns = new String[]{"*"};
        String selection = "user_id = ?";
        String[] selectionArgs = new String[]{String.valueOf(user_id)};

        Cursor cursor = db.query(Cart.TB_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) { // Di chuyển đến dòng đầu tiên
            do {
                Cart cart = new Cart();
                cart.setCart_id(cursor.getInt(cursor.getColumnIndexOrThrow("cart_id")));
                cart.setUser_id(cursor.getInt(cursor.getColumnIndexOrThrow("user_id")));
                cart.setDish_id(cursor.getInt(cursor.getColumnIndexOrThrow("dish_id")));
                cart.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow("quantity")));
                cart.setSum(cursor.getInt(cursor.getColumnIndexOrThrow("sum")));
                carts.add(cart);
            } while (cursor.moveToNext()); // Di chuyển tới dòng kế tiếp
        } else {
            Log.w("CartDAO", "No cart found for user_id: " + user_id);
        }

        if (cursor != null) {
            cursor.close(); // Đóng Cursor để tránh rò rỉ tài nguyên
        }
        return carts;

    }
}
