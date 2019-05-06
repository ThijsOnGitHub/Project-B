package project.b;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_DATABASE_HROOPENDAY_VERSION = 10;
    private static final String DB_DATABASE_HROOPENDAY = "hro_openday.db";

    private static final String DB_TABLE_OPENDAY = "openday";
    private static final String DB_TABLE_OPENDAY_ID = "id";
    private static final String DB_TABLE_OPENDAY_DATE = "date";
    private static final String DB_TABLE_OPENDAY_STARTTIME = "starttime";
    private static final String DB_TABLE_OPENDAY_ENDTIME = "endtime";
    private static final String DB_TABLE_OPENDAY_INSTITUTEFULLNAME = "institute_fullname";

    private static final String DB_TABLE_INSTITUTE = "institute";
    private static final String DB_TABLE_INSTITUTE_ID = "id";
    private static final String DB_TABLE_INSTITUTE_FULLNAME = "fullname";
    private static final String DB_TABLE_INSTITUTE_SHORTNAME = "shortname";
    private static final String DB_TABLE_INSTITUTE_GENERALINFORMATION_ENGLISH = "generalinformation_english";
    private static final String DB_TABLE_INSTITUTE_GENERALINFORMATION_DUTCH = "generalinformation_dutch";

    private static final String DB_TABLE_STUDY = "study";
    private static final String DB_TABLE_STUDY_ID = "id";
    private static final String DB_TABLE_STUDY_GENERALINFORMATION_ENGLISH = "generalinformation_english";
    private static final String DB_TABLE_STUDY_GENERALINFORMATION_DUTCH = "generalinformation_dutch";
    private static final String DB_TABLE_STUDY_INSTITUTEFULLNAME = "institute_fullname";
    private static final String DB_TABLE_STUDY_NAME_DUTCH = "name_dutch";
    private static final String DB_TABLE_STUDY_NAME_ENGLISH = "name_english";
    private static final String DB_TABLE_STUDY_TYPE = "type";

    private static final String DB_TABLE_ACTIVITY = "activity";
    private static final String DB_TABLE_ACTIVITY_ID = "id";
    private static final String DB_TABLE_ACTIVITY_OPENDAYDATE = "openday_date";
    private static final String DB_TABLE_ACTIVITY_STUDYNAME_DUTCH = "study_name_dutch";
    private static final String DB_TABLE_ACTIVITY_STARTTIME = "starttime";
    private static final String DB_TABLE_ACTIVITY_ENDTIME = "endtime";
    private static final String DB_TABLE_ACTIVITY_CLASSROOM = "classroom";
    private static final String DB_TABLE_ACTIVITY_INFORMATION_ENGLISH = "information_english";
    private static final String DB_TABLE_ACTIVITY_INFORMATION_DUTCH = "information_dutch";

    private static final String DB_TABLE_LOCATION = "location";
    private static final String DB_TABLE_LOCATION_ID = "id";
    private static final String DB_TABLE_LOCATION_INSTITUTEFULLNAME = "institute_fullname";
    private static final String DB_TABLE_LOCATION_STREET = "street";
    private static final String DB_TABLE_LOCATION_CITY = "city";
    private static final String DB_TABLE_LOCATION_ZIPCODE = "zipcode";
    private static final String DB_TABLE_LOCATION_PHONENUMBER = "phonenumber";
    private static final String DB_TABLE_LOCATION_IMAGEDESCRIPTION = "image_description";

    private static final String DB_TABLE_IMAGE = "image";
    private static final String DB_TABLE_IMAGE_ID = "id";
    private static final String DB_TABLE_IMAGE_FILENAME = "filename";
    private static final String DB_TABLE_IMAGE_CONTEXT = "context";
    private static final String DB_TABLE_IMAGE_DESCRIPTION = "description";
    private static final String DB_TABLE_IMAGE_FLOORNUMBER = "floornumber";

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // GET DATA
    public ArrayList<String> getAllLocationsByInstitute(String institute_fullname) {
        return getHandler(DB_TABLE_LOCATION, Arrays.asList(DB_TABLE_LOCATION_INSTITUTEFULLNAME), Arrays.asList(institute_fullname), DB_TABLE_LOCATION_IMAGEDESCRIPTION);
    }
    public ArrayList<String> getNamesOfStudiesByInstitute(String institute_fullname, Boolean language) {
        if (language == true) {
            return getHandler(DB_TABLE_STUDY, Arrays.asList(DB_TABLE_STUDY_INSTITUTEFULLNAME), Arrays.asList(institute_fullname), DB_TABLE_STUDY_NAME_DUTCH);
        } else {
            return getHandler(DB_TABLE_STUDY, Arrays.asList(DB_TABLE_STUDY_INSTITUTEFULLNAME), Arrays.asList(institute_fullname), DB_TABLE_STUDY_NAME_ENGLISH);
        }
    }
    public ArrayList<String> getAllFloorplanByFloornumber(String description, String floornumber) {
        return getHandler(DB_TABLE_IMAGE, Arrays.asList(DB_TABLE_IMAGE_DESCRIPTION, DB_TABLE_IMAGE_FLOORNUMBER), Arrays.asList(description, floornumber), DB_TABLE_IMAGE_FILENAME);
    }
    public ArrayList<String> getAllFloorplansByLocation(String description) {
        return getHandler(DB_TABLE_IMAGE, Arrays.asList(DB_TABLE_IMAGE_DESCRIPTION), Arrays.asList(description), DB_TABLE_IMAGE_FILENAME);
    }
    public ArrayList<String> getLocationInformation(String description) {
        ArrayList<String> result = new ArrayList<>();

        String street = getHandler(DB_TABLE_LOCATION, Arrays.asList(DB_TABLE_LOCATION_IMAGEDESCRIPTION), Arrays.asList(description), DB_TABLE_LOCATION_STREET).get(0);
        String zipcode = getHandler(DB_TABLE_LOCATION, Arrays.asList(DB_TABLE_LOCATION_IMAGEDESCRIPTION), Arrays.asList(description), DB_TABLE_LOCATION_ZIPCODE).get(0);
        String city = getHandler(DB_TABLE_LOCATION, Arrays.asList(DB_TABLE_LOCATION_IMAGEDESCRIPTION), Arrays.asList(description), DB_TABLE_LOCATION_CITY).get(0);
        String phonenumber = getHandler(DB_TABLE_LOCATION, Arrays.asList(DB_TABLE_LOCATION_IMAGEDESCRIPTION), Arrays.asList(description), DB_TABLE_LOCATION_PHONENUMBER).get(0);
        String institute_fullname = getHandler(DB_TABLE_LOCATION, Arrays.asList(DB_TABLE_LOCATION_IMAGEDESCRIPTION), Arrays.asList(description), DB_TABLE_LOCATION_INSTITUTEFULLNAME).get(0);

        result.add(street);
        result.add(zipcode);
        result.add(city);
        result.add(phonenumber);
        result.add(institute_fullname);

        return result;
    }
    public ArrayList<String> getStudyInformation(String study_name, Boolean language) {
        ArrayList<String> result = new ArrayList<>();
        String institute_fullname = "";
        String name = "";
        String type = "";
        String general_information = "";

        if (getHandler(DB_TABLE_STUDY, Arrays.asList(DB_TABLE_STUDY_NAME_DUTCH), Arrays.asList(study_name), DB_TABLE_STUDY_INSTITUTEFULLNAME).size() > 0) {
            institute_fullname = getHandler(DB_TABLE_STUDY, Arrays.asList(DB_TABLE_STUDY_NAME_DUTCH), Arrays.asList(study_name), DB_TABLE_STUDY_INSTITUTEFULLNAME).get(0);
            type = general_information = getHandler(DB_TABLE_STUDY, Arrays.asList(DB_TABLE_STUDY_NAME_DUTCH), Arrays.asList(study_name), DB_TABLE_STUDY_TYPE).get(0);
            if (language == true) {
                name = getHandler(DB_TABLE_STUDY, Arrays.asList(DB_TABLE_STUDY_NAME_DUTCH), Arrays.asList(study_name), DB_TABLE_STUDY_NAME_DUTCH).get(0);
                general_information = getHandler(DB_TABLE_STUDY, Arrays.asList(DB_TABLE_STUDY_NAME_DUTCH), Arrays.asList(study_name), DB_TABLE_STUDY_GENERALINFORMATION_DUTCH).get(0);
            } else {
                name = getHandler(DB_TABLE_STUDY, Arrays.asList(DB_TABLE_STUDY_NAME_DUTCH), Arrays.asList(study_name), DB_TABLE_STUDY_NAME_ENGLISH).get(0);
                general_information = getHandler(DB_TABLE_STUDY, Arrays.asList(DB_TABLE_STUDY_NAME_DUTCH), Arrays.asList(study_name), DB_TABLE_STUDY_GENERALINFORMATION_ENGLISH).get(0);
            }
        } else if (getHandler(DB_TABLE_STUDY, Arrays.asList(DB_TABLE_STUDY_NAME_ENGLISH), Arrays.asList(study_name), DB_TABLE_STUDY_INSTITUTEFULLNAME).size() > 0) {
            institute_fullname = getHandler(DB_TABLE_STUDY, Arrays.asList(DB_TABLE_STUDY_NAME_ENGLISH), Arrays.asList(study_name), DB_TABLE_STUDY_INSTITUTEFULLNAME).get(0);
            type = general_information = getHandler(DB_TABLE_STUDY, Arrays.asList(DB_TABLE_STUDY_NAME_ENGLISH), Arrays.asList(study_name), DB_TABLE_STUDY_TYPE).get(0);
            if (language == true) {
                name = getHandler(DB_TABLE_STUDY, Arrays.asList(DB_TABLE_STUDY_NAME_ENGLISH), Arrays.asList(study_name), DB_TABLE_STUDY_NAME_DUTCH).get(0);
                general_information = getHandler(DB_TABLE_STUDY, Arrays.asList(DB_TABLE_STUDY_NAME_ENGLISH), Arrays.asList(study_name), DB_TABLE_STUDY_GENERALINFORMATION_DUTCH).get(0);
            } else {
                name = getHandler(DB_TABLE_STUDY, Arrays.asList(DB_TABLE_STUDY_NAME_ENGLISH), Arrays.asList(study_name), DB_TABLE_STUDY_NAME_ENGLISH).get(0);
                general_information = getHandler(DB_TABLE_STUDY, Arrays.asList(DB_TABLE_STUDY_NAME_ENGLISH), Arrays.asList(study_name), DB_TABLE_STUDY_GENERALINFORMATION_ENGLISH).get(0);
            }
        }

        result.add(institute_fullname);
        result.add(type);
        result.add(name);
        result.add(general_information);

        return result;
    }
    public ArrayList<String> getCalenderInfoByInstituteAndDate(String institute_fullname, String inputdate) {
        ArrayList<String> result = new ArrayList<>();
        String institute_shortname = getHandler(DB_TABLE_INSTITUTE, Arrays.asList(DB_TABLE_INSTITUTE_FULLNAME), Arrays.asList(institute_fullname), DB_TABLE_INSTITUTE_SHORTNAME).get(0);
        String zipcode = getHandler(DB_TABLE_LOCATION, Arrays.asList(DB_TABLE_LOCATION_INSTITUTEFULLNAME), Arrays.asList(institute_fullname), DB_TABLE_LOCATION_ZIPCODE).get(0);
        String starttime = getHandler(DB_TABLE_OPENDAY, Arrays.asList(DB_TABLE_OPENDAY_INSTITUTEFULLNAME, DB_TABLE_OPENDAY_DATE), Arrays.asList(institute_fullname, inputdate), DB_TABLE_OPENDAY_STARTTIME).get(0);
        String endtime = getHandler(DB_TABLE_OPENDAY, Arrays.asList(DB_TABLE_OPENDAY_INSTITUTEFULLNAME, DB_TABLE_OPENDAY_DATE), Arrays.asList(institute_fullname, inputdate), DB_TABLE_OPENDAY_ENDTIME).get(0);
        String date = getHandler(DB_TABLE_OPENDAY, Arrays.asList(DB_TABLE_OPENDAY_INSTITUTEFULLNAME, DB_TABLE_OPENDAY_DATE), Arrays.asList(institute_fullname, inputdate), DB_TABLE_OPENDAY_DATE).get(0);

        result.add(institute_shortname);
        result.add(zipcode);
        result.add(starttime);
        result.add(endtime);
        result.add(date);

        return result;
    }
    public ArrayList<String> getOpendayByID(String ID) {
        ArrayList<String> result = new ArrayList<>();
        String institute_fullname = getHandler(DB_TABLE_OPENDAY, Arrays.asList(DB_TABLE_OPENDAY_ID), Arrays.asList(ID), DB_TABLE_OPENDAY_INSTITUTEFULLNAME).get(0);
        String date = getHandler(DB_TABLE_OPENDAY, Arrays.asList(DB_TABLE_OPENDAY_ID), Arrays.asList(ID), DB_TABLE_OPENDAY_DATE).get(0);
        String startime = getHandler(DB_TABLE_OPENDAY, Arrays.asList(DB_TABLE_OPENDAY_ID), Arrays.asList(ID), DB_TABLE_OPENDAY_STARTTIME).get(0);
        String endtime = getHandler(DB_TABLE_OPENDAY, Arrays.asList(DB_TABLE_OPENDAY_ID), Arrays.asList(ID), DB_TABLE_OPENDAY_ENDTIME).get(0);

        result.add(institute_fullname);
        result.add(date);
        result.add(startime);
        result.add(endtime);

        return result;
    }
    public ArrayList<String> getAllOpendaysID() {
        return getHandler(DB_TABLE_OPENDAY, null, null, DB_TABLE_OPENDAY_ID);
    }
        // TODO: info activity, institute

    // SET DATA
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
    } // OPENDAY
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
    } // INSTITUTE
    public Boolean createStudy(String institute_fullname, String name_dutch, String name_english, String type, String generalinformation_dutch, String generalinformation_english) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_TABLE_STUDY_INSTITUTEFULLNAME, institute_fullname);
        contentValues.put(DB_TABLE_STUDY_NAME_DUTCH, name_dutch);
        contentValues.put(DB_TABLE_STUDY_NAME_ENGLISH, name_english);
        contentValues.put(DB_TABLE_STUDY_TYPE, type);
        contentValues.put(DB_TABLE_STUDY_GENERALINFORMATION_DUTCH, generalinformation_dutch);
        contentValues.put(DB_TABLE_STUDY_GENERALINFORMATION_ENGLISH, generalinformation_english);

        long result = db.insert(DB_TABLE_STUDY, null, contentValues);
        db.close();
        return result != -1; // if result == true then the values are inserted
    } // INSTITUTE
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
    } // ACTIVITY
    public Boolean createLocation(String street, String city, String institute_fullname, String zipcode, String phonenumber, String image_description) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_TABLE_LOCATION_STREET, street);
        contentValues.put(DB_TABLE_LOCATION_CITY, city);
        contentValues.put(DB_TABLE_LOCATION_INSTITUTEFULLNAME, institute_fullname);
        contentValues.put(DB_TABLE_LOCATION_ZIPCODE, zipcode);
        contentValues.put(DB_TABLE_LOCATION_PHONENUMBER, phonenumber);
        contentValues.put(DB_TABLE_LOCATION_IMAGEDESCRIPTION, image_description);

        long result = db.insert(DB_TABLE_LOCATION, null, contentValues);
        db.close();
        return result != -1; // if result == true then the values are inserted
    } // LOCATION
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
    } // IMAGE

    // Database Checks
    public void fillDatabase() {
        // Create CMI
        createInstitute("Communicatie, Media en Informatietechnologie", "CMI", "The School of Communication, Media and Information Technology (CMI) provides higher education and applied research for the creative industry. As a committed partner CMI creates knowledge, skills and expertise for the ongoing development of the industry.", "Het instituut voor Communicatie, Media en Informatietechnologie (CMI) heeft met de opleidingen Communicatie, Informatica, Technische Informatica, Creative Media and Game Technologies en Communication and Multimedia Design maar liefst 3000 studenten die een waardevolle bijdrage leveren aan de onbegrensde wereld van communicatie, media en ICT.");

        // CMI Studies
        createStudy("Communicatie, Media en Informatietechnologie", "Informatica", "Software engineering", "Full-time", "", "");
        createStudy("Communicatie, Media en Informatietechnologie", "Informatica", "Software engineering", "Part-time", "", "");
        createStudy("Communicatie, Media en Informatietechnologie", "Technisch Informatica", "Computer engineering", "Full-time", "", "");
        createStudy("Communicatie, Media en Informatietechnologie", "Creative Media and Game Technologies", "Creative Media and Game Technologies", "Full-time", "", "");
        createStudy("Communicatie, Media en Informatietechnologie", "Communicatie", "Communication","Full-time", "", "");
        createStudy("Communicatie, Media en Informatietechnologie", "Communicatie", "Communication","Part-time", "", "");
        createStudy("Communicatie, Media en Informatietechnologie", "Communication & Multimedia Design", "Communication & Multimedia Design", "Full-time", "", "");

        // CMI locations
        createLocation("Wijnhaven 107", "Rotterdam", "Communicatie, Media en Informatietechnologie", "3011WN", "0107944000", "3011WN107");
        createLocation("Wijnhaven 103", "Rotterdam", "Communicatie, Media en Informatietechnologie", "3011WN", "0107944000", "3011WN103");
        createLocation("Wijnhaven 99", "Rotterdam", "Communicatie, Media en Informatietechnologie", "3011WN","0107944000", "3011WN99");

        // CMI Images
        for (int i = 0; i <= 6; i++) {
            // Wijnhaven 107 (0-6)
            createImage("h107"+ Integer.toString(i) +"e.png", "H.0" + Integer.toString(i), "3011WN107", Integer.toString(i));
            // Wijnhaven 103 (0-6)
            createImage("wd103"+ Integer.toString(i) +"e.png", "WD.0" + Integer.toString(i), "3011WN103", Integer.toString(i));
            // Wijnhaven 99 (0-5)
            if (i < 6) {
                createImage("wn99"+ Integer.toString(i) +"e.png", "WN.0" + Integer.toString(i), "3011WN99", Integer.toString(i));
            }
        }

        // Create openday for CMI
        createOpenday("04-06-1900", "17:00:00", "20:00:00", "Communicatie, Media en Informatietechnologie");
        createOpenday("04-06-2019", "17:00:00", "20:00:00", "Communicatie, Media en Informatietechnologie");
        createOpenday("09-06-2019", "17:00:00", "20:00:00", "Communicatie, Media en Informatietechnologie");

        // Create activities for CMI
        createActivity("04-06-2019", "Technisch Informatica", "18:15:00", "19:00:00", "WD.02.002", "Python stuff", "Python dingen");
        createActivity("04-06-2019", "Informatica", "17:30:00", "18:15:00", "H.05.314-C120", "General Information", "Algemene informatie");
        createActivity("04-06-2019", "Informatica", "17:30:00", "18:00:00", "WD.02.002", "Workshop Android Studio and SQLite", "Workshop over Android Studio en SQLite");
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

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // GET Handler
    private ArrayList<String> getHandler(String table, List<String> arguments, List<String> values, String returnColumn) {
        ArrayList<String> mArrayList = new ArrayList<>();
        Cursor mCursor = null;

        if (arguments.size() == values.size()) {
            if (table == DB_TABLE_ACTIVITY) {
                mCursor = viewAllActivities(arguments, values);
            } else if(table == DB_TABLE_OPENDAY) {
                mCursor = viewAllOpendays(arguments, values);
            } else if(table == DB_TABLE_INSTITUTE) {
                mCursor = viewAllInstitutes(arguments, values);
            } else if(table == DB_TABLE_STUDY) {
                mCursor = viewAllStudies(arguments, values);
            } else if(table == DB_TABLE_LOCATION) {
                mCursor = viewAllLocations(arguments, values);
            } else if(table == DB_TABLE_IMAGE) {
                mCursor = viewAllImages(arguments, values);
            }

            if (mCursor != null) {
                mCursor.moveToFirst();
                while (!mCursor.isAfterLast()) {
                    if (!mArrayList.contains(mCursor.getString(mCursor.getColumnIndex(returnColumn)))) {
                        mArrayList.add(mCursor.getString(mCursor.getColumnIndex(returnColumn)));
                    }
                    mCursor.moveToNext();
                }
            }
        }

        return mArrayList;
    }

    // SELECT Queries
    private Cursor viewAllOpendays(List<String> arguments, List<String> values) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + DB_TABLE_OPENDAY_ID + ", " + DB_TABLE_OPENDAY_DATE + ", " + DB_TABLE_OPENDAY_STARTTIME + ", " + DB_TABLE_OPENDAY_ENDTIME + ", " + DB_TABLE_OPENDAY_INSTITUTEFULLNAME + " FROM " + DB_TABLE_OPENDAY;
        query += ArgumentHandler(arguments);
        Cursor cursor = db.rawQuery(query, values.toArray(new String[0]));
        return cursor;
    }
    private Cursor viewAllInstitutes(List<String> arguments, List<String> values) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + DB_TABLE_INSTITUTE_ID + ", " + DB_TABLE_INSTITUTE_FULLNAME + ", " + DB_TABLE_INSTITUTE_SHORTNAME + ", " + DB_TABLE_INSTITUTE_GENERALINFORMATION_ENGLISH + ", " + DB_TABLE_INSTITUTE_GENERALINFORMATION_DUTCH + " FROM " + DB_TABLE_INSTITUTE;
        query += ArgumentHandler(arguments);
        Cursor cursor = db.rawQuery(query, values.toArray(new String[0]));
        return cursor;
    }
    private Cursor viewAllActivities(List<String> arguments, List<String> values) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + DB_TABLE_ACTIVITY_ID + ", " + DB_TABLE_ACTIVITY_OPENDAYDATE + ", " + DB_TABLE_ACTIVITY_STUDYNAME_DUTCH + ", " + DB_TABLE_ACTIVITY_STARTTIME + ", " + DB_TABLE_ACTIVITY_ENDTIME + ", " + DB_TABLE_ACTIVITY_CLASSROOM + ", " + DB_TABLE_INSTITUTE_GENERALINFORMATION_ENGLISH  + ", " + DB_TABLE_ACTIVITY_INFORMATION_DUTCH + " FROM " + DB_TABLE_ACTIVITY;
        query += ArgumentHandler(arguments);
        Cursor cursor = db.rawQuery(query, values.toArray(new String[0]));
        return cursor;
    }
    private Cursor viewAllImages(List<String> arguments, List<String> values) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + DB_TABLE_IMAGE_ID + ", " + DB_TABLE_IMAGE_FILENAME + ", " + DB_TABLE_IMAGE_CONTEXT + ", " + DB_TABLE_IMAGE_DESCRIPTION + ", " + DB_TABLE_IMAGE_FLOORNUMBER + " FROM " + DB_TABLE_IMAGE;
        query += ArgumentHandler(arguments);
        Cursor cursor = db.rawQuery(query, values.toArray(new String[0]));
        return cursor;
    }
    private Cursor viewAllLocations(List<String> arguments, List<String> values) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + DB_TABLE_LOCATION_ID + ", " + DB_TABLE_LOCATION_STREET + ", " + DB_TABLE_LOCATION_CITY + ", " + DB_TABLE_LOCATION_ZIPCODE + ", " + DB_TABLE_LOCATION_PHONENUMBER + ", " + DB_TABLE_LOCATION_IMAGEDESCRIPTION + " FROM " + DB_TABLE_LOCATION;
        query += ArgumentHandler(arguments);
        Cursor cursor = db.rawQuery(query, values.toArray(new String[0]));
        return cursor;
    }
    private Cursor viewAllStudies(List<String> arguments, List<String> values) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + DB_TABLE_STUDY_ID + ", " + DB_TABLE_STUDY_INSTITUTEFULLNAME + ", " + DB_TABLE_STUDY_NAME_DUTCH + ", " +DB_TABLE_STUDY_TYPE + ", " + DB_TABLE_STUDY_NAME_ENGLISH + " FROM " + DB_TABLE_STUDY;
        query += ArgumentHandler(arguments);
        Cursor cursor = db.rawQuery(query, values.toArray(new String[0]));
        return cursor;
    }

    // ARGUMENTS (WHERE ... = ? AND ... = ? ......)
    private String ArgumentHandler(List<String> arguments) {
        String query = "";

        if (arguments != null) {
            query = " WHERE ";
            for(int i = 0; i < arguments.size(); i++) {
                query += arguments.get(i) + " = ?";
            }
        }

        return query;
    }

    // Standard For SQLite
    public DatabaseHelper(Context context) {
        super(context, DB_DATABASE_HROOPENDAY, null, DB_DATABASE_HROOPENDAY_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DB_TABLE_OPENDAY + "(" + DB_TABLE_OPENDAY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DB_TABLE_OPENDAY_DATE + " TEXT, " + DB_TABLE_OPENDAY_STARTTIME + " TEXT, " + DB_TABLE_OPENDAY_ENDTIME + " TEXT, " + DB_TABLE_OPENDAY_INSTITUTEFULLNAME + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + DB_TABLE_INSTITUTE + "(" + DB_TABLE_INSTITUTE_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DB_TABLE_INSTITUTE_FULLNAME + " TEXT, " + DB_TABLE_INSTITUTE_SHORTNAME + " TEXT, " + DB_TABLE_INSTITUTE_GENERALINFORMATION_ENGLISH + " TEXT, " + DB_TABLE_INSTITUTE_GENERALINFORMATION_DUTCH + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + DB_TABLE_STUDY + "(" + DB_TABLE_STUDY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DB_TABLE_STUDY_INSTITUTEFULLNAME + " TEXT, " + DB_TABLE_STUDY_GENERALINFORMATION_DUTCH + " TEXT, " + DB_TABLE_STUDY_GENERALINFORMATION_ENGLISH + " TEXT, " + DB_TABLE_STUDY_NAME_DUTCH + " TEXT, " + DB_TABLE_STUDY_TYPE + " TEXT, " + DB_TABLE_STUDY_NAME_ENGLISH + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + DB_TABLE_ACTIVITY + "(" + DB_TABLE_ACTIVITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DB_TABLE_ACTIVITY_OPENDAYDATE + " TEXT, " + DB_TABLE_ACTIVITY_STUDYNAME_DUTCH + " TEXT, " + DB_TABLE_ACTIVITY_STARTTIME + " TEXT, " + DB_TABLE_ACTIVITY_ENDTIME + " TEXT, " + DB_TABLE_ACTIVITY_CLASSROOM + " TEXT, " + DB_TABLE_ACTIVITY_INFORMATION_DUTCH + " TEXT, " + DB_TABLE_ACTIVITY_INFORMATION_ENGLISH + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + DB_TABLE_LOCATION + "(" + DB_TABLE_LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DB_TABLE_LOCATION_STREET + " TEXT, " + DB_TABLE_LOCATION_CITY + " TEXT, " + DB_TABLE_LOCATION_ZIPCODE + " TEXT, " + DB_TABLE_LOCATION_INSTITUTEFULLNAME + " TEXT, " + DB_TABLE_LOCATION_PHONENUMBER + " TEXT, " + DB_TABLE_LOCATION_IMAGEDESCRIPTION + " TEXT" + ")");
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
}