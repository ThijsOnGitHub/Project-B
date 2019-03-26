package com.example.opendagapp;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_DATABASE_VERSION = 1;
    private static final String DB_DATABASE_NAME = "hro_opendays.db";
        public static final String DB_TABLE_INSTITUTE = "institute";
            public static final String DB_TABLE_INSTITUTE_ID = "id"; // Primary key, autoincrement
            public static final String DB_TABLE_INSTITUTE_NAME = "name"; // Text
            public static final String DB_TABLE_INSTITUTE_CONTACTINFO = "contact_info"; // Text
            public static final String DB_TABLE_INSTITUTE_TEXT = "text"; // Text
            public static final String DB_TABLE_INSTITUTE_DATE = "date"; // Text
        public static final String DB_TABLE_INSTITUTEADDRESS = "institute_address";
            public static final String DB_TABLE_INSTITUTEADDRESS_ID = "id"; // Primary key, autoincrement
            public static final String DB_TABLE_INSTITUTEADDRESS_INSTITUTEID = "institute_id"; // int
            public static final String DB_TABLE_INSTITUTEADDRESS_ADDRESS = "address"; // text
            public static final String DB_TABLE_INSTITUTEADDRESS_NUMBERFLOORS = "number_floors"; // int
        public static final String DB_TABLE_STUDY = "study_programs";
            public static final String DB_TABLE_STUDY_ID = "id"; // Primary key, autoincrement
            public static final String DB_TABLE_STUDY_STUDYNAME = "study_name"; // text
            public static final String DB_TABLE_STUDY_STUDYTYPE = "study_type"; // text
            public static final String DB_TABLE_STUDY_INSTITUTEID = "institute_id"; // int
            public static final String DB_TABLE_STUDY_LOCATION = "location"; // text
        public static final String DB_TABLE_STUDYACTIVITIES = "study_activities";
            public static final String DB_TABLE_STUDYACTIVITIES_ID = "id"; // Primary key, autoincrement
            public static final String DB_TABLE_STUDYACTIVITIES_STUDYID = "study_id"; // int
            public static final String DB_TABLE_STUDYACTIVITIES_LOCATION = "location"; // text
            public static final String DB_TABLE_STUDYACTIVITIES_TIME = "time"; // text
            public static final String DB_TABLE_STUDYACTIVITIES_INFORMATION = "information"; // text

    public DatabaseHelper(Context context) {
        super(context, DB_DATABASE_NAME, null, DB_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
