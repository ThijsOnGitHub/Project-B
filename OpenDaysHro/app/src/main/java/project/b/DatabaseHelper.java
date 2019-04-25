package project.b;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_DATABASE_HROOPENDAY_VERSION = 1;
    private static final String DB_DATABASE_HROOPENDAY = "hro_openday.db";

    public static final String DB_TABLE_OPENDAY = "openday";
    public static final String DB_TABLE_OPENDAY_ID = "id";
    public static final String DB_TABLE_OPENDAY_DATE = "date";
    public static final String DB_TABLE_OPENDAY_STARTTIME = "starttime";
    public static final String DB_TABLE_OPENDAY_ENDTIME = "endtime";
    public static final String DB_TABLE_OPENDAY_INSTITUTEFULLNAME = "institute_fullname";

    public static final String DB_TABLE_INSTITUTE = "institute";
    public static final String DB_TABLE_INSTITUTE_ID = "id";
    public static final String DB_TABLE_INSTITUTE_FULLNAME = "fullname";
    public static final String DB_TABLE_INSTITUTE_SHORTNAME = "shortname";
    public static final String DB_TABLE_INSTITUTE_GENERALINFORMATION_ENGLISH = "generalinformation_english";
    public static final String DB_TABLE_INSTITUTE_GENERALINFORMATION_DUTCH = "generalinformation_dutch";

    public static final String DB_TABLE_STUDY = "study";
    public static final String DB_TABLE_STUDY_ID = "id";
    public static final String DB_TABLE_STUDY_GENERALINFORMATION_ENGLISH = "generalinformation_english";
    public static final String DB_TABLE_STUDY_GENERALINFORMATION_DUTCH = "generalinformation_dutch";
    public static final String DB_TABLE_STUDY_INSTITUTEFULLNAME = "institute_fullname";
    public static final String DB_TABLE_STUDY_NAME_DUTCH = "name_dutch";
    public static final String DB_TABLE_STUDY_NAME_ENGLISH = "name_english";
    public static final String DB_TABLE_STUDY_TYPE = "type";

    public static final String DB_TABLE_ACTIVITY = "activity";
    public static final String DB_TABLE_ACTIVITY_ID = "id";
    public static final String DB_TABLE_ACTIVITY_OPENDAYDATE = "openday_date";
    public static final String DB_TABLE_ACTIVITY_STUDYNAME_DUTCH = "study_name_dutch";
    public static final String DB_TABLE_ACTIVITY_STARTTIME = "starttime";
    public static final String DB_TABLE_ACTIVITY_ENDTIME = "endtime";
    public static final String DB_TABLE_ACTIVITY_CLASSROOM = "classroom";
    public static final String DB_TABLE_ACTIVITY_INFORMATION_ENGLISH = "information_english";
    public static final String DB_TABLE_ACTIVITY_INFORMATION_DUTCH = "information_dutch";

    public static final String DB_TABLE_LOCATION = "location";
    public static final String DB_TABLE_LOCATION_ID = "id";
    public static final String DB_TABLE_LOCATION_INSTITUTEFULLNAME = "institute_fullname";
    public static final String DB_TABLE_LOCATION_STREET = "street";
    public static final String DB_TABLE_LOCATION_CITY = "city";
    public static final String DB_TABLE_LOCATION_ZIPCODE = "zipcode";
    public static final String DB_TABLE_LOCATION_PHONENUMBER = "phonenumber";
    public static final String DB_TABLE_LOCATION_IMAGEDESCRIPRION = "image_description";

    public static final String DB_TABLE_IMAGE = "image";
    public static final String DB_TABLE_IMAGE_ID = "id";
    public static final String DB_TABLE_IMAGE_FILENAME = "filename";
    public static final String DB_TABLE_IMAGE_CONTEXT = "context";
    public static final String DB_TABLE_IMAGE_DESCRIPTION = "description";
    public static final String DB_TABLE_IMAGE_FLOORNUMBER = "floornumber";

    // APP FUNCTIONS
    public void fillDatabase() {
        // Create CMI
        createInstitute("Communicatie, Media en Informatietechnologie", "CMI", "The School of Communication, Media and Information Technology (CMI) provides higher education and applied research for the creative industry. As a committed partner CMI creates knowledge, skills and expertise for the ongoing development of the industry.", "Het instituut voor Communicatie, Media en Informatietechnologie (CMI) heeft met de opleidingen Communicatie, Informatica, Technische Informatica, Creative Media and Game Technologies en Communication and Multimedia Design maar liefst 3000 studenten die een waardevolle bijdrage leveren aan de onbegrensde wereld van communicatie, media en ICT.");
        // CMI Studies
        createStudy("Communicatie, Media en Informatietechnologie", "Informatica", "Software engineering", "Full-time");
        createStudy("Communicatie, Media en Informatietechnologie", "Informatica", "Software engineering", "Part-time");
        createStudy("Communicatie, Media en Informatietechnologie", "Technisch Informatica", "Computer engineering", "Full-time");
        createStudy("Communicatie, Media en Informatietechnologie", "Creative Media and Game Technologies", "Creative Media and Game Technologies", "Full-time");
        createStudy("Communicatie, Media en Informatietechnologie", "Communicatie", "Communication","Full-time");
        createStudy("Communicatie, Media en Informatietechnologie", "Communicatie", "Communication","Part-time");
        createStudy("Communicatie, Media en Informatietechnologie", "Communication & Multimedia Design", "Communication & Multimedia Design", "Full-time");
        // CMI locations
        createLocation("Wijnhaven 107", "Rotterdam", "Communicatie, Media en Informatietechnologie", "3011WN", "0107944000", "rotterdamwijnhaven107");
        createLocation("Wijnhaven 103", "Rotterdam", "Communicatie, Media en Informatietechnologie", "3011WN", "0107944000", "rotterdamwijnhaven103");
        createLocation("Wijnhaven 99", "Rotterdam", "Communicatie, Media en Informatietechnologie", "3011WN","0107944000", "rotterdamwijnhaven99");
        // CMI Images
        createImage("h1070e.png", "plattegrond 0e wijnhaven 107 rotterdam", "rotterdamwijnhaven107", "0");
        createImage("h1071e.png", "plattegrond 1e wijnhaven 107 rotterdam", "rotterdamwijnhaven107", "1");
        createImage("h1072e.png", "plattegrond 2e wijnhaven 107 rotterdam", "rotterdamwijnhaven107", "2");
        createImage("h1073e.png", "plattegrond 3e wijnhaven 107 rotterdam", "rotterdamwijnhaven107", "3");
        createImage("h1074e.png", "plattegrond 4e wijnhaven 107 rotterdam", "rotterdamwijnhaven107", "4");
        createImage("h1075e.png", "plattegrond 5e wijnhaven 107 rotterdam", "rotterdamwijnhaven107", "5");
        createImage("h1076e.png", "plattegrond 6e wijnhaven 107 rotterdam", "rotterdamwijnhaven107", "6");
        createImage("wd1030e.png", "plattegrond 0e wijnhaven 103 rotterdam", "rotterdamwijnhaven103", "0");
        createImage("wd1031e.png", "plattegrond 1e wijnhaven 103 rotterdam", "rotterdamwijnhaven103", "1");
        createImage("wd1032e.png", "plattegrond 2e wijnhaven 103 rotterdam", "rotterdamwijnhaven103", "2");
        createImage("wd1033e.png", "plattegrond 3e wijnhaven 103 rotterdam", "rotterdamwijnhaven103", "3");
        createImage("wd1034e.png", "plattegrond 4e wijnhaven 103 rotterdam", "rotterdamwijnhaven103", "4");
        createImage("wd1035e.png", "plattegrond 5e wijnhaven 103 rotterdam", "rotterdamwijnhaven103", "5");
        createImage("wd1036e.png", "plattegrond 6e wijnhaven 103 rotterdam", "rotterdamwijnhaven103", "6");
        createImage("wn990e.png", "plattegrond 0e wijnhaven 99 rotterdam", "rotterdamwijnhaven99", "0");
        createImage("wn991e.png", "plattegrond 1e wijnhaven 99 rotterdam", "rotterdamwijnhaven99", "1");
        createImage("wn992e.png", "plattegrond 2e wijnhaven 99 rotterdam", "rotterdamwijnhaven99", "2");
        createImage("wn993e.png", "plattegrond 3e wijnhaven 99 rotterdam", "rotterdamwijnhaven99", "3");
        createImage("wn994e.png", "plattegrond 4e wijnhaven 99 rotterdam", "rotterdamwijnhaven99", "4");
        createImage("wn995e.png", "plattegrond 5e wijnhaven 99 rotterdam", "rotterdamwijnhaven99", "5");
    }
    public Boolean emptyDatabase() {
        Boolean empty = true;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM " + DB_TABLE_OPENDAY, null);
        if (cur != null && cur.moveToFirst()) {
            empty = (cur.getInt (0) == 0);
        }

        if (empty == true) {
            cur = db.rawQuery("SELECT COUNT(*) FROM " + DB_TABLE_INSTITUTE, null);
            if (cur != null && cur.moveToFirst()) {
                empty = (cur.getInt (0) == 0);
            }
        }

        if (empty == true) {
            cur = db.rawQuery("SELECT COUNT(*) FROM " + DB_TABLE_STUDY, null);
            if (cur != null && cur.moveToFirst()) {
                empty = (cur.getInt (0) == 0);
            }
        }

        if (empty == true) {
            cur = db.rawQuery("SELECT COUNT(*) FROM " + DB_TABLE_LOCATION, null);
            if (cur != null && cur.moveToFirst()) {
                empty = (cur.getInt (0) == 0);
            }
        }

        if (empty == true) {
            cur = db.rawQuery("SELECT COUNT(*) FROM " + DB_TABLE_IMAGE, null);
            if (cur != null && cur.moveToFirst()) {
                empty = (cur.getInt (0) == 0);
            }
        }

        if (empty == true) {
            cur = db.rawQuery("SELECT COUNT(*) FROM " + DB_TABLE_ACTIVITY, null);
            if (cur != null && cur.moveToFirst()) {
                empty = (cur.getInt (0) == 0);
            }
        }

        cur.close();
        db.close();

        return empty;
    }
    public ArrayList<String> getNamesOfStudiesByInstitute(String institute_fullname, Boolean english) {
        ArrayList<String> mArrayList = new ArrayList<>();
        Cursor mCursor = viewAllStudiesByInstitute(institute_fullname);

        String study_name;
        if (english == true) {
            study_name = DB_TABLE_STUDY_NAME_ENGLISH;
        } else {
            study_name = DB_TABLE_STUDY_NAME_DUTCH;
        }

        if (mCursor != null) {
            mCursor.moveToFirst();
            while (!mCursor.isAfterLast()) {
                if (!mArrayList.contains(mCursor.getString(mCursor.getColumnIndex(study_name)))) {
                    mArrayList.add(mCursor.getString(mCursor.getColumnIndex(study_name)));
                }
                mCursor.moveToNext();
            }
        }

        return  mArrayList;
    }
    // APP FUNCTIONS

    // INSERT DATA INTO THE DATABASE
    public Boolean createOpenday(String date, String starttime, String endtime, String institute_fullname) {
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
    public Boolean createInstitute(String fullname, String shortname, String generalinformation_english, String generalinformation_dutch) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_TABLE_INSTITUTE_FULLNAME, fullname);
        contentValues.put(DB_TABLE_INSTITUTE_SHORTNAME, shortname);
        contentValues.put(DB_TABLE_INSTITUTE_GENERALINFORMATION_ENGLISH, generalinformation_english);
        contentValues.put(DB_TABLE_INSTITUTE_GENERALINFORMATION_DUTCH, generalinformation_dutch);

        long result = db.insert(DB_TABLE_INSTITUTE, null, contentValues);
        db.close();
        return result != -1; // if result == true then the values are inserted
    }
    public Boolean createStudy(String institute_fullname, String name_dutch, String name_english, String type) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_TABLE_STUDY_INSTITUTEFULLNAME, institute_fullname);
        contentValues.put(DB_TABLE_STUDY_NAME_DUTCH, name_dutch);
        contentValues.put(DB_TABLE_STUDY_NAME_ENGLISH, name_english);
        contentValues.put(DB_TABLE_STUDY_TYPE, type);

        long result = db.insert(DB_TABLE_STUDY, null, contentValues);
        db.close();
        return result != -1; // if result == true then the values are inserted
    }
    public Boolean createActivity(String openday_date, String study_name_dutch, String starttime, String endtime, String classroom, String information_english, String information_dutch) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_TABLE_ACTIVITY_OPENDAYDATE, openday_date);
        contentValues.put(DB_TABLE_ACTIVITY_STUDYNAME_DUTCH, study_name_dutch);
        contentValues.put(DB_TABLE_ACTIVITY_STARTTIME, starttime);
        contentValues.put(DB_TABLE_ACTIVITY_ENDTIME, endtime);
        contentValues.put(DB_TABLE_ACTIVITY_CLASSROOM, classroom);
        contentValues.put(DB_TABLE_ACTIVITY_INFORMATION_ENGLISH, information_english);
        contentValues.put(DB_TABLE_ACTIVITY_INFORMATION_DUTCH, information_dutch);

        long result = db.insert(DB_TABLE_ACTIVITY, null, contentValues);
        db.close();
        return result != -1; // if result == true then the values are inserted
    }
    public Boolean createLocation(String street, String city, String institute_fullname, String zipcode, String phonenumber, String image_description) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_TABLE_LOCATION_STREET, street);
        contentValues.put(DB_TABLE_LOCATION_CITY, city);
        contentValues.put(DB_TABLE_LOCATION_INSTITUTEFULLNAME, institute_fullname);
        contentValues.put(DB_TABLE_LOCATION_ZIPCODE, zipcode);
        contentValues.put(DB_TABLE_LOCATION_PHONENUMBER, phonenumber);
        contentValues.put(DB_TABLE_LOCATION_IMAGEDESCRIPRION, image_description);

        long result = db.insert(DB_TABLE_LOCATION, null, contentValues);
        db.close();
        return result != -1; // if result == true then the values are inserted
    }
    public Boolean createImage(String filename, String context, String description, String floornumber) {
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
    // INSERT DATA INTO THE DATABASE

    // SELECT * FROM TABLE
    public Cursor viewAllOpendays() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + DB_TABLE_OPENDAY_ID + ", " + DB_TABLE_OPENDAY_DATE + ", " + DB_TABLE_OPENDAY_STARTTIME + ", " + DB_TABLE_OPENDAY_ENDTIME + ", " + DB_TABLE_OPENDAY_INSTITUTEFULLNAME + " FROM " + DB_TABLE_OPENDAY;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
    public Cursor viewAllInstitutes() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + DB_TABLE_INSTITUTE_ID + ", " + DB_TABLE_INSTITUTE_FULLNAME + ", " + DB_TABLE_INSTITUTE_SHORTNAME + ", " + DB_TABLE_INSTITUTE_GENERALINFORMATION_ENGLISH + ", " + DB_TABLE_INSTITUTE_GENERALINFORMATION_DUTCH + " FROM " + DB_TABLE_INSTITUTE;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
    public Cursor viewAllStudiesByInstitute(String institute_fullname) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + DB_TABLE_STUDY_ID + ", " + DB_TABLE_STUDY_INSTITUTEFULLNAME + ", " + DB_TABLE_STUDY_NAME_DUTCH + ", " +DB_TABLE_STUDY_TYPE + ", " + DB_TABLE_STUDY_NAME_ENGLISH + " FROM " + DB_TABLE_STUDY + " WHERE " + DB_TABLE_STUDY_INSTITUTEFULLNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[] {institute_fullname});
        return cursor;
    }
    public Cursor viewAllActivities() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + DB_TABLE_ACTIVITY_ID + ", " + DB_TABLE_ACTIVITY_OPENDAYDATE + ", " + DB_TABLE_ACTIVITY_STUDYNAME_DUTCH + ", " + DB_TABLE_ACTIVITY_STARTTIME + ", " + DB_TABLE_ACTIVITY_ENDTIME + ", " + DB_TABLE_ACTIVITY_CLASSROOM + ", " + DB_TABLE_INSTITUTE_GENERALINFORMATION_ENGLISH  + ", " + DB_TABLE_ACTIVITY_INFORMATION_DUTCH + " FROM " + DB_TABLE_ACTIVITY;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
    public Cursor viewAllLocations() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + DB_TABLE_LOCATION_ID + ", " + DB_TABLE_LOCATION_STREET + ", " + DB_TABLE_LOCATION_CITY + ", " + DB_TABLE_LOCATION_ZIPCODE + ", " + DB_TABLE_LOCATION_PHONENUMBER + ", " + DB_TABLE_LOCATION_IMAGEDESCRIPRION + " FROM " + DB_TABLE_LOCATION;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
    public Cursor viewAllImages() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + DB_TABLE_IMAGE_ID + ", " + DB_TABLE_IMAGE_FILENAME + ", " + DB_TABLE_IMAGE_CONTEXT + ", " + DB_TABLE_IMAGE_DESCRIPTION + ", " + DB_TABLE_IMAGE_FLOORNUMBER + " FROM " + DB_TABLE_IMAGE;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
    // SELECT * FROM TABLE

    // DATABASE NORMAL
    public DatabaseHelper(Context context) {
        super(context, DB_DATABASE_HROOPENDAY, null, DB_DATABASE_HROOPENDAY_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DB_TABLE_OPENDAY + "(" + DB_TABLE_OPENDAY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DB_TABLE_OPENDAY_DATE + " TEXT, " + DB_TABLE_OPENDAY_STARTTIME + " TEXT, " + DB_TABLE_OPENDAY_ENDTIME + " TEXT, " + DB_TABLE_OPENDAY_INSTITUTEFULLNAME + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + DB_TABLE_INSTITUTE + "(" + DB_TABLE_INSTITUTE_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DB_TABLE_INSTITUTE_FULLNAME + " TEXT, " + DB_TABLE_INSTITUTE_SHORTNAME + " TEXT, " + DB_TABLE_INSTITUTE_GENERALINFORMATION_ENGLISH + " TEXT, " + DB_TABLE_INSTITUTE_GENERALINFORMATION_DUTCH + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + DB_TABLE_STUDY + "(" + DB_TABLE_STUDY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DB_TABLE_STUDY_INSTITUTEFULLNAME + " TEXT, " + DB_TABLE_STUDY_NAME_DUTCH + " TEXT, " + DB_TABLE_STUDY_TYPE + " TEXT, " + DB_TABLE_STUDY_NAME_ENGLISH + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + DB_TABLE_ACTIVITY + "(" + DB_TABLE_ACTIVITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DB_TABLE_ACTIVITY_OPENDAYDATE + " TEXT, " + DB_TABLE_ACTIVITY_STUDYNAME_DUTCH + " TEXT, " + DB_TABLE_ACTIVITY_STARTTIME + " TEXT, " + DB_TABLE_ACTIVITY_ENDTIME + " TEXT, " + DB_TABLE_ACTIVITY_CLASSROOM + " TEXT, " + DB_TABLE_ACTIVITY_INFORMATION_DUTCH + " TEXT, " + DB_TABLE_ACTIVITY_INFORMATION_ENGLISH + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + DB_TABLE_LOCATION + "(" + DB_TABLE_LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DB_TABLE_LOCATION_STREET + " TEXT, " + DB_TABLE_LOCATION_CITY + " TEXT, " + DB_TABLE_LOCATION_ZIPCODE + " TEXT, " + DB_TABLE_LOCATION_PHONENUMBER + " TEXT, " + DB_TABLE_LOCATION_IMAGEDESCRIPRION + " TEXT" + ")");
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
    // DATABASE NORMAL
}