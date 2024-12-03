package com.example.pro1122_nhm4.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pro1122_nhm4.DbHelper.DbHelper;
import com.example.pro1122_nhm4.Model.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {
    SQLiteDatabase db;
    DbHelper dbHelper;
    public OrderItemDAO(Context context){
        dbHelper = new DbHelper(context);
    }
    public void open(){
        db = dbHelper.getWritableDatabase();
    }
    public void close(){
        dbHelper.close();
    }
    public long insert(OrderItem orderItem) {
        open();
        ContentValues values = new ContentValues();
        values.put(OrderItem.COL_Ma_Order, orderItem.getOrder_id());
        values.put(OrderItem.COL_Ma_Dish, orderItem.getDish_id());
        values.put(OrderItem.COL_Quantity, orderItem.getQuantity());
        values.put(OrderItem.COL_Price, orderItem.getPrice());

        long newRowId = db.insert(OrderItem.TB_NAME, null, values);
        close();
        return newRowId;
    }

    public List<OrderItem> getAllOrderItemsByOrderId(int orderId) {
        open();
        List<OrderItem> orderItems = new ArrayList<>();
        String selection = OrderItem.COL_Ma_Order + " = ?";
        String[] selectionArgs = {String.valueOf(orderId)};
        Cursor cursor = db.query(OrderItem.TB_NAME, null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder_item_id(cursor.getInt(0));
                orderItem.setOrder_id(cursor.getInt(1));
                orderItem.setDish_id(cursor.getInt(2));
                orderItem.setQuantity(cursor.getInt(3));
                orderItem.setPrice(cursor.getInt(4));


                orderItems.add(orderItem);
                cursor.moveToNext();
            }
        }
        cursor.close();
        close();
        return orderItems;
    }

}
