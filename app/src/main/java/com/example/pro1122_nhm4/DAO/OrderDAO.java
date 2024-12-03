package com.example.pro1122_nhm4.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.example.pro1122_nhm4.DbHelper.DbHelper;
import com.example.pro1122_nhm4.Model.Order;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderDAO {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public OrderDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long createOrder(int userId, int totalAmount, String status, LocalDate orderDate) {
        open();
        ContentValues values = new ContentValues();
        values.put(Order.COL_Ma_User, userId);
        values.put(Order.COL_Total_Amount, totalAmount);
        values.put(Order.COL_Status, status);
        values.put(Order.COL_Order_Date, String.valueOf(orderDate));
        long newRowId =  db.insert("`Order`", null, values);
        close();
        return newRowId;
    }

    public long update(Order order) {
        open();
        ContentValues values = new ContentValues();
        values.put(Order.COL_Ma_User, order.getUser_id());
        values.put(Order.COL_Status, order.getStatus());
        values.put(Order.COL_Total_Amount, order.getTotal_amount());
        values.put(Order.COL_Order_Date, String.valueOf(order.getOrder_date()));
        long rowsAffected = db.update("`Order`", values, "order_id = ?", new String[]{String.valueOf(order.getOrder_id())});
        close();
        return rowsAffected;
    }

    public List<Order> getAllOrdersByUserId(int userId) {
        open();
        List<Order> orders = new ArrayList<>();
        String selection = "user_id = ?";
        String[] selectionArgs = {String.valueOf(userId)};
        Cursor cursor = db.query("`Order`", null, selection, selectionArgs, null, null, "order_id DESC");

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Order order = new Order();
                order.setOrder_id(cursor.getInt(0));
                order.setUser_id(cursor.getInt(1));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    order.setOrder_date(LocalDate.parse(cursor.getString(2)));
                }
                order.setTotal_amount(cursor.getInt(3));
                order.setStatus(cursor.getString(4));
                orders.add(order);
                cursor.moveToNext();
            }
        }
        cursor.close();
        close();
        return orders;
    }
    public List<Order> getAllOrdersByUserIDAndStatus2(int userId) {
        open();
        List<Order> orders = new ArrayList<>();

        // Thêm điều kiện trạng thái "Đã hoàn thành" vào truy vấn
        String selection = "user_id = ? AND status = ?";
        String[] selectionArgs = {String.valueOf(userId), "Đã hoàn thành"};

        Cursor cursor = db.query("`Order`", null, selection, selectionArgs, null, null, "order_id DESC");

        if (cursor.moveToFirst()) {
            do {
                Order order = new Order();
                order.setOrder_id(cursor.getInt(0));
                order.setUser_id(cursor.getInt(1));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    order.setOrder_date(LocalDate.parse(cursor.getString(2)));
                }
                order.setTotal_amount(cursor.getInt(3));
                order.setStatus(cursor.getString(4));
                orders.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return orders;

    }
    public List<Order> getRevenueByDateRange(String startDate, String endDate) {
        open();
        List<Order> revenueList = new ArrayList<>();

        // Định dạng ngày chuẩn để sử dụng
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault());
        }

        // Truy vấn SQL với điều kiện khoảng ngày
        String query = "SELECT order_date, SUM(total_amount) as total_revenue " +
                "FROM `Order` " +
                "WHERE status = 'Đã hoàn thành' " +
                "AND order_date BETWEEN ? AND ? " +
                "GROUP BY order_date " +
                "ORDER BY order_date DESC";

        Cursor cursor = db.rawQuery(query, new String[]{startDate, endDate});

        if (cursor.moveToFirst()) {
            do {
                Order order = new Order();

                // Chuyển đổi String từ cursor sang LocalDate
                String dateString = cursor.getString(0); // Lấy ngày từ cơ sở dữ liệu
                LocalDate orderDate = null; // Chuyển sang LocalDate
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    orderDate = LocalDate.parse(dateString, formatter);
                }

                // Gán vào đối tượng Order
                order.setOrder_date(orderDate); // Gán LocalDate vào Order
                order.setTotal_amount(cursor.getInt(1)); // Tổng doanh thu
                revenueList.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return revenueList;
    }
    public List<Order> getAllOrdersByUserIDAndStatus(int userId) {
        open();
        List<Order> orders = new ArrayList<>();

        // Thêm điều kiện trạng thái "Đã hoàn thành" vào truy vấn
        String selection = "user_id = ? AND status != ?";
        String[] selectionArgs = {String.valueOf(userId), "Đã hoàn thành"};

        Cursor cursor = db.query("`Order`", null, selection, selectionArgs, null, null, "order_id DESC");

        if (cursor.moveToFirst()) {
            do {
                Order order = new Order();
                order.setOrder_id(cursor.getInt(0));
                order.setUser_id(cursor.getInt(1));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    order.setOrder_date(LocalDate.parse(cursor.getString(2)));
                }
                order.setTotal_amount(cursor.getInt(3));
                order.setStatus(cursor.getString(4));
                orders.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return orders;

    }
    public List<Order> getAllOrders() {
        open();
        List<Order> orders = new ArrayList<>();
        Cursor cursor = db.query("`Order`", null, null, null, null, null, "order_id DESC");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Order order = new Order();
                order.setOrder_id(cursor.getInt(0));
                order.setUser_id(cursor.getInt(1));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    order.setOrder_date(LocalDate.parse(cursor.getString(2)));
                }
                order.setTotal_amount(cursor.getInt(3));
                order.setStatus(cursor.getString(4));
                orders.add(order);
                cursor.moveToNext();
            }
        }
        cursor.close();
        close();
        return orders;
    }

}
