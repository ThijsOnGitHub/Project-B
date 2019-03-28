package com.example.opendagapp;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_DATABASE_VERSION = 1;
    private static final String DB_DATABASE_HROOPENDAY = "hro_openday.db";
        public static final String DB_TABLE_OPENDAY = "openday";
            public static final String DB_TABLE_OPENDAY_ID = "id"; // Primary key, autoincrement
            public static final String DB_TABLE_OPENDAY_DATE = "date"; // text
            public static final String DB_TABLE_OPENDAY_STARTTIME = "start_time"; // text
            public static final String DB_TABLE_OPENDAY_ENDTIME = "end_time"; // text
            public static final String DB_TABLE_OPENDAY_INSTITUTEFULLNAME = "institute_fullname"; // text

        public static final String DB_TABLE_INSTITUTE = "institute";
            public static final String DB_TABLE_INSTITUTE_ID = "id"; // Primary key, autoincrement
            public static final String DB_TABLE_INSTITUTE_FULLNAME = "full_name"; // text
            public static final String DB_TABLE_INSTITUTE_SHORTNAME = "short_name"; // text
            public static final String DB_TABLE_INSTITUTE_GENERALINFORMATION = "general_information"; // text

        public static final String DB_TABLE_STUDY = "study";
            public static final String DB_TABLE_STUDY_ID = "id"; // Primary key, autoincrement
            public static final String DB_TABLE_STUDY_INSTITUTEFULLNAME = "institute_fullname"; // text
            public static final String DB_TABLE_STUDY_NAME = "name"; // text
            public static final String DB_TABLE_STUDY_TYPE = "type"; // text
            public static final String DB_TABLE_STUDY_LOCATIONADRESS = "location_address"; // text

        public static final String DB_TABLE_LOCATION = "location";
            public static final String DB_TABLE_LOCATION_ID = "id"; // Primary key, autoincrement
            public static final String DB_TABLE_LOCATION_ADDRESS = "address"; // text
            public static final String DB_TABLE_LOCATION_ZIPCODE = "zipcode"; // text
            public static final String DB_TABLE_LOCATION_PHONENUMBER = "phone_number"; // text
            public static final String DB_TABLE_LOCATION_IMAGEDESCRIPRION = "image_description"; // text

        public static final String DB_TABLE_IMAGE = "image";
            public static final String DB_TABLE_IMAGE_ID = "id"; // Primary key, autoincrement
            public static final String DB_TABLE_IMAGE_FILENAME = "filename"; // text
            public static final String DB_TABLE_IMAGE_CONTEXT = "context"; // text
            public static final String DB_TABLE_IMAGE_DESCRIPTION = "description"; // text
            public static final String DB_TABLE_IMAGE_FLOORNUMBER = "floor_number"; // int

        public static final String DB_TABLE_ACTIVITY = "activity";
            public static final String DB_TABLE_ACTIVITY_ID = "id"; // Primary key, autoincrement
            public static final String DB_TABLE_ACTIVITY_STUDYNAME = "study_name"; // text
            public static final String DB_TABLE_ACTIVITY_OPENDAYDATE = "openday_date"; // text
            public static final String DB_TABLE_ACTIVITY_STARTTIME = "start_time"; // text
            public static final String DB_TABLE_ACTIVITY_ENDTIME = "end_time"; // text
            public static final String DB_TABLE_ACTIVITY_CLASSROOM = "classroom"; // text
            public static final String DB_TABLE_ACTIVITY_INFORMATION = "information"; // text

    public DatabaseHelper(Context context) {
        super(context, DB_DATABASE_HROOPENDAY, null, DB_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DB_TABLE_OPENDAY + "(" +
                            DB_TABLE_OPENDAY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            DB_TABLE_OPENDAY_DATE + " TEXT, " +
                            DB_TABLE_OPENDAY_STARTTIME + " TEXT, " +
                            DB_TABLE_OPENDAY_ENDTIME + " TEXT, " +
                            DB_TABLE_OPENDAY_INSTITUTEFULLNAME + " TEXT" +
                        ")";
        db.execSQL(query);

        query = "CREATE TABLE " + DB_TABLE_INSTITUTE + "(" +
                    DB_TABLE_INSTITUTE_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DB_TABLE_INSTITUTE_FULLNAME + " TEXT, " +
                    DB_TABLE_INSTITUTE_SHORTNAME + " TEXT, " +
                    DB_TABLE_INSTITUTE_GENERALINFORMATION + " TEXT" +
                ")";
        db.execSQL(query);

        query = "CREATE TABLE " + DB_TABLE_STUDY + "(" +
                    DB_TABLE_STUDY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DB_TABLE_STUDY_INSTITUTEFULLNAME + " TEXT, " +
                    DB_TABLE_STUDY_NAME + " TEXT, " +
                    DB_TABLE_STUDY_TYPE + " TEXT, " +
                    DB_TABLE_STUDY_LOCATIONADRESS + " TEXT" +
                ")";
        db.execSQL(query);

        query = "CREATE TABLE " + DB_TABLE_LOCATION + "(" +
                    DB_TABLE_LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DB_TABLE_LOCATION_ADDRESS + " TEXT, " +
                    DB_TABLE_LOCATION_ZIPCODE + " TEXT, " +
                    DB_TABLE_LOCATION_PHONENUMBER + " TEXT, " +
                    DB_TABLE_LOCATION_IMAGEDESCRIPRION + " TEXT" +
                ")";
        db.execSQL(query);

        query = "CREATE TABLE " + DB_TABLE_IMAGE + "(" +
                    DB_TABLE_IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DB_TABLE_IMAGE_FILENAME + " TEXT, " +
                    DB_TABLE_IMAGE_CONTEXT + " TEXT, " +
                    DB_TABLE_IMAGE_DESCRIPTION + " TEXT, " +
                    DB_TABLE_IMAGE_FLOORNUMBER + " INTEGER" +
                ")";
        db.execSQL(query);

        query = "CREATE TABLE " + DB_TABLE_ACTIVITY + "(" +
                    DB_TABLE_ACTIVITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DB_TABLE_ACTIVITY_OPENDAYDATE + " TEXT, " +
                    DB_TABLE_ACTIVITY_STUDYNAME + " TEXT, " +
                    DB_TABLE_ACTIVITY_STARTTIME + " TEXT, " +
                    DB_TABLE_ACTIVITY_ENDTIME + " TEXT, " +
                    DB_TABLE_ACTIVITY_CLASSROOM + " TEXT, " +
                    DB_TABLE_ACTIVITY_INFORMATION + " TEXT" +
                ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_OPENDAY);
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_INSTITUTE);
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_STUDY);
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_IMAGE);
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_ACTIVITY);
        onCreate(db);
    }
}
