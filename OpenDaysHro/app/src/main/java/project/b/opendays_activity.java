package project.b;

import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.content.Intent;

import org.w3c.dom.Text;

public class opendays_activity extends appHelper {

    LayoutHelper layout;

    String[] passedInfo;

    String Description;
    String Location;
    String Time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opendays);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        layout = new LayoutHelper(this, false);

        passedInfo = getIntent().getStringArrayExtra("INFO");
            Description = passedInfo[0];
            Location = passedInfo[1];
            Time = passedInfo[2];

        layout.ListItem_openday("General Information\n" + Description,"H2.002", Time,R.id.page_container);
        layout.ListItem_openday("General Information\n" + Description,"H2.002", Time,R.id.page_container);
        layout.ListItem_openday("General Information\n" + Description,"H2.002", Time,R.id.page_container);


    }


}
