package project.b;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class contact_activity extends appHelper {
    LayoutHelper layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        layout = new LayoutHelper(this);

        Intent home = new Intent(getBaseContext(), MainActivity.class);
        Intent educations = new Intent(getBaseContext(), educations_activity.class);
        Intent about_cmi = new Intent(getBaseContext(), About_activity.class);
        Intent contact = new Intent(getBaseContext(), contact_activity.class);

        Intent[] myIntents = new Intent[]{home, educations, about_cmi, contact};
        int[] images = new int[]{R.drawable.ic_home_white_24dp, R.drawable.baseline_school_24px, R.drawable.ic_location_city_white_24dp, R.drawable.ic_chat_grey_24dp};
        String[] text = new String[]{"Home", "Study Programs", "About CMI", "Contact"};

        int[] contact_images = new int[]{ R.drawable.button_website, R.drawable.ask_question, R.drawable.button_call_us };
        int[] social_images = new int[]{ R.drawable.facebook_logo, R.drawable.instagram, R.drawable.twitter };

        layout.generate_menu(R.id.menu_bar, images, text, myIntents);
        layout.contact_page(R.drawable.blaak, contact_images, social_images);
    }
}
