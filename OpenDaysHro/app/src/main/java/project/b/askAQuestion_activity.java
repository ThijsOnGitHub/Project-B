package project.b;

import android.content.Intent;
import android.os.Bundle;


public class askAQuestion_activity extends appHelper {
    int resumeCounter=0;
    LayoutHelper layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_aquestion);


        layout = new LayoutHelper(this);

        layout.generateAskQuestionPage(findViewById(R.id.page_container));

        Intent home = new Intent(getBaseContext(), MainActivity.class);
        Intent educations = new Intent(getBaseContext(), educations_activity.class);
        Intent about_cmi = new Intent(getBaseContext(), About_activity.class);
        Intent contact = new Intent(getBaseContext(), contact_activity.class);

        Intent[] myIntents = new Intent[]{home,educations,about_cmi,contact};
        int[] images = new int[]{R.drawable.ic_home_white_24dp,R.drawable.baseline_school_24px,R.drawable.ic_location_city_white_24dp,R.drawable.ic_chat_grey_24dp};

        String[] text = new String[]{getResources().getString(R.string.Home),getResources().getString(R.string.Study_Programs),getResources().getString(R.string.About_Institute),getResources().getString(R.string.Conctact)};


        layout.generate_menu(R.id.menu_bar,images,text,myIntents);
    }




    @Override
    protected void onResume() {
        super.onResume();
        if(resumeCounter==0){
            resumeCounter+=1;
        }else{
            finish();
        }
    }



}
