package com.example.quanlygiatla_nhom1.Activity.SQLite.DAO;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlygiatla_nhom1.Activity.Class.UserClass;
import com.example.quanlygiatla_nhom1.Activity.SQLite.SQLite;

import java.util.ArrayList;
import java.util.List;

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
    public boolean AddOneUser(UserClass user){
        SQLiteDatabase sqLiteDatabase = sqLite.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ColumnName,user.getName());
        cv.put(ColumnPhone,user.getPhone());
        cv.put(ColumnUserName,user.getUserName());
        cv.put(ColumnPassword,user.getPassword());
        cv.put(ColumnRole,user.getRole());
        cv.put(ColumnDelete,user.getDelete());
        long insert = sqLiteDatabase.insert(TableName,null,cv);
        if(insert==-1){
            return false;
        }else{
            return true;
        }
    }
    public String ChangePassword( String UserName,String OldPassword,String NewPassword){
        SQLiteDatabase sqLiteDatabase = sqLite.getWritableDatabase();
        ContentValues cv = new ContentValues();
        UserClass u = FindOneUser(UserName);
        if(!OldPassword.equalsIgnoreCase(u.getPassword())){
            return "Mật khẩu cũ không đúng";
        }else{
            cv.put(ColumnName,u.getName());
            cv.put(ColumnPhone,u.getPhone());
            cv.put(ColumnUserName,u.getUserName());
            cv.put(ColumnPassword,NewPassword);
            cv.put(ColumnRole,u.getRole());
            cv.put(ColumnDelete,u.getDelete());
            long update = sqLiteDatabase.update(TableName,cv,ColumnUserName+"=?",new String[]{UserName});
            if(update==-1){
                return "Thay đổi mật khẩu thất bại";
            }else{
                return "Thay đổi mật khẩu thành công";
            }
        }
    }
    public Boolean UpdateUser(String UserName, String Name, String Phone){
        SQLiteDatabase sqLiteDatabase = sqLite.getWritableDatabase();
        ContentValues cv = new ContentValues();
        UserClass u = FindOneUser(UserName);
        cv.put(ColumnName,Name);
        cv.put(ColumnPhone,Phone);
        cv.put(ColumnUserName,u.getUserName());
        cv.put(ColumnPassword,u.getPassword());
        cv.put(ColumnRole,u.getRole());
        cv.put(ColumnDelete,u.getDelete());
        long update = sqLiteDatabase.update(TableName,cv,ColumnUserName+"=?",new String[]{UserName});
        if(update==-1){
            return false;
        }else{
            return true;
        }
    }
    public Boolean DeleteUser(String UserName){
        SQLiteDatabase sqLiteDatabase = sqLite.getWritableDatabase();
        ContentValues cv = new ContentValues();
        UserClass u = FindOneUser(UserName);
        cv.put(ColumnName,u.getName());
        cv.put(ColumnPhone,u.getPhone());
        cv.put(ColumnUserName,u.getUserName());
        cv.put(ColumnPassword,u.getPassword());
        cv.put(ColumnRole,u.getRole());
        cv.put(ColumnDelete,"Đã xóa");
        long update = sqLiteDatabase.update(TableName,cv,ColumnUserName+"=?",new String[]{UserName});
        if(update==-1){
            return false;
        }else{
            return true;
        }
    }
    public String ResetPassword( String UserName,String NewPassword){
        SQLiteDatabase db = sqLite.getWritableDatabase();
        ContentValues cv = new ContentValues();
        UserClass u = FindOneUser(UserName);
        cv.put(ColumnName,u.getName());
        cv.put(ColumnPhone,u.getPhone());
        cv.put(ColumnUserName,u.getUserName());
        cv.put(ColumnPassword,NewPassword);
        cv.put(ColumnRole,u.getRole());
        cv.put(ColumnDelete,u.getDelete());
        long update = db.update(TableName,cv,ColumnUserName+"=?",new String[]{UserName});
        if(update==-1){
            return "Thay đổi mật khẩu thất bại";
        }else{
            return "Thay đổi mật khẩu thành công";
        }
    }
    @SuppressLint("Range")
    public UserClass FindOneUser (String UserName){
        SQLiteDatabase sqLiteDatabase = sqLite.getReadableDatabase();
        UserClass u;
        String query = "SELECT * FROM "+TableName+" WHERE "+ColumnUserName+" =?";
        Cursor cursor = sqLiteDatabase.rawQuery(query,new String[]{UserName});
        cursor.moveToFirst();
        u = new UserClass(
                cursor.getString(cursor.getColumnIndex(ColumnName)),
                cursor.getString(cursor.getColumnIndex(ColumnPhone)),
                cursor.getString(cursor.getColumnIndex(ColumnUserName)),
                cursor.getString(cursor.getColumnIndex(ColumnPassword)),
                cursor.getString(cursor.getColumnIndex(ColumnRole)),
                cursor.getString(cursor.getColumnIndex(ColumnDelete))
        );
        cursor.close();
        return u;
    }
    @SuppressLint("Range")
    public List<UserClass> GetAllUser(){
        List<UserClass> users = new ArrayList<>();
        String query = "SELECT * FROM "+TableName+" WHERE "+ColumnRole+" = 'User'";
        SQLiteDatabase sqLiteDatabase = sqLite.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            UserClass u = new UserClass(
                    cursor.getString(cursor.getColumnIndex(ColumnName)),
                    cursor.getString(cursor.getColumnIndex(ColumnPhone)),
                    cursor.getString(cursor.getColumnIndex(ColumnUserName)),
                    cursor.getString(cursor.getColumnIndex(ColumnPassword)),
                    cursor.getString(cursor.getColumnIndex(ColumnRole)),
                    cursor.getString(cursor.getColumnIndex(ColumnDelete))
            );
            users.add(u);
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return users;
    }
    @SuppressLint("Range")
    public List<UserClass> GetAllNotDeleteUser(){
        List<UserClass> users = new ArrayList<>();
        String query = "SELECT * FROM "+TableName+" WHERE "+ColumnDelete+" = 'Chưa xóa'";
        SQLiteDatabase sqLiteDatabase = sqLite.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            UserClass u = new UserClass(
                    cursor.getString(cursor.getColumnIndex(ColumnName)),
                    cursor.getString(cursor.getColumnIndex(ColumnPhone)),
                    cursor.getString(cursor.getColumnIndex(ColumnUserName)),
                    cursor.getString(cursor.getColumnIndex(ColumnPassword)),
                    cursor.getString(cursor.getColumnIndex(ColumnRole)),
                    cursor.getString(cursor.getColumnIndex(ColumnDelete))
            );
            users.add(u);
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return users;
    }

    @SuppressLint("Range")
    public String Login(String userName, String password){
        String query = "SELECT * FROM "+TableName+" WHERE "+ColumnUserName+" =? AND "+ColumnDelete+" = 'Chưa xóa'";
        SQLiteDatabase sqLiteDatabase = sqLite.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,new String[]{userName});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            UserClass u = new UserClass(
                    cursor.getString(cursor.getColumnIndex(ColumnName)),
                    cursor.getString(cursor.getColumnIndex(ColumnPhone)),
                    cursor.getString(cursor.getColumnIndex(ColumnUserName)),
                    cursor.getString(cursor.getColumnIndex(ColumnPassword)),
                    cursor.getString(cursor.getColumnIndex(ColumnRole)),
                    cursor.getString(cursor.getColumnIndex(ColumnDelete))
            );
            cursor.close();
            sqLiteDatabase.close();
            if(u.getUserName().equals(userName) && u.getPassword().equals(password)){
                if(u.getRole().equalsIgnoreCase("User")){
                    return "User";
                } else if(u.getRole().equalsIgnoreCase("Admin")){
                    return "Admin";
                } else{
                    return "Wrong";
                }
            }else{
                return "Wrong";
            }
        }else{
            return "Wrong";
        }
    }

    public Boolean ResetUserDao (){
        SQLiteDatabase sqLiteDatabase = sqLite.getReadableDatabase();
        sqLiteDatabase.execSQL(DropTable);
        sqLiteDatabase.execSQL(CreateTable);
        return true;
    }


}
