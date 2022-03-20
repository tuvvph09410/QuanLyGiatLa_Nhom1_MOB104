package com.example.quanlygiatla_nhom1.Activity.SQLite.DAO;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlygiatla_nhom1.Activity.Class.BillDetailClass;
import com.example.quanlygiatla_nhom1.Activity.Class.ProfitClass;
import com.example.quanlygiatla_nhom1.Activity.SQLite.SQLite;

import java.util.ArrayList;
import java.util.List;

public class BillDetailDAO {
public SQLite sqLite;
public BillDetailDAO(Context context){sqLite = new SQLite(context);}
    public static String TableName = "HoaDonChiTiet";
    public static String ColumnBillID = "MaHoaDon";
    public static String ColumnServiceID = "MaDichVu";
    public static String ColumnWarehouseID = "MaKho";
    public static String ColumnQuantity = "SoLuong";
    public static String ColumnPrice = "GiaTien";
    public static final String CreateTable = "CREATE TABLE "
            +TableName+" ("
            +ColumnBillID+" TEXT REFERENCES "+ BillDAO.ColumnID+"("+BillDAO.TableName+") ON DELETE CASCADE ON UPDATE CASCADE, "
            +ColumnServiceID+" TEXT REFERENCES "+ ServiceDAO.ColumnID+"("+ServiceDAO.TableName+") ON DELETE CASCADE ON UPDATE CASCADE, "
            +ColumnWarehouseID+" TEXT REFERENCES "+ WarehouseDAO.ColumnID+"("+WarehouseDAO.TableName+") ON DELETE CASCADE ON UPDATE CASCADE, "
            +ColumnQuantity+" INT, "
            +ColumnPrice+" INT "
            +")";
    public static final String DropTable = " DROP TABLE IF EXISTS " + TableName;
public boolean AddOneBillDetail(BillDetailClass billDetail){
    SQLiteDatabase sqLiteDatabase = sqLite.getWritableDatabase();
    ContentValues cv = new ContentValues();
    cv.put(ColumnBillID,billDetail.getBillID());
    cv.put(ColumnServiceID,billDetail.getServiceID());
    cv.put(ColumnWarehouseID,billDetail.getWarehouseID());
    cv.put(ColumnQuantity,billDetail.getQuantity());
    cv.put(ColumnPrice,billDetail.getTotal());
    long insert = sqLiteDatabase.insert(TableName,null,cv);
    if(insert==-1){
        return false;
    }else{
        return true;
    }
}
    @SuppressLint("Range")
    public List<String> GetAllWarehouseBill (String bill_id){
        SQLiteDatabase sqLiteDatabase = sqLite.getReadableDatabase();
        List<String> warehouse = new ArrayList<>();
        String query = "SELECT * FROM "+TableName+" WHERE "+ColumnBillID+" =?";
        Cursor cursor = sqLiteDatabase.rawQuery(query,new String[]{bill_id});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            warehouse.add(cursor.getString(cursor.getColumnIndex(ColumnWarehouseID)));
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return warehouse;
    }
    @SuppressLint("Range")
    public List<String> GetAllServiceBill (String bill_id){
        SQLiteDatabase sqLiteDatabase = sqLite.getReadableDatabase();
        List<String> service = new ArrayList<>();
        String query = "SELECT * FROM "+TableName+" WHERE "+ColumnBillID+" =?";
        Cursor cursor = sqLiteDatabase.rawQuery(query,new String[]{bill_id});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            service.add(cursor.getString(cursor.getColumnIndex(ColumnServiceID)));
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return service;
    }
    @SuppressLint("Range")
    public Integer GetBillTotal (String bill_id){
        SQLiteDatabase sqLiteDatabase = sqLite.getReadableDatabase();
        Integer total = 0;
        String query = "SELECT * FROM "+TableName+" WHERE "+ColumnBillID+" =?";
        Cursor cursor = sqLiteDatabase.rawQuery(query,new String[]{bill_id});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            total+= cursor.getInt(cursor.getColumnIndex(ColumnPrice));
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return total;
    }
    @SuppressLint("Range")
    public List<BillDetailClass> GetAllBillDetailInfo (String bill_id){
        SQLiteDatabase sqLiteDatabase = sqLite.getReadableDatabase();
        List<BillDetailClass> billDetails = new ArrayList<>();
        String query = "SELECT * FROM "+TableName+
                " INNER JOIN "+
                ServiceDAO.TableName + " ON "+ TableName+"."+ColumnServiceID + " = "+ServiceDAO.TableName+"."+ServiceDAO.ColumnID +
                " INNER JOIN "+
                WarehouseDAO.TableName + " ON "+ TableName+"."+ColumnWarehouseID + " = "+WarehouseDAO.TableName+"."+WarehouseDAO.ColumnID +
                " WHERE "+ColumnBillID+" =? ";
        Cursor cursor = sqLiteDatabase.rawQuery(query,new String[]{bill_id});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            BillDetailClass b = new BillDetailClass(
                    cursor.getString(cursor.getColumnIndex(ColumnBillID)),
                    cursor.getString(cursor.getColumnIndex(ServiceDAO.ColumnID))+ " - " +
                            cursor.getString(cursor.getColumnIndex(ServiceDAO.ColumnServiceType))+ " - " +
                            cursor.getString(cursor.getColumnIndex(ServiceDAO.ColumnProductType)),
                    cursor.getString(cursor.getColumnIndex(WarehouseDAO.ColumnID))+" - "+
                            cursor.getString(cursor.getColumnIndex(WarehouseDAO.ColumnName)) +" - "+
                            cursor.getString(cursor.getColumnIndex(WarehouseDAO.ColumnStatus)),
                    cursor.getInt(cursor.getColumnIndex(ColumnQuantity)),
                    cursor.getInt(cursor.getColumnIndex(ColumnPrice))
            );
            billDetails.add(b);
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return billDetails;
    }
    @SuppressLint("Range")
    public Integer GetProfitByMonth(String month){
        SQLiteDatabase sqLiteDatabase = sqLite.getReadableDatabase();
        int profit =0;
        String query = "SELECT SUM("+ColumnPrice+") FROM "+TableName +
                " INNER JOIN "+
                BillDAO.TableName + " ON "+ TableName+"."+ColumnBillID + " = "+BillDAO.TableName+"."+BillDAO.ColumnID +
                " WHERE strftime('%m',"+BillDAO.TableName+"."+BillDAO.ColumnCreateDate+") =? "+
                "AND "+BillDAO.ColumnDelete +" = 'Chưa xóa'"+
                " AND "+BillDAO.TableName+"."+BillDAO.ColumnStatus+" = 'Đã giao'";
        Cursor cursor = sqLiteDatabase.rawQuery(query,new String[]{month});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            profit = cursor.getInt(0);
        }
        cursor.close();
        sqLiteDatabase.close();
        return profit;
    }
    @SuppressLint("Range")
    public Integer GetOneDayProfit(String day){
        SQLiteDatabase sqLiteDatabase = sqLite.getReadableDatabase();
        int profit =0;
        String query = "SELECT SUM("+ColumnPrice+") FROM "+TableName +
                " INNER JOIN "+
                BillDAO.TableName + " ON "+ TableName+"."+ColumnBillID + " = "+BillDAO.TableName+"."+BillDAO.ColumnID +
                " WHERE "+BillDAO.TableName+"."+BillDAO.ColumnCreateDate+" =?"+
                " AND "+BillDAO.TableName+"."+BillDAO.ColumnStatus+" = 'Đã giao'"
                +"AND "+BillDAO.TableName+"."+BillDAO.ColumnDelete+" = 'Chưa xóa' ";
        Cursor cursor = sqLiteDatabase.rawQuery(query,new String[]{day});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            profit = cursor.getInt(0);
        }
        cursor.close();
        sqLiteDatabase.close();
        return profit;
    }
    @SuppressLint("Range")
    public List<ProfitClass> GetOneMonthProfit(String month){
        SQLiteDatabase sqLiteDatabase = sqLite.getReadableDatabase();
        List<ProfitClass> profits = new ArrayList<>();
        String query = "SELECT SUM("+ColumnPrice+") AS THANHTIEN,"+BillDAO.ColumnCreateDate+" AS NGAY FROM "+TableName +
                " INNER JOIN "+
                BillDAO.TableName + " ON "+ TableName+"."+ColumnBillID + " = "+BillDAO.TableName+"."+BillDAO.ColumnID +
                " WHERE strftime('%m',"+BillDAO.TableName+"."+BillDAO.ColumnCreateDate+") =? "+
                "AND "+BillDAO.TableName+"."+BillDAO.ColumnDelete+" = 'Chưa xóa' "+
                " AND "+BillDAO.TableName+"."+BillDAO.ColumnStatus+" = 'Đã giao'"+
                "GROUP BY "+BillDAO.TableName+"."+BillDAO.ColumnCreateDate+
                " ORDER BY NGAY ASC";
        Cursor cursor = sqLiteDatabase.rawQuery(query,new String[]{month});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ProfitClass p = new ProfitClass(
                    cursor.getString(cursor.getColumnIndex("NGAY")),
                    cursor.getInt(cursor.getColumnIndex("THANHTIEN"))
            );
            profits.add(p);
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return profits;
    }
    public Integer GetAllDayProfit(){
        SQLiteDatabase sqLiteDatabase = sqLite.getReadableDatabase();
        int profit =0;
        String query = "SELECT SUM("+ColumnPrice+") FROM "+TableName +
                " INNER JOIN "+
                BillDAO.TableName + " ON "+ TableName+"."+ColumnBillID + " = "+BillDAO.TableName+"."+BillDAO.ColumnID +
                " WHERE "+BillDAO.TableName+"."+BillDAO.ColumnDelete+" = 'Chưa xóa'"+
                " AND "+BillDAO.TableName+"."+BillDAO.ColumnStatus+" = 'Đã giao'";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            profit += cursor.getInt(0);
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return profit;
    }
    public Boolean ResetUserDao (){
        SQLiteDatabase sqLiteDatabase = sqLite.getReadableDatabase();
        sqLiteDatabase.execSQL(DropTable);
        sqLiteDatabase.execSQL(CreateTable);
        return true;
    }
}
