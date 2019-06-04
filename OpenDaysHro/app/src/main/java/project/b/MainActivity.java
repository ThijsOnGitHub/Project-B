package project.b;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

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
            if (layout.db.emptyDatabase()) {
                Log.d("Syncing", "onCreate: " + "Database is empty");
                if (layout.db.isOnline(this) == true) {
                    Log.d("Syncing", "onCreate: " + "Phone is online");
                    if (layout.db.versionDatabase() == true) {
                        Log.d("Syncing", "onCreate: " + "Database is not the latest version");
                        jsonApi json = new jsonApi();
                        json.execute(layout.db.latestAppInfo()[1]);
                        while (!json.finish) {
                            // wait
                        }
                        JSONObject jsonObject = new JSONObject(json.data);
                        layout.db.fillDatabaseWithJson(jsonObject);
                    } else {
                        Log.d("Syncing", "onCreate: " + "Database is up-to-date");
                    }
                } else {
                    Log.d("Syncing", "onCreate: " + "Phone is offline");
                    layout.db.fillDatabase_offline();
                }
            } else {
                Log.d("Syncing", "onCreate: " + "Database is not empty");
                if (layout.db.isOnline(this) == true) {
                    Log.d("Syncing", "onCreate: " + "Phone is online");
                    if (layout.db.versionDatabase() == true) {
                        Log.d("Syncing", "onCreate: " + "Database is not the latest version");
                        jsonApi json = new jsonApi();
                        json.execute(layout.db.latestAppInfo()[1]);
                        while(!json.finish) {
                            // wait
                        }
                        JSONObject jsonObject = new JSONObject(json.data);
                        layout.db.fillDatabaseWithJson(jsonObject);
                    } else {
                        Log.d("Syncing", "onCreate: " + "Database is up-to-date");
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        layout.Image_with_Buttons(R.id.page_container,drawables);

        Boolean firstdatefirst;

        try { firstdatefirst = getIntent().getBooleanExtra("opendaylist", true); } catch (Exception e){ System.out.println(e); firstdatefirst = true;}

        LinearLayout main = (LinearLayout) findViewById(R.id.page_container);
        LinearLayout buttonForSorting = new LinearLayout(this);
            LinearLayout.LayoutParams buttonForSorting_params = new LinearLayout.LayoutParams(250, 250);
            buttonForSorting.setLayoutParams(buttonForSorting_params);
            buttonForSorting.setBackgroundColor(getResources().getColor(R.color.hro_red));
        Boolean finalFirstdatefirst = firstdatefirst;
        buttonForSorting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gotoMainActivity = new Intent(getBaseContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);;
                        if (finalFirstdatefirst == true) { gotoMainActivity.putExtra("opendaylist", false); }
                        else {gotoMainActivity.putExtra("opendaylist", true); }
                        startActivity(gotoMainActivity);
                }
            });
        main.addView(buttonForSorting);

        String[] opendays_ids = layout.db.getUpcomingOpendays(firstdatefirst);
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
        if(layout.db.language() == true) {
            text = new String[]{"Home", "Studies", "Over CMI", "Contact"};
        }

        layout.generate_menu(R.id.menu_bar,images,text,myIntents);

    }
}
