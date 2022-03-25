package com.example.quanlygiatla_nhom1.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.quanlygiatla_nhom1.SQLite.DAO.BillDAO;
import com.example.quanlygiatla_nhom1.SQLite.DAO.BillDetailDAO;
import com.example.quanlygiatla_nhom1.SQLite.DAO.ServiceDAO;
import com.example.quanlygiatla_nhom1.SQLite.DAO.UserDAO;
import com.example.quanlygiatla_nhom1.SQLite.DAO.WarehouseDAO;

public class SQLite extends SQLiteOpenHelper {
    public SQLite(Context context){
super(context,"shopping",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(BillDAO.CreateTable);
        sqLiteDatabase.execSQL(BillDetailDAO.CreateTable);
        sqLiteDatabase.execSQL(ServiceDAO.CreateTable);
        sqLiteDatabase.execSQL(UserDAO.CreateTable);
        sqLiteDatabase.execSQL(WarehouseDAO.CreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(BillDAO.DropTable);
        sqLiteDatabase.execSQL(BillDetailDAO.DropTable);
        sqLiteDatabase.execSQL(ServiceDAO.DropTable);
        sqLiteDatabase.execSQL(UserDAO.DropTable);
        sqLiteDatabase.execSQL(WarehouseDAO.DropTable);
        onCreate(sqLiteDatabase);
    }
    public void BackupAndReset (){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(BillDAO.DropTable);
        sqLiteDatabase.execSQL(BillDetailDAO.DropTable);
        sqLiteDatabase.execSQL(ServiceDAO.DropTable);
        sqLiteDatabase.execSQL(UserDAO.DropTable);
        sqLiteDatabase.execSQL(WarehouseDAO.DropTable);
        onCreate(sqLiteDatabase);
    }
}
