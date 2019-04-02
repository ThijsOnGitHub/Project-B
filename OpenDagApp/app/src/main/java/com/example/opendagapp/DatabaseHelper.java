package com.example.opendagapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_DATABASE_HROOPENDAY_VERSION = 1;
    private static final String DB_DATABASE_HROOPENDAY = "hro_openday.db";

    public static final String DB_TABLE_OPENDAY = "openday";
    public static final String DB_TABLE_OPENDAY_ID = "id"; // Primary key, autoincrement
    public static final String DB_TABLE_OPENDAY_DATE = "date"; // text
    public static final String DB_TABLE_OPENDAY_STARTTIME = "starttime"; // text
    public static final String DB_TABLE_OPENDAY_ENDTIME = "endtime"; // text
    public static final String DB_TABLE_OPENDAY_INSTITUTEFULLNAME = "institute_fullname"; // text

    public static final String DB_TABLE_INSTITUTE = "institute";
    public static final String DB_TABLE_INSTITUTE_ID = "id"; // Primary key, autoincrement
    public static final String DB_TABLE_INSTITUTE_FULLNAME = "fullname"; // Text
    public static final String DB_TABLE_INSTITUTE_SHORTNAME = "shortname"; // Text
    public static final String DB_TABLE_INSTITUTE_GENERALINFORMATION = "generalinformation"; // Text

    public static final String DB_TABLE_STUDY = "study";
    public static final String DB_TABLE_STUDY_ID = "id"; // Primary key, autoincrement
    public static final String DB_TABLE_STUDY_INSTITUTEFULLNAME = "institute_fullname"; // text
    public static final String DB_TABLE_STUDY_NAME = "name"; // text
    public static final String DB_TABLE_STUDY_TYPE = "type"; // text
    public static final String DB_TABLE_STUDY_LOCATIONADRESS = "location_address"; // text

    public static final String DB_TABLE_ACTIVITY = "activity";
    public static final String DB_TABLE_ACTIVITY_ID = "id"; // Primary key, autoincrement
    public static final String DB_TABLE_ACTIVITY_OPENDAYDATE = "openday_date"; // text
    public static final String DB_TABLE_ACTIVITY_STUDYNAME = "study_name"; // text
    public static final String DB_TABLE_ACTIVITY_STARTTIME = "starttime"; // text
    public static final String DB_TABLE_ACTIVITY_ENDTIME = "endtime"; // text
    public static final String DB_TABLE_ACTIVITY_CLASSROOM = "classroom"; // text
    public static final String DB_TABLE_ACTIVITY_INFORMATION = "information"; // text

    public static final String DB_TABLE_LOCATION = "location";
    public static final String DB_TABLE_LOCATION_ID = "id"; // Primary key, autoincrement
    public static final String DB_TABLE_LOCATION_ADDRESS = "address"; // text
    public static final String DB_TABLE_LOCATION_ZIPCODE = "zipcode"; // text
    public static final String DB_TABLE_LOCATION_PHONENUMBER = "phonenumber"; // text
    public static final String DB_TABLE_LOCATION_IMAGEDESCRIPRION = "image_description"; // text

    public static final String DB_TABLE_IMAGE = "image";
    public static final String DB_TABLE_IMAGE_ID = "id"; // Primary key, autoincrement
    public static final String DB_TABLE_IMAGE_FILENAME = "filename"; // text
    public static final String DB_TABLE_IMAGE_CONTEXT = "context"; // text
    public static final String DB_TABLE_IMAGE_DESCRIPTION = "description"; // text
    public static final String DB_TABLE_IMAGE_FLOORNUMBER = "floornumber"; // text

    public DatabaseHelper(Context context) {
        super(context, DB_DATABASE_HROOPENDAY, null, DB_DATABASE_HROOPENDAY_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DB_TABLE_OPENDAY + "(" + DB_TABLE_OPENDAY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DB_TABLE_OPENDAY_DATE + " TEXT, " + DB_TABLE_OPENDAY_STARTTIME + " TEXT, " + DB_TABLE_OPENDAY_ENDTIME + " TEXT, " + DB_TABLE_OPENDAY_INSTITUTEFULLNAME + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + DB_TABLE_INSTITUTE + "(" + DB_TABLE_INSTITUTE_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DB_TABLE_INSTITUTE_FULLNAME + " TEXT, " + DB_TABLE_INSTITUTE_SHORTNAME + " TEXT, " + DB_TABLE_INSTITUTE_GENERALINFORMATION + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + DB_TABLE_STUDY + "(" + DB_TABLE_STUDY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DB_TABLE_STUDY_INSTITUTEFULLNAME + " TEXT, " + DB_TABLE_STUDY_NAME + " TEXT, " + DB_TABLE_STUDY_TYPE + " TEXT, " + DB_TABLE_STUDY_LOCATIONADRESS + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + DB_TABLE_ACTIVITY + "(" + DB_TABLE_ACTIVITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DB_TABLE_ACTIVITY_OPENDAYDATE + " TEXT, " + DB_TABLE_ACTIVITY_STUDYNAME + " TEXT, " + DB_TABLE_ACTIVITY_STARTTIME + " TEXT, " + DB_TABLE_ACTIVITY_ENDTIME + " TEXT, " + DB_TABLE_ACTIVITY_CLASSROOM + " TEXT, " + DB_TABLE_ACTIVITY_INFORMATION + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + DB_TABLE_LOCATION + "(" + DB_TABLE_LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DB_TABLE_LOCATION_ADDRESS + " TEXT, " + DB_TABLE_LOCATION_ZIPCODE + " TEXT, " + DB_TABLE_LOCATION_PHONENUMBER + " TEXT, " + DB_TABLE_LOCATION_IMAGEDESCRIPRION + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + DB_TABLE_IMAGE + "(" + DB_TABLE_IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DB_TABLE_IMAGE_FILENAME + " TEXT, " + DB_TABLE_IMAGE_CONTEXT + " TEXT, " + DB_TABLE_IMAGE_DESCRIPTION + " TEXT, " + DB_TABLE_IMAGE_FLOORNUMBER + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_OPENDAY);
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_INSTITUTE);
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_STUDY);
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_ACTIVITY);
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_IMAGE);
        onCreate(db);
    }

    public boolean createOpenday(String date, String starttime, String endtime, String institute_fullname) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_TABLE_OPENDAY_DATE, date);
        contentValues.put(DB_TABLE_OPENDAY_STARTTIME, starttime);
        contentValues.put(DB_TABLE_OPENDAY_ENDTIME, endtime);
        contentValues.put(DB_TABLE_OPENDAY_INSTITUTEFULLNAME, institute_fullname);

        long result = db.insert(DB_TABLE_OPENDAY, null, contentValues);
        db.close();
        return result != -1; // if result == true then the values are inserted
    }

    public boolean createInstitute(String fullname, String shortname, String generalinformation) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_TABLE_INSTITUTE_FULLNAME, fullname);
        contentValues.put(DB_TABLE_INSTITUTE_SHORTNAME, shortname);
        contentValues.put(DB_TABLE_INSTITUTE_GENERALINFORMATION, generalinformation);

        long result = db.insert(DB_TABLE_INSTITUTE, null, contentValues);
        db.close();
        return result != -1; // if result == true then the values are inserted
    }

    public boolean createStudy(String institute_fullname, String name, String type, String location_address) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_TABLE_STUDY_INSTITUTEFULLNAME, institute_fullname);
        contentValues.put(DB_TABLE_STUDY_NAME, name);
        contentValues.put(DB_TABLE_STUDY_TYPE, type);
        contentValues.put(DB_TABLE_STUDY_LOCATIONADRESS, location_address);

        long result = db.insert(DB_TABLE_STUDY, null, contentValues);
        db.close();
        return result != -1; // if result == true then the values are inserted
    }

    public boolean createActivity(String openday_date, String study_name, String starttime, String endtime, String classroom, String information) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_TABLE_ACTIVITY_OPENDAYDATE, openday_date);
        contentValues.put(DB_TABLE_ACTIVITY_STUDYNAME, study_name);
        contentValues.put(DB_TABLE_ACTIVITY_STARTTIME, starttime);
        contentValues.put(DB_TABLE_ACTIVITY_ENDTIME, endtime);
        contentValues.put(DB_TABLE_ACTIVITY_CLASSROOM, classroom);
        contentValues.put(DB_TABLE_ACTIVITY_INFORMATION, information);

        long result = db.insert(DB_TABLE_ACTIVITY, null, contentValues);
        db.close();
        return result != -1; // if result == true then the values are inserted
    }

    public boolean createLocation(String address, String zipcode, String phonenumber, String image_description) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_TABLE_LOCATION_ADDRESS, address);
        contentValues.put(DB_TABLE_LOCATION_ZIPCODE, zipcode);
        contentValues.put(DB_TABLE_LOCATION_PHONENUMBER, phonenumber);
        contentValues.put(DB_TABLE_LOCATION_IMAGEDESCRIPRION, image_description);

        long result = db.insert(DB_TABLE_LOCATION, null, contentValues);
        db.close();
        return result != -1; // if result == true then the values are inserted
    }

    public boolean createImage(String filename, String context, String description, String floornumber) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_TABLE_IMAGE_FILENAME, filename);
        contentValues.put(DB_TABLE_IMAGE_CONTEXT, context);
        contentValues.put(DB_TABLE_IMAGE_DESCRIPTION, description);
        contentValues.put(DB_TABLE_IMAGE_FLOORNUMBER, floornumber);

        long result = db.insert(DB_TABLE_IMAGE, null, contentValues);
        db.close();
        return result != -1; // if result == true then the values are inserted
    }

    public Cursor viewOpendays() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + DB_TABLE_OPENDAY_ID + ", " + DB_TABLE_OPENDAY_DATE + ", " + DB_TABLE_OPENDAY_STARTTIME + ", " + DB_TABLE_OPENDAY_ENDTIME + ", " + DB_TABLE_OPENDAY_INSTITUTEFULLNAME + " FROM " + DB_TABLE_OPENDAY;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor viewInstitutes() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + DB_TABLE_INSTITUTE_ID + ", " + DB_TABLE_INSTITUTE_FULLNAME + ", " + DB_TABLE_INSTITUTE_SHORTNAME + ", " + DB_TABLE_INSTITUTE_GENERALINFORMATION + " FROM " + DB_TABLE_INSTITUTE;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor viewStudies() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + DB_TABLE_STUDY_ID + ", " + DB_TABLE_STUDY_INSTITUTEFULLNAME + ", " + DB_TABLE_STUDY_NAME + ", " +DB_TABLE_STUDY_TYPE + ", " + DB_TABLE_STUDY_LOCATIONADRESS + " FROM " + DB_TABLE_STUDY;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor viewActivities() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + DB_TABLE_ACTIVITY_ID + ", " + DB_TABLE_ACTIVITY_OPENDAYDATE + ", " + DB_TABLE_ACTIVITY_STUDYNAME + ", " + DB_TABLE_ACTIVITY_STARTTIME + ", " + DB_TABLE_ACTIVITY_ENDTIME + ", " + DB_TABLE_ACTIVITY_CLASSROOM + ", " + DB_TABLE_ACTIVITY_INFORMATION + " FROM " + DB_TABLE_ACTIVITY;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor viewLocations() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + DB_TABLE_LOCATION_ID + ", " + DB_TABLE_LOCATION_ADDRESS + ", " + DB_TABLE_LOCATION_ZIPCODE + ", " + DB_TABLE_LOCATION_PHONENUMBER + ", " + DB_TABLE_LOCATION_IMAGEDESCRIPRION + " FROM " + DB_TABLE_LOCATION;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor viewImages() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + DB_TABLE_IMAGE_ID + ", " + DB_TABLE_IMAGE_FILENAME + ", " + DB_TABLE_IMAGE_CONTEXT + ", " + DB_TABLE_IMAGE_DESCRIPTION + ", " + DB_TABLE_IMAGE_FLOORNUMBER + " FROM " + DB_TABLE_IMAGE;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
}
