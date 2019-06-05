package project.b;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

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

        layout.Image_with_Buttons(R.id.page_container,drawables);

        Boolean firstdatefirst;

        try { firstdatefirst = getIntent().getBooleanExtra("opendaylist", true); } catch (Exception e){ System.out.println(e); firstdatefirst = true;}

        LinearLayout main = (LinearLayout) findViewById(R.id.page_container);
        LinearLayout buttonForSorting = new LinearLayout(this);
            LinearLayout.LayoutParams buttonForSorting_params = new LinearLayout.LayoutParams(250, 250);
            buttonForSorting.setLayoutParams(buttonForSorting_params);
        buttonForSorting.setBackgroundColor(getResources().getColor(R.color.light_grey));
            if (firstdatefirst == true) {
                buttonForSorting.setForeground(getResources().getDrawable(R.drawable.sort19));
                buttonForSorting.getForeground().setTint(getResources().getColor(R.color.hro_red));
            }else {
                    buttonForSorting.setForeground(getResources().getDrawable(R.drawable.sort91));
                    buttonForSorting.getForeground().setTint(getResources().getColor(R.color.hro_red));
                }

        Boolean finalFirstdatefirst = firstdatefirst;
        buttonForSorting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gotoMainActivity = new Intent(getBaseContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);;
                        if (finalFirstdatefirst == true) {
                            gotoMainActivity.putExtra("opendaylist", false); }
                        else {
                            gotoMainActivity.putExtra("opendaylist", true);
                        }
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
