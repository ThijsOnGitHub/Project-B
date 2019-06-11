package project.b;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class opendays_activity extends appHelper {

    LayoutHelper layout;

    String[] passedInfo;

    String openday_id;
    String study_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opendays);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        layout = new LayoutHelper(this);

        int header_image = R.drawable.blaak;
        int add_to_calendar_image = R.drawable.twotone_calendar_today_24;
        int share_image = R.drawable.twotone_share_24px;


        try { passedInfo = getIntent().getStringArrayExtra("INFO"); study_id = passedInfo[1]; }
        catch (Exception e) { study_id = ""; }
        passedInfo = getIntent().getStringArrayExtra("INFO");
        openday_id = passedInfo[0];

        String[] calendar = layout.db.getCalenderInfo(openday_id);

        String Calendar_event_title = "HRO Open day";
        String Calendar_event_description = calendar[0];
        String Calendar_event_location = calendar[1];

        String[] starttime = calendar[2].split(":");
        String[] endtime = calendar[3].split(":");
        String[] date = calendar[4].split("-");
        
        int Calendar_event_year = Integer.parseInt(date[2]);
        int Calendar_event_month = Integer.parseInt(date[1]);
        int Calendar_event_day = Integer.parseInt(date[0]);
        int Calendar_event_START_hour = Integer.parseInt(starttime[0]);
        int Calendar_event_START_min = Integer.parseInt(starttime[1]);
        int Calendar_event_END_hour = Integer.parseInt(endtime[0]);
        int Calendar_event_END_min = Integer.parseInt(endtime[1]);

        layout.calendar_page(R.id.page_container, header_image, add_to_calendar_image, share_image, Calendar_event_title,
                Calendar_event_description, Calendar_event_location, Calendar_event_year, Calendar_event_month, Calendar_event_day,
                Calendar_event_START_hour, Calendar_event_START_min, Calendar_event_END_hour, Calendar_event_END_min, openday_id);

        String[] openday = layout.db.getOpendayInfo(openday_id);

        if ( study_id.equals("")) {
            // Studies
            String[] studies = layout.db.getStudiesWithActivitiesByOpenday(openday_id);

            for (int i = 0; i < studies.length; i++) {
                layout.workshop_menu(studies[i], openday_id, R.id.page_container);
            }
        }
        else{
            String[] activities = layout.db.getActivitiesByStudyAndOpenday(openday_id, study_id);

            for (int i = 0; i < activities.length; i++) {
                String[] activity = layout.db.getActivityInfo(activities[i]);
                String institute_id = layout.db.getInstitute_id(openday[0])[0];

                layout.workshop(activity[0], activity[2], activity[3].substring(0, activity[3].length() - 3) + "-" + activity[4].substring(0, activity[4].length() -3), institute_id, R.id.page_container);
            }
        }

        Intent home = new Intent(getBaseContext(), MainActivity.class);
        Intent educations = new Intent(getBaseContext(), educations_activity.class);
        Intent about_cmi = new Intent(getBaseContext(), About_activity.class);
        Intent contact = new Intent(getBaseContext(), contact_activity.class);

        Intent[] myIntents = new Intent[]{home,educations,about_cmi,contact};
        int[] images = new int[]{R.drawable.ic_home_grey_24dp,R.drawable.baseline_school_24px,R.drawable.ic_location_city_white_24dp,R.drawable.ic_chat_white_24dp};

        String[] text = new String[]{"Home","Study Programs","About CMI","Contact"};
        if(layout.db.language() == true) {
            text = new String[]{"Home", "Studies", "Over CMI", "Contact"};
        }

        layout.generate_menu(R.id.menu_bar,images,text,myIntents);


    }


}
