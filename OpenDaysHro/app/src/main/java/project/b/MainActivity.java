package project.b;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends appHelper {

    DisplayMetrics metrics = new DisplayMetrics();
    int phone_width, phone_height;

    LayoutHelper layout;

    int numOfListItems;

    int[] drawables = new int[]{R.drawable.lerende_student, R.drawable.werkende_studenten,R.drawable.header_cmi};


    private int calcHeightFromDesign(float elementHeight){
        float designHeight= 1920.f;
        return (int)((elementHeight*phone_height)/designHeight);
    }

    private int calcWithFromDesign(float elementWidth){
        float designWidth= 1080.f;
        return (int)((elementWidth*phone_width)/designWidth);
    }

    private String captFirstLetter(String string){
        return string.substring(0,1).toUpperCase()+string.substring(1);
    }

    private float makeTextFit(int availableWidth,String tekst) {

        //https://stackoverflow.com/questions/7259016/scale-text-in-a-view-to-fit/7259136#7259136
        // and edited by myself to fit
        TextView textView=new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(availableWidth, LinearLayout.LayoutParams.WRAP_CONTENT));

        CharSequence text = tekst;
        float textSize = 0;
        textView.setTextSize(textSize);


        while (text == (TextUtils.ellipsize(text, textView.getPaint(), availableWidth, TextUtils.TruncateAt.END))) {
            textSize += 1;
            textView.setTextSize(textSize);
        }
        textSize -= 5;
        textView.setTextSize(textSize);
        return  textSize;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        layout = new LayoutHelper(this);
      
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        phone_width = metrics.widthPixels;
        phone_height = metrics.heightPixels;
        layout.Image_with_Buttons(R.id.page_container,drawables);

        Boolean firstdatefirst;

        try { firstdatefirst = getIntent().getBooleanExtra("opendaylist", true); } catch (Exception e){ System.out.println(e); firstdatefirst = true;}

        LinearLayout main = (LinearLayout) findViewById(R.id.page_container);
        FrameLayout buttonForSorting = new FrameLayout(this);
        FrameLayout.LayoutParams buttonForSorting_params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            buttonForSorting_params.setMargins(0,0,0,0);
            buttonForSorting.setLayoutParams(buttonForSorting_params);

            TextView dateText = new TextView(this);
                FrameLayout.LayoutParams dateText_params= new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dateText_params.setMargins(0,0,0,0);
                dateText_params.gravity=Gravity.CENTER;
                dateText.setLayoutParams(dateText_params);
                dateText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                dateText.setText(captFirstLetter(getResources().getString(R.string.open_days)));
                dateText.setTextSize(makeTextFit(calcWithFromDesign(500),getResources().getString(R.string.sort_on_Date)));
                dateText.setTextColor(getResources().getColor(android.R.color.black));
                buttonForSorting.addView(dateText);


            ImageView sortOrderIcon = new ImageView(this);
                FrameLayout.LayoutParams sortOrderIcon_layout= new FrameLayout.LayoutParams(calcWithFromDesign(150), calcWithFromDesign(150));
                sortOrderIcon_layout.gravity=Gravity.RIGHT;
                sortOrderIcon.setLayoutParams(sortOrderIcon_layout);
                sortOrderIcon.setBackgroundColor(getResources().getColor(R.color.dark_grey));
        sortOrderIcon.setColorFilter(getResources().getColor(R.color.hro_red));

            buttonForSorting.addView(sortOrderIcon);

        buttonForSorting.setBackgroundColor(getResources().getColor(R.color.light_grey));
            if (firstdatefirst == true) {
                sortOrderIcon.setImageResource(R.drawable.sort19);
            }else {
                sortOrderIcon.setImageResource(R.drawable.sort91);
                }

        buttonForSorting.isClickable();
        Boolean finalFirstdatefirst = firstdatefirst;
        buttonForSorting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gotoMainActivity = new Intent(getBaseContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);;
                        if (finalFirstdatefirst == true) {
                            gotoMainActivity.putExtra("opendaylist", false); }
                        else {
                            gotoMainActivity.putExtra("opendaylist", true);
                        }
                        startActivity(gotoMainActivity);

                }
            });

        main.addView(buttonForSorting);

        String[] opendays_ids = layout.db.getUpcomingOpendays(firstdatefirst);
        for (int i = 0; i < opendays_ids.length; i++) {
            layout.ListItem_openday(opendays_ids[i], R.id.page_container);
        }

        Intent home = new Intent(getBaseContext(), MainActivity.class);
        Intent educations = new Intent(getBaseContext(), educations_activity.class);
        Intent about_cmi = new Intent(getBaseContext(), About_activity.class);
        Intent contact = new Intent(getBaseContext(), contact_activity.class);

        Intent[] myIntents = new Intent[]{home,educations,about_cmi,contact};
        int[] images = new int[]{R.drawable.ic_home_grey_24dp,R.drawable.baseline_school_24px,R.drawable.ic_location_city_white_24dp,R.drawable.ic_chat_white_24dp};

        String[] text = new String[]{getResources().getString(R.string.Home),getResources().getString(R.string.Study_Programs),getResources().getString(R.string.About_Institute),getResources().getString(R.string.Conctact)};

        layout.generate_menu(R.id.menu_bar,images,text,myIntents);

    }
}
