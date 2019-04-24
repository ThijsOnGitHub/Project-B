package project.b;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class educations_activity extends AppCompatActivity {


    TextView text1;
    TextView text2;

    String content = "Informatica (Computer Science) Fulltime study:\n" +
            "\n" +
            "(This course is also available as a part-time course)\n" +
            "\n" +
            "We will train you into becoming a software engineer. You’ll be able to work on software with a variety of purposes. You’ll become capable of analyzing, designing and implementing complex IT-systems.\n" +
            "\n" +
            "Software can be found al around us. You are the specialist in making big and complex software information which work quickly, efficiently and safely. For example, you could think of developing a functional app, but you could also think of analyzing large quantities of data form social media. When developing these systems, you will come to know various programming languages. For instance, you will be programming using python and Java/C# during your first year. Starting from your second year, you will be able to choose which language you wish to use. Besides the programming and developing of various applications you will also learn to cooperate with various people on a project.\n" +
            "\n" +
            "After completing the course, you will be widely employable within the discipline. From functions such as data engineer, software developer and software engineer you’ll also be able to grow even further.\n" +
            "\n" +
            "Are you interested in new technological developments? Are you curious? A little quirky and someone who doesn’t give up easily? Then this course is for you!";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educations);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        text1 = (TextView) findViewById(R.id.text1); text1.setText("Informatica"); text1.setTextSize(22);
        text2 = (TextView) findViewById(R.id.text2); text2.setText(content); text2.setTextSize(18);


    }



}
