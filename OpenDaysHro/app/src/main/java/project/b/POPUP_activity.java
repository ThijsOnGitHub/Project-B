package project.b;

import android.os.Bundle;

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
