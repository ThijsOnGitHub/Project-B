package project.b;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class educations_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educations);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void swapPages(View v){
        Intent swapToHome = new Intent(this,MainActivity.class); swapToHome.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Intent swapToEducation = new Intent(this,educations_activity.class); swapToEducation.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Intent swapToMap = new Intent(this,map_activity.class); swapToMap.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Intent swapToOpenDays = new Intent(this,opendays_activity.class); swapToOpenDays.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Intent swapToContact = new Intent(this,contact_activity.class); swapToContact.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        switch (v.getId()){
            case R.id.menuItem_homepage:
                startActivity(swapToHome);
                break;
            case R.id.menuItem_education:
                startActivity(swapToEducation);
                break;
            case R.id.menuItem_map:
                startActivity(swapToMap);
                break;
            case R.id.menuItem_openDays:
                startActivity(swapToOpenDays);
                break;
            case R.id.menuItem_contact:
                startActivity(swapToContact);
                break;
        }
    }

}
