package com.example.quanlygiatla_nhom1.Activity.SQLite.DAO;


import android.content.Context;

import com.example.quanlygiatla_nhom1.Activity.SQLite.SQLite;

public class WarehouseDAO {
SQLite sqLite;
public WarehouseDAO(Context context){sqLite=new SQLite(context);}
    public static String TableName = "Kho";
    public static String ColumnID = "MaKho";
    public static String ColumnName = "TenKho";
    public static String ColumnStatus = "TrangThai";

    public static  final String CreateTable = "CREATE TABLE "
            +TableName+" ("
            +ColumnID+" TEXT PRIMARY KEY, "
            +ColumnName+" TEXT, "
            +ColumnStatus+" TEXT "
            +")";
    public static final String DropTable = " DROP TABLE IF EXISTS " + TableName;
}
