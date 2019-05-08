package project.b;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class menu_activity extends appHelper {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

}
/*
public class menu_activity extends appHelper {

    LayoutHelper layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        layout = new LayoutHelper(this,true);

        Intent swapToHome = new Intent(this,MainActivity.class); swapToHome.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Intent swapToEducation = new Intent(this,educations_activity.class); swapToEducation.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Intent swapToMap = new Intent(this,map_activity.class); swapToMap.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Intent swapToOpenDays = new Intent(this,opendays_activity.class); swapToOpenDays.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Intent swapToContact = new Intent(this,contact_activity.class); swapToContact.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        Intent about_cmi = new Intent(getBaseContext(), educations_activity.class);

        Intent[] myIntents = new Intent[]{swapToHome,swapToEducation,about_cmi,swapToContact};
        int[] images = new int[]{R.drawable.ic_home_white_24dp,R.drawable.ic_location_city_white_24dp,R.drawable.ic_map_white_24dp,R.drawable.ic_chat_white_24dp};
        String[] text = new String[]{"home","educations","About CMI","Contact"};

        layout.generate_menu(R.id.page_container,images,text,myIntents);
    }

}
 */
