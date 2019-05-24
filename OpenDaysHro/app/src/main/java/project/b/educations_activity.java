package project.b;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class educations_activity extends appHelper {

    LayoutHelper layout;
    String passedStudyID, passedInstituteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educations);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        try { passedStudyID = getIntent().getStringExtra("StudyID"); } catch (Exception e){ System.out.println(e); passedStudyID = null; }
        try { passedInstituteID = getIntent().getStringExtra("InstituteID"); } catch (Exception e){ System.out.println(e); passedInstituteID = null;}


        layout = new LayoutHelper(this);

        if (passedInstituteID == null) {
            String[] institutes = layout.db.getInstitutes();
            for (int i = 0; i < institutes.length; i++) {
                String[] institute_info = layout.db.getInstituteInfo(institutes[i]);
                if (institute_info[1].equals("CMI")) {
                    passedInstituteID = institute_info[3];
                }
            }
        }

        if (passedStudyID == null) {
            String[] id_all = layout.db.getStudiesByInstitute(passedInstituteID);
            layout.generate_study_program_menu(R.id.page_container, id_all);
        }
        else {
            layout.generate_page_study_programs(R.drawable.blaak,passedStudyID,R.id.page_container);
        }

        Intent home = new Intent(getBaseContext(), MainActivity.class);
        Intent educations = new Intent(getBaseContext(), educations_activity.class);
        Intent about_cmi = new Intent(getBaseContext(), About_activity.class);
        Intent contact = new Intent(getBaseContext(), contact_activity.class);

        Intent[] myIntents = new Intent[]{home,educations,about_cmi,contact};
        int[] images = new int[]{R.drawable.ic_home_white_24dp,R.drawable.baseline_school_grey_24px,R.drawable.ic_location_city_white_24dp,R.drawable.ic_chat_white_24dp};
        String[] text = new String[]{"Home","Study Programs","About CMI","Contact"};

        layout.generate_menu(R.id.menu_bar,images,text,myIntents);

    }


}
