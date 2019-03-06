package com.example.deopendagapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    /*

    DATABASE NAME = 'student.db'
        - TABLE NAME = 'student_table'
            =====================================
            | ID    | NAME  | SURNAME   | MARKS |
            =====================================
            |       |       |           |       |
            -------------------------------------
    */

    private  static final int DATABASE_VERSION = 2; // when updating structure increase this number by 1
    private static final String DATABASE_NAME = "student.db";
        public static final String TABLE_NAME_1 = "student_table";
            public static final String COL_1 = "ID";
            public static final String COL_2 = "NAME";
            public static final String COL_3 = "SURNAME";
            public static final String COL_4 = "MARKS";



    // CREATE DATABASE WHEN CALLED
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // CREATE TABLE WHEN CALLED
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME_1 + "(" +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT, " +
                COL_3 + " TEXT, " +
                COL_4 + " INTEGER" +
                ")";

        db.execSQL(query);
    }

    // DELETE TABLE AND CREATE AGAIN WHEN CALLED
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
        onCreate(db);
    }


    public boolean insertData(String col2_value, String col3_value, String col4_value) {
        SQLiteDatabase db = this.getWritableDatabase();

        // columns, values stored in contentvalues
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, col2_value);
        contentValues.put(COL_3, col3_value);
        contentValues.put(COL_4, col4_value);

        long result = db.insert(TABLE_NAME_1, null, contentValues);

        if (result == -1) {
            return false; // error, not inserted
        } else {
            return true; // inserted
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME_1, null);
        return res;
    }

    public boolean updateData(String id, String name, String surname, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();

        // columns, values stored in contentvalues
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, surname);
        contentValues.put(COL_4, marks);

        db.update(TABLE_NAME_1, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    public boolean deleteData(String id, String name, String surname, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME_1, "ID = ?", new String[]{id});
        return true;
    }
}
