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

public class opendays_activity extends AppCompatActivity {

    String[] passedInfo;
    String Description;
    String Location;
    String Time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opendays);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        passedInfo = getIntent().getStringArrayExtra("INFO");
        Description = passedInfo[0];
        Location = passedInfo[1];
        Time = passedInfo[2];

        TextView info1 = new TextView(this);
        info1.setText(Description);
        TextView info2 = new TextView(this);
        info2.setText(Location);
        TextView info3 = new TextView(this);
        info3.setText(Time);
        LinearLayout page = (LinearLayout) findViewById(R.id.page_container);
        page.setOrientation(LinearLayout.VERTICAL);

        page.addView(info1);
        page.addView(info2);
        page.addView(info3);
    }


}
