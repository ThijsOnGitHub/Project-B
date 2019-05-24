package project.b;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int HROOPENDAY_VERSION = 51;
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
            private static final String INSTITUTE_PHONENUMBER = "phonenumber";
    
        private static final String HROOPENDAY_STUDY = "study";
            private static final String STUDY_ID = "id";
            private static final String STUDY_GENERALINFORMATION_ENGLISH = "generalinformation_english";
            private static final String STUDY_GENERALINFORMATION_DUTCH = "generalinformation_dutch";
            private static final String STUDY_INSTITUTEFULLNAME = "institute_fullname";
            private static final String STUDY_NAME_DUTCH = "name_dutch";
            private static final String STUDY_NAME_ENGLISH = "name_english";
            private static final String STUDY_ICON = "icon";
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
            private static final String LOCATION_IMAGEDESCRIPTION = "image_description";
    
        private static final String HROOPENDAY_IMAGE = "image";
            private static final String IMAGE_ID = "id";
            private static final String IMAGE_FILENAME = "filename";
            private static final String IMAGE_CONTEXT = "context";
            private static final String IMAGE_DESCRIPTION = "description";
        
        private static final String HROOPENDAY_APPINFO = "app_info";
            private static final String APPINFO_ID = "id";
            private static final String APPINFO_DATAVERSION = "data_version";
            private static final String APPINFO_APILINK = "api_link";

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // GET DATA

    public String[] getUpcomingOpendays() {
        ArrayList<String> result = new ArrayList<>();
        String[] opendays_id = getAllOpendays();
        String[] openday = new String[]{};
        ArrayList<String> current_openday = new ArrayList<>();

        Date current_openday_date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        if (opendays_id.length > 0) {
            for (int i = 0; i < opendays_id.length; i++) {
                openday = getOpendayInfo(opendays_id[i]);
                if (openday.length > 0) {
                    Date date_now = new Date();
                    String date = openday[1] + " " + openday[3];
                    Date date_openday = dateFormat.parse(date, new ParsePosition(0));

                    if (date_now.compareTo(date_openday) <= 0) {
                        // add to result
                        Integer index = 0;
                        if (result.size() > 0) {
                            for (int j = 0; j < result.size(); j++) {
                                current_openday_date = dateFormat.parse(getOpendayInfo(result.get(j))[1] + " " + getOpendayInfo(result.get(j))[3], new ParsePosition(0));

                                if (date_openday.compareTo(current_openday_date) > 0) {
                                    index++;
                                } else {
                                    break;
                                }
                            }
                        }
                        result.add(index, opendays_id[i]);
                    }
                }
            }
        }

        String[] result_string = result.toArray(new String[result.size()]);
        return result_string;
    }
    public String[] getOpendayInfo(String openday_id) {
        ArrayList<String> result = new ArrayList<>();

        result.add(getHandler(HROOPENDAY_OPENDAY, Arrays.asList(OPENDAY_ID), Arrays.asList(openday_id), OPENDAY_INSTITUTEFULLNAME, true)[0]);
        result.add(getHandler(HROOPENDAY_OPENDAY, Arrays.asList(OPENDAY_ID), Arrays.asList(openday_id), OPENDAY_DATE, true)[0]);
        result.add(getHandler(HROOPENDAY_OPENDAY, Arrays.asList(OPENDAY_ID), Arrays.asList(openday_id), OPENDAY_STARTTIME, true)[0]);
        result.add(getHandler(HROOPENDAY_OPENDAY, Arrays.asList(OPENDAY_ID), Arrays.asList(openday_id), OPENDAY_ENDTIME, true)[0]);
        result.add(openday_id);

        return stringListType(result);
    }
    public String[] getCalenderInfo(String openday_id) {
        ArrayList<String> result = new ArrayList<>();
        String[] location = new String[]{};
        String institute_id = "";
        String institute_fullname = "";
        String institute_shortname = "";
        String zipcode = "";
        String starttime = "";
        String endtime = "";
        String date = "";


        String[] openday = getOpendayInfo(openday_id);
        if(openday.length > 0) {
            institute_fullname = openday[0];
            date = openday[1];
            starttime = openday[2];
            endtime = openday[3];
        }
        String[] institutes = getInstitute_id(institute_fullname);
        if(institutes.length > 0) {
            institute_id = institutes[0];
        }
        String[] institute = getInstituteInfo(institute_id);
        if(institute.length > 0) {
            institute_shortname = institute[1];
        }
        String[] location_id = getLocation_id(institute_fullname);
        if(location_id.length > 0) {
            location = getLocationInfo(location_id[0]);
            if(location.length > 0) {
                zipcode = location[2];
            }
        }

        result.add(institute_shortname);
        result.add(zipcode);
        result.add(starttime);
        result.add(endtime);
        result.add(date);

        return stringListType(result);
    }

    public String[] getActivitiesByOpenday(String openday_id) {
        ArrayList<String> result = new ArrayList<>();
        String[] activity = new String[]{};
        String[] study = new String[] {};
        ArrayList<String> StudyNameEnglishByInstitute = new ArrayList<>();
        ArrayList<String> StudyNameDutchByInstitute = new ArrayList<>();
        ArrayList<String> StudyNameByDate = new ArrayList<>();
        String institute_fullname = "";
        String openday_date = "";

        String[] openday = getOpendayInfo(openday_id);
        if (openday.length > 0) {
            institute_fullname = openday[0];
            openday_date = openday[1];
        }

        String[] IdByDate = getActivity_id(openday_date);
        if(IdByDate.length > 0) {
            for (int i = 0; i < IdByDate.length; i++) {
                activity = getActivityInfo(IdByDate[i]);
                if(activity.length > 0) {
                    StudyNameByDate.add(activity[1]);
                }
            }
        }

        String[] studies = getStudy_id(institute_fullname);
        if (studies.length > 0) {
            for (int i = 0; i < studies.length; i++) {
                study = getStudy(studies[i], false);
                if (study.length > 0) {
                    StudyNameEnglishByInstitute.add(study[2]);
                }
            }
        }

        if (studies.length > 0) {
            for (int i = 0; i < studies.length; i++) {
                study = getStudy(studies[i], true);
                if (study.length > 0) {
                    StudyNameDutchByInstitute.add(study[2]);
                }
            }
        }

        String currentDutch = "";
        String currentEnglish = "";
        String currentDate = "";

        for (int i = 0; i < StudyNameByDate.size(); i++) {
            currentDate = StudyNameByDate.get(i);
            for (int j = 0; j < StudyNameDutchByInstitute.size(); j++) {
                currentDutch = StudyNameDutchByInstitute.get(j);
                currentEnglish = StudyNameEnglishByInstitute.get(j);

                if (currentDate.equals(currentDutch)) {
                    if (!result.contains(IdByDate[i])){
                        result.add(IdByDate[i]);
                    }
                } else if(currentDate.equals(currentEnglish)) {
                    if (!result.contains(IdByDate[i])) {
                        result.add(IdByDate[i]);
                    }
                }
            }
        }

        return stringListType(result);
    }
    public String[] getActivitiesByStudyAndOpenday(String openday_id, String study_id) {
        ArrayList<String> result = new ArrayList<>();
        String[] activities = getActivitiesByOpenday(openday_id);
        String[] studyid = new String[]{};
        String result_add = "";

        for (int i = 0; i < activities.length; i++) {
            String[] activity = getActivityInfo(activities[i]);

            studyid = getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_NAME_DUTCH), Arrays.asList(activity[1]), STUDY_ID, true);
            if (studyid.length > 0) {
                if (studyid[0].equals(study_id)) {
                    result.add(activities[i]);
                }
            } else {
                study_id = getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_NAME_ENGLISH), Arrays.asList(activity[1]), STUDY_ID, true)[0];
                if (studyid.length > 0) {
                    if (studyid[0].equals(study_id)) {
                        result.add(activities[i]);
                    }
                }
            }

        }

        return stringListType(result);
    }
    public String[] getStudiesWithActivitiesByOpenday(String openday_id) {
        ArrayList<String> result = new ArrayList<>();
        String[] activities = getActivitiesByOpenday(openday_id);
        String[] activity = new String[]{};
        String[] studyid = new String[]{};
        String result_add = "";
        for (int i = 0; i < activities.length; i++) {
            activity = getActivityInfo(activities[i]);

            studyid = getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_NAME_DUTCH), Arrays.asList(activity[1]), STUDY_ID, true);
            if (studyid.length > 0) {
                result_add = studyid[0];
                if (!result.contains(result_add)) {
                    result.add(result_add);
                }
            } else {
                result_add = getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_NAME_ENGLISH), Arrays.asList(activity[1]), STUDY_ID, true)[0];
                if (!result.contains(result_add)){
                    result.add(result_add);
                }
            }
        }
        return stringListType(result);
    }
    public String[] getActivityInfo(String activity_id) {
        Boolean language = language();
        ArrayList<String> result = new ArrayList<>();

        if (language == true) {
            result.add(getHandler(HROOPENDAY_ACTIVITY, Arrays.asList(ACTIVITY_ID), Arrays.asList(activity_id), ACTIVITY_INFORMATION_DUTCH, true)[0]);
            result.add(getHandler(HROOPENDAY_ACTIVITY, Arrays.asList(ACTIVITY_ID), Arrays.asList(activity_id), ACTIVITY_STUDYNAME, true)[0]);
        } else {
            result.add(getHandler(HROOPENDAY_ACTIVITY, Arrays.asList(ACTIVITY_ID), Arrays.asList(activity_id), ACTIVITY_INFORMATION_ENGLISH, true)[0]);
            result.add(getHandler(HROOPENDAY_ACTIVITY, Arrays.asList(ACTIVITY_ID), Arrays.asList(activity_id), ACTIVITY_STUDYNAME, true)[0]);
        }
        result.add(getHandler(HROOPENDAY_ACTIVITY, Arrays.asList(ACTIVITY_ID), Arrays.asList(activity_id), ACTIVITY_CLASSROOM, true)[0]);
        result.add(getHandler(HROOPENDAY_ACTIVITY, Arrays.asList(ACTIVITY_ID), Arrays.asList(activity_id), ACTIVITY_STARTTIME, true)[0]);
        result.add(getHandler(HROOPENDAY_ACTIVITY, Arrays.asList(ACTIVITY_ID), Arrays.asList(activity_id), ACTIVITY_ENDTIME, true)[0]);
        result.add(getHandler(HROOPENDAY_ACTIVITY, Arrays.asList(ACTIVITY_ID), Arrays.asList(activity_id), ACTIVITY_OPENDAYDATE, true)[0]);
        result.add(activity_id);

        return stringListType(result);
    }

    public String[] getStudiesByInstitute(String institute_id) {
        ArrayList<String> result = new ArrayList<>();
        String institute_fullname = "";

        String[] institute = getInstituteInfo(institute_id);
        if (institute.length > 0) {
            institute_fullname = institute[0];
        }

        String[] studies_id = getStudy_id(institute_fullname);
        if (studies_id.length > 0) {
            for (int i = 0; i < studies_id.length; i++) {
                result.add(studies_id[i]);
            }
        }

        return stringListType(result);
    }
    public String[] getStudyInfo(String study_id) {
        return getStudy(study_id, language());
    }

    public String[] getLocationsByInstitute(String institute_id) {
        ArrayList<String> result = new ArrayList<>();
        String institute_fullname = "";

        String[] institute = getInstituteInfo(institute_id);
        if (institute.length > 0) {
            institute_fullname = institute[0];
        }

        String[] locations_id = getLocation_id(institute_fullname);
        if (locations_id.length > 0) {
            for (int i = 0; i < locations_id.length; i++) {
                result.add(locations_id[i]);
            }
        }

        return stringListType(result);
    }
    public String[] getLocationInfo(String location_id) {
        ArrayList<String> result = new ArrayList<>();

        result.add(getHandler(HROOPENDAY_LOCATION, Arrays.asList(LOCATION_ID), Arrays.asList(location_id), LOCATION_IMAGEDESCRIPTION, true)[0]);
        result.add(getHandler(HROOPENDAY_LOCATION, Arrays.asList(LOCATION_ID), Arrays.asList(location_id), LOCATION_STREET, true)[0]);
        result.add(getHandler(HROOPENDAY_LOCATION, Arrays.asList(LOCATION_ID), Arrays.asList(location_id), LOCATION_ZIPCODE, true)[0]);
        result.add(getHandler(HROOPENDAY_LOCATION, Arrays.asList(LOCATION_ID), Arrays.asList(location_id), LOCATION_CITY, true)[0]);
        result.add(getHandler(HROOPENDAY_LOCATION, Arrays.asList(LOCATION_ID), Arrays.asList(location_id), LOCATION_INSTITUTEFULLNAME, true)[0]);
        result.add(location_id);

        return stringListType(result);
    }

    public String[] getImagesByLocation(String location_id, Boolean floorplan) {
        ArrayList<String> result = new ArrayList<>();
        String image_description = "";

        String[] location = getLocationInfo(location_id);
        if (location.length > 0) {
            image_description = location[0];
        }

        String[] images_id = getImage_id(image_description, floorplan);
        if (images_id.length > 0) {
            for (int i = 0; i < images_id.length; i++) {
                result.add(images_id[i]);
            }
        }

        return stringListType(result);
    }
    public String[] getImageInfo(String image_id) {
        ArrayList<String> result = new ArrayList<>();

        result.add(getHandler(HROOPENDAY_IMAGE, Arrays.asList(IMAGE_ID), Arrays.asList(image_id), IMAGE_FILENAME, true)[0]);
        result.add(getHandler(HROOPENDAY_IMAGE, Arrays.asList(IMAGE_ID), Arrays.asList(image_id), IMAGE_CONTEXT, true)[0]);
        result.add(getHandler(HROOPENDAY_IMAGE, Arrays.asList(IMAGE_ID), Arrays.asList(image_id), IMAGE_DESCRIPTION, true)[0]);
        result.add(image_id);

        return stringListType(result);
    }
    public String[] getFloorplansByInstitute(String institute_id) {
        ArrayList<String> result = new ArrayList<>();
        String[] location = new String[]{};
        String[] images_id = new String[]{};
        String[] image = new String[]{};
        String institute_fullname = "";
        String imagedescription = "";

        String[] institute = getInstituteInfo(institute_id);
        if(institute.length > 0) {
            institute_fullname = institute[0];
        }

        String[] locations_id = getLocation_id(institute_fullname);
        if (locations_id.length > 0) {
            for (int i = 0; i < locations_id.length; i++) {
                location = getLocationInfo(locations_id[i]);

                if (location.length > 0) {
                    imagedescription = location[0];
                    images_id = getImage_id(imagedescription, true);

                    if (images_id.length > 0) {
                        for (int j = 0; j < images_id.length; j++) {
                            image = getImageInfo(images_id[j]);

                            if (image.length > 0) {
                                if (!result.contains(image[1])) {
                                    result.add(image[0]);
                                }
                            }
                        }
                    }
                }
            }
        }

        return stringListType(result);
    }

    public String[] getInstitutes() {
        return getHandler(HROOPENDAY_INSTITUTE, null, null, INSTITUTE_ID, true);
    }
    public String[] getInstituteInfo(String institute_id) {
        Boolean language = language();
        ArrayList<String> result = new ArrayList<>();

        result.add(getHandler(HROOPENDAY_INSTITUTE, Arrays.asList(INSTITUTE_ID), Arrays.asList(institute_id), INSTITUTE_FULLNAME, true)[0]);
        result.add(getHandler(HROOPENDAY_INSTITUTE, Arrays.asList(INSTITUTE_ID), Arrays.asList(institute_id), INSTITUTE_SHORTNAME, true)[0]);
        if (language == true) {
            result.add(getHandler(HROOPENDAY_INSTITUTE, Arrays.asList(INSTITUTE_ID), Arrays.asList(institute_id), INSTITUTE_GENERALINFORMATION_DUTCH, true)[0]);
        } else {
            result.add(getHandler(HROOPENDAY_INSTITUTE, Arrays.asList(INSTITUTE_ID), Arrays.asList(institute_id), INSTITUTE_GENERALINFORMATION_ENGLISH, true)[0]);
        }
        result.add(institute_id);
        result.add(getHandler(HROOPENDAY_INSTITUTE, Arrays.asList(INSTITUTE_ID),Arrays.asList(institute_id), INSTITUTE_PHONENUMBER, true)[0]);

        return stringListType(result);
    }
    public String[] getInstitute_id(String institute_fullname) {
        return getHandler(HROOPENDAY_INSTITUTE, Arrays.asList(INSTITUTE_FULLNAME), Arrays.asList(institute_fullname), INSTITUTE_ID, true);
    }

    //////////////////////////////////////////////////////////////////////////
    // INSERT

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
    }
    public Boolean createInstitute(String fullname, String shortname, String generalinformation_english, String generalinformation_dutch, String phonenumber) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(INSTITUTE_FULLNAME, fullname);
        contentValues.put(INSTITUTE_SHORTNAME, shortname);
        contentValues.put(INSTITUTE_GENERALINFORMATION_ENGLISH, generalinformation_english);
        contentValues.put(INSTITUTE_GENERALINFORMATION_DUTCH, generalinformation_dutch);
        contentValues.put(INSTITUTE_PHONENUMBER, phonenumber);

        long result = db.insert(HROOPENDAY_INSTITUTE, null, contentValues);
        db.close();
        return result != -1; // if result == true then the values are inserted
    }
    public Boolean createStudy(String institute_fullname, String name_dutch, String name_english, String type, String generalinformation_dutch, String generalinformation_english, String icon) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(STUDY_INSTITUTEFULLNAME, institute_fullname);
        contentValues.put(STUDY_NAME_DUTCH, name_dutch);
        contentValues.put(STUDY_NAME_ENGLISH, name_english);
        contentValues.put(STUDY_TYPE, type);
        contentValues.put(STUDY_GENERALINFORMATION_DUTCH, generalinformation_dutch);
        contentValues.put(STUDY_GENERALINFORMATION_ENGLISH, generalinformation_english);
        contentValues.put(STUDY_ICON, icon);

        long result = db.insert(HROOPENDAY_STUDY, null, contentValues);
        db.close();
        return result != -1; // if result == true then the values are inserted
    }
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
    }
    public Boolean createLocation(String street, String city, String institute_fullname, String zipcode, String image_description) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(LOCATION_STREET, street);
        contentValues.put(LOCATION_CITY, city);
        contentValues.put(LOCATION_INSTITUTEFULLNAME, institute_fullname);
        contentValues.put(LOCATION_ZIPCODE, zipcode);
        contentValues.put(LOCATION_IMAGEDESCRIPTION, image_description);

        long result = db.insert(HROOPENDAY_LOCATION, null, contentValues);
        db.close();
        return result != -1; // if result == true then the values are inserted
    }
    public Boolean createImage(String filename, String context, String description) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(IMAGE_FILENAME, filename);
        contentValues.put(IMAGE_CONTEXT, context);
        contentValues.put(IMAGE_DESCRIPTION, description);

        long result = db.insert(HROOPENDAY_IMAGE, null, contentValues);
        db.close();
        return result != -1; // if result == true then the values are inserted
    }
    public Boolean createAppinfo(String apilink, String version) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(APPINFO_APILINK, apilink);
        contentValues.put(APPINFO_DATAVERSION, version);

        long result = db.insert(HROOPENDAY_APPINFO, null, contentValues);
        db.close();
        return result != -1;
    }
    //////////////////////////////////////////////////////////////////////////
    // CHECKS

    public void fillDatabase() {
        // API CALL......

        // Set version
        createAppinfo("projectb.caslayoort.nl/api", "0");

        // Create CMI
        createInstitute("Communicatie, Media en Informatietechnologie", "CMI", "The School of Communication, Media and Information Technology (CMI) provides higher education and applied research for the creative industry. As a committed partner CMI creates knowledge, skills and expertise for the ongoing development of the industry.", "Het instituut voor Communicatie, Media en Informatietechnologie (CMI) heeft met de opleidingen Communicatie, Informatica, Technische Informatica, Creative Media and Game Technologies en Communication and Multimedia Design maar liefst 3000 studenten die een waardevolle bijdrage leveren aan de onbegrensde wereld van communicatie, media en ICT.", "0107944000");

        // CMI Studies
        createStudy("Communicatie, Media en Informatietechnologie", "Informatica", "Software engineering", "Full-time / Part-time", "informatica info dutch", "informatica info english", "calendar_icon");
        createStudy("Communicatie, Media en Informatietechnologie", "Technisch Informatica", "Computer engineering", "Full-time", "TI info dutch", "TI info english", "ic_location_city_white_24dp");
        createStudy("Communicatie, Media en Informatietechnologie", "Creative Media and Game Technologies", "Creative Media and Game Technologies", "Full-time", "CMGT info dutch", "CMGT info english", "ic_map_white_24dp");
        createStudy("Communicatie, Media en Informatietechnologie", "Communicatie", "Communication","Full-time / Part-time", "Communicatie info dutch", "Communicatie info english", "ic_home_white_24dp");
        createStudy("Communicatie, Media en Informatietechnologie", "Communication & Multimedia Design", "Communication & Multimedia Design", "Full-time", "CMD info dutch", "CMD info english", "ic_chat_white_24dp");
        // CMI locations
        createLocation("Wijnhaven 107", "Rotterdam", "Communicatie, Media en Informatietechnologie", "3011WN", "3011WN107");
        createLocation("Wijnhaven 103", "Rotterdam", "Communicatie, Media en Informatietechnologie", "3011WN", "3011WN103");
        createLocation("Wijnhaven 99", "Rotterdam", "Communicatie, Media en Informatietechnologie", "3011WN","3011WN99");

        // Floorplans
        createImage("h107", "floorplan", "3011WN107");
        createImage("wd103", "floorplan", "3011WN103");
        createImage("wn99", "floorplan", "3011WN99");

        // Create openday for CMI
        createOpenday("04-06-1900", "17:00:00", "20:00:00", "Communicatie, Media en Informatietechnologie");
        createOpenday("09-06-2019", "17:00:00", "20:00:00", "Communicatie, Media en Informatietechnologie");
        createOpenday("04-06-2019", "17:00:00", "20:00:00", "Communicatie, Media en Informatietechnologie");
        createOpenday("21-05-2019", "17:00:00", "20:00:00", "Communicatie, Media en Informatietechnologie");
        createOpenday("15-08-2019", "17:00:00", "20:00:00", "Communicatie, Media en Informatietechnologie");

        // Create activities for CMI
        createActivity("04-06-2019", "Technisch Informatica", "18:15:00", "19:00:00", "H.04.318", "Python stuff", "Python dingen");
        createActivity("04-06-2019", "Informatica", "17:30:00", "18:15:00", "H.05.314", "General Information", "Algemene informatie");
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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

   // GET All ID's
    private String[] getAllOpendays() {
        return getHandler(HROOPENDAY_OPENDAY, null, null, OPENDAY_ID, true);
    }
    private String[] getAllAppInfo() {
        return getHandler(HROOPENDAY_APPINFO, null, null, APPINFO_ID, true);
    }

    // GET LINKED ID
    private String[] getLocation_id(String institute_fullname) {
        return getHandler(HROOPENDAY_LOCATION, Arrays.asList(LOCATION_INSTITUTEFULLNAME), Arrays.asList(institute_fullname), LOCATION_ID, true);
    }
    private String[] getImage_id(String image_description, Boolean floorplan) {
        if (floorplan == true) {
            return getHandler(HROOPENDAY_IMAGE, Arrays.asList(IMAGE_DESCRIPTION, IMAGE_CONTEXT), Arrays.asList(image_description, "floorplan"), IMAGE_ID, true);
        } else {
            return getHandler(HROOPENDAY_IMAGE, Arrays.asList(IMAGE_DESCRIPTION), Arrays.asList(image_description), IMAGE_ID, true);
        }
    }
    private String[] getStudy_id(String institute_fullname) {
        return getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_INSTITUTEFULLNAME), Arrays.asList(institute_fullname), STUDY_ID, true);
    }
    private String[] getActivity_id(String openday_date) {
        return getHandler(HROOPENDAY_ACTIVITY, Arrays.asList(ACTIVITY_OPENDAYDATE), Arrays.asList(openday_date), ACTIVITY_ID, true);
    }
    private String[] getAppinfo(String id) {
        ArrayList<String> result = new ArrayList<>();

        result.add(getHandler(HROOPENDAY_APPINFO, Arrays.asList(APPINFO_ID), Arrays.asList(id), APPINFO_DATAVERSION, true)[0]);
        result.add(getHandler(HROOPENDAY_APPINFO, Arrays.asList(APPINFO_ID), Arrays.asList(id), APPINFO_APILINK, true)[0]);

        return stringListType(result);
    }

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
        String[] appinfo_id = getAllAppInfo();
        String link = "";
        Integer local_version = -1;
        Integer online_version = -1;
        if (appinfo_id.length > 0) {
            for (int i = 0; i < appinfo_id.length; i++) {
                if (Integer.parseInt(getAppinfo(appinfo_id[i])[0]) > local_version) {
                    local_version = Integer.parseInt(getAppinfo(appinfo_id[i])[0]);
                    link = getAppinfo(appinfo_id[i])[1];
                }
            }
            link += "/version";

            if (online_version == local_version) {
                return false;
            } else if(online_version == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    // Language
    private Boolean language() {
        if(Locale.getDefault().getLanguage() == "nl") {
            return true;
        } else {
            return false;
        }
    }
    private String[] getStudy(String study_id, Boolean language) {
        ArrayList<String> result = new ArrayList<>();

        result.add(getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_ID), Arrays.asList(study_id), STUDY_INSTITUTEFULLNAME, true)[0]);
        result.add(getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_ID), Arrays.asList(study_id), STUDY_TYPE, true)[0]);
        if (language == true) {
            result.add(getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_ID), Arrays.asList(study_id), STUDY_NAME_DUTCH, true)[0]);
            result.add(getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_ID), Arrays.asList(study_id), STUDY_GENERALINFORMATION_DUTCH, true)[0]);
        } else {
            result.add(getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_ID), Arrays.asList(study_id), STUDY_NAME_ENGLISH, true)[0]);
            result.add(getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_ID), Arrays.asList(study_id), STUDY_GENERALINFORMATION_ENGLISH, true)[0]);
        }
        result.add(study_id);
        result.add(getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_ID), Arrays.asList(study_id), STUDY_ICON, true)[0]);

        return stringListType(result);
    }

    // GET Handler
    private String[] stringListType(ArrayList<String> mArraylist) {
        return mArraylist.toArray(new String[mArraylist.size()]);
    }
    private String[] getHandler(String table, List<String> arguments, List<String> values, String returnColumn, Boolean doubleCheck) {
        ArrayList<String> mArrayList = new ArrayList<>();
        Cursor mCursor = viewAll(table, arguments, values);

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

        return stringListType(mArrayList);
    }
    private Cursor viewAll(String table, List<String> arguments, List<String> values) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + table;
        Cursor cursor;
        if (values != null && arguments != null) {
            query += ArgumentHandler(arguments);
            cursor = db.rawQuery(query, values.toArray(new String[0]));
        } else {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
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
        db.execSQL("CREATE TABLE " + HROOPENDAY_INSTITUTE + "(" + INSTITUTE_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, " + INSTITUTE_FULLNAME + " TEXT, " + INSTITUTE_SHORTNAME + " TEXT, " + INSTITUTE_PHONENUMBER + " TEXT, " + INSTITUTE_GENERALINFORMATION_ENGLISH + " TEXT, " + INSTITUTE_GENERALINFORMATION_DUTCH + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + HROOPENDAY_STUDY + "(" + STUDY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + STUDY_INSTITUTEFULLNAME + " TEXT, " + STUDY_GENERALINFORMATION_DUTCH + " TEXT, " + STUDY_GENERALINFORMATION_ENGLISH + " TEXT, " + STUDY_NAME_DUTCH + " TEXT, " + STUDY_TYPE + " TEXT, " + STUDY_ICON + " TEXT, " + STUDY_NAME_ENGLISH + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + HROOPENDAY_ACTIVITY + "(" + ACTIVITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ACTIVITY_OPENDAYDATE + " TEXT, " + ACTIVITY_STUDYNAME + " TEXT, " + ACTIVITY_STARTTIME + " TEXT, " + ACTIVITY_ENDTIME + " TEXT, " + ACTIVITY_CLASSROOM + " TEXT, " + ACTIVITY_INFORMATION_DUTCH + " TEXT, " + ACTIVITY_INFORMATION_ENGLISH + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + HROOPENDAY_LOCATION + "(" + LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + LOCATION_STREET + " TEXT, " + LOCATION_CITY + " TEXT, " + LOCATION_ZIPCODE + " TEXT, " + LOCATION_INSTITUTEFULLNAME + " TEXT, " + LOCATION_IMAGEDESCRIPTION + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + HROOPENDAY_IMAGE + "(" + IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + IMAGE_FILENAME + " TEXT, " + IMAGE_CONTEXT + " TEXT, " + IMAGE_DESCRIPTION + " TEXT" + ")");
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
