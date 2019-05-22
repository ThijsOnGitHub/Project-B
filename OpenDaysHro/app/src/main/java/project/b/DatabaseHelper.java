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
    private static final int HROOPENDAY_VERSION = 39;
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
        
        private static final String HROOPENDAY_APPINFO = "app_info";
            private static final String APPINFO_ID = "id";
            private static final String APPINFO_DATAVERSION = "data_version";
            private static final String APPINFO_APILINK = "api_link";

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // GET DATA

    public String[] getUpcomingOpendays() {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> opendays_id = getAllOpendays();
        ArrayList<String> openday = new ArrayList<>();
        ArrayList<String> current_openday = new ArrayList<>();

        Date current_openday_date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        if (opendays_id.size() > 0) {
            for (int i = 0; i < opendays_id.size(); i++) {
                openday = getOpenday(opendays_id.get(i));
                if (openday.size() > 0) {
                    Date date_now = new Date();
                    String date = openday.get(1) + " " + openday.get(3);
                    Date date_openday = dateFormat.parse(date, new ParsePosition(0));

                    if (date_now.compareTo(date_openday) <= 0) {
                        // add to result
                        Integer index = 0;
                        if (result.size() > 0) {
                            for (int j = 0; j < result.size(); j++) {
                                current_openday_date = dateFormat.parse(getOpenday(result.get(j)).get(1) + " " + getOpenday(result.get(j)).get(3), new ParsePosition(0));

                                if (date_openday.compareTo(current_openday_date) > 0) {
                                    index++;
                                } else {
                                    break;
                                }
                            }
                        }
                        result.add(index, opendays_id.get(i));
                    }
                }
            }
        }

        String[] result_string = result.toArray(new String[result.size()]);
        return result_string;
    }
        public String[] getOpendayInfo(String openday_id) {
            ArrayList<String> result = getOpenday(openday_id);
            String[] result_string = result.toArray(new String[result.size()]);
            return result_string;
        }
        public String[] getCalenderInfo(String openday_id) {
            ArrayList<String> result = new ArrayList<>();
            ArrayList<String> location = new ArrayList<>();
            String institute_id = "";
            String institute_fullname = "";
            String institute_shortname = "";
            String zipcode = "";
            String starttime = "";
            String endtime = "";
            String date = "";


            ArrayList<String> openday = getOpenday(openday_id);
            if(openday.size() > 0) {
                institute_fullname = openday.get(0);
                date = openday.get(1);
                starttime = openday.get(2);
                endtime = openday.get(3);
            }
            ArrayList<String> institutes = getInstitute_id(institute_fullname);
            if(institutes.size() > 0) {
                institute_id = institutes.get(0);
            }
            ArrayList<String> institute = getInstitute(institute_id);
            if(institute.size() > 0) {
                institute_shortname = institute.get(1);
            }
            ArrayList<String> location_id = getLocation_id(institute_fullname);
            if(location_id.size() > 0) {
                location = getLocation(location_id.get(0));
                if(location.size() > 0) {
                    zipcode = location.get(2);
                }
            }

            result.add(institute_shortname);
            result.add(zipcode);
            result.add(starttime);
            result.add(endtime);
            result.add(date);

            String[] result_string = result.toArray(new String[result.size()]);
            return result_string;
        }

    public String[] getActivitiesByOpenday(String openday_id) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> activity = new ArrayList<>();
        ArrayList<String> study = new ArrayList<>();
        ArrayList<String> StudyNameEnglishByInstitute = new ArrayList<>();
        ArrayList<String> StudyNameDutchByInstitute = new ArrayList<>();
        ArrayList<String> StudyNameByDate = new ArrayList<>();
        String institute_fullname = "";
        String openday_date = "";

        ArrayList<String> openday = getOpenday(openday_id);
        if (openday.size() > 0) {
            institute_fullname = openday.get(0);
            openday_date = openday.get(1);
        }

        ArrayList<String> IdByDate = getActivity_id(openday_date);
        if(IdByDate.size() > 0) {
            for (int i = 0; i < IdByDate.size(); i++) {
                activity = getActivity(IdByDate.get(i));
                if(activity.size() > 0) {
                    StudyNameByDate.add(activity.get(0));
                }
            }
        }

        ArrayList<String> studies = getStudy_id(institute_fullname);
        if (studies.size() > 0) {
            for (int i = 0; i < studies.size(); i++) {
                study = getStudy(studies.get(i));
                if (study.size() > 0) {
                    StudyNameEnglishByInstitute.add(study.get(2));
                }
            }
        }

        if (studies.size() > 0) {
            for (int i = 0; i < studies.size(); i++) {
                study = getStudy(studies.get(i));
                if (study.size() > 0) {
                    StudyNameDutchByInstitute.add(study.get(2));
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
                    if (!result.contains(IdByDate.get(i))){
                        result.add(IdByDate.get(i));
                    }
                } else if(currentDate.equals(currentEnglish)) {
                    if (!result.contains(IdByDate.get(i))) {
                        result.add(IdByDate.get(i));
                    }
                }
            }
        }

        String[] result_string = result.toArray(new String[result.size()]);
        return result_string;
    }
        public String[] getActivityInfo(String activity_id) {
            ArrayList<String> result = getActivity(activity_id);
            String[] result_string = result.toArray(new String[result.size()]);
            return result_string;
        }

    public String[] getStudiesByInstitute(String institute_id) {
        ArrayList<String> result = new ArrayList<>();
        String institute_fullname = "";

        ArrayList<String> institute = getInstitute(institute_id);
        if (institute.size() > 0) {
            institute_fullname = institute.get(0);
        }

        ArrayList<String> studies_id = getStudy_id(institute_fullname);
        if (studies_id.size() > 0) {
            for (int i = 0; i < studies_id.size(); i++) {
                result.add(studies_id.get(i));
            }
        }

        String[] result_string = result.toArray(new String[result.size()]);
        return result_string;
    }
        public String[] getStudyInfo(String study_id) {
            ArrayList<String> result = getStudy(study_id);
            String[] result_string = result.toArray(new String[result.size()]);
            return result_string;
        }
        public String[] getNamesOfStudiesByInstitute(String institute_id) {
            ArrayList<String> result = new ArrayList<>();
            ArrayList<String> study_info = new ArrayList<>();
            String institute_fullname = "";
            ArrayList<String> institute = getInstitute(institute_id);

            if(institute.size() > 0) {
                institute_fullname = institute.get(0);
            }

            ArrayList<String> studies = getStudy_id(institute_fullname);

            for (int i = 0; i < studies.size(); i++) {
                study_info = getStudy(studies.get(i));
                for (int j = 0; j < study_info.size(); j++) {
                    if (!result.contains(study_info.get(2))) {
                        result.add(study_info.get(2));
                    }
                }
            }

            String[] result_string = result.toArray(new String[result.size()]);
            return result_string;
        }

    public String[] getLocationsByInstitute(String institute_id) {
        ArrayList<String> result = new ArrayList<>();
        String institute_fullname = "";

        ArrayList<String> institute = getLocation(institute_id);
        if (institute.size() > 0) {
            institute_fullname = institute.get(0);
        }

        ArrayList<String> locations_id = getLocation_id(institute_fullname);
        if (locations_id.size() > 0) {
            for (int i = 0; i < locations_id.size(); i++) {
                result.add(locations_id.get(i));
            }
        }

        String[] result_string = result.toArray(new String[result.size()]);
        return result_string;
    }
        public String[] getLocationInfo(String location_id) {
            ArrayList<String> result = getLocation(location_id);
            String[] result_string = result.toArray(new String[result.size()]);
            return result_string;
        }

    public String[] getImagesByLocation(String location_id, Boolean floorplan) {
        ArrayList<String> result = new ArrayList<>();
        String image_description = "";

        ArrayList<String> location = getLocation(location_id);
        if (location.size() > 0) {
            image_description = location.get(0);
        }

        ArrayList<String> images_id = getImage_id(image_description, floorplan);
        if (images_id.size() > 0) {
            for (int i = 0; i < images_id.size(); i++) {
                result.add(images_id.get(i));
            }
        }

        String[] result_string = result.toArray(new String[result.size()]);
        return result_string;
    }
        public String[] getImageInfo(String image_id) {
            ArrayList<String> result = getImage(image_id);
            String[] result_string = result.toArray(new String[result.size()]);
            return result_string;
        }
        public String[] getFloorplansByInstitute(String institute_id) {
            ArrayList<String> result = new ArrayList<>();
            ArrayList<String> location = new ArrayList<>();
            ArrayList<String> images_id = new ArrayList<>();
            ArrayList<String> image = new ArrayList<>();
            String institute_fullname = "";
            String imagedescription = "";

            ArrayList<String> institute = getInstitute(institute_id);
            if(institute.size() > 0) {
                institute_fullname = institute.get(0);
            }

            ArrayList<String> locations_id = getLocation_id(institute_fullname);
            if (locations_id.size() > 0) {
                for (int i = 0; i < locations_id.size(); i++) {
                    location = getLocation(locations_id.get(i));

                    if (location.size() > 0) {
                        imagedescription = location.get(0);
                        images_id = getImage_id(imagedescription, true);

                        if (images_id.size() > 0) {
                            for (int j = 0; j < images_id.size(); j++) {
                                image = getImage(images_id.get(j));

                                if (image.size() > 0) {
                                    if (!result.contains(image.get(1))) {
                                        result.add(image.get(0));
                                    }
                                }
                            }
                        }

                    }

                }
            }

            String[] result_string = result.toArray(new String[result.size()]);
            return result_string;
        }

    public String[] getInstitutes() {
        ArrayList<String> result = getAllInstitutes();
        String[] result_string = result.toArray(new String[result.size()]);
        return result_string;
    }
        public String[] getInstituteInfo(String institute_id) {
            ArrayList<String> result = getInstitute(institute_id);
            String[] result_string = result.toArray(new String[result.size()]);
            return result_string;
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
    }
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

    //////////////////////////////////////////////////////////////////////////
    // CHECKS

    public void fillDatabase() {
        // API CALL......



        // Create CMI
        createInstitute("Communicatie, Media en Informatietechnologie", "CMI", "The School of Communication, Media and Information Technology (CMI) provides higher education and applied research for the creative industry. As a committed partner CMI creates knowledge, skills and expertise for the ongoing development of the industry.", "Het instituut voor Communicatie, Media en Informatietechnologie (CMI) heeft met de opleidingen Communicatie, Informatica, Technische Informatica, Creative Media and Game Technologies en Communication and Multimedia Design maar liefst 3000 studenten die een waardevolle bijdrage leveren aan de onbegrensde wereld van communicatie, media en ICT.");

        // CMI Studies
        createStudy("Communicatie, Media en Informatietechnologie", "Informatica", "Software engineering", "Full-time / Part-time", "informatica info dutch", "informatica info english");
        createStudy("Communicatie, Media en Informatietechnologie", "Technisch Informatica", "Computer engineering", "Full-time", "TI info dutch", "TI info english");
        createStudy("Communicatie, Media en Informatietechnologie", "Creative Media and Game Technologies", "Creative Media and Game Technologies", "Full-time", "CMGT info dutch", "CMGT info english");
        createStudy("Communicatie, Media en Informatietechnologie", "Communicatie", "Communication","Full-time / Part-time", "Comminucatie info dutch", "Comminucatie info english");
        createStudy("Communicatie, Media en Informatietechnologie", "Communication & Multimedia Design", "Communication & Multimedia Design", "Full-time", "CMD info dutch", "CMD info english");
        // CMI locations
        createLocation("Wijnhaven 107", "Rotterdam", "Communicatie, Media en Informatietechnologie", "3011WN", "0107944000", "3011WN107");
        createLocation("Wijnhaven 103", "Rotterdam", "Communicatie, Media en Informatietechnologie", "3011WN", "0107944000", "3011WN103");
        createLocation("Wijnhaven 99", "Rotterdam", "Communicatie, Media en Informatietechnologie", "3011WN","0107944000", "3011WN99");

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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // GET CONTENT
    private ArrayList<String> getLocation(String id) {
        ArrayList<String> result = new ArrayList<>();

        String imagedescription = getHandler(HROOPENDAY_LOCATION, Arrays.asList(LOCATION_ID), Arrays.asList(id), LOCATION_IMAGEDESCRIPTION, true).get(0);
        String street = getHandler(HROOPENDAY_LOCATION, Arrays.asList(LOCATION_ID), Arrays.asList(id), LOCATION_STREET, true).get(0);
        String zipcode = getHandler(HROOPENDAY_LOCATION, Arrays.asList(LOCATION_ID), Arrays.asList(id), LOCATION_ZIPCODE, true).get(0);
        String city = getHandler(HROOPENDAY_LOCATION, Arrays.asList(LOCATION_ID), Arrays.asList(id), LOCATION_CITY, true).get(0);
        String phonenumber = getHandler(HROOPENDAY_LOCATION, Arrays.asList(LOCATION_ID), Arrays.asList(id), LOCATION_PHONENUMBER, true).get(0);
        String institute_fullname = getHandler(HROOPENDAY_LOCATION, Arrays.asList(LOCATION_ID), Arrays.asList(id), LOCATION_INSTITUTEFULLNAME, true).get(0);

        result.add(imagedescription);
        result.add(street);
        result.add(zipcode);
        result.add(city);
        result.add(phonenumber);
        result.add(institute_fullname);
        result.add(id);

        return result;
    }
    private ArrayList<String> getStudy(String id) {
        Boolean language = language();
        ArrayList<String> result = new ArrayList<>();
        String institute_fullname = "";
        String name = "";
        String type = "";
        String general_information = "";

        institute_fullname = getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_ID), Arrays.asList(id), STUDY_INSTITUTEFULLNAME, true).get(0);
        type = general_information = getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_ID), Arrays.asList(id), STUDY_TYPE, true).get(0);
        if (language == true) {
            name = getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_ID), Arrays.asList(id), STUDY_NAME_DUTCH, true).get(0);
            general_information = getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_ID), Arrays.asList(id), STUDY_GENERALINFORMATION_DUTCH, true).get(0);
        } else {
            name = getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_ID), Arrays.asList(id), STUDY_NAME_ENGLISH, true).get(0);
            general_information = getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_ID), Arrays.asList(id), STUDY_GENERALINFORMATION_ENGLISH, true).get(0);
        }


        result.add(institute_fullname);
        result.add(type);
        result.add(name);
        result.add(general_information);
        result.add(id);

        return result;
    }
    private ArrayList<String> getOpenday(String id) {
        ArrayList<String> result = new ArrayList<>();
        String institute_fullname = getHandler(HROOPENDAY_OPENDAY, Arrays.asList(OPENDAY_ID), Arrays.asList(id), OPENDAY_INSTITUTEFULLNAME, true).get(0);
        String date = getHandler(HROOPENDAY_OPENDAY, Arrays.asList(OPENDAY_ID), Arrays.asList(id), OPENDAY_DATE, true).get(0);
        String startime = getHandler(HROOPENDAY_OPENDAY, Arrays.asList(OPENDAY_ID), Arrays.asList(id), OPENDAY_STARTTIME, true).get(0);
        String endtime = getHandler(HROOPENDAY_OPENDAY, Arrays.asList(OPENDAY_ID), Arrays.asList(id), OPENDAY_ENDTIME, true).get(0);

        result.add(institute_fullname);
        result.add(date);
        result.add(startime);
        result.add(endtime);
        result.add(id);

        return result;
    }
    private ArrayList<String> getActivity(String id) {
        Boolean language = language();
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
        result.add(id);

        return result;
    }
    private ArrayList<String> getInstitute(String id) {
        Boolean language = language();
        ArrayList<String> result = new ArrayList<>();
        String fullname = getHandler(HROOPENDAY_INSTITUTE, Arrays.asList(INSTITUTE_ID), Arrays.asList(id), INSTITUTE_FULLNAME, true).get(0);
        String shortname = getHandler(HROOPENDAY_INSTITUTE, Arrays.asList(INSTITUTE_ID), Arrays.asList(id), INSTITUTE_SHORTNAME, true).get(0);
        String information = "";

        if (language == true) {
            information = getHandler(HROOPENDAY_INSTITUTE, Arrays.asList(INSTITUTE_ID), Arrays.asList(id), INSTITUTE_GENERALINFORMATION_DUTCH, true).get(0);
        } else {
            information = getHandler(HROOPENDAY_INSTITUTE, Arrays.asList(INSTITUTE_ID), Arrays.asList(id), INSTITUTE_GENERALINFORMATION_ENGLISH, true).get(0);
        }

        result.add(fullname);
        result.add(shortname);
        result.add(information);
        result.add(id);

        return result;
    }
    private ArrayList<String> getImage(String id) {
        ArrayList<String> result = new ArrayList<>();
        String filename = getHandler(HROOPENDAY_IMAGE, Arrays.asList(IMAGE_ID), Arrays.asList(id), IMAGE_FILENAME, true).get(0);
        String context = getHandler(HROOPENDAY_IMAGE, Arrays.asList(IMAGE_ID), Arrays.asList(id), IMAGE_CONTEXT, true).get(0);
        String description = getHandler(HROOPENDAY_IMAGE, Arrays.asList(IMAGE_ID), Arrays.asList(id), IMAGE_DESCRIPTION, true).get(0);

        result.add(filename);
        result.add(context);
        result.add(description);
        result.add(id);

        return result;
    }

   // GET All ID
    private ArrayList<String> getAllOpendays() {
        return getHandler(HROOPENDAY_OPENDAY, null, null, OPENDAY_ID, true);
    }
    private ArrayList<String> getAllInstitutes() {
        return getHandler(HROOPENDAY_INSTITUTE, null, null, INSTITUTE_ID, true);
    }

    // GET LINKED ID
    private ArrayList<String> getLocation_id(String institute_fullname) {
        return getHandler(HROOPENDAY_LOCATION, Arrays.asList(LOCATION_INSTITUTEFULLNAME), Arrays.asList(institute_fullname), LOCATION_ID, true);
    }
    private ArrayList<String> getImage_id(String image_description, Boolean floorplan) {
        if (floorplan == true) {
            return getHandler(HROOPENDAY_IMAGE, Arrays.asList(IMAGE_DESCRIPTION, IMAGE_CONTEXT), Arrays.asList(image_description, "floorplan"), IMAGE_ID, true);
        } else {
            return getHandler(HROOPENDAY_IMAGE, Arrays.asList(IMAGE_DESCRIPTION), Arrays.asList(image_description), IMAGE_ID, true);
        }
    }
    private ArrayList<String> getStudy_id(String institute_fullname) {
        return getHandler(HROOPENDAY_STUDY, Arrays.asList(STUDY_INSTITUTEFULLNAME), Arrays.asList(institute_fullname), STUDY_ID, true);
    }
    private ArrayList<String> getInstitute_id(String institute_fullname) {
        return getHandler(HROOPENDAY_INSTITUTE, Arrays.asList(INSTITUTE_FULLNAME), Arrays.asList(institute_fullname), INSTITUTE_ID, true);
    }
    private ArrayList<String> getActivity_id(String openday_date) {
        return getHandler(HROOPENDAY_ACTIVITY, Arrays.asList(ACTIVITY_OPENDAYDATE), Arrays.asList(openday_date), ACTIVITY_ID, true);
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

    // Language
    private Boolean language() {
        if(Locale.getDefault().getLanguage() == "nl") {
            return true;
        } else {
            return false;
        }
    }

    // GET Handler
    private ArrayList<String> getHandler(String table, List<String> arguments, List<String> values, String returnColumn, Boolean doubleCheck) {
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

        return mArrayList;
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
        db.execSQL("CREATE TABLE " + HROOPENDAY_INSTITUTE + "(" + INSTITUTE_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, " + INSTITUTE_FULLNAME + " TEXT, " + INSTITUTE_SHORTNAME + " TEXT, " + INSTITUTE_GENERALINFORMATION_ENGLISH + " TEXT, " + INSTITUTE_GENERALINFORMATION_DUTCH + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + HROOPENDAY_STUDY + "(" + STUDY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + STUDY_INSTITUTEFULLNAME + " TEXT, " + STUDY_GENERALINFORMATION_DUTCH + " TEXT, " + STUDY_GENERALINFORMATION_ENGLISH + " TEXT, " + STUDY_NAME_DUTCH + " TEXT, " + STUDY_TYPE + " TEXT, " + STUDY_NAME_ENGLISH + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + HROOPENDAY_ACTIVITY + "(" + ACTIVITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ACTIVITY_OPENDAYDATE + " TEXT, " + ACTIVITY_STUDYNAME + " TEXT, " + ACTIVITY_STARTTIME + " TEXT, " + ACTIVITY_ENDTIME + " TEXT, " + ACTIVITY_CLASSROOM + " TEXT, " + ACTIVITY_INFORMATION_DUTCH + " TEXT, " + ACTIVITY_INFORMATION_ENGLISH + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + HROOPENDAY_LOCATION + "(" + LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + LOCATION_STREET + " TEXT, " + LOCATION_CITY + " TEXT, " + LOCATION_ZIPCODE + " TEXT, " + LOCATION_INSTITUTEFULLNAME + " TEXT, " + LOCATION_PHONENUMBER + " TEXT, " + LOCATION_IMAGEDESCRIPTION + " TEXT" + ")");
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
