package project.b;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends appHelper {

    DisplayMetrics metrics = new DisplayMetrics();
    int phone_width, phone_height;

    LayoutHelper layout;

    int numOfListItems;

    int[] drawables = new int[]{R.drawable.gebouw_cmi,R.drawable.lerende_student,
                                    R.drawable.werkende_studenten,R.drawable.wijnhaven};


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
        LinearLayout buttonForSorting = new LinearLayout(this);
            LinearLayout.LayoutParams buttonForSorting_params = new LinearLayout.LayoutParams(calcWithFromDesign(600),calcHeightFromDesign(125));
            buttonForSorting_params.setMargins(calcWithFromDesign(450),calcHeightFromDesign(20),0,calcWithFromDesign(20));
            buttonForSorting.setLayoutParams(buttonForSorting_params);
        buttonForSorting.setGravity(Gravity.CENTER_VERTICAL);
            buttonForSorting.setOrientation(LinearLayout.HORIZONTAL);

            TextView dateText = new TextView(this);
                LinearLayout.LayoutParams dateText_params= new LinearLayout.LayoutParams(calcWithFromDesign(400), ViewGroup.LayoutParams.WRAP_CONTENT);
                dateText_params.setMargins(calcWithFromDesign(30),0,0,0);
                dateText.setLayoutParams(dateText_params);
                dateText.setText(captFirstLetter(getResources().getString(R.string.sort_on_Date)));
                dateText.setTextSize(makeTextFit(calcWithFromDesign(400),getResources().getString(R.string.sort_on_Date)));
                dateText.setTextColor(getResources().getColor(android.R.color.black));
                buttonForSorting.addView(dateText);

            ImageView sortOrderIcon = new ImageView(this);
                LinearLayout.LayoutParams sortOrderIcon_layout= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
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
