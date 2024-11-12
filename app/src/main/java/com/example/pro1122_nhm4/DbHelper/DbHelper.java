package com.example.pro1122_nhm4.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydb";
    private static final int DATABASE_VERSION = 1;
    public DbHelper(@Nullable Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_TV = "CREATE TABLE User (user_id INTEGER primary key autoincrement, hoten text not null, ngaysinh date not null, sdt text not null, diachi text not null, matkhau text not null, email text not null, user_role text not null)";
        db.execSQL(CREATE_TABLE_TV);
        db.execSQL("INSERT INTO User VALUES (1,'Bùi Quang Mạnh','2003-02-09','0984938203','Số 14, Ngách 6 Ngõ 113 Đường Đan Khê Xã Di Trạch, Huyện Hoài Đức, Hà Nội','manh123','manhbqph28509@fpt.edu.vn','admin')," +
                "(2,'Nguyễn Thế Việt','2003-09-08','0984943646','Số 14, Ngách 6 Ngõ 113 Đường Đan Khê Xã Di Trạch, Huyện Hoài Đức, Hà Nội','viet123','theviet89@gmail.com','user')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
    }
}
