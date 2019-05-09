package project.b;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int HROOPENDAY_VERSION = 24;
    private static final String HROOPENDAY = "hro_openday.db";
        private static final String HROOPENDAY_OPENDAY = "openday";
            private static final String OPENDAY_ID = "id";
            private static final String OPENDAY_DATE = "date";
            private static final String OPENDAY_STARTTIME = "starttime";
            private static final String OPENDAY_ENDTIME = "endtime";
            private static final String OPENDAY_INSTITUTEFULLNAME = "institute_fullname";
    
        private static final String HROOPENDAY_INSTITUTE = "institute";
            private static final String INSTITUTE_ID = "id";
            private static final String INSTITUTE_FULLNAME = "fullname";
            private static final String INSTITUTE_SHORTNAME = "shortname";
            private static final String INSTITUTE_GENERALINFORMATION_ENGLISH = "generalinformation_english";
            private static final String INSTITUTE_GENERALINFORMATION_DUTCH = "generalinformation_dutch";
    
        private static final String HROOPENDAY_STUDY = "study";
            private static final String STUDY_ID = "id";
            private static final String STUDY_GENERALINFORMATION_ENGLISH = "generalinformation_english";
            private static final String STUDY_GENERALINFORMATION_DUTCH = "generalinformation_dutch";
            private static final String STUDY_INSTITUTEFULLNAME = "institute_fullname";
            private static final String STUDY_NAME_DUTCH = "name_dutch";
            private static final String STUDY_NAME_ENGLISH = "name_english";
            private static final String STUDY_TYPE = "type";
    
        private static final String HROOPENDAY_ACTIVITY = "activity";
            private static final String ACTIVITY_ID = "id";
            private static final String ACTIVITY_OPENDAYDATE = "openday_date";
            private static final String ACTIVITY_STUDYNAME = "study_name";
            private static final String ACTIVITY_STARTTIME = "starttime";
            private static final String ACTIVITY_ENDTIME = "endtime";
            private static final String ACTIVITY_CLASSROOM = "classroom";
            private static final String ACTIVITY_INFORMATION_ENGLISH = "information_english";
            private static final String ACTIVITY_INFORMATION_DUTCH = "information_dutch";
    
        private static final String HROOPENDAY_LOCATION = "location";
            private static final String LOCATION_ID = "id";
            private static final String LOCATION_INSTITUTEFULLNAME = "institute_fullname";
            private static final String LOCATION_STREET = "street";
            private static final String LOCATION_CITY = "city";
            private static final String LOCATION_ZIPCODE = "zipcode";
            private static final String LOCATION_PHONENUMBER = "phonenumber";
            private static final String LOCATION_IMAGEDESCRIPTION = "image_description";
    
        private static final String HROOPENDAY_IMAGE = "image";
            private static final String IMAGE_ID = "id";
            private static final String IMAGE_FILENAME = "filename";
            private static final String IMAGE_CONTEXT = "context";
            private static final String IMAGE_DESCRIPTION = "description";
            private static final String IMAGE_FLOORNUMBER = "floornumber";
        
        private static final String HROOPENDAY_APPINFO = "app_info";
            private static final String APPINFO_ID = "id";
            private static final String APPINFO_DATAVERSION = "data_version";
            private static final String APPINFO_APILINK = "api_link";


    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<String> getNamesOfStudiesByInstitute(String institute_fullname, Boolean language) {
        if (language == true) {
            return getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_INSTITUTEFULLNAME), Arrays.asList(institute_fullname), STUDY_NAME_DUTCH, true);
        } else {
            return getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_INSTITUTEFULLNAME), Arrays.asList(institute_fullname), STUDY_NAME_ENGLISH, true);
        }
    }
    public ArrayList<String> getCalenderInfoByInstituteAndDate(String institute_fullname, String inputdate) {
        ArrayList<String> result = new ArrayList<>();
        String institute_shortname = getHandler(HROOPENDAY_INSTITUTE, Arrays.asList(INSTITUTE_FULLNAME), Arrays.asList(institute_fullname), INSTITUTE_SHORTNAME, true).get(0);
        String zipcode = getHandler(HROOPENDAY_LOCATION, Arrays.asList(LOCATION_INSTITUTEFULLNAME), Arrays.asList(institute_fullname), LOCATION_ZIPCODE, true).get(0);
        String starttime = getHandler(HROOPENDAY_OPENDAY, Arrays.asList(OPENDAY_INSTITUTEFULLNAME, OPENDAY_DATE), Arrays.asList(institute_fullname, inputdate), OPENDAY_STARTTIME, true).get(0);
        String endtime = getHandler(HROOPENDAY_OPENDAY, Arrays.asList(OPENDAY_INSTITUTEFULLNAME, OPENDAY_DATE), Arrays.asList(institute_fullname, inputdate), OPENDAY_ENDTIME, true).get(0);
        String date = getHandler(HROOPENDAY_OPENDAY, Arrays.asList(OPENDAY_INSTITUTEFULLNAME, OPENDAY_DATE), Arrays.asList(institute_fullname, inputdate), OPENDAY_DATE, true).get(0);

        result.add(institute_shortname);
        result.add(zipcode);
        result.add(starttime);
        result.add(endtime);
        result.add(date);

        return result;
    }

    // GET CONTENT
    public ArrayList<String> getLocationInformation(String description) {
        ArrayList<String> result = new ArrayList<>();

        String imagedescription = getHandler(HROOPENDAY_LOCATION, Arrays.asList(LOCATION_IMAGEDESCRIPTION), Arrays.asList(description), LOCATION_IMAGEDESCRIPTION, true).get(0);
        String street = getHandler(HROOPENDAY_LOCATION, Arrays.asList(LOCATION_IMAGEDESCRIPTION), Arrays.asList(description), LOCATION_STREET, true).get(0);
        String zipcode = getHandler(HROOPENDAY_LOCATION, Arrays.asList(LOCATION_IMAGEDESCRIPTION), Arrays.asList(description), LOCATION_ZIPCODE, true).get(0);
        String city = getHandler(HROOPENDAY_LOCATION, Arrays.asList(LOCATION_IMAGEDESCRIPTION), Arrays.asList(description), LOCATION_CITY, true).get(0);
        String phonenumber = getHandler(HROOPENDAY_LOCATION, Arrays.asList(LOCATION_IMAGEDESCRIPTION), Arrays.asList(description), LOCATION_PHONENUMBER, true).get(0);
        String institute_fullname = getHandler(HROOPENDAY_LOCATION, Arrays.asList(LOCATION_IMAGEDESCRIPTION), Arrays.asList(description), LOCATION_INSTITUTEFULLNAME, true).get(0);

        result.add(imagedescription);
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

        if (getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_NAME_DUTCH), Arrays.asList(study_name), STUDY_INSTITUTEFULLNAME, true).size() > 0) {
            institute_fullname = getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_NAME_DUTCH), Arrays.asList(study_name), STUDY_INSTITUTEFULLNAME, true).get(0);
            type = general_information = getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_NAME_DUTCH), Arrays.asList(study_name), STUDY_TYPE, true).get(0);
            if (language == true) {
                name = getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_NAME_DUTCH), Arrays.asList(study_name), STUDY_NAME_DUTCH, true).get(0);
                general_information = getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_NAME_DUTCH), Arrays.asList(study_name), STUDY_GENERALINFORMATION_DUTCH, true).get(0);
            } else {
                name = getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_NAME_DUTCH), Arrays.asList(study_name), STUDY_NAME_ENGLISH, true).get(0);
                general_information = getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_NAME_DUTCH), Arrays.asList(study_name), STUDY_GENERALINFORMATION_ENGLISH, true).get(0);
            }
        } else if (getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_NAME_ENGLISH), Arrays.asList(study_name), STUDY_INSTITUTEFULLNAME, true).size() > 0) {
            institute_fullname = getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_NAME_ENGLISH), Arrays.asList(study_name), STUDY_INSTITUTEFULLNAME, true).get(0);
            type = general_information = getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_NAME_ENGLISH), Arrays.asList(study_name), STUDY_TYPE, true).get(0);
            if (language == true) {
                name = getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_NAME_ENGLISH), Arrays.asList(study_name), STUDY_NAME_DUTCH, true).get(0);
                general_information = getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_NAME_ENGLISH), Arrays.asList(study_name), STUDY_GENERALINFORMATION_DUTCH, true).get(0);
            } else {
                name = getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_NAME_ENGLISH), Arrays.asList(study_name), STUDY_NAME_ENGLISH, true).get(0);
                general_information = getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_NAME_ENGLISH), Arrays.asList(study_name), STUDY_GENERALINFORMATION_ENGLISH, true).get(0);
            }
        }

        result.add(institute_fullname);
        result.add(type);
        result.add(name);
        result.add(general_information);

        return result;
    }
    public ArrayList<String> getOpendayByID(String ID) {
        ArrayList<String> result = new ArrayList<>();
        String institute_fullname = getHandler(HROOPENDAY_OPENDAY, Arrays.asList(OPENDAY_ID), Arrays.asList(ID), OPENDAY_INSTITUTEFULLNAME, true).get(0);
        String date = getHandler(HROOPENDAY_OPENDAY, Arrays.asList(OPENDAY_ID), Arrays.asList(ID), OPENDAY_DATE, true).get(0);
        String startime = getHandler(HROOPENDAY_OPENDAY, Arrays.asList(OPENDAY_ID), Arrays.asList(ID), OPENDAY_STARTTIME, true).get(0);
        String endtime = getHandler(HROOPENDAY_OPENDAY, Arrays.asList(OPENDAY_ID), Arrays.asList(ID), OPENDAY_ENDTIME, true).get(0);

        result.add(institute_fullname);
        result.add(date);
        result.add(startime);
        result.add(endtime);

        return result;
    }
    public ArrayList<String> getActivityById(String id, Boolean language) {
        ArrayList<String> result = new ArrayList<>();
        String studyname = "";
        String information = "";
        String classroom = getHandler(HROOPENDAY_ACTIVITY, Arrays.asList(ACTIVITY_ID), Arrays.asList(id), ACTIVITY_CLASSROOM, true).get(0);
        String starttime = getHandler(HROOPENDAY_ACTIVITY, Arrays.asList(ACTIVITY_ID), Arrays.asList(id), ACTIVITY_STARTTIME, true).get(0);
        String endtime = getHandler(HROOPENDAY_ACTIVITY, Arrays.asList(ACTIVITY_ID), Arrays.asList(id), ACTIVITY_ENDTIME, true).get(0);
        String openday_date = getHandler(HROOPENDAY_ACTIVITY, Arrays.asList(ACTIVITY_ID), Arrays.asList(id), ACTIVITY_OPENDAYDATE, true).get(0);

        if (language == true) {
            // Dutch
            information = getHandler(HROOPENDAY_ACTIVITY, Arrays.asList(ACTIVITY_ID), Arrays.asList(id), ACTIVITY_INFORMATION_DUTCH, true).get(0);
            studyname = getHandler(HROOPENDAY_ACTIVITY, Arrays.asList(ACTIVITY_ID), Arrays.asList(id), ACTIVITY_STUDYNAME, true).get(0);
        } else {
            information = getHandler(HROOPENDAY_ACTIVITY, Arrays.asList(ACTIVITY_ID), Arrays.asList(id), ACTIVITY_INFORMATION_ENGLISH, true).get(0);
            studyname = getHandler(HROOPENDAY_ACTIVITY, Arrays.asList(ACTIVITY_ID), Arrays.asList(id), ACTIVITY_STUDYNAME, true).get(0);
        }

        result.add(studyname);
        result.add(information);
        result.add(classroom);
        result.add(starttime);
        result.add(endtime);
        result.add(openday_date);

        return result;
    }
    public ArrayList<String> getAllFloorplanByFloornumber(String description, String floornumber) {
        return getHandler(HROOPENDAY_IMAGE, Arrays.asList(IMAGE_DESCRIPTION, IMAGE_FLOORNUMBER), Arrays.asList(description, floornumber), IMAGE_FILENAME, true);
    }
    public ArrayList<String> getInstituteInformation(String institute_fullname, Boolean language) {
        ArrayList<String> result = new ArrayList<>();
        String fullname = getHandler(HROOPENDAY_INSTITUTE, Arrays.asList(INSTITUTE_FULLNAME), Arrays.asList(institute_fullname), INSTITUTE_FULLNAME, true).get(0);
        String shortname = getHandler(HROOPENDAY_INSTITUTE, Arrays.asList(INSTITUTE_FULLNAME), Arrays.asList(institute_fullname), INSTITUTE_SHORTNAME, true).get(0);
        String information = "";

        if (language == true) {
            information = getHandler(HROOPENDAY_INSTITUTE, Arrays.asList(INSTITUTE_FULLNAME), Arrays.asList(institute_fullname), INSTITUTE_GENERALINFORMATION_DUTCH, true).get(0);
        } else {
            information = getHandler(HROOPENDAY_INSTITUTE, Arrays.asList(INSTITUTE_FULLNAME), Arrays.asList(institute_fullname), INSTITUTE_GENERALINFORMATION_ENGLISH, true).get(0);
        }

        result.add(fullname);
        result.add(shortname);
        result.add(information);

        return result;
    }

    // GET LINK
    public ArrayList<String> getAllOpendaysID() {
        return getHandler(HROOPENDAY_OPENDAY, null, null, OPENDAY_ID, true);
    }
    public ArrayList<String> getAllInstituteFullName() {
        return getHandler(HROOPENDAY_INSTITUTE, null, null, INSTITUTE_FULLNAME, true);
    }
    public ArrayList<String> getAllLocationsByInstitute(String institute_fullname) {
        return getHandler(HROOPENDAY_LOCATION, Arrays.asList(LOCATION_INSTITUTEFULLNAME), Arrays.asList(institute_fullname), LOCATION_IMAGEDESCRIPTION, true);
    }
    public ArrayList<String> getAllFloorplansByLocation(String description) {
        return getHandler(HROOPENDAY_IMAGE, Arrays.asList(IMAGE_DESCRIPTION), Arrays.asList(description), IMAGE_FILENAME, true);
    }
    public ArrayList<String> getAllActivitiesIDByInstituteAndDate(String institute_fullname, String openday_date) {
        ArrayList<String> result = new ArrayList<>();

        ArrayList<String> IdByDate = getHandler(HROOPENDAY_ACTIVITY, Arrays.asList(ACTIVITY_OPENDAYDATE), Arrays.asList(openday_date), ACTIVITY_ID, true);
        ArrayList<String> StudyNameByDate = getHandler(HROOPENDAY_ACTIVITY, Arrays.asList(ACTIVITY_OPENDAYDATE), Arrays.asList(openday_date), ACTIVITY_STUDYNAME, false);
        ArrayList<String> StudyNameEnglishByInstitute = getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_INSTITUTEFULLNAME), Arrays.asList(institute_fullname), STUDY_NAME_ENGLISH, true);
        ArrayList<String> StudyNameDutchByInstitute = getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_INSTITUTEFULLNAME), Arrays.asList(institute_fullname), STUDY_NAME_DUTCH, true);
        String currentDutch = "";
        String currentEnglish = "";
        String currentDate = "";

        for (int i = 0; i < StudyNameByDate.size(); i++) {
            currentDate = StudyNameByDate.get(i);
            for (int j = 0; j < StudyNameDutchByInstitute.size(); j++) {
                currentDutch = StudyNameDutchByInstitute.get(j);
                currentEnglish = StudyNameEnglishByInstitute.get(j);

                if (currentDate.equals(currentDutch)) {
                    result.add(IdByDate.get(i));
                } else if(currentDate.equals(currentEnglish)) {
                    result.add(IdByDate.get(i));
                }
            }
        }


        return result;
    }


    // SET DATA
    public Boolean createOpenday(String date, String starttime, String endtime, String institute_fullname) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(OPENDAY_DATE, date);
        contentValues.put(OPENDAY_STARTTIME, starttime);
        contentValues.put(OPENDAY_ENDTIME, endtime);
        contentValues.put(OPENDAY_INSTITUTEFULLNAME, institute_fullname);

        long result = db.insert(HROOPENDAY_OPENDAY, null, contentValues);
        db.close();
        return result != -1; // if result == true then the values are inserted
    } // OPENDAY
    public Boolean createInstitute(String fullname, String shortname, String generalinformation_english, String generalinformation_dutch) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(INSTITUTE_FULLNAME, fullname);
        contentValues.put(INSTITUTE_SHORTNAME, shortname);
        contentValues.put(INSTITUTE_GENERALINFORMATION_ENGLISH, generalinformation_english);
        contentValues.put(INSTITUTE_GENERALINFORMATION_DUTCH, generalinformation_dutch);

        long result = db.insert(HROOPENDAY_INSTITUTE, null, contentValues);
        db.close();
        return result != -1; // if result == true then the values are inserted
    } // INSTITUTE
    public Boolean createStudy(String institute_fullname, String name_dutch, String name_english, String type, String generalinformation_dutch, String generalinformation_english) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(STUDY_INSTITUTEFULLNAME, institute_fullname);
        contentValues.put(STUDY_NAME_DUTCH, name_dutch);
        contentValues.put(STUDY_NAME_ENGLISH, name_english);
        contentValues.put(STUDY_TYPE, type);
        contentValues.put(STUDY_GENERALINFORMATION_DUTCH, generalinformation_dutch);
        contentValues.put(STUDY_GENERALINFORMATION_ENGLISH, generalinformation_english);

        long result = db.insert(HROOPENDAY_STUDY, null, contentValues);
        db.close();
        return result != -1; // if result == true then the values are inserted
    } // INSTITUTE
    public Boolean createActivity(String openday_date, String study_name, String starttime, String endtime, String classroom, String information_english, String information_dutch) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ACTIVITY_OPENDAYDATE, openday_date);
        contentValues.put(ACTIVITY_STUDYNAME, study_name);
        contentValues.put(ACTIVITY_STARTTIME, starttime);
        contentValues.put(ACTIVITY_ENDTIME, endtime);
        contentValues.put(ACTIVITY_CLASSROOM, classroom);
        contentValues.put(ACTIVITY_INFORMATION_ENGLISH, information_english);
        contentValues.put(ACTIVITY_INFORMATION_DUTCH, information_dutch);

        long result = db.insert(HROOPENDAY_ACTIVITY, null, contentValues);
        db.close();
        return result != -1; // if result == true then the values are inserted
    } // ACTIVITY
    public Boolean createLocation(String street, String city, String institute_fullname, String zipcode, String phonenumber, String image_description) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(LOCATION_STREET, street);
        contentValues.put(LOCATION_CITY, city);
        contentValues.put(LOCATION_INSTITUTEFULLNAME, institute_fullname);
        contentValues.put(LOCATION_ZIPCODE, zipcode);
        contentValues.put(LOCATION_PHONENUMBER, phonenumber);
        contentValues.put(LOCATION_IMAGEDESCRIPTION, image_description);

        long result = db.insert(HROOPENDAY_LOCATION, null, contentValues);
        db.close();
        return result != -1; // if result == true then the values are inserted
    } // LOCATION
    public Boolean createImage(String filename, String context, String description, String floornumber) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(IMAGE_FILENAME, filename);
        contentValues.put(IMAGE_CONTEXT, context);
        contentValues.put(IMAGE_DESCRIPTION, description);
        contentValues.put(IMAGE_FLOORNUMBER, floornumber);

        long result = db.insert(HROOPENDAY_IMAGE, null, contentValues);
        db.close();
        return result != -1; // if result == true then the values are inserted
    } // IMAGE

    // Database Checks
    public void fillDatabase() {
        // API CALL......



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
    public Boolean checkDatabase() {
        Boolean empty = emptyDatabase();
        Boolean version = versionDatabase();

        if (empty == true) {
            return true;
        } else if (version == true) {
            return true;
        } else {
            return false;
        }
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Database Checkers
    private Boolean emptyDatabase() {
        Boolean empty = true;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM " + HROOPENDAY_OPENDAY, null);
        if (cur != null && cur.moveToFirst()) {
            empty = (cur.getInt (0) == 0);
        }

        if (empty == true) {
            cur = db.rawQuery("SELECT COUNT(*) FROM " + HROOPENDAY_INSTITUTE, null);
            if (cur != null && cur.moveToFirst()) {
                empty = (cur.getInt (0) == 0);
            }
        }

        if (empty == true) {
            cur = db.rawQuery("SELECT COUNT(*) FROM " + HROOPENDAY_STUDY, null);
            if (cur != null && cur.moveToFirst()) {
                empty = (cur.getInt (0) == 0);
            }
        }

        if (empty == true) {
            cur = db.rawQuery("SELECT COUNT(*) FROM " + HROOPENDAY_LOCATION, null);
            if (cur != null && cur.moveToFirst()) {
                empty = (cur.getInt (0) == 0);
            }
        }

        if (empty == true) {
            cur = db.rawQuery("SELECT COUNT(*) FROM " + HROOPENDAY_IMAGE, null);
            if (cur != null && cur.moveToFirst()) {
                empty = (cur.getInt (0) == 0);
            }
        }

        if (empty == true) {
            cur = db.rawQuery("SELECT COUNT(*) FROM " + HROOPENDAY_ACTIVITY, null);
            if (cur != null && cur.moveToFirst()) {
                empty = (cur.getInt (0) == 0);
            }
        }

        cur.close();
        db.close();

        return empty;
    }
    private Boolean versionDatabase() {
        /*

        Integer online_version; // API
        Integer local_version; // DATABASE

        Integer highest_id = 0;
        ArrayList<String> appinfo_id_all = getHandler(HROOPENDAY_APPINFO, null, null, APPINFO_ID, true);



        if (online_version == local_version) {
            return false;
        } else {
            return true;
        }

        */
        return false;
    }

    // GET Handler
    private ArrayList<String> getHandler(String table, List<String> arguments, List<String> values, String returnColumn, Boolean doubleCheck) {
        ArrayList<String> mArrayList = new ArrayList<>();
        Cursor mCursor = null;

        if (arguments == null && values == null) {
            if (table == HROOPENDAY_ACTIVITY) {
                mCursor = viewAllActivities(arguments, values);
            } else if(table == HROOPENDAY_OPENDAY) {
                mCursor = viewAllOpendays(arguments, values);
            } else if(table == HROOPENDAY_INSTITUTE) {
                mCursor = viewAllInstitutes(arguments, values);
            } else if(table == HROOPENDAY_STUDY) {
                mCursor = viewAllStudies(arguments, values);
            } else if(table == HROOPENDAY_LOCATION) {
                mCursor = viewAllLocations(arguments, values);
            } else if(table == HROOPENDAY_IMAGE) {
                mCursor = viewAllImages(arguments, values);
            } else if(table == HROOPENDAY_APPINFO) {
                mCursor = viewAllAppInfo(arguments, values);
            }

            if (mCursor != null) {
                mCursor.moveToFirst();
                while (!mCursor.isAfterLast()) {
                    if (doubleCheck == true){
                        if (!mArrayList.contains(mCursor.getString(mCursor.getColumnIndex(returnColumn)))) {
                            mArrayList.add(mCursor.getString(mCursor.getColumnIndex(returnColumn)));
                        }
                    } else {
                        mArrayList.add(mCursor.getString(mCursor.getColumnIndex(returnColumn)));
                    }
                    mCursor.moveToNext();
                }
            }
        } else if (arguments.size() == values.size()) {
            if (table == HROOPENDAY_ACTIVITY) {
                mCursor = viewAllActivities(arguments, values);
            } else if(table == HROOPENDAY_OPENDAY) {
                mCursor = viewAllOpendays(arguments, values);
            } else if(table == HROOPENDAY_INSTITUTE) {
                mCursor = viewAllInstitutes(arguments, values);
            } else if(table == HROOPENDAY_STUDY) {
                mCursor = viewAllStudies(arguments, values);
            } else if(table == HROOPENDAY_LOCATION) {
                mCursor = viewAllLocations(arguments, values);
            } else if(table == HROOPENDAY_IMAGE) {
                mCursor = viewAllImages(arguments, values);
            }

            if (mCursor != null) {
                mCursor.moveToFirst();
                while (!mCursor.isAfterLast()) {
                    if (doubleCheck == true){
                        if (!mArrayList.contains(mCursor.getString(mCursor.getColumnIndex(returnColumn)))) {
                            mArrayList.add(mCursor.getString(mCursor.getColumnIndex(returnColumn)));
                        }
                    } else {
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
        String query = "SELECT " + OPENDAY_ID + ", " + OPENDAY_DATE + ", " + OPENDAY_STARTTIME + ", " + OPENDAY_ENDTIME + ", " + OPENDAY_INSTITUTEFULLNAME + " FROM " + HROOPENDAY_OPENDAY;
        Cursor cursor;
        if (values != null && arguments != null) {
            query += ArgumentHandler(arguments);
            cursor = db.rawQuery(query, values.toArray(new String[0]));
        } else {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    private Cursor viewAllInstitutes(List<String> arguments, List<String> values) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + INSTITUTE_ID + ", " + INSTITUTE_FULLNAME + ", " + INSTITUTE_SHORTNAME + ", " + INSTITUTE_GENERALINFORMATION_ENGLISH + ", " + INSTITUTE_GENERALINFORMATION_DUTCH + " FROM " + HROOPENDAY_INSTITUTE;
        Cursor cursor;
        if (values != null && arguments != null) {
            query += ArgumentHandler(arguments);
            cursor = db.rawQuery(query, values.toArray(new String[0]));
        } else {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    private Cursor viewAllActivities(List<String> arguments, List<String> values) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + ACTIVITY_ID + ", " + ACTIVITY_OPENDAYDATE + ", " + ACTIVITY_STUDYNAME + ", " + ACTIVITY_STARTTIME + ", " + ACTIVITY_ENDTIME + ", " + ACTIVITY_CLASSROOM + ", " + ACTIVITY_INFORMATION_ENGLISH  + ", " + ACTIVITY_INFORMATION_DUTCH + " FROM " + HROOPENDAY_ACTIVITY;
        Cursor cursor;
        if (values != null && arguments != null) {
            query += ArgumentHandler(arguments);
            cursor = db.rawQuery(query, values.toArray(new String[0]));
        } else {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    private Cursor viewAllImages(List<String> arguments, List<String> values) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + IMAGE_ID + ", " + IMAGE_FILENAME + ", " + IMAGE_CONTEXT + ", " + IMAGE_DESCRIPTION + ", " + IMAGE_FLOORNUMBER + " FROM " + HROOPENDAY_IMAGE;
        Cursor cursor;
        if (values != null && arguments != null) {
            query += ArgumentHandler(arguments);
            cursor = db.rawQuery(query, values.toArray(new String[0]));
        } else {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    private Cursor viewAllLocations(List<String> arguments, List<String> values) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + LOCATION_ID + ", " + LOCATION_STREET + ", " + LOCATION_INSTITUTEFULLNAME + ", " + LOCATION_CITY + ", " + LOCATION_ZIPCODE + ", " + LOCATION_PHONENUMBER + ", " + LOCATION_IMAGEDESCRIPTION + " FROM " + HROOPENDAY_LOCATION;
        Cursor cursor;
        if (values != null && arguments != null) {
            query += ArgumentHandler(arguments);
            cursor = db.rawQuery(query, values.toArray(new String[0]));
        } else {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    private Cursor viewAllStudies(List<String> arguments, List<String> values) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + STUDY_ID + ", " + STUDY_INSTITUTEFULLNAME + ", " + STUDY_GENERALINFORMATION_DUTCH + ", " + STUDY_GENERALINFORMATION_ENGLISH + ", " + STUDY_NAME_DUTCH + ", " +STUDY_TYPE + ", " + STUDY_NAME_ENGLISH + " FROM " + HROOPENDAY_STUDY;
        Cursor cursor;
        if (values != null && arguments != null) {
            query += ArgumentHandler(arguments);
            cursor = db.rawQuery(query, values.toArray(new String[0]));
        } else {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    private Cursor viewAllAppInfo(List<String> arguments, List<String> values) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + APPINFO_ID + ", " + APPINFO_DATAVERSION + ", " + APPINFO_APILINK + " FROM " + HROOPENDAY_APPINFO;
        Cursor cursor;
        if (values != null && arguments != null) {
            query += ArgumentHandler(arguments);
            cursor = db.rawQuery(query, values.toArray(new String[0]));
        } else {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    // ARGUMENTS (WHERE ... = ? AND ... = ? ......)
    private String ArgumentHandler(List<String> arguments) {
        String query = "";

        if (arguments != null) {
            query = " WHERE ";
            for(int i = 0; i < arguments.size(); i++) {
                query += arguments.get(i) + " = ? AND ";
            }

            if (query.endsWith(" AND ")) {
                query = query.substring(0, query.length() - 5);
            }
        }

        return query;
    }

    // Standard For SQLite
    public DatabaseHelper(Context context) {
        super(context, HROOPENDAY, null, HROOPENDAY_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + HROOPENDAY_OPENDAY + "(" + OPENDAY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + OPENDAY_DATE + " TEXT, " + OPENDAY_STARTTIME + " TEXT, " + OPENDAY_ENDTIME + " TEXT, " + OPENDAY_INSTITUTEFULLNAME + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + HROOPENDAY_INSTITUTE + "(" + INSTITUTE_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, " + INSTITUTE_FULLNAME + " TEXT, " + INSTITUTE_SHORTNAME + " TEXT, " + INSTITUTE_GENERALINFORMATION_ENGLISH + " TEXT, " + INSTITUTE_GENERALINFORMATION_DUTCH + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + HROOPENDAY_STUDY + "(" + STUDY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + STUDY_INSTITUTEFULLNAME + " TEXT, " + STUDY_GENERALINFORMATION_DUTCH + " TEXT, " + STUDY_GENERALINFORMATION_ENGLISH + " TEXT, " + STUDY_NAME_DUTCH + " TEXT, " + STUDY_TYPE + " TEXT, " + STUDY_NAME_ENGLISH + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + HROOPENDAY_ACTIVITY + "(" + ACTIVITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ACTIVITY_OPENDAYDATE + " TEXT, " + ACTIVITY_STUDYNAME + " TEXT, " + ACTIVITY_STARTTIME + " TEXT, " + ACTIVITY_ENDTIME + " TEXT, " + ACTIVITY_CLASSROOM + " TEXT, " + ACTIVITY_INFORMATION_DUTCH + " TEXT, " + ACTIVITY_INFORMATION_ENGLISH + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + HROOPENDAY_LOCATION + "(" + LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + LOCATION_STREET + " TEXT, " + LOCATION_CITY + " TEXT, " + LOCATION_ZIPCODE + " TEXT, " + LOCATION_INSTITUTEFULLNAME + " TEXT, " + LOCATION_PHONENUMBER + " TEXT, " + LOCATION_IMAGEDESCRIPTION + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + HROOPENDAY_IMAGE + "(" + IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + IMAGE_FILENAME + " TEXT, " + IMAGE_CONTEXT + " TEXT, " + IMAGE_DESCRIPTION + " TEXT, " + IMAGE_FLOORNUMBER + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + HROOPENDAY_APPINFO + "(" + APPINFO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + APPINFO_DATAVERSION + " TEXT, " + APPINFO_APILINK + " TEXT" + ")");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + HROOPENDAY_OPENDAY);
        db.execSQL("DROP TABLE IF EXISTS " + HROOPENDAY_INSTITUTE);
        db.execSQL("DROP TABLE IF EXISTS " + HROOPENDAY_STUDY);
        db.execSQL("DROP TABLE IF EXISTS " + HROOPENDAY_ACTIVITY);
        db.execSQL("DROP TABLE IF EXISTS " + HROOPENDAY_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + HROOPENDAY_IMAGE);
        db.execSQL("DROP TABLE IF EXISTS " + HROOPENDAY_APPINFO);
        onCreate(db);
    }
}
