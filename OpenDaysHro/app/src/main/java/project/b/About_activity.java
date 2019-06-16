package project.b;



import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.content.Intent;

public class About_activity extends appHelper {
    LayoutHelper layout;
    String passedInstituteID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        layout = new LayoutHelper(this);

        try {passedInstituteID = getIntent().getStringExtra("InstituteID"); } catch (Exception e) { System.out.println(e); passedInstituteID = null;}
 /*
        if (passedInstituteID == null) {
            String[] institutes = layout.db.getInstitutes();
            for (int i = 0; i < institutes.length; i++) {
                String[] institute_info = layout.db.getInstituteInfo(institutes[i]);
                if (institute_info[1].equals("CMI")) {
                    passedInstituteID = institute_info[3];
                }
            }
        }
*/
        if (passedInstituteID==null){
            layout.generate_study_program_menu(R.id.page_container,null,null,null,layout.ABOUT);
        }else{
            layout.generate_page_about_page(R.drawable.header_cmi,passedInstituteID,R.id.page_container);
        }


        Intent home = new Intent(getBaseContext(), MainActivity.class);
        Intent educations = new Intent(getBaseContext(), educations_activity.class);
        Intent about_cmi = new Intent(getBaseContext(), About_activity.class);
        Intent contact = new Intent(getBaseContext(), contact_activity.class);

        Intent[] myIntents = new Intent[]{home,educations,about_cmi,contact};
        int[] images = new int[]{R.drawable.ic_home_white_24dp,R.drawable.baseline_school_24px,R.drawable.ic_location_city_grey_24dp,R.drawable.ic_chat_white_24dp};

        String[] text = new String[]{getResources().getString(R.string.Home),getResources().getString(R.string.Study_Programs),getResources().getString(R.string.About_Institute),getResources().getString(R.string.Conctact)};

        layout.generate_menu(R.id.menu_bar,images,text,myIntents);

    }

}
