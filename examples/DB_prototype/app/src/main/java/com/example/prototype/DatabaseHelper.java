package com.example.prototype;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// https://www.youtube.com/watch?v=cp2rL3sAFmI&list=PLS1QulWo1RIaRdy16cOzBO5Jr6kEagA07

public class DatabaseHelper extends SQLiteOpenHelper {

    /*

    DATABASE NAME = 'open_days.db'
        - TABLE NAME = 'workshops'
            ===============================================
            | ID | ROOMCODE | STUDY | STARTTIME | SUBJECT |
            ===============================================
            |    |          |       |           |         |
            -----------------------------------------------
    */

    private static final int DB_DATABASE_VERSION = 1; // when updating structure increase this number by 1
    private static final String DB_DATABASE_OPEN_DAYS = "workshops.db";
    public static final String DB_OPEN_DAYS_TABLE_WORKSHOPS = "workshops";
    public static final String DB_OPEN_DAYS_TABLE_WORKSHOPS_COL_ID = "ID";
    public static final String DB_OPEN_DAYS_TABLE_WORKSHOPS_COL_ROOMCODE = "ROOMCODE";
    public static final String DB_OPEN_DAYS_TABLE_WORKSHOPS_COL_STUDY = "STUDY";
    public static final String DB_OPEN_DAYS_TABLE_WORKSHOPS_COL_STARTDATETIME = "STARTTIME";
    public static final String DB_OPEN_DAYS_TABLE_WORKSHOPS_COL_SUBJECT = "SUBJECT";



    // CREATE DATABASE WHEN CALLED
    public DatabaseHelper(Context context) {
        super(context, DB_DATABASE_OPEN_DAYS, null, DB_DATABASE_VERSION);
    }

    // CREATE TABLE WHEN CALLED
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DB_OPEN_DAYS_TABLE_WORKSHOPS + "(" +
                DB_OPEN_DAYS_TABLE_WORKSHOPS_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DB_OPEN_DAYS_TABLE_WORKSHOPS_COL_ROOMCODE + " TEXT, " +
                DB_OPEN_DAYS_TABLE_WORKSHOPS_COL_STUDY + " TEXT, " +
                DB_OPEN_DAYS_TABLE_WORKSHOPS_COL_STARTDATETIME + " TEXT, " +
                DB_OPEN_DAYS_TABLE_WORKSHOPS_COL_SUBJECT + " TEXT" +
                ")";

        db.execSQL(query);
    }

    // DELETE TABLE AND CREATE AGAIN WHEN CALLED
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_OPEN_DAYS_TABLE_WORKSHOPS);
        onCreate(db);
    }

    public boolean insertData(String roomcode, String study, String startdatetime, String subject) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_OPEN_DAYS_TABLE_WORKSHOPS_COL_ROOMCODE, roomcode);
        contentValues.put(DB_OPEN_DAYS_TABLE_WORKSHOPS_COL_STUDY, study);
        contentValues.put(DB_OPEN_DAYS_TABLE_WORKSHOPS_COL_STARTDATETIME, startdatetime);
        contentValues.put(DB_OPEN_DAYS_TABLE_WORKSHOPS_COL_SUBJECT, subject);

        long result = db.insert(DB_OPEN_DAYS_TABLE_WORKSHOPS, null, contentValues);

        return result != -1;
    }

    public Cursor viewData() {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + DB_OPEN_DAYS_TABLE_WORKSHOPS;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }
}
