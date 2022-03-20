package com.example.quanlygiatla_nhom1.Activity.SQLite.DAO;


import android.content.Context;

import com.example.quanlygiatla_nhom1.Activity.SQLite.SQLite;

public class UserDAO {
SQLite sqLite;
 public UserDAO(Context context){sqLite = new SQLite(context);}
    public static String TableName = "NguoiDung";
    public static String ColumnUserName = "TenDangNhap";
    public static String ColumnPassword = "MatKhau";
    public static String ColumnName = "HoVaTen";
    public static String ColumnPhone = "SoDienThoai";
    public static String ColumnRole = "VaiTro";
    public static String ColumnDelete = "Xoa";
   public static final String CreateTable = "CREATE TABLE "
           +TableName+" ("
           +ColumnUserName+" TEXT PRIMARY KEY, "
           +ColumnPassword+" TEXT, "
           +ColumnName+" TEXT, "
           +ColumnPhone+" TEXT, "
           +ColumnRole+" TEXT, "
           +ColumnDelete+" TEXT"
           +")";
   public static final String DropTable = " DROP TABLE IF EXISTS " + TableName;

}
