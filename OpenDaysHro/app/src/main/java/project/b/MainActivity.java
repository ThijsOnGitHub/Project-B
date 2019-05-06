package project.b;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    int[] drawables = new int[]{R.drawable.beginning_by_ryky,R.drawable.best_friends_by_ryky,R.drawable.bffs_by_synderen,R.drawable.beginning_of_love_by_ryky,R.drawable.better_day_by_ryky,R.drawable.beyond_by_auroralion};

    int imageCounter = 0;
    ImageView mainImage;


    // https://stackoverflow.com/questions/3204852/android-add-a-textview-to-linear-layout-programmatically

    public void addToLayout(String object, String... args){
        switch (object){
            case "ListItem":
                LinearLayout myLLayout = new LinearLayout(this);
                RelativeLayout myRLayoutLarge = new RelativeLayout(this);
                RelativeLayout myRLayoutSmall = new RelativeLayout(this);
                TextView myTextview = new TextView(this);
                Button myButton = new Button(this);

                myTextview.setText(args[0]); myTextview.setGravity(Gravity.CENTER);
                myButton.setText(args[1]);
                myLLayout.setBackgroundColor(getResources().getColor(R.color.white));
                myButton.setBackgroundColor(getResources().getColor(R.color.hro_red)); myButton.setTextColor(getResources().getColor(R.color.text));
                LinearLayout.LayoutParams mainLLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
                mainLLayout.setMargins(0,50,0,0);

                myLLayout.setOrientation(LinearLayout.HORIZONTAL);

                myLLayout.setLayoutParams(mainLLayout);
                myRLayoutLarge.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
                myRLayoutSmall.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,2));
                myTextview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
                myButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

                ((RelativeLayout)myRLayoutLarge).addView((TextView)myTextview);
                ((RelativeLayout)myRLayoutSmall).addView((Button)myButton);
                ((LinearLayout)myLLayout).addView((RelativeLayout)myRLayoutLarge);
                ((LinearLayout)myLLayout).addView((RelativeLayout)myRLayoutSmall);

                LinearLayout main = (LinearLayout)findViewById(R.id.hallo_wereld); ((LinearLayout)main).addView((LinearLayout)myLLayout);
        }
    }



    DatabaseHelper myDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        myDatabase = new DatabaseHelper(this);
        if (myDatabase.emptyDatabase() == true) {
            myDatabase.fillDatabase();
        }

        ArrayList<String> institutefullname_all = myDatabase.getAllInstituteFullName();
        ArrayList<String> locationsByInstitute_all = myDatabase.getAllLocationsByInstitute(institutefullname_all.get(0));
        ArrayList<String> floorplansByLocation_all = myDatabase.getAllFloorplansByLocation(locationsByInstitute_all.get(0));
        ArrayList<String> floor2ofwijnhaven107 = myDatabase.getAllFloorplanByFloornumber(locationsByInstitute_all.get(0), "2");
        ArrayList<String> studyofCMI_all_dutch = myDatabase.getNamesOfStudiesByInstitute(institutefullname_all.get(0), true);
        ArrayList<String> studyofCMI_all_english = myDatabase.getNamesOfStudiesByInstitute(institutefullname_all.get(0), false);
        ArrayList<String> locationinfo = myDatabase.getLocationInformation(locationsByInstitute_all.get(0));
//        ArrayList<String> studyinfo_dutch_dutch = myDatabase.getStudyInformation(studyofCMI_all_dutch.get(0), true);
//        ArrayList<String> studyinfo_english_dutch = myDatabase.getStudyInformation(studyofCMI_all_english.get(0), true);
//        ArrayList<String> studyinfo_english_english = myDatabase.getStudyInformation(studyofCMI_all_english.get(0), false);
//        ArrayList<String> studyinfo_dutch_english = myDatabase.getStudyInformation(studyofCMI_all_dutch.get(0), false);

        Log.d("ALL INSTITUTE FULLNAME", "onCreate: " + institutefullname_all);
        Log.d("ALL LOCATIONS BY CMI", "onCreate: " + locationsByInstitute_all);
        Log.d("ALL FLOORPLANS FROM H", "onCreate: " + floorplansByLocation_all);
        Log.d("FLOOR 2 OF WIJNHVEN 107", "onCreate: " + floor2ofwijnhaven107);
        Log.d("WIJNHAVEN 107 INFO", "onCreate: " + locationinfo);
//        Log.d("STUDY INFO DUTCH-DUTCH", "onCreate: " + studyinfo_dutch_dutch);
//        Log.d("STUDY INFO DUTCH-ENGLI", "onCreate: " + studyinfo_dutch_english);
//        Log.d("STUDY INFO ENGLI-DUTCH", "onCreate: " + studyinfo_english_dutch);
//        Log.d("STUDY INFO ENGLI-ENGLI", "onCreate: " + studyinfo_english_english);


        mainImage = (ImageView)findViewById(R.id.mainImage);


        int numOfListItems = 5;

        for(int i = 0; i < numOfListItems; i++) {
            addToLayout("ListItem", "Informatica\n04/04/2019\nWijnhaven 107", "Options");
        }
        
    }



    public void onClick(View v){
        switch (v.getId()){

            case R.id.leftButton:
                imageCounter = imageCounter-1;
                if (imageCounter < 0){
                    imageCounter = drawables.length - 1;
                }
                mainImage.setImageResource(drawables[imageCounter]);
                break;

            case R.id.rightButton:
                imageCounter = imageCounter + 1;
                if(imageCounter > drawables.length - 1){
                    imageCounter = 0;
                }
                mainImage.setImageResource(drawables[imageCounter]);
                break;
        }
    }

}
