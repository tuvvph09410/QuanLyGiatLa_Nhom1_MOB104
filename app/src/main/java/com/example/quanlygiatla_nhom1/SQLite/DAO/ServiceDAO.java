package com.example.quanlygiatla_nhom1.SQLite.DAO;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlygiatla_nhom1.Class.ServiceClass;
import com.example.quanlygiatla_nhom1.SQLite.SQLite;

import java.util.ArrayList;
import java.util.List;

public class ServiceDAO {
SQLite sqLite;
public ServiceDAO(Context context){sqLite=new SQLite(context);}
    public static String TableName = "DichVu";
    public static String ColumnID = "MaDichVu";
    public static String ColumnServiceType = "LoaiDichVu";
    public static String ColumnProductType = "LoaiSanPham";
    public static String ColumnUnit = "DonVi";
    public static String ColumnPrice = "DonGia";
    public static String ColumnStatus = "TrangThai";
    public static String CreateTable = "CREATE TABLE "
            +TableName+" ("
            +ColumnID+" TEXT PRIMARY KEY, "
            +ColumnServiceType+" TEXT, "
            +ColumnProductType+" TEXT, "
            +ColumnUnit+" TEXT, "
            +ColumnPrice+" INTEGER, "
            +ColumnStatus+" TEXT "
            +")";
    public static final String DropTable = " DROP TABLE IF EXISTS " + TableName;
    public boolean AddOneService(ServiceClass service){
        SQLiteDatabase db = sqLite.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ColumnID,service.getId());
        cv.put(ColumnServiceType,service.getService_type());
        cv.put(ColumnProductType,service.getProduct_type());
        cv.put(ColumnUnit,service.getUnit());
        cv.put(ColumnPrice,service.getPrice());
        cv.put(ColumnStatus,service.getStatus());
        long insert = db.insert(TableName,null,cv);
        if(insert==-1){
            return false;
        }else{
            return true;
        }
    }
    @SuppressLint("Range")
    public Integer GetPrice(String service_id){
        SQLiteDatabase sqLiteDatabase = sqLite.getReadableDatabase();
        ServiceClass s;
        String query = "SELECT * FROM "+TableName+" WHERE "+ColumnID+" =?";
        Cursor cursor = sqLiteDatabase.rawQuery(query,new String[]{service_id});
        cursor.moveToFirst();
        s = new ServiceClass(
                cursor.getString(cursor.getColumnIndex(ColumnID)),
                cursor.getString(cursor.getColumnIndex(ColumnServiceType)),
                cursor.getString(cursor.getColumnIndex(ColumnProductType)),
                cursor.getString(cursor.getColumnIndex(ColumnUnit)),
                cursor.getInt(cursor.getColumnIndex(ColumnPrice)),
                cursor.getString(cursor.getColumnIndex(ColumnStatus))
        );
        cursor.close();
        return s.getPrice();
    }
    @SuppressLint("Range")
    public List<ServiceClass> GetAllService(){
        List<ServiceClass> services = new ArrayList<>();
        String query = "SELECT * FROM "+TableName;
        SQLiteDatabase sqLiteDatabase = sqLite.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ServiceClass s = new ServiceClass(
                    cursor.getString(cursor.getColumnIndex(ColumnID)),
                    cursor.getString(cursor.getColumnIndex(ColumnServiceType)),
                    cursor.getString(cursor.getColumnIndex(ColumnProductType)),
                    cursor.getString(cursor.getColumnIndex(ColumnUnit)),
                    cursor.getInt(cursor.getColumnIndex(ColumnPrice)),
                    cursor.getString(cursor.getColumnIndex(ColumnStatus))
            );
            services.add(s);
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return services;
    }
    public Boolean UpdateOneService(ServiceClass service) {
        SQLiteDatabase sqLiteDatabase = sqLite.getWritableDatabase();
        ContentValues cv =  new ContentValues();
        cv.put(ColumnID,service.getId());
        cv.put(ColumnServiceType,service.getService_type());
        cv.put(ColumnProductType,service.getProduct_type());
        cv.put(ColumnUnit,service.getUnit());
        cv.put(ColumnPrice,service.getPrice());
        cv.put(ColumnStatus,service.getStatus());

        long update = sqLiteDatabase.update(TableName, cv, ColumnID+" = ?", new String[]{service.getId()});
        sqLiteDatabase.close();
        if(update==-1){
            return false;
        }
        else{
            return true;
        }
    }
    public Boolean UpdateStatusOneService(ServiceClass service) {
        SQLiteDatabase sqLiteDatabase = sqLite.getWritableDatabase();
        ContentValues cv =  new ContentValues();
        cv.put(ColumnID,service.getId());
        cv.put(ColumnServiceType,service.getService_type());
        cv.put(ColumnProductType,service.getProduct_type());
        cv.put(ColumnUnit,service.getUnit());
        cv.put(ColumnPrice,service.getPrice());
        cv.put(ColumnStatus,service.getStatus().equalsIgnoreCase("Hoạt động")?"Không hoạt động":"Hoạt động");

        long update = sqLiteDatabase.update(TableName, cv, ColumnID+" = ?", new String[]{service.getId()});
        sqLiteDatabase.close();
        if(update==-1){
            return false;
        }
        else{
            return true;
        }
    }
    @SuppressLint("Range")
    public List<ServiceClass> GetSpinnerService(){
        List<ServiceClass> services = new ArrayList<>();
        String query = "SELECT * FROM "+TableName +" WHERE "+ColumnStatus+" = 'Hoạt động'";
        SQLiteDatabase sqLiteDatabase = sqLite.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ServiceClass s = new ServiceClass(
                    cursor.getString(cursor.getColumnIndex(ColumnID)),
                    cursor.getString(cursor.getColumnIndex(ColumnServiceType)),
                    cursor.getString(cursor.getColumnIndex(ColumnProductType)),
                    cursor.getString(cursor.getColumnIndex(ColumnUnit)),
                    cursor.getInt(cursor.getColumnIndex(ColumnPrice)),
                    cursor.getString(cursor.getColumnIndex(ColumnStatus))
            );
            services.add(s);
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return services;
    }

    public Boolean ResetUserDao (){
        SQLiteDatabase sqLiteDatabase = sqLite.getReadableDatabase();
        sqLiteDatabase.execSQL(DropTable);
        sqLiteDatabase.execSQL(CreateTable);
        return true;
    }
}
