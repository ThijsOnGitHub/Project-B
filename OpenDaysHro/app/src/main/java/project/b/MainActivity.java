package project.b;

import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.content.Intent;

public class MainActivity extends appHelper {

    DatabaseHelper myDatabase;
    LayoutHelper layout;

    int numOfListItems;

    int[] drawables = new int[]{R.drawable.blaak, R.drawable.centraal_station,R.drawable.gebouw_cmi,R.drawable.gebouw_museumpark_hoogbouw,
                                    R.drawable.haven_rotterdam_containers, R.drawable.haven_rotterdam_schip, R.drawable.lerende_student,
                                    R.drawable.overview_rotterdam_1, R.drawable.overview_rotterdam_2, R.drawable.overview_rotterdam_3,
                                    R.drawable.overview_rotterdam_erasmusbrug, R.drawable.werkende_studenten};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        layout = new LayoutHelper(this);

        myDatabase = new DatabaseHelper(this);
        if (myDatabase.emptyDatabase() == true) myDatabase.fillDatabase();

        layout.Image_with_Buttons(R.id.page_container,drawables);

        String[] desc = {"Thursday\n4 April 2019","Thursday\n11 April 2019", "Thursday\n18 April 2019", "Thursday\n25 April 2019"};
        String[] loc = {"Wijnhaven 107", "Wijnhaven 107", "Wijnhaven 108", "Wijnhaven 107"};
        String[] time = {"8:00-10:00", "9:00-18:00", "8:00-16:00", "7:00-15:00"};
        numOfListItems = desc.length;

        for (int i = 0; i < numOfListItems; i++) {
            layout.ListItem_openday(desc[i], loc[i], time[i], R.id.page_container);
        }

        Intent home = new Intent(getBaseContext(), MainActivity.class);
        Intent educations = new Intent(getBaseContext(), educations_activity.class);
        Intent about_cmi = new Intent(getBaseContext(), About_activity.class);
        Intent contact = new Intent(getBaseContext(), contact_activity.class);

        Intent[] myIntents = new Intent[]{home,educations,about_cmi,contact};
        int[] images = new int[]{R.drawable.ic_home_grey_24dp,R.drawable.baseline_school_24px,R.drawable.ic_location_city_white_24dp,R.drawable.ic_chat_white_24dp};
        String[] text = new String[]{"home","Study programs","About CMI","Contact"};

        layout.generate_menu(R.id.menu_bar,images,text,myIntents);

    }

}
