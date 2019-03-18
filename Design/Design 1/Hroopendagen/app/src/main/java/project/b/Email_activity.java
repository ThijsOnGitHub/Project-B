package project.b;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Email_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_activity);
    }

    public void onclick(View v){
        switch (v.getId()){
            case R.id.backBtn:
                finish();
                break;
        }
    }
}
