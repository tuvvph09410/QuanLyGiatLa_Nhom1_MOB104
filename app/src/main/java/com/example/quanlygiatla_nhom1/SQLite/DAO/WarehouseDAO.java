package com.example.quanlygiatla_nhom1.SQLite.DAO;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlygiatla_nhom1.Class.WarehouseClass;
import com.example.quanlygiatla_nhom1.SQLite.SQLite;

import java.util.ArrayList;
import java.util.List;

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
    public boolean AddOneWarehouse(WarehouseClass warehouseClass){
        SQLiteDatabase sqLiteDatabase = sqLite.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ColumnID,warehouseClass.getId());
        cv.put(ColumnName,warehouseClass.getName());
        cv.put(ColumnStatus,warehouseClass.getStatus());
        long insert = sqLiteDatabase.insert(TableName,null,cv);
        if(insert==-1){
            return false;
        }else{
            return true;
        }
    }

    public String GetWareHouseName (String warehouse_id){

        return "";
    }
    @SuppressLint("Range")
    public List<WarehouseClass> GetAllWarehouse(){
        List<WarehouseClass> warehouseClasses = new ArrayList<>();
        String query = "SELECT * FROM "+TableName;
        SQLiteDatabase sqLiteDatabase = sqLite.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            WarehouseClass w = new WarehouseClass(
                    cursor.getString(cursor.getColumnIndex(ColumnID)),
                    cursor.getString(cursor.getColumnIndex(ColumnName)),
                    cursor.getString(cursor.getColumnIndex(ColumnStatus))
            );
            warehouseClasses.add(w);
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return warehouseClasses;
    }

    public Boolean UpdateOneWarehouseStatus(WarehouseClass warehouse) {
        SQLiteDatabase sqLiteDatabase = sqLite.getWritableDatabase();
        ContentValues cv =  new ContentValues();
        cv.put(ColumnID,warehouse.getId());
        cv.put(ColumnName,warehouse.getName());
        cv.put(ColumnStatus,warehouse.getStatus().equalsIgnoreCase("Trống")?"Đã đầy":"Trống");
        long update = sqLiteDatabase.update(TableName, cv, ColumnID+" = ?", new String[]{warehouse.getId()});
        sqLiteDatabase.close();
        if(update==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public Boolean UpdateOneWarehouse(WarehouseClass warehouse) {
        SQLiteDatabase sqLiteDatabase = sqLite.getWritableDatabase();
        ContentValues cv =  new ContentValues();
        cv.put(ColumnID,warehouse.getId());
        cv.put(ColumnName,warehouse.getName());
        cv.put(ColumnStatus,warehouse.getStatus());
        long update = sqLiteDatabase.update(TableName, cv, ColumnID+" = ?", new String[]{warehouse.getId()});
        sqLiteDatabase.close();
        if(update==-1){
            return false;
        }
        else{
            return true;
        }
    }

    @SuppressLint("Range")
    public List<WarehouseClass> GetSpinnerWarehouse(){
        List<WarehouseClass> warehouseClasses = new ArrayList<>();
        String query = "SELECT * FROM "+TableName +" WHERE "+ColumnStatus+" = 'Trống'";
        SQLiteDatabase sqLiteDatabase = sqLite.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            WarehouseClass w = new WarehouseClass(
                    cursor.getString(cursor.getColumnIndex(ColumnID)),
                    cursor.getString(cursor.getColumnIndex(ColumnName)),
                    cursor.getString(cursor.getColumnIndex(ColumnStatus))
            );
            warehouseClasses.add(w);
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return warehouseClasses;
    }

    public Boolean ResetUserDao (){
        SQLiteDatabase sqLiteDatabase = sqLite.getReadableDatabase();
        sqLiteDatabase.execSQL(DropTable);
        sqLiteDatabase.execSQL(CreateTable);
        return true;
    }

}
