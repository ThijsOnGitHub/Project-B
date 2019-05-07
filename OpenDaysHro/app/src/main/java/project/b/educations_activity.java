package project.b;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class educations_activity extends appHelper {

    DatabaseHelper myDatabase;
    LayoutHelper layout;
    String passedName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educations);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        try { passedName = getIntent().getStringExtra("NAME"); } catch (Exception e){ System.out.println(e); passedName = null; }

        layout = new LayoutHelper(this);
        if (passedName == null) {

            myDatabase = new DatabaseHelper(this);

            // getting a list with names of all studies. (for english use false and for dutch use true). ~Credits: Christian.
            ArrayList<String> studyNames = myDatabase.getNamesOfStudiesByInstitute("Communicatie, Media en Informatietechnologie", false);

            int[] images = new int[]{R.drawable.calendar_icon, R.drawable.ic_location_city_white_24dp, R.drawable.ic_map_white_24dp, R.drawable.ic_home_white_24dp, R.drawable.ic_chat_white_24dp};
            String[] text = new String[studyNames.size()];

            // creating a string[] from a ArrayList<String>
            for ( int x = 0; x < studyNames.size(); x++) { text[x] = studyNames.get(x); }

            layout.generate_study_program_menu(R.id.page_container, images, text);
        }
        else {
            layout.generate_page_study_programs(R.drawable.blaak,passedName,R.id.page_container);
        }

        Intent home = new Intent(getBaseContext(), MainActivity.class);
        Intent educations = new Intent(getBaseContext(), educations_activity.class);
        Intent about_cmi = new Intent(getBaseContext(), About_activity.class);
        Intent contact = new Intent(getBaseContext(), contact_activity.class);

        Intent[] myIntents = new Intent[]{home,educations,about_cmi,contact};
        int[] images = new int[]{R.drawable.ic_home_white_24dp,R.drawable.baseline_school_24px,R.drawable.ic_location_city_white_24dp,R.drawable.ic_chat_white_24dp};
        String[] text = new String[]{"home","Study programs","About CMI","Contact"};

        layout.generate_menu(R.id.menu_bar,images,text,myIntents);

    }


}
