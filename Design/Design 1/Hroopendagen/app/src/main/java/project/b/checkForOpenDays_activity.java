/*

This is the activity for the "check for open days" button of the home page. if pressed you will go to this page
connected layout: x/checkforopendays_activity_layout

 */


package project.b;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class checkForOpenDays_activity extends AppCompatActivity {

    // Actions
    //Intent swapToHomePage = new Intent(this, MainActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkforopendays_activity_layout);
    }

    public void swappingPages(View view){
        switch (view.getId()){
            case R.id.backBtn:
                finish();
                break;
            /*
            case R.id.latestNews:
                break;
            case R.id.myOpenDays:
                break;
            */
        }
    }
}
