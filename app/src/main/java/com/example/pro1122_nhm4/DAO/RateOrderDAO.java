package com.example.pro1122_nhm4.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pro1122_nhm4.DbHelper.DbHelper;
import com.example.pro1122_nhm4.Model.RateOrder;

import java.util.ArrayList;
import java.util.List;

public class RateOrderDAO {
    private SQLiteDatabase db;
    private DbHelper dbHelper;
    public RateOrderDAO(Context context){
        dbHelper = new DbHelper(context);
    }
    public void open(){
        db = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }
    public long insert(RateOrder rateOrder) {
        open();
        ContentValues values = new ContentValues();
        values.put(RateOrder.COL_Ma_User, rateOrder.getUser_id());
        values.put(RateOrder.COL_Ma_Order, rateOrder.getOrder_id());
        values.put(RateOrder.COL_Rate, rateOrder.getRating());
        values.put(RateOrder.COL_Comment, rateOrder.getComment());
        values.put(RateOrder.COL_Date, rateOrder.getDate_rate());
        long newRowId = db.insert(RateOrder.TB_NAME, null, values);
        close();
        return newRowId;

    }
    public List<RateOrder> getAllRateOrders() {
        open();
        List<RateOrder> rateOrders = new ArrayList<>();
        Cursor cursor = db.query(RateOrder.TB_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                RateOrder rateOrder = new RateOrder();
                rateOrder.setRate_order_id(cursor.getInt(0));
                rateOrder.setUser_id(cursor.getInt(1));
                rateOrder.setOrder_id(cursor.getInt(2));
                rateOrder.setRating(cursor.getInt(3));
                rateOrder.setComment(cursor.getString(4));
                rateOrder.setDate_rate(cursor.getString(5));
                rateOrders.add(rateOrder);
                cursor.moveToNext();
            }
        }
        cursor.close();
        close();
        return rateOrders;
    }
    public boolean isOrderRated(int orderId) {
        open();
        String query = "SELECT COUNT(*) FROM RateOrder WHERE order_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(orderId)});
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            return count > 0; // Nếu số lượng > 0, đơn hàng đã được đánh giá
        }
        if (cursor != null) {
            cursor.close();
        }
        return false;
    }
    public List<RateOrder> getRateOrdersByUserId(int userId) {
        open();
        List<RateOrder> rateOrders = new ArrayList<>();
        String selection = RateOrder.COL_Ma_User + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};
        Cursor cursor = db.query(RateOrder.TB_NAME, null, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                RateOrder rateOrder = new RateOrder();
                rateOrder.setRate_order_id(cursor.getInt(0));
                rateOrder.setUser_id(cursor.getInt(1));
                rateOrder.setOrder_id(cursor.getInt(2));
                rateOrder.setRating(cursor.getInt(3));
                rateOrder.setComment(cursor.getString(4));
                rateOrder.setDate_rate(cursor.getString(5));
                rateOrders.add(rateOrder);
                cursor.moveToNext();
            }
        }
        cursor.close();
        close();
        return rateOrders;
    }

}
