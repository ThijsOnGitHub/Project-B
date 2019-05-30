package project.b;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends appHelper {

    LayoutHelper layout;

    int numOfListItems;

    int[] drawables = new int[]{R.drawable.gebouw_cmi,R.drawable.lerende_student,
                                    R.drawable.werkende_studenten,R.drawable.wijnhaven};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        layout = new LayoutHelper(this);

        try {
            if (layout.db.checkDatabase()) {
                if (layout.db.isOnline(this) == true) {
                    jsonApi json = new jsonApi();
                    json.execute();
                    JSONObject jsonObject = new JSONObject(json.data);
                    layout.db.fillDatabaseWithJson(jsonObject);
                } else {
                    layout.db.fillDatabase_offline();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        layout.Image_with_Buttons(R.id.page_container,drawables);

        String[] opendays_ids = layout.db.getUpcomingOpendays();
        for (int i = 0; i < opendays_ids.length; i++) {
            layout.ListItem_openday(opendays_ids[i], R.id.page_container);
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
