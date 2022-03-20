package com.example.quanlygiatla_nhom1.Activity.SQLite.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlygiatla_nhom1.Activity.Class.BillClass;
import com.example.quanlygiatla_nhom1.Activity.SQLite.SQLite;

import java.util.ArrayList;
import java.util.List;

public class BillDAO {
public SQLite sqLite;
public BillDAO (Context context){
sqLite=new SQLite(context);
}

    public static String TableName = "HoaDon";
    public static String ColumnID = "MaHoaDon";
    public static String ColumnCreateDate = "NgayTao";
    public static String ColumnUserName = "TenDangNhap";
    public static String ColumnCustomerName = "TenKhachHang";
    public static String ColumnPhone = "SoDienThoai";
    public static String ColumnStatus = "TinhTrang";
    public static String ColumnDelete = "Xoa";
    public static final String CreateTable = "CREATE TABLE "
            +TableName+" ("
            +ColumnID+" TEXT PRIMARY KEY, "
            +ColumnCreateDate+" DATE, "
            +ColumnUserName+" TEXT REFERENCES "+ UserDAO.ColumnUserName+"("+UserDAO.TableName+") ON DELETE CASCADE ON UPDATE CASCADE, "
            +ColumnCustomerName+" TEXT, "
            +ColumnPhone+" TEXT, "
            +ColumnStatus+" TEXT, "
            +ColumnDelete+" TEXT"
            +")";
    public static final String DropTable = " DROP TABLE IF EXISTS " + TableName;
public boolean AddOneBill(BillClass bill){
String[] array=bill.getDate().split("/",3);
if (Integer.parseInt(array[0])<10){
array[0]="0"+array[0];
}
else {
array[0]=array[0];
}
if (Integer.parseInt(array[1])<10){
array[1]="0"+array[1];
}else{
array[1]=array[1];
}
String Date=array[2]+"-"+array[1]+"-"+array[0];
    SQLiteDatabase sqLiteDatabase=sqLite.getWritableDatabase();
    ContentValues cv=new ContentValues();
    cv.put(ColumnID,bill.getId());
    cv.put(ColumnCustomerName,bill.getCustomer_name());
    cv.put(ColumnPhone,bill.getCustomer_phone());
    cv.put(ColumnCreateDate,Date);
    cv.put(ColumnUserName,bill.getUser_name());
    cv.put(ColumnStatus,bill.getStatus());
    cv.put(ColumnDelete,bill.getDelete());
    long insert= sqLiteDatabase.insert(TableName,null,cv);
if (insert==-1){
    return false;
    }else
{return  true;}
}
    @SuppressLint("Range")
public List<BillClass> GetAllBill(){
SQLiteDatabase sqLiteDatabase=sqLite.getReadableDatabase();
List<BillClass> bills=new ArrayList<>();
String query ="SELECT * FROM "+TableName;
    Cursor cursor=sqLiteDatabase.rawQuery(query,null);
cursor.moveToFirst();
    while (!cursor.isAfterLast()){
         BillClass b = new BillClass(
                cursor.getString(cursor.getColumnIndex(ColumnID)),
                cursor.getString(cursor.getColumnIndex(ColumnCustomerName)),
                cursor.getString(cursor.getColumnIndex(ColumnPhone)),
                cursor.getString(cursor.getColumnIndex(ColumnCreateDate)),
                cursor.getString(cursor.getColumnIndex(ColumnUserName)),
                cursor.getString(cursor.getColumnIndex(ColumnStatus)),
                cursor.getString(cursor.getColumnIndex(ColumnDelete))
        );
        bills.add(b);
        cursor.moveToNext();
    }
cursor.close();
    sqLiteDatabase.close();
return bills;

}

public  Boolean UpdateStatusOneBill(BillClass bill){
SQLiteDatabase sqLiteDatabase=sqLite.getWritableDatabase();
ContentValues cv=new ContentValues();
    cv.put(ColumnID,bill.getId());
    cv.put(ColumnCustomerName,bill.getCustomer_name());
    cv.put(ColumnPhone,bill.getCustomer_phone());
    cv.put(ColumnCreateDate,bill.getDate());
    cv.put(ColumnUserName,bill.getUser_name());
    String status;
    if(bill.getStatus().trim().equalsIgnoreCase("Chưa giặt")){
        status="Đã giặt";
    } else if(bill.getStatus().trim().equalsIgnoreCase("Đã giặt")){
        status="Đã giao";
    } else{
        return false;
    }
cv.put(ColumnStatus,status);
cv.put(ColumnDelete,bill.getDelete());
    long update = sqLiteDatabase.update(TableName, cv, ColumnID+" = ?", new String[]{bill.getId()});
    sqLiteDatabase.close();
    if(update==-1){
        return false;
    }
    else{
        return true;
    }
}
    public Boolean UpdateOneBill(BillClass bill) {
        SQLiteDatabase sqLiteDatabase = sqLite.getWritableDatabase();
        ContentValues cv =  new ContentValues();
        cv.put(ColumnID,bill.getId());
        cv.put(ColumnCustomerName,bill.getCustomer_name());
        cv.put(ColumnPhone,bill.getCustomer_phone());
        cv.put(ColumnCreateDate,bill.getDate());
        cv.put(ColumnUserName,bill.getUser_name());
        cv.put(ColumnStatus,bill.getStatus());
        cv.put(ColumnDelete,bill.getDelete());
        long update = sqLiteDatabase.update(TableName, cv, ColumnID+" = ?", new String[]{bill.getId()});
        sqLiteDatabase.close();
        if(update==-1){
            return false;
        }
        else{
            return true;
        }
    }
    public Boolean DeleteOneBill(BillClass bill) {
        SQLiteDatabase sqLiteDatabase = sqLite.getWritableDatabase();
        ContentValues cv =  new ContentValues();
        cv.put(ColumnID,bill.getId());
        cv.put(ColumnCustomerName,bill.getCustomer_name());
        cv.put(ColumnPhone,bill.getCustomer_phone());
        cv.put(ColumnCreateDate,bill.getDate());
        cv.put(ColumnUserName,bill.getUser_name());
        cv.put(ColumnStatus,bill.getStatus());
        cv.put(ColumnDelete,"Đã xóa");
        long update = sqLiteDatabase.update(TableName, cv, ColumnID+" = ?", new String[]{bill.getId()});
        sqLiteDatabase.close();
        if(update==-1){
            return false;
        }
        else{
            return true;
        }
    }
    public Boolean ResetUserDao (){
        SQLiteDatabase sqLiteDatabase = sqLite.getReadableDatabase();
        sqLiteDatabase.execSQL(DropTable);
        sqLiteDatabase.execSQL(CreateTable);
        return true;
    }

}
