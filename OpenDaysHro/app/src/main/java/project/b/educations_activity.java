package project.b;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class educations_activity extends appHelper {

    LayoutHelper layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educations);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        layout = new LayoutHelper(this, true);
        int[] images = new int[]{R.drawable.beginning_by_ryky, R.drawable.better_day_by_ryky, R.drawable.beginning_by_ryky, R.drawable.better_day_by_ryky, R.drawable.beginning_by_ryky};
        String[] text = new String[]{"test","test","test", "test", "test"};

        layout.generate_study_program_menu(R.id.page_container, images, text);

    }

}
