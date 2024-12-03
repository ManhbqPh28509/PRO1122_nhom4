package com.example.pro1122_nhm4.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.example.pro1122_nhm4.DbHelper.DbHelper;
import com.example.pro1122_nhm4.Model.User;

import java.time.LocalDate;
import java.util.ArrayList;

public class
UserDAO {
    SQLiteDatabase db;
    DbHelper dbHelper;
    public UserDAO(Context context){dbHelper = new DbHelper(context);}
    public void open(){db = dbHelper.getWritableDatabase();}
    public void close(){ dbHelper.close();}
    public long insertNew(User user){
        ContentValues contentValues = new ContentValues();
        contentValues.put(User.COL_HoTen,user.getHoten());
        contentValues.put(User.COL_NgaySinh, String.valueOf(user.getNgaysinh()));
        contentValues.put(User.COL_Email,user.getEmail());
        contentValues.put(User.COL_SDT,user.getSdt());
        contentValues.put(User.COL_DiaChi,user.getDiachi());
        contentValues.put(User.COL_MatKhau,user.getMatkhau());
        contentValues.put(User.COL_UserRole, "user");
        long res = db.insert(User.TB_NAME,null,contentValues);
        return res;
    }
    public long editThanhVien(User user){
        ContentValues contentValues = new ContentValues();
        contentValues.put(User.COL_HoTen,user.getHoten());
        contentValues.put(User.COL_NgaySinh, String.valueOf(user.getNgaysinh()));
        contentValues.put(User.COL_Email,user.getEmail());
        contentValues.put(User.COL_SDT,user.getSdt());
        contentValues.put(User.COL_DiaChi,user.getDiachi());
        contentValues.put(User.COL_MatKhau,user.getMatkhau());
        contentValues.put(User.COL_UserRole,user.getUser_role());
        long res = db.update(User.TB_NAME,contentValues,"user_id = ? ",new String[]{user.getUser_id()+""});
        return res;
    }
    public long editForgotPass(String email, String matKhau){
        String query = "SELECT * FROM User WHERE email = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        if (cursor.moveToFirst()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(User.COL_MatKhau, matKhau);
            long res = db.update(User.TB_NAME, contentValues, "email = ?", new String[]{email});
            return res;
        }else {
            return -1;
        }
    }
    public long deleteTV(User user){
        long res = db.delete(User.TB_NAME,"user_id = ?",new String[]{user.getUser_id()+""});
        return res;
    }

    public User getUserByEmailAndPassword(String email,String password){
        String query = "SELECT * FROM User WHERE email = ? AND matkhau = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            user.setUser_id(cursor.getInt(0));
            user.setHoten(cursor.getString(1));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                user.setNgaysinh(LocalDate.parse(cursor.getString(2)));
            }
            user.setEmail(cursor.getString(6));
            user.setSdt(cursor.getString(3));
            user.setDiachi(cursor.getString(4));
            user.setMatkhau(cursor.getString(5));
            user.setUser_role(cursor.getString(6));
        }
        cursor.close();
        return user;
    }
    public boolean checkLoginThanhVien(String email, String matKhau) {
        String query = "SELECT * FROM User WHERE email = ? AND matkhau = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, matKhau});
        boolean result = cursor.getCount() > 0;
        return result;
    }
    public boolean checkEmail(String email) {
        String query = "SELECT * FROM User WHERE email = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        boolean result = cursor.getCount() > 0;
        return result;
    }

    public  boolean checkRoleUser(String email, String matKhau){
        String query = "SELECT user_role FROM User WHERE email = ? AND matkhau = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, matKhau});

        boolean result = false;
        if (cursor.moveToFirst()) {
            String role = cursor.getString(cursor.getColumnIndex("user_role"));
            result = role.equals("user");
        }
        cursor.close();
        return result;
    }

    public ArrayList<User> selectAll() {
        ArrayList<User> arrayList = new ArrayList<>();
        String[] ds_cot = new String[]{"*"};

        // Thêm điều kiện WHERE role = 2 vào truy vấn SQL
        Cursor cursor = db.query(User.TB_NAME, ds_cot,null,null,null,null,null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                User user = new User();
                user.setUser_id(cursor.getInt(0));
                user.setHoten(cursor.getString(1));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    user.setNgaysinh(LocalDate.parse(cursor.getString(2)));
                }
                user.setEmail(cursor.getString(6));
                user.setSdt(cursor.getString(3));
                user.setDiachi(cursor.getString(4));
                user.setMatkhau(cursor.getString(5));
                user.setUser_role(cursor.getString(6));
                arrayList.add(user);
                cursor.moveToNext();
            }

        }

        // Đóng cursor
        cursor.close();

        return arrayList;
    }

    public String getUserNameById(int userId) {
        String query = "SELECT hoten FROM User WHERE user_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor != null && cursor.moveToFirst()) {
            String userName = cursor.getString(0);
            cursor.close();
            return userName;
        }
        return null;
    }
}
