package project.b;



import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.content.pm.ActivityInfo;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;

import java.util.ArrayList;

public class MainActivity extends appHelper {

    DatabaseHelper myDatabase;
    LayoutHelper layout;

    int numOfListItems;
    int imageCounter = 0;

    int[] drawables = new int[]{R.drawable.beginning_by_ryky,R.drawable.best_friends_by_ryky,R.drawable.bffs_by_synderen,R.drawable.beginning_of_love_by_ryky,R.drawable.better_day_by_ryky,R.drawable.beyond_by_auroralion};

    ImageView mainImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        myDatabase = new DatabaseHelper(this);
//        myDatabase.createOpenday("09-04-2020", "09:00:00", "16:00:00", "Communicatie, Media en ICT");

        mainImage = (ImageView)findViewById(R.id.mainImage);

        layout = new LayoutHelper(this);

        String[] desc = {"Informatica","Technische Informatica", "CMGT", "Informatica"};
        String[] loc = {"Wijnhaven 107", "Wijnhaven 107", "Wijnhaven 108", "Wijnhaven 107"};
        String[] time = {"8:00\nStart over: 20 min", "9:00\nStart over: 1 day", "8:00\nStart over: 59 min", "18:50\nStart over: 160 hours"};
        numOfListItems = desc.length;

        for (int i = 0; i < numOfListItems; i++) {
            layout.ListItem_openday(desc[i], loc[i], time[i], R.id.list);
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
