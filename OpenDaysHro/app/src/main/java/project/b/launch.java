package project.b;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class launch extends appHelper {

    LayoutHelper layoutHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        layoutHelper = new LayoutHelper(this);
        layoutHelper.sync(this);
    }
}
