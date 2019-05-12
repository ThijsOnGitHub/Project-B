package project.b;

import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.widget.LinearLayout;

public class POPUP_activity extends appHelper {

    LayoutHelper layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        layout = new LayoutHelper(this);

        int pop_width = (int) getIntent().getIntExtra("WIDTH",0);
        int pop_height = (int) getIntent().getIntExtra("HEIGHT",0);

        layout.popup(R.id.popup_container,pop_width,pop_height);
    }
}
