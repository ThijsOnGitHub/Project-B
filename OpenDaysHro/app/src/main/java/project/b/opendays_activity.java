package project.b;

import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.content.Intent;
import android.util.EventLogTags;

public class opendays_activity extends appHelper {

    LayoutHelper layout;

    String[] passedInfo;

    String openday_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opendays);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        layout = new LayoutHelper(this);

        int header_image = R.drawable.blaak;
        int add_to_calendar_image = R.drawable.calendar_icon;
        int share_image = R.drawable.twotone_share_24px;

        String Calendar_event_title = "HRO Open day";
        String Calendar_event_description = "CMI";
        String Calendar_event_location = "3011WN Rotterdam";
        int Calendar_event_year = 2019;
        int Calendar_event_month = 5;
        int Calendar_event_day = 5;
        int Calendar_event_START_hour = 8;
        int Calendar_event_START_min = 0;
        int Calendar_event_END_hour = 18;
        int Calendar_event_END_min = 0;

        layout.calendar_page(R.id.page_container, header_image, add_to_calendar_image, share_image, Calendar_event_title,
                Calendar_event_description, Calendar_event_location, Calendar_event_year, Calendar_event_month, Calendar_event_day,
                Calendar_event_START_hour, Calendar_event_START_min, Calendar_event_END_hour, Calendar_event_END_min);

        String study_id;

        try { passedInfo = getIntent().getStringArrayExtra("INFO"); study_id = passedInfo[1]; }
        catch (Exception e) { study_id = ""; }

        passedInfo = getIntent().getStringArrayExtra("INFO");
        openday_id = passedInfo[0];

        String[] openday = layout.db.getOpendayInfo(openday_id);

        if ( study_id.equals("")) {
            // Studies
            String[] studies = layout.db.getStudiesWithActivitiesByOpenday(openday_id);

            for (int i = 0; i < studies.length; i++) {
                layout.workshop_menu(studies[i], openday_id, R.id.page_container);
            }
        }
        else{
//            get workshops from study id + openday id
//            layout.workshop("Python workshop", "H2.002", "06:00-08:00", R.id.page_container);
            String[] activities = layout.db.getActivitiesByStudyAndOpenday(openday_id, study_id);

            for (int i = 0; i < activities.length; i++) {
                String[] activity = layout.db.getActivityInfo(activities[i]);

                layout.workshop(activity[0], activity[2], activity[3].substring(0, activity[3].length() - 3) + "-" + activity[4].substring(0, activity[4].length() -3), R.id.page_container);
            }
        }

        Intent home = new Intent(getBaseContext(), MainActivity.class);
        Intent educations = new Intent(getBaseContext(), educations_activity.class);
        Intent about_cmi = new Intent(getBaseContext(), About_activity.class);
        Intent contact = new Intent(getBaseContext(), contact_activity.class);

        Intent[] myIntents = new Intent[]{home,educations,about_cmi,contact};
        int[] images = new int[]{R.drawable.ic_home_grey_24dp,R.drawable.baseline_school_24px,R.drawable.ic_location_city_white_24dp,R.drawable.ic_chat_white_24dp};
        String[] text = new String[]{"Home","Study Programs","About CMI","Contact"};

        layout.generate_menu(R.id.menu_bar,images,text,myIntents);


    }


}
