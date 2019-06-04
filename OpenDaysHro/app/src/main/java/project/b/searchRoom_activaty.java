package project.b;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class searchRoom_activaty extends appHelper {

    LayoutHelper layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        layout = new LayoutHelper(this);


        layout.generateSearchPopup(R.id.popup_container);
    }
}
