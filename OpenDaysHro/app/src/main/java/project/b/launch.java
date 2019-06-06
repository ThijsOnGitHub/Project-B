package project.b;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

public class launch extends appHelper {

    LayoutHelper layoutHelper;
    TextView hro, version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        layoutHelper = new LayoutHelper(this);

        hro = (TextView) findViewById(R.id.hro_opendays);
        version = (TextView) findViewById(R.id.versie);

        String version_number = "0.9";

        if (layoutHelper.db.language()) {
            hro.setText("Hogeschool Rotterdam\nOpen dagen");
            version.setText("Versie " + version_number);
        } else {
            hro.setText("Hogeschool Rotterdam\nOpen days");
            version.setText("Version " + version_number);
        }

        layoutHelper.sync(this);
    }
}
