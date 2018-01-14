package com.example.aliasghar.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static  final String DATABASE_NAME = "MyDatabase.db";
    public static  final String TABLE_NAME = "Contact";

    public static  final String COL1= "ID";
    public static  final String COL2 = "NAME";
    public static  final String COL3 = "EMAIL";
    public static  final String COL4 = "PHONE";


    public DatabaseHelper(Context context ) {
        super(context, DATABASE_NAME, null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, EMAIL TEXT, PHONE TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String email, String phone)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,name);
        contentValues.put(COL3,email);
        contentValues.put(COL4,phone);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1)
            return  false;
        else
            return true;
    }

    public boolean deleteData (String id){
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.delete(TABLE_NAME,"ID ='" + id +"'  ",null);
        if(result == -1) {
            db.close();
            return false;
        }
        else {
            db.close();
            return true;
        }
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_NAME, null);
        return res;
    }

    public Cursor searchData(String phone)
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String query ="SELECT * FROM Contact WHERE PHONE ='" + phone +"' ";
        Cursor res =  sqLiteDatabase.rawQuery( query, null );
        return res;
    }

    public boolean updateData(String id,String name, String email, String phone)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1,id);
        contentValues.put(COL2,name);
        contentValues.put(COL3,email);
        contentValues.put(COL4,phone);

        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;

    }
}

