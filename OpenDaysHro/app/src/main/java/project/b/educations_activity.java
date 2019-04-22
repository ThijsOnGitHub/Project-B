package project.b;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import static java.security.AccessController.getContext;

public class educations_activity extends appHelper {

    LayoutHelper layout;
    String passedName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educations);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        try { passedName = getIntent().getStringExtra("NAME"); } catch (Exception e){ System.out.println(e); passedName = null; }

        layout = new LayoutHelper(this, true);
        if (passedName == null) {
            int[] images = new int[]{R.drawable.calendar_icon, R.drawable.ic_location_city_white_24dp, R.drawable.ic_map_white_24dp, R.drawable.ic_home_white_24dp, R.drawable.ic_chat_white_24dp};
            String[] text = new String[]{"testing", "the", "generated", "button", "menu"};

            layout.generate_study_program_menu(R.id.page_container, images, text);
        }
        else {
            TextView test = new TextView(this);
                test.setText(passedName);
            LinearLayout lay = (LinearLayout) findViewById(R.id.page_container);
            lay.addView(test);
        }
    }

}
