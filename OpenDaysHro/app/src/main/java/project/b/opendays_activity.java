package project.b;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class opendays_activity extends AppCompatActivity {

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
                mainLLayout.setMargins(0,10,0,0);

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

                LinearLayout main = (LinearLayout)findViewById(R.id.page_container); ((LinearLayout)main).addView((LinearLayout)myLLayout);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opendays);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int numOfListItems = 30;

        for(int i = 0; i < numOfListItems; i++) {
            addToLayout("ListItem", "Informatica\n04/04/2019\nWijnhaven 107", "Options");
        }
    }
}
