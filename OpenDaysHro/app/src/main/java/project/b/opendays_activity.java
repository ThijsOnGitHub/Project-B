package project.b;

import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.content.Intent;

import org.w3c.dom.Text;

public class opendays_activity extends appHelper {

    LayoutHelper layout;

    String[] passedInfo;

    String Description;
    String Location;
    String Time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opendays);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        layout = new LayoutHelper(this);

        int header_image = R.drawable.beginning_by_ryky;
        int add_to_calendar_image = R.drawable.calendar_icon;
        int share_image = R.drawable.ic_chat_white_24dp;

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

        passedInfo = getIntent().getStringArrayExtra("INFO");
        try {
            Description = passedInfo[0];
            Location = passedInfo[1];
            Time = passedInfo[2];
        }
        catch (Exception e){ Description = ""; Location = ""; Time = ""; System.out.println(e); }

        layout.workshop("General Information\n" + Description,"H2.002", Time,R.id.page_container);
        layout.workshop("General Information\n" + Description,"H2.002", Time,R.id.page_container);
        layout.workshop("General Information\n" + Description,"H2.002", Time,R.id.page_container);


        Intent home = new Intent(getBaseContext(), MainActivity.class);
        Intent educations = new Intent(getBaseContext(), educations_activity.class);
        Intent about_cmi = new Intent(getBaseContext(), About_activity.class);
        Intent contact = new Intent(getBaseContext(), contact_activity.class);

        Intent[] myIntents = new Intent[]{home,educations,about_cmi,contact};
        int[] images = new int[]{R.drawable.ic_home_white_24dp,R.drawable.ic_location_city_white_24dp,R.drawable.ic_map_white_24dp,R.drawable.ic_chat_white_24dp};
        String[] text = new String[]{"home","Study programs","About CMI","Contact"};

        layout.generate_menu(R.id.menu_bar,images,text,myIntents);


    }


}
