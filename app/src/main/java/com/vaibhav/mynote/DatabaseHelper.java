package com.vaibhav.mynote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "SQLiteExample.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "dailyTasks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "task";
    public static final String COLUMN_DATE = "date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_NAME + " TEXT, " + COLUMN_DATE + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String id , String tasks , String date){
        SQLiteDatabase myDb = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID , id);
        values.put(COLUMN_NAME , tasks);
        values.put(COLUMN_DATE , date);
        myDb.insert(TABLE_NAME , null , values);
        return  true;
    }

   public Cursor getALLdata(){
        SQLiteDatabase myDb = getReadableDatabase();
        Cursor res = myDb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
   }

    public Integer deleteData(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }
}
