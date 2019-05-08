package project.b;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.net.URI;

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

        Intent[] myIntents = new Intent[]{home,educations,about_cmi,contact};
        int[] images = new int[]{R.drawable.ic_home_white_24dp,R.drawable.baseline_school_24px,R.drawable.ic_location_city_white_24dp,R.drawable.ic_chat_grey_24dp};
        String[] text = new String[]{"home","Study programs","About CMI","Contact"};

        layout.generate_menu(R.id.menu_bar,images,text,myIntents);
    }
    public void startIntentWithAction(String intentString){
        Uri intentUri=Uri.parse(intentString);
        Intent intent=new Intent(Intent.ACTION_VIEW,intentUri);
        startActivity(intent);
    }

    public void clickFacebookPage(View v){
        System.out.println("test");

        //https://stackoverflow.com/questions/34564211/open-facebook-page-in-facebook-app-if-installed-on-android/34564284
        String FACEBOOK_URL = "https://www.facebook.com/HRCMI";
        String FACEBOOK_PAGE_ID = "164903761123527";
        String intentString;
            PackageManager packageManager = getPackageManager();
            try {
                int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
                if (versionCode >= 3002850) { //newer versions of fb app
                    intentString= "fb://facewebmodal/f?href=" + FACEBOOK_URL;
                } else { //older versions of fb app
                    intentString= "fb://page/" + FACEBOOK_PAGE_ID;

                }
                intentString= "fb://page/" + FACEBOOK_PAGE_ID;
                startIntentWithAction(intentString);
            } catch(Exception e) /*(PackageManager.NameNotFoundException e)*/ {
                intentString= FACEBOOK_URL; //normal web url
                startIntentWithAction(intentString);
            }
    }

    public void clickInstagramPage(View v) {
        //https://stackoverflow.com/questions/21505941/intent-to-open-instagram-user-profile-on-android
        String istagramName = "hogeschoolrotterdam";
        String intentString;
        startIntentWithAction("https://www.instagram.com/_u/" + istagramName);

    }

    public void clickTwitterPage(View v){
        //https://stackoverflow.com/questions/15497261/open-instagram-user-profile
        String twitterUsername="hsrotterdam";
        String intentString;
            try {
                startIntentWithAction("twitter://user?screen_name="+twitterUsername);

            } catch (Exception e) {
                startIntentWithAction("https://twitter.com/"+twitterUsername);
            }
    }

    public void clickWebsite(View v){
        startIntentWithAction("https://www.hogeschoolrotterdam.nl/");
    }

    public void clickCall(View v){
        //https://developer.android.com/training/basics/intents/sending.html#java
        Uri number = Uri.parse("tel:010794 4400");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    public void askAQuetionClick(View v){
        startActivity(new Intent(this,askAQuestion_activity.class));
    }
}
