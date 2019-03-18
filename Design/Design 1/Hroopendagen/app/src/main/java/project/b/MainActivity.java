/*

This is the activity for the home screen of the app.
Connected layout: x/activity_main

 */

// to remeber: https://developer.android.com/guide/components/intents-common#java and filer on calendar


package project.b;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    // objects to use later on
    ImageView homeImage;
    TextView homeTxt;
    TextView studies;

    Button nextBtn;
    Button prevBtn;

    // variables to use late on
    int imageCounter = 0;

    int[] drawables = new int[]{R.drawable.image1,R.drawable.image2};

    String txtForHomePage = "Hello! Welcome to the app for the open days of the Rotterdam Academy!";
    String study = "Our studies:\n- Informatica\n- CMGT\n- Technisch Informatica";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // declaring where every element is located
        homeImage = (ImageView) findViewById(R.id.homePageImageView);

        homeTxt = (TextView) findViewById(R.id.textHomePage);
        studies = (TextView) findViewById(R.id.ourStudies);

        nextBtn = (Button) findViewById(R.id.nextButton);
        prevBtn = (Button) findViewById(R.id.prevButton);

        // setting up the app for use
        this.setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        homeImage.setImageResource(drawables[imageCounter]);

        homeTxt.setText(txtForHomePage);
        studies.setText(study);

        nextBtn.setText(">");
        prevBtn.setText("<");
    }

    public void picture (View v){

        switch (v.getId()){
            case R.id.prevButton:
                imageCounter--;
                if (imageCounter<0){
                    imageCounter = drawables.length-1;
                }
                homeImage.setImageResource(drawables[imageCounter]);
                break;
            case R.id.nextButton:
                imageCounter++;
                if(imageCounter > drawables.length - 1){
                    imageCounter = 0;
                }
                homeImage.setImageResource(drawables[imageCounter]);
                break;
        }
    }

    public void onclick(View view){

        Intent swapToCheckForOpenDays = new Intent(this, checkForOpenDays_activity.class);
        Intent swapToLatestNews = new Intent(this, LatestNews_activity.class);
        Intent swapToMyOpenDays = new Intent(this, MyOpenDays_activity.class);
        Intent swapToStudyInfo = new Intent(this, StudyInfo_activity.class);
        Intent swapToEmail = new Intent(this, Email_activity.class);
        Intent swapToFAQ = new Intent(this, FAQ_activity.class);

        // i took the phone number from https://www.hogeschoolrotterdam.nl/hogeschool/contact/mijn-studiekeuze/
        String phoneNo = "tel: 0107944400";

        Intent call = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNo));

        switch (view.getId()){
            case R.id.checkForOpenDays:
                startActivity(swapToCheckForOpenDays);
                break;
            case R.id.latestNews:
                startActivity(swapToLatestNews);
                break;
            case R.id.myOpenDays:
                startActivity(swapToMyOpenDays);
                break;
            case R.id.studieInfoButton:
                startActivity(swapToStudyInfo);
                break;
            case R.id.callButton:
                // https://developer.android.com/training/permissions/requesting
                // https://stackoverflow.com/questions/15842328/android-intent-action-call-uri
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 0666);
                } else {startActivity(call);}
                break;
            case R.id.FAQButton:
                startActivity(swapToFAQ);
                break;
            case R.id.emailButton:
                startActivity(swapToEmail);

        }
    }

}
