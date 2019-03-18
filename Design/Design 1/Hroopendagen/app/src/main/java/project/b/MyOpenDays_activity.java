package project.b;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MyOpenDays_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_open_days_activity_layout);
    }

    public void swappingPages(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                finish();
                break;
        }
    }
}