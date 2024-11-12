package com.example.pro1122_nhm4.Model;

import java.time.LocalDate;

public class User {
    private int user_id;
    private String  hoten, diachi, email, matkhau,user_role;
    private String sdt;
    private LocalDate ngaysinh;
    public static final String TB_NAME = "User";
    public static final String COL_Ma_TV = "user_id";
    public static final String COL_HoTen = "hoten";
    public static final String COL_DiaChi = "diachi";
    public static final String COL_Email = "email";
    public static final String COL_MatKhau = "matkhau";
    public static final String COL_SDT = "sdt";
    public static final String COL_NgaySinh = "ngaysinh";
    public static final  String COL_UserRole = "user_role" ;

    public User() {
    }

    public User(int user_id, String hoten, String diachi, String email, String matkhau, String user_role, String sdt, LocalDate ngaysinh) {
        this.user_id = user_id;
        this.hoten = hoten;
        this.diachi = diachi;
        this.email = email;
        this.matkhau = matkhau;
        this.user_role = user_role;
        this.sdt = sdt;
        this.ngaysinh = ngaysinh;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public LocalDate getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(LocalDate ngaysinh) {
        this.ngaysinh = ngaysinh;
    }
}
