package project.b;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StudyInfo_activity extends AppCompatActivity {
    String LoremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc orci neque, aliquam nec nisi ac, pretium pulvinar lacus. Nulla erat eros, ornare at elit a, pharetra imperdiet eros. Proin id finibus mauris. Ut porttitor ante tellus, et dignissim augue accumsan porttitor. Nunc dignissim congue nisi ut pretium. Aliquam in dignissim orci, at laoreet nisi. Sed magna lacus, laoreet ut neque sed, dignissim tempus neque. Praesent sollicitudin sapien non ipsum hendrerit, vel sagittis turpis molestie. Pellentesque commodo lacinia ipsum in aliquet. Quisque ullamcorper tristique mauris, at elementum risus elementum sit amet. Sed tincidunt faucibus auctor. Sed finibus, odio eget tristique sagittis, dui massa interdum ex, a luctus ligula ante et odio. Mauris orci leo, sodales vitae molestie id, commodo et arcu. Nunc et ligula tortor. Nunc eleifend tellus eget justo maximus, et ornare lacus molestie.\n" +
            "\n" +
            "Mauris quis diam nisl. Proin consequat, mi sed egestas viverra, odio nibh porttitor lorem, a pulvinar est nunc quis dui. Proin tellus ligula, eleifend vel elit vel, molestie tristique risus. Sed vel rhoncus nisi. Vestibulum condimentum venenatis lectus, nec fermentum turpis cursus at. Proin non velit suscipit, sodales erat vel, pulvinar est. Nulla facilisi. Nam quis mi ligula. Nam tincidunt blandit malesuada. In rhoncus euismod facilisis. Phasellus lacinia ipsum nec quam tristique, eget cursus justo vulputate.";
    String Title1 = "Informatica";
    String Title2 = "Technisch Informatica";
    String Title3 = "CMGT";

    TextView textViewTitle1;
    TextView textViewTitle2;
    TextView textViewTitle3;

    TextView textViewText1;
    TextView textViewText2;
    TextView textViewText3;

    Button backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_info);
        this.setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        textViewTitle1 = (TextView)findViewById(R.id.studyTitle1);
        textViewTitle2 = (TextView)findViewById(R.id.studyTitle2);
        textViewTitle3 = (TextView)findViewById(R.id.studyTitle3);

        textViewText1 = (TextView)findViewById(R.id.studyText1);
        textViewText2 = (TextView)findViewById(R.id.studyText2);
        textViewText3 = (TextView)findViewById(R.id.studyText3);


            textViewTitle1.setText(Title1);
            textViewTitle2.setText(Title2);
            textViewTitle3.setText(Title3);

            textViewText1.setText(LoremIpsum);
            textViewText2.setText(LoremIpsum);
            textViewText3.setText(LoremIpsum);

            backbtn = (Button)findViewById(R.id.backBtn);
            backbtn.setText("<-- Back");
    }

    public void onclick(View v){
        switch (v.getId()){
            case R.id.backBtn:
                finish();
                break;
        }
    }


}
