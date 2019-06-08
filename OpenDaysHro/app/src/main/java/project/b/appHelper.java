package project.b;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class appHelper extends AppCompatActivity {

    // https://stackoverflow.com/questions/3204852/android-add-a-textview-to-linear-layout-programmatically

    @FunctionalInterface
    private interface calc {
        int xyz(int x, int y, int z);
    }

    public class LayoutHelper {
        Context context; DisplayMetrics metrics; int imageCounter; int phone_width; int phone_height; int default_margin;
        DatabaseHelper db;

        int menuColor = R.color.dark_grey;

        public LayoutHelper(Context context){
            this.context = context;
            this.imageCounter = 0;
            this.metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            this.phone_width = metrics.widthPixels;
            this.phone_height = metrics.heightPixels;
            this.default_margin = (int) ( (float) 2.4 * (float)( (float) phone_width / (float) 100) );
            this.db = new DatabaseHelper(context);
        }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        public void ListItem_openday(final String openday_id, int addToThisLayout) {

            String[] openday = this.db.getOpendayInfo(openday_id);
            String institute_shortname = this.db.getInstituteInfo(this.db.getInstitute_id(openday[0])[0])[1];
            String starttime = openday[2];
            String endtime = openday[3];
            starttime = starttime.substring(0, starttime.length() - 3);
            endtime = endtime.substring(0, endtime.length() -3);

            String ListItem_Time = starttime + "-" + endtime;
            String ListItem_Description = institute_shortname + "\n" + openday[1];

            int button_height = (int) ( (float) ( (float) 200 / (float) 2200) * (float) phone_height );
            int info_layout_width = phone_width / 6;
            int info_button_size; if (button_height < info_layout_width) { info_button_size = button_height; } else { info_button_size = info_layout_width; }

            LinearLayout LinearLayout_main = new LinearLayout(this.context);
                LinearLayout_main.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout_main.isClickable();
                LinearLayout_main.setBackgroundColor(getResources().getColor(menuColor));
                LinearLayout.LayoutParams LinearLayout_main_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, button_height);
                    LinearLayout_main_layoutParams.setMargins(0,0,0,0);
                    LinearLayout_main.setLayoutParams(LinearLayout_main_layoutParams);

            RelativeLayout listItem_description_layout = new RelativeLayout(this.context);
                listItem_description_layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 5));

            RelativeLayout listItem_loc = new RelativeLayout(this.context);
                listItem_loc.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,5));

            RelativeLayout info_layout = new RelativeLayout(this.context);
                info_layout.setGravity(Gravity.CENTER);
                info_layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,2));

            TextView listItem_description = new TextView(this.context);
                listItem_description.setText(ListItem_Description); listItem_description.setGravity(Gravity.CENTER);
                listItem_description.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

            TextView listItem_Time = new TextView(this.context);
                listItem_Time.setText(ListItem_Time); listItem_Time.setGravity(Gravity.CENTER);
                listItem_Time.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

            LinearLayout info_button = new LinearLayout(this.context);
            info_button.setLayoutParams(new LinearLayout.LayoutParams( info_button_size, info_button_size ));
                info_button.setBackground(getDrawable(R.drawable.twotone_info_24px));


            ((RelativeLayout) listItem_description_layout ).addView((TextView) listItem_description);
                ((LinearLayout)LinearLayout_main).addView((RelativeLayout)listItem_description_layout);
            ((RelativeLayout) listItem_loc).addView(listItem_Time);
                ((LinearLayout)LinearLayout_main).addView((RelativeLayout)listItem_loc);
            ((RelativeLayout) info_layout).addView(info_button);
                ((LinearLayout)LinearLayout_main).addView((RelativeLayout)info_layout);


            LinearLayout_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gotoOpenDay_activity = new Intent(context, opendays_activity.class);
                    gotoOpenDay_activity.putExtra("INFO", new String[]{openday_id, ""});
                    gotoOpenDay_activity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(gotoOpenDay_activity);
                }
            });


            LinearLayout main = (LinearLayout)findViewById( addToThisLayout );
                ((LinearLayout)main).addView((LinearLayout)LinearLayout_main);

            if (this.menuColor == R.color.light_grey) { this.menuColor = R.color.dark_grey; } else { this.menuColor = R.color.light_grey; }

        }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        public void workshop_menu(final String study_id, final String openday_id , int addToThisLayout) {

            int button_height = (int) ( (float) ( (float) 200 / (float) 2200) * (float) phone_height );
            int info_layout_width = phone_width / 6;
            int info_button_size; if (button_height < info_layout_width) { info_button_size = button_height; } else { info_button_size = info_layout_width; }

            String ListItem_Description = this.db.getStudyInfo(study_id)[2];
            String[] all_workshops = this.db.getActivitiesByStudyAndOpenday(openday_id, study_id);
            String workshops = "Workshops: " + String.valueOf(all_workshops.length);

            LinearLayout LinearLayout_main = new LinearLayout(this.context);
                LinearLayout_main.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout_main.isClickable();
                LinearLayout_main.setBackgroundColor(getResources().getColor(menuColor));
                LinearLayout.LayoutParams LinearLayout_main_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, button_height);
                    LinearLayout_main_layoutParams.setMargins(0,0,0,0);
                    LinearLayout_main.setLayoutParams(LinearLayout_main_layoutParams);

            RelativeLayout listItem_description_layout = new RelativeLayout(this.context);
                listItem_description_layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 5));

            RelativeLayout listItem_loc = new RelativeLayout(this.context);
                listItem_loc.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,5));

            RelativeLayout info_layout = new RelativeLayout(this.context);
                info_layout.setGravity(Gravity.CENTER);
                info_layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,2));

            TextView listItem_description = new TextView(this.context);
                listItem_description.setText(ListItem_Description); listItem_description.setGravity(Gravity.CENTER);
                listItem_description.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

            TextView listItem_Time = new TextView(this.context);
                listItem_Time.setText(workshops); listItem_Time.setGravity(Gravity.CENTER);
                listItem_Time.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

            LinearLayout info_button = new LinearLayout(this.context);
                info_button.setLayoutParams(new LinearLayout.LayoutParams( info_button_size, info_button_size ));
                info_button.setBackground(getDrawable(R.drawable.twotone_info_24px));


            ((RelativeLayout) listItem_description_layout ).addView((TextView) listItem_description);
            ((LinearLayout)LinearLayout_main).addView((RelativeLayout)listItem_description_layout);
            ((RelativeLayout) listItem_loc).addView(listItem_Time);
            ((LinearLayout)LinearLayout_main).addView((RelativeLayout)listItem_loc);
            ((RelativeLayout) info_layout).addView(info_button);
            ((LinearLayout)LinearLayout_main).addView((RelativeLayout)info_layout);


            LinearLayout_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gotoOpenDay_activity = new Intent(context, opendays_activity.class);
                    gotoOpenDay_activity.putExtra("INFO", new String[]{openday_id, study_id});
                    gotoOpenDay_activity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(gotoOpenDay_activity);
                }
            });


            LinearLayout main = (LinearLayout)findViewById( addToThisLayout );
            ((LinearLayout)main).addView((LinearLayout)LinearLayout_main);

            if (this.menuColor == R.color.light_grey) { this.menuColor = R.color.dark_grey; } else { this.menuColor = R.color.light_grey; }

        }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        public void workshop( String ListItem_Description, String ListItem_Location, String ListItem_Time , int addToThisLayout) {

            int button_height = (int) ( (float) ( (float) 200 / (float) 2200) * (float) phone_height );

            LinearLayout LinearLayout_main = new LinearLayout(this.context);
                LinearLayout_main.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout_main.setBackgroundColor(getResources().getColor(menuColor));
                LinearLayout.LayoutParams LinearLayout_main_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, button_height);
                    LinearLayout_main_layoutParams.setMargins(0,0,0,0);
                    LinearLayout_main.setLayoutParams(LinearLayout_main_layoutParams);

            RelativeLayout listItem_description_layout = new RelativeLayout(this.context);
                listItem_description_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));

            RelativeLayout listItem_loc = new RelativeLayout(this.context);
                listItem_loc.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,2));

            RelativeLayout listItem_time = new RelativeLayout(this.context);
                listItem_time.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,2));

            TextView listItem_description = new TextView(this.context);
                listItem_description.setText(ListItem_Description); listItem_description.setGravity(Gravity.CENTER);
                listItem_description.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

            TextView listItem_Location = new TextView(this.context);
                listItem_Location.setText(ListItem_Location); listItem_Location.setGravity(Gravity.CENTER);
                listItem_Location.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                listItem_Location.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
                listItem_Location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent openMap = new Intent(context,map_activity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        String[] locationList= ListItem_Location.split("\\.");
                        openMap.putExtra("building",locationList[0].toLowerCase());
                        openMap.putExtra("floor",Integer.parseInt(locationList[1]));
                        openMap.putExtra("rawString",ListItem_Location);
                        startActivity(openMap);
                    }
                });

            TextView listItem_Time = new TextView(this.context);
                listItem_Time.setText(ListItem_Time); listItem_Time.setGravity(Gravity.CENTER);
                listItem_Time.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));


            ((RelativeLayout) listItem_description_layout ).addView((TextView) listItem_description);
                ((LinearLayout)LinearLayout_main).addView((RelativeLayout)listItem_description_layout);
            ((RelativeLayout) listItem_loc).addView(listItem_Location);
                ((LinearLayout)LinearLayout_main).addView((RelativeLayout)listItem_loc);
            ((RelativeLayout) listItem_time).addView(listItem_Time);
                ((LinearLayout)LinearLayout_main).addView((RelativeLayout)listItem_time);


            LinearLayout main = (LinearLayout)findViewById( addToThisLayout );
                ((LinearLayout)main).addView((LinearLayout)LinearLayout_main);

            if (this.menuColor == R.color.light_grey) { this.menuColor = R.color.dark_grey; } else { this.menuColor = R.color.light_grey; }

        }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        public void generate_study_program_menu(int addToThisLayout, String[] List_with_id){

            /*
             https://stackoverflow.com/questions/8833825/error-getting-window-size-on-android-the-method-getwindowmanager-is-undefined

             Getting the screen size. In case of an oneplus 6 it is width: 1080, height: 2200.
            */

            int max_ammount_of_buttons_in_a_row = 2;


            // Get studies <--
            ArrayList<String> study_names = new ArrayList<>();
            ArrayList<String> study_ids = new ArrayList<>();
            ArrayList<String> study_icons = new ArrayList<>();

            String studyname = "";
            String studyid = "";
            String icon = "";
            for (int i = 0; i < List_with_id.length; i++) {
                studyname = this.db.getStudyInfo(List_with_id[i])[2];
                studyid = this.db.getStudyInfo(List_with_id[i])[4];
                icon = this.db.getStudyInfo(List_with_id[i])[5];
                study_ids.add(studyid);
                study_names.add(studyname);
                study_icons.add(icon);
            }


            String[] List_with_text = study_names.toArray(new String[study_names.size()]);
            List_with_id = study_ids.toArray(new String[study_ids.size()]);
            // Get studies -->

            // Get icons -->
            // https://stackoverflow.com/questions/16369814/how-to-access-the-drawable-resources-by-name-in-android
            ArrayList<Integer> icons = new ArrayList<>();
            Resources resources = this.context.getResources();

            for (int i = 0; i < study_icons.size(); i++) {
                icon = study_icons.get(i);
                icons.add(resources.getIdentifier(icon, "drawable", this.context.getPackageName()));
            }

            Integer[] List_with_images = icons.toArray(new Integer[icons.size()]);



            // Get icons <--

            calc calculator = (x, y, z) -> (int) ( ( (float) x / (float) y) * (float) z);

            int button_size = calculator.xyz(500, 1080, phone_width);
            int button_margin = calculator.xyz(phone_width, 1080, 20) - (default_margin / max_ammount_of_buttons_in_a_row);
                if (button_margin < 0) { button_margin = 0; } // prevent crashing (not needed so far)

            // picture_margin = int[]{left,top,right,bottom}
            int[] picture_margin = new int[] {calculator.xyz(90, 500, button_size), calculator.xyz(30, 500, button_size), calculator.xyz(90, 500, button_size), calculator.xyz(10, 500, button_size)};

            System.out.println(button_size + "  " + button_margin + "   { " + picture_margin[0] + ", " + picture_margin[1] + ", " + picture_margin[2] + ", " + picture_margin[3] + " }");

            int ammountOfItems;
            if (List_with_images.length < List_with_text.length) { ammountOfItems = List_with_images.length; } else { ammountOfItems = List_with_text.length; }

            LinearLayout main = (LinearLayout) findViewById(addToThisLayout);
                main.setOrientation(LinearLayout.VERTICAL);
            LinearLayout Main_layout = new LinearLayout(this.context);
                LinearLayout.LayoutParams my_main_params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    my_main_params.setMargins(default_margin,default_margin,default_margin,default_margin);
                    Main_layout.setLayoutParams(my_main_params);
                Main_layout.setOrientation(LinearLayout.VERTICAL);

            String longest_string;
            int Chars = 0;

            for ( int counter = 0; counter < List_with_text.length; counter++ ) {
                if ( List_with_text[counter].length() > Chars ) { longest_string = List_with_text[counter]; Chars = List_with_text[counter].length(); }
            }

            // Technische Informatica = 22 char. textSize = 17 for 500 width (22 char). textSize = 22 for 500 width (12 char).
            //calc text_size_calculator_backup = (x, y, z) -> (int) ( ( ( (float) z - ( (float) x / 2 ) ) / (float) 500) * (float) y );
            calc text_size_calculator = (x, y, z) -> (int) ( (float) ( (float) ( ( ( (float) z - ( (float) x / 2 ) ) / (float) 500) * (float) y ) / (float) metrics.density ) * (float) 2.625 );

            //int textSize_backup = (int) ( (float) ( (float) text_size_calculator.xyz(Chars, button_size, 28) / (float) metrics.density ) * (float) 2.625 );
            int textSize = text_size_calculator.xyz(Chars, button_size, 28);
            System.out.println(metrics.density + "     " + textSize);

            for (int i = 0; i < ammountOfItems; i++){

                //zet er 2 naast elkaar
                if (i + max_ammount_of_buttons_in_a_row - 1 < ammountOfItems){
                    LinearLayout horizontal = new LinearLayout(this.context);
                    horizontal.setOrientation(LinearLayout.HORIZONTAL);

                    for (int y = 0; y < max_ammount_of_buttons_in_a_row; y++) {
                        LinearLayout Button = new LinearLayout(this.context);
                            Button.setOrientation(LinearLayout.VERTICAL);
                            Button.setBackgroundColor(getResources().getColor(R.color.light_grey));
                            LinearLayout.LayoutParams btnSize = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(button_size, button_size));
                                btnSize.setMargins(button_margin,button_margin,button_margin,button_margin);
                                Button.setLayoutParams(btnSize);
                            RelativeLayout button_image = new RelativeLayout(this.context);
                                button_image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 2));
                                button_image.setGravity(Gravity.CENTER);
                                LinearLayout the_image = new LinearLayout(this.context);
                                    LinearLayout.LayoutParams the_image_params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT ));
                                        the_image_params.setMargins(picture_margin[0], picture_margin[1], picture_margin[2], picture_margin[3]);
                                        the_image.setLayoutParams(the_image_params);
                                    the_image.setBackground(getDrawable(List_with_images[i]));
                                    button_image.addView(the_image);
                            RelativeLayout button_text = new RelativeLayout(this.context);
                                button_text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 5));
                                button_text.setGravity(Gravity.CENTER);
                                TextView text = new TextView(this.context);
                                    text.setText(List_with_text[i]);
                                    text.setTextSize(textSize);
                                    button_text.addView(text);
                            Button.addView(button_image);
                            Button.addView(button_text);

                        final String this_button_id = List_with_id[i];
                        Button.isClickable();
                        Button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent gotoPage = new Intent(context, educations_activity.class);
                                    gotoPage.putExtra("StudyID", this_button_id);
                                    gotoPage.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(gotoPage);
                            }
                        });

                        i++;

                        horizontal.addView(Button);
                    }
                    i--;

                    Main_layout.addView(horizontal);
                }
                // zet er 1 in het midden
                else {
                    LinearLayout horizontal = new LinearLayout(this.context);
                        horizontal.setOrientation(LinearLayout.HORIZONTAL);
                        LinearLayout.LayoutParams horizontal_params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT ));
                            horizontal.setLayoutParams(horizontal_params);
                        horizontal.setGravity(Gravity.CENTER_HORIZONTAL);

                    LinearLayout Button = new LinearLayout(this.context);
                        Button.setOrientation(LinearLayout.VERTICAL);
                        Button.setBackgroundColor(getResources().getColor(R.color.light_grey));
                        LinearLayout.LayoutParams btnSize = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(button_size, button_size));
                            btnSize.setMargins(button_margin,button_margin,button_margin,button_margin);
                            Button.setLayoutParams(btnSize);
                        RelativeLayout button_image = new RelativeLayout(this.context);
                            button_image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 2));
                            button_image.setGravity(Gravity.CENTER);
                            LinearLayout the_image = new LinearLayout(this.context);
                                LinearLayout.LayoutParams the_image_params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT ));
                                    the_image_params.setMargins(picture_margin[0], picture_margin[1], picture_margin[2], picture_margin[3]);
                                    the_image.setLayoutParams(the_image_params);
                                the_image.setBackground(getDrawable(List_with_images[i]));
                                the_image.getBackground().mutate().setColorFilter(getResources().getColor(R.color.hro_red),PorterDuff.Mode.SRC_IN);
                                button_image.addView(the_image);
                        RelativeLayout button_text = new RelativeLayout(this.context);
                            button_text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 5));
                            button_text.setGravity(Gravity.CENTER);
                            TextView text = new TextView(this.context);
                                text.setText(List_with_text[i]);
                                text.setTextSize(textSize);
                                button_text.addView(text);
                        Button.addView(button_image);
                        Button.addView(button_text);

                        final String this_button_id = List_with_id[i];
                        Button.isClickable();
                        Button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent gotoPage = new Intent(context, educations_activity.class);
                                    gotoPage.putExtra("StudyID", this_button_id);
                                    gotoPage.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(gotoPage);
                            }
                        });

                    horizontal.addView(Button);
                    Main_layout.addView(horizontal);
                }
            }
            main.addView(Main_layout);
        }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        public void generate_page_study_programs(int Image, String ID, int addViewTo){
            String[] study = this.db.getStudyInfo(ID);

            String study_name = study[2];
            String study_information = study[3];

            int amountOfQuestions = db.amountOfQuestions(study_name);
            String[] QuizQuestions = db.getQuizQuestions(study_name);

            for (int i = 0; i < amountOfQuestions; i++){ System.out.println(QuizQuestions[i]); };

            String[] contentList = new String[]{study_name,study_information};

            int header_height = (int) ( (float) phone_height / (float) 3.5 );
            int quiz_height = (int) ( (float) phone_height / (float) 5 );
            int studycheckimage_height = quiz_height - (2 * default_margin);
            int studycheckimage_width = (int) ( ( (float) studycheckimage_height / (float) 1500 ) * (float) 2100 );
            //int studycheckimage_width = phone_width - (10 * default_margin);
            //int studycheckimage_height = (int) ( ( (float) studycheckimage_width / (float) 2100 ) * (float) 1500 );
            int textSize = (int) ( (float) ( (float) (float) 16 * (float) ((float) phone_height / (float) 2200) / (float) metrics.density ) * (float) 2.625 );

            LinearLayout this_page = new LinearLayout(this.context);
                this_page.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams this_page_lp = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                this_page.setLayoutParams(this_page_lp);
            LinearLayout this_page_header = new LinearLayout(this.context);
                this_page_header.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams this_page_header_lp = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, header_height));
                this_page_header.setLayoutParams(this_page_header_lp);
                this_page_header.setBackground(getDrawable(Image));
            LinearLayout this_page_text = new LinearLayout(this.context);
                this_page_text.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams this_page_text_lp = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                this_page_text_lp.setMargins(0,default_margin,0,default_margin);
                this_page_text.setLayoutParams(this_page_text_lp);

            if (amountOfQuestions > 0) {
                LinearLayout quiz_button = new LinearLayout(this.context);
                    quiz_button.setGravity(Gravity.CENTER);
                    LinearLayout.LayoutParams quiz_button_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, quiz_height);
                        quiz_button_params.setMargins(default_margin,0,default_margin,default_margin);
                        quiz_button.setLayoutParams(quiz_button_params);
                    quiz_button.setBackgroundColor(getResources().getColor(R.color.light_grey));
                    LinearLayout quiz_button_image = new LinearLayout(this.context);
                        LinearLayout.LayoutParams quiz_button_image_lp = new LinearLayout.LayoutParams(studycheckimage_width, studycheckimage_height);
                            quiz_button_image_lp.setMargins(0 ,0, 0 ,0);
                            quiz_button_image.setLayoutParams(quiz_button_image_lp);
                        quiz_button_image.setBackground(getDrawable(R.drawable.studycheck));
                        quiz_button.addView(quiz_button_image);
                    this_page_text.addView(quiz_button);
                    quiz_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent goto_quiz_page = new Intent(context,educations_activity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);;
                                goto_quiz_page.putExtra("QUIZARRAY", QuizQuestions);
                                goto_quiz_page.putExtra("MYANSWERARRAY",new String[QuizQuestions.length]);
                                goto_quiz_page.putExtra("ANSWERARRAY", new String[QuizQuestions.length]);
                                goto_quiz_page.putExtra("AMOUNTOFQUESTIONS", (int) amountOfQuestions);
                                goto_quiz_page.putExtra("PROGRESSION", 0);
                                startActivity(goto_quiz_page);
                        }
                    });
            }

            for (int x = 0; x < contentList.length; x++) {
                TextView this_text = new TextView(this.context);
                LinearLayout.LayoutParams text_params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                    text_params.setMargins(default_margin,0,default_margin,0);
                    this_text.setLayoutParams(text_params);
                this_text.setText(contentList[x]);
                this_text.setTextSize(textSize);
                this_page_text.addView(this_text);
            }
            LinearLayout main = (LinearLayout) findViewById(addViewTo);
                this_page.addView(this_page_header);
                this_page.addView(this_page_text);
                main.addView(this_page);
        }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        public void generate_menu(int addToThisLayout, int[] List_with_images, String[] List_with_text, Intent[] gotoThisPage){

            int ammountOfItems;
            if (List_with_images.length < List_with_text.length) { ammountOfItems = List_with_images.length; } else { ammountOfItems = List_with_text.length; }
            int button_width = (int) ( (float) phone_width / (float) ammountOfItems );

            RelativeLayout main = (RelativeLayout) findViewById(addToThisLayout);
            LinearLayout Main_layout = new LinearLayout(this.context);
                LinearLayout.LayoutParams main_params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    Main_layout.setLayoutParams(main_params);
                Main_layout.setBackgroundColor(getResources().getColor(R.color.hro_red));
                Main_layout.setOrientation(LinearLayout.HORIZONTAL);

            String longest_string;
            int Chars = 0;

            for ( int counter = 0; counter < List_with_text.length; counter++ ) {
                if ( List_with_text[counter].length() > Chars ) { longest_string = List_with_text[counter]; Chars = List_with_text[counter].length(); }
            }

            calc text_size_calculator = (x, y, z) -> (int) ( (float) ( (float) ( ( ( (float) z - ( (float) x / 2 ) ) / (float) 500) * (float) y ) / (float) metrics.density ) * (float) 2.625 );

            int textSize = text_size_calculator.xyz(Chars, button_width, 30);
            System.out.println(metrics.density + "     " + textSize);

            // calculating the height of the menu part of the screen.
            int menu_part_height;
            if (phone_width < 300) { menu_part_height = (int) ((float) phone_height / (float) 8); }
            else if (phone_width >= 300 && phone_width < 800 && phone_height < 600) { menu_part_height = (int) ((float) phone_height / (float) 8); }
            else if (phone_width >= 300 && phone_width < 800 && phone_height >= 600) { menu_part_height = (int) ( (float) ( (float) phone_height / (float) 8.25) * 0.75 ); }
            else if (phone_width >= 800 && phone_width < 1280) { menu_part_height = phone_height / 10; }
            else if (phone_width >= 1280) { menu_part_height = (int) ((float) phone_height / (float) 8.75); }
            else { menu_part_height = (int) ((float) phone_height / (float) 8); } // just in case there is a bug it has a height.

            int margin_horizontal; if ( menu_part_height < button_width ) { margin_horizontal = (int) ( (float) button_width - (float) menu_part_height ); } else { margin_horizontal = 0; }
            int margin_vertical; if ( menu_part_height < button_width ) { margin_vertical = 0; } else { margin_vertical = (int) ( (float) ( (float) menu_part_height - (float) button_width ) / (float) 2 );  }


            int image_size_height = (menu_part_height / 2)  - (menu_part_height / 5);
            int image_size_width = image_size_height;


            for (int i = 0; i < List_with_images.length; i++){
                LinearLayout Button = new LinearLayout(this.context);
                    Button.setOrientation(LinearLayout.VERTICAL);
                    Button.setBackgroundColor(getResources().getColor(R.color.hro_red));
                    LinearLayout.LayoutParams btnSize = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(button_width, LinearLayout.LayoutParams.MATCH_PARENT));
                        Button.setLayoutParams(btnSize);

                    RelativeLayout button_image = new RelativeLayout(this.context);
                        button_image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
                        button_image.setGravity(Gravity.CENTER);
                        LinearLayout the_image = new LinearLayout(this.context);
                            LinearLayout.LayoutParams the_image_params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams( image_size_width, image_size_height ));
                                the_image_params.setMargins(0,(menu_part_height / 10),0,0);
                                the_image.setLayoutParams(the_image_params);
                            the_image.setBackground(getDrawable(List_with_images[i]));
                        button_image.addView(the_image);

                    RelativeLayout button_text = new RelativeLayout(this.context);
                        button_text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
                        button_text.setGravity(Gravity.CENTER);
                        TextView text = new TextView(this.context);
                            text.setText(List_with_text[i]);
                            text.setTextSize(textSize);
                            text.setTextColor(getResources().getColor(R.color.white));
                            button_text.addView(text);
                    Button.addView(button_image);
                    Button.addView(button_text);

                Button.isClickable();
                int counter = i;
                Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(gotoThisPage[counter].addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                    }
                });

                Main_layout.addView(Button);

            }
            main.addView(Main_layout);
        }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        public void Image_with_Buttons(int addToThisLinearLayout, int[] images){
            int image_height = (int) ( (float) phone_height / (float) 3.5 );
            int text_size = (int) ( (float) ( (float) (float) 40 * (float) ((float) phone_height / (float) 2200) / (float) metrics.density ) * (float) 2.625 );
            LinearLayout the_image = new LinearLayout(this.context);
                the_image.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams image_lp = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, image_height));
                    the_image.setLayoutParams(image_lp);
                the_image.setBackground(getDrawable(images[0]));


            RelativeLayout button_left = new RelativeLayout(this.context);
                button_left.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 2));
                TextView button1_txt = new TextView(this.context);
                    button1_txt.setText("<");
                    button1_txt.setTextSize(text_size);
                    button_left.setGravity(Gravity.CENTER); button1_txt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    button1_txt.setTextColor(getResources().getColor(R.color.white));
                    button_left.addView(button1_txt);
            the_image.addView(button_left);

            RelativeLayout space = new RelativeLayout(this.context);
                space.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 11));
                the_image.addView(space);

            RelativeLayout button_right = new RelativeLayout(this.context);
            button_right.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 2));
                TextView button2_txt = new TextView(this.context);
                    button2_txt.setText(">");
                    button2_txt.setTextSize(text_size);
                    button_right.setGravity(Gravity.CENTER); button2_txt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    button2_txt.setTextColor(getResources().getColor(R.color.white));
                    button_right.addView(button2_txt);
            the_image.addView(button_right);


            button_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageCounter = imageCounter - 1;
                    if (imageCounter < 0) { imageCounter = images.length - 1; }
                    the_image.setBackground(getDrawable(images[imageCounter]));
                }
            });
            button_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageCounter = imageCounter + 1;
                    if (imageCounter > images.length - 1) { imageCounter = 0; }
                    the_image.setBackground(getDrawable(images[imageCounter]));
                }
            });

            the_image.setBackground(getDrawable(images[0]));


            LinearLayout main = (LinearLayout) findViewById(addToThisLinearLayout);
            main.addView(the_image);
        }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



        public void calendar_page(int addToThisLayout, int Image, int Calendar_Image,
                                  int Share_Image, String EVENT_TITLE, String EVENT_DESCRIPTION, String EVENT_LOCATION,
                                  int EVENT_YEAR, int EVENT_MONTH, int EVENT_DAY, int EVENT_START_HOUR, int EVENT_START_MINUTE,
                                  int EVENT_END_HOUR, int EVENT_END_MINUTE, String openday_id){

            int horizontal_space = ( (int) ( (float) phone_width / (float) 50 ) );
            int vertical_space = ( (int) ( (float) phone_height / (float) 50 ) );

            int button_height = ( (int) ( (float) phone_height / (float) 7.0 ) ) - vertical_space;
            int button_width = ( phone_width / 2 ) - (horizontal_space * 6);

            int button_size;

            if (button_height < button_width){ button_size = button_height; } else { button_size = button_width; }

            int text_size_buttons = (int) ( ( ( ( (float) 16 / (float) 270 ) * (float) button_size) / (float) metrics.density ) * (float) 2.625 );

            LinearLayout main = (LinearLayout) findViewById(addToThisLayout);
                main.setOrientation(LinearLayout.VERTICAL);
            LinearLayout page = new LinearLayout(this.context);
                LinearLayout.LayoutParams page_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    page.setLayoutParams(page_params);
                page.setOrientation(LinearLayout.VERTICAL);
            LinearLayout image = new LinearLayout(this.context);
                LinearLayout.LayoutParams image_params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,(int) ( (float) phone_height / (float) 3.5 ) ));
                    image.setLayoutParams(image_params);
                image.setBackground(getDrawable(Image));
            LinearLayout buttons = new LinearLayout(this.context);
                LinearLayout.LayoutParams buttons_params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(int) ( (float) phone_height / (float) 7.0 )));
                    buttons_params.setMargins( horizontal_space, vertical_space, horizontal_space, vertical_space );
                    buttons.setLayoutParams(buttons_params);
                buttons.setOrientation(LinearLayout.HORIZONTAL);
                buttons.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams button_params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(button_size,button_size));
                button_params.setMargins( ( horizontal_space * 2 ), 0, ( horizontal_space * 2 ), 0 );

            LinearLayout addToCalendar = new LinearLayout(this.context);
                addToCalendar.setOrientation(LinearLayout.VERTICAL);
                addToCalendar.setLayoutParams(button_params);
                addToCalendar.setBackgroundColor(getResources().getColor(R.color.light_grey));
                LinearLayout addToCalendar_inner_image = new LinearLayout(this.context);
                    addToCalendar_inner_image.setBackground(getDrawable(Calendar_Image));
                    addToCalendar_inner_image.getBackground().mutate().setColorFilter(getResources().getColor(R.color.hro_red),PorterDuff.Mode.SRC_IN);
                    LinearLayout.LayoutParams addToCalendar_inner_image_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1);
                        addToCalendar_inner_image_params.setMargins(( (button_size / 4) + (button_size / 60)),(button_size / 30),( (button_size / 4) + (button_size / 60)),0);
                        addToCalendar_inner_image.setLayoutParams(addToCalendar_inner_image_params);
                    addToCalendar.addView(addToCalendar_inner_image);
                    addToCalendar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //https://stackoverflow.com/questions/7859005/how-to-read-and-edit-android-calendar-events-using-the-new-android-4-0-ice-cream

                            Calendar begin = Calendar.getInstance(); begin.set(EVENT_YEAR, EVENT_MONTH, EVENT_DAY, EVENT_START_HOUR, EVENT_START_MINUTE); long dtstart = begin.getTimeInMillis();
                            Calendar end = Calendar.getInstance(); end.set(EVENT_YEAR, EVENT_MONTH, EVENT_DAY, EVENT_END_HOUR, EVENT_END_MINUTE); long dtend = end.getTimeInMillis();

                            Intent addToCalendar = new Intent(Intent.ACTION_INSERT)
                                    .setType("vnd.android.cursor.item/event")
                                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, dtstart)
                                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, dtend)
                                    .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false)
                                    .putExtra(CalendarContract.Events.TITLE, EVENT_TITLE)
                                    .putExtra(CalendarContract.Events.DESCRIPTION, EVENT_DESCRIPTION)
                                    .putExtra(CalendarContract.Events.EVENT_LOCATION, EVENT_LOCATION);

                            startActivity(addToCalendar);
                        }
                    });

                LinearLayout addToCalendar_text = new LinearLayout(this.context);
                    addToCalendar_text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1));
                    addToCalendar_text.setGravity(Gravity.CENTER);
                    TextView addToCalendar_TextView = new TextView(this.context);
                        if(this.db.language() == true){
                            addToCalendar_TextView.setText("Voeg toe\naan agenda");
                        } else {
                            addToCalendar_TextView.setText("Add to\ncalendar");
                        }
                        addToCalendar_TextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        addToCalendar_TextView.setTextSize(text_size_buttons);
                        addToCalendar_text.addView(addToCalendar_TextView);
                    addToCalendar.addView(addToCalendar_text);
                buttons.addView(addToCalendar);

            LinearLayout share = new LinearLayout(this.context);
                share.setOrientation(LinearLayout.VERTICAL);
                share.setLayoutParams(button_params);
                share.setBackgroundColor(getResources().getColor(R.color.light_grey));
                LinearLayout share_inner_image = new LinearLayout(this.context);
                    share_inner_image.setBackground(getDrawable(Share_Image));
                    share_inner_image.getBackground().mutate().setColorFilter(getResources().getColor(R.color.hro_red),PorterDuff.Mode.SRC_IN);
                    LinearLayout.LayoutParams share_inner_image_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1);
                        share_inner_image_params.setMargins(( (button_size / 4) + (button_size / 60)),(button_size / 30),( (button_size / 4) + (button_size / 60)),0);
                        share_inner_image.setLayoutParams(share_inner_image_params);
                    share.addView(share_inner_image);
                    share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent share_intent = new Intent(context, POPUP_activity.class);
                                share_intent.putExtra("WIDTH", (int) (phone_width * 0.8));
                                share_intent.putExtra("HEIGHT", (int) (phone_width * 0.8));
                                share_intent.putExtra("Openday_id", openday_id);
                            startActivity(share_intent);
                        }
                    });

            LinearLayout share_text = new LinearLayout(this.context);
                share_text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1));
                share_text.setGravity(Gravity.CENTER);
                TextView share_TextView = new TextView(this.context);
                    if (this.db.language() == true) {
                        share_TextView.setText("Delen");
                    } else {
                        share_TextView.setText("Share");
                    }
                    share_TextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    share_TextView.setTextSize(text_size_buttons);
                    share_text.addView(share_TextView);
                share.addView(share_text);
            buttons.addView(share);

            page.addView(image);
            page.addView(buttons);
            main.addView(page);
        }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        public void generate_page_about_page(int Image, final String institute_id, int addViewTo){
            String[] institute = this.db.getInstituteInfo(institute_id);

            String Title = institute[1];
            String Text = institute[2];

            String[] contentList = new String[]{Text};

            int header_height = (int) ( (float) phone_height / (float) 3.5 );
            int textSize = (int) ( (float) ( (float) (float) 16 * (float) ((float) phone_height / (float) 2200) / (float) metrics.density ) * (float) 2.625 );
            int textSize_header = (int) ( (float) ( (float) (float) ( (float) 86 - (float) ( Title.length() * 2 ) ) * (float) ((float) phone_width / (float) 1080) / (float) metrics.density ) * (float) 2.625 );


            LinearLayout this_page = new LinearLayout(this.context);
                this_page.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams this_page_lp = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    this_page.setLayoutParams(this_page_lp);
                LinearLayout this_page_header = new LinearLayout(this.context);
                    this_page_header.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout.LayoutParams this_page_header_lp = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, header_height));
                        this_page_header.setLayoutParams(this_page_header_lp);
                    this_page_header.setBackground(getDrawable(Image));
                    LinearLayout header_text = new LinearLayout(this.context);
                        LinearLayout.LayoutParams header_text_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                            header_text_params.setMargins((phone_width / 25),(phone_height / 75),(phone_width / 25),(phone_height / 75));
                            header_text.setLayoutParams(header_text_params);
                        TextView title = new TextView(this.context);
                            title.setText(Title); title.setTextSize(textSize_header); title.setTextColor(getResources().getColor(R.color.hro_red));
                            header_text.setGravity(Gravity.CENTER);
                            header_text.addView(title);
                        this_page_header.addView(header_text);
                LinearLayout this_page_text = new LinearLayout(this.context);
                    this_page_text.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams this_page_text_lp = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        this_page_text_lp.setMargins(0,default_margin,0,default_margin);
                        this_page_text.setLayoutParams(this_page_text_lp);

            for (int x = 0; x < contentList.length; x++) {
                TextView this_text = new TextView(this.context);
                LinearLayout.LayoutParams text_params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                text_params.setMargins(default_margin,0,default_margin,0);
                this_text.setLayoutParams(text_params);
                this_text.setText(contentList[x]);
                this_text.setTextSize(textSize);
                this_page_text.addView(this_text);
            }
            LinearLayout main = (LinearLayout) findViewById(addViewTo);
            this_page.addView(this_page_header);
            this_page.addView(this_page_text);
            main.addView(this_page);


            LinearLayout button_map = new LinearLayout(this.context);
                button_map.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams layoutParamsButtonMap = new LinearLayout.LayoutParams(calcWithFromDesign(950),calcHeightFromDesign(350));
                layoutParamsButtonMap.setMargins(calcWithFromDesign(70),calcHeightFromDesign(20),0,0);
                button_map.setLayoutParams(layoutParamsButtonMap);
                button_map.setBackgroundColor(getResources().getColor( R.color.light_grey));
                button_map.setGravity(Gravity.CENTER_HORIZONTAL);

                ImageView icon = new ImageView(context);
                    LinearLayout.LayoutParams iconLayout=new LinearLayout.LayoutParams(calcWithFromDesign(150),calcWithFromDesign(150));
                    iconLayout.setMargins(0,calcHeightFromDesign(40),0,0);
                    icon.setLayoutParams(iconLayout);
                    icon.setImageResource(R.drawable.twotone_map_black_18dp);

                    icon.setColorFilter(getResources().getColor(R.color.hro_red));
                button_map.addView(icon);

                TextView floorplanText=new TextView(context);
                    floorplanText.setText(captFirstLetter(getResources().getText(R.string.floorPlan).toString()));
                    floorplanText.setTextSize(makeTextFit(calcWithFromDesign(350),captFirstLetter(getResources().getText(R.string.floorPlan).toString())));
                    floorplanText.setGravity(Gravity.CENTER_HORIZONTAL);
                    floorplanText.setTextColor(getResources().getColor(android.R.color.black));
                button_map.addView(floorplanText);



                button_map.isClickable();
                button_map.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent map = new Intent(context,map_activity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);;
                        map.putExtra("InstituteID", institute_id);
                        startActivity(map);
                    }
                });
                main.addView(button_map);
        }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        public void generate_page_quiz_page(String[] Questions, String[] myAnswer, String[] answer, int amountOfQuestions, int Progression){

            String[] this_question = Questions[Progression].split("\n", -1);

            int highest = 0; int this_answer1 = Integer.parseInt(this_question[2]); int this_answer2 = Integer.parseInt(this_question[4]); int this_answer3 = Integer.parseInt(this_question[6]);
            if ( this_answer1 >= highest) { highest = this_answer1; }
            if ( this_answer2 >= highest) { highest = this_answer2; }
            if ( this_answer3 >= highest) { highest = this_answer3; }
            answer[Progression] = Integer.toString(highest);

            int header_height = (int) ( (float) phone_height / (float) 3.5 );
            int studycheckimage_height = header_height - (2 * default_margin);
            int studycheckimage_width = (int) ( ( (float) studycheckimage_height / (float) 1500 ) * (float) 2100 );
            if ( studycheckimage_width > phone_width ) {
                studycheckimage_width = phone_width - ( 4 * default_margin );
                studycheckimage_height = (int) ( ( (float) studycheckimage_width / (float) 2100 ) * (float) 1500 );
            }

            int progressionbar_height = ( default_margin * 3 ) / 2;
            int progressionbar_width = phone_width - (default_margin * 4);
            int progression_done_width = ( progressionbar_width / amountOfQuestions ) * Progression;
            int buttons_width = phone_width - (default_margin * 4);
            int buttons_height = phone_height / 11;

            int textSize = (int) ( (float) ( (float) (float) 16 * (float) ((float) phone_height / (float) 2200) / (float) metrics.density ) * (float) 2.625 );

            LinearLayout this_page = new LinearLayout(this.context);
                this_page.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams this_page_lp = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    this_page.setLayoutParams(this_page_lp);
                LinearLayout this_page_header = new LinearLayout(this.context);
                    this_page_header.setOrientation(LinearLayout.HORIZONTAL);
                    this_page_header.setGravity(Gravity.CENTER);
                    LinearLayout.LayoutParams this_page_header_lp = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, header_height));
                        this_page_header.setLayoutParams(this_page_header_lp);
                    LinearLayout this_page_header_image = new LinearLayout(this.context);
                        LinearLayout.LayoutParams this_page_header_image_params = new LinearLayout.LayoutParams(studycheckimage_width,studycheckimage_height);
                            this_page_header_image.setLayoutParams(this_page_header_image_params);
                        this_page_header_image.setBackground(getDrawable(R.drawable.studycheck));
                        this_page_header.addView(this_page_header_image);
                LinearLayout this_page_question = new LinearLayout(this.context);
                    this_page_question.setOrientation(LinearLayout.VERTICAL); this_page_question.setGravity(Gravity.CENTER);
                    LinearLayout.LayoutParams this_page_question_lp = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        this_page_question_lp.setMargins(0,default_margin * 2,0,default_margin * 2);
                        this_page_question.setLayoutParams(this_page_question_lp);

                LinearLayout Progress = new LinearLayout(this.context);
                    Progress.setOrientation(LinearLayout.VERTICAL); Progress.setGravity(Gravity.CENTER); this_page_question.addView(Progress);
                    TextView progression = new TextView(this.context); progression.setText("Progression: (" + Progression + "/" + amountOfQuestions + ")"); progression.setGravity(Gravity.CENTER); progression.setTextAlignment(View.TEXT_ALIGNMENT_CENTER); Progress.addView(progression);
                    LinearLayout progressionBar = new LinearLayout(this.context);
                        LinearLayout.LayoutParams progressionBar_params = new LinearLayout.LayoutParams(progressionbar_width, progressionbar_height);
                            progressionBar.setLayoutParams(progressionBar_params);
                            progressionBar.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                        Progress.addView(progressionBar);
                        progressionBar.setGravity(Gravity.LEFT);
                        LinearLayout progression_done = new LinearLayout(this.context);
                            progression_done.setBackgroundColor(getResources().getColor(R.color.hro_red));
                            LinearLayout.LayoutParams progression_done_params = new LinearLayout.LayoutParams(progression_done_width, progressionbar_height);
                                progression_done.setLayoutParams(progression_done_params);
                            progressionBar.addView(progression_done);


                LinearLayout question_layout = new LinearLayout(this.context); question_layout.setGravity(Gravity.CENTER); question_layout.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams question_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        question_layout_params.setMargins(default_margin,default_margin * 3,default_margin,default_margin * 3);
                    question_layout.setLayoutParams(question_layout_params);
                    TextView question = new TextView(this.context); question.setText(this_question[0]); question.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        question_layout.addView(question);
                    this_page_question.addView(question_layout);

                LinearLayout radio_spacing = new LinearLayout(this.context); this_page_question.addView(radio_spacing);
                    LinearLayout.LayoutParams radio_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        radio_params.setMargins(default_margin,default_margin,default_margin,default_margin);
                        radio_spacing.setLayoutParams(radio_params);
                RadioGroup answer_group = new RadioGroup(this.context); radio_spacing.addView(answer_group);
                    RadioButton answerButton = new RadioButton(this.context); answerButton.setText(this_question[1]); answer_group.addView(answerButton);
                    RadioButton answerButton2 = new RadioButton(this.context); answerButton2.setText(this_question[3]); answer_group.addView(answerButton2);
                    RadioButton answerButton3 = new RadioButton(this.context); answerButton3.setText(this_question[5]); answer_group.addView(answerButton3);

                LinearLayout buttons = new LinearLayout(this.context);
                    buttons.setOrientation(LinearLayout.HORIZONTAL); buttons.setGravity(Gravity.CENTER);
                    LinearLayout.LayoutParams buttons_params = new LinearLayout.LayoutParams(buttons_width,buttons_height); buttons_params.setMargins(default_margin, default_margin, default_margin, default_margin);
                        buttons.setLayoutParams(buttons_params);
                    LinearLayout backButton = new LinearLayout(this.context); LinearLayout.LayoutParams backButton_params; backButton.setBackgroundColor(getResources().getColor(R.color.dark_grey)); buttons.addView(backButton);
                        TextView backButton_text = new TextView(this.context); backButton_text.setText("Back");
                    LinearLayout nextButton = new LinearLayout(this.context); LinearLayout.LayoutParams nextButton_params; nextButton.setBackgroundColor(getResources().getColor(R.color.dark_grey)); buttons.addView(nextButton);
                        TextView nextButton_text = new TextView(this.context); nextButton.addView(nextButton_text);
                        nextButton.setGravity(Gravity.CENTER);

                    if (Progression + 1 >= amountOfQuestions) { nextButton_text.setText("Finish"); } else { nextButton_text.setText("Next"); }

                    if (Progression <= 0){
                        backButton_params = new LinearLayout.LayoutParams(0,0);
                        nextButton_params = new LinearLayout.LayoutParams(buttons_width,buttons_height);
                    }
                    else {
                        backButton_params = new LinearLayout.LayoutParams(0,buttons_height,1); backButton_params.setMargins(0,0,default_margin / 2,0);
                            backButton.addView(backButton_text);
                            backButton.setGravity(Gravity.CENTER);
                        nextButton_params = new LinearLayout.LayoutParams(0,buttons_height,1); nextButton_params.setMargins(default_margin / 2,0,0,0);
                    }
                    backButton.setLayoutParams(backButton_params); nextButton.setLayoutParams(nextButton_params);

                    nextButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if ( answerButton.isChecked() || answerButton2.isChecked() || answerButton3.isChecked() ) {
                                int chosen_answer = 0;
                                if (answerButton.isChecked()) { chosen_answer = this_answer1; }
                                if (answerButton2.isChecked()) { chosen_answer = this_answer2; }
                                if (answerButton3.isChecked()) { chosen_answer = this_answer3; }
                                myAnswer[Progression] = Integer.toString(chosen_answer);

                                Intent goto_quiz_page = new Intent(context, educations_activity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    goto_quiz_page.putExtra("QUIZARRAY", Questions);
                                    goto_quiz_page.putExtra("MYANSWERARRAY",myAnswer);
                                    goto_quiz_page.putExtra("ANSWERARRAY", answer);
                                    goto_quiz_page.putExtra("AMOUNTOFQUESTIONS", (int) amountOfQuestions);
                                    goto_quiz_page.putExtra("PROGRESSION", (int) Progression + 1);
                                    startActivity(goto_quiz_page);
                                    finish(); overridePendingTransition(0, 0);
                            }
                            else { Toast.makeText(context,"Select one.",Toast.LENGTH_LONG).show(); }
                        }
                    });

                    backButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent goto_quiz_page = new Intent(context, educations_activity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                goto_quiz_page.putExtra("QUIZARRAY", Questions);
                                goto_quiz_page.putExtra("MYANSWERARRAY", myAnswer);
                                goto_quiz_page.putExtra("ANSWERARRAY", answer);
                                goto_quiz_page.putExtra("AMOUNTOFQUESTIONS", (int) amountOfQuestions);
                                goto_quiz_page.putExtra("PROGRESSION", (int) Progression - 1);
                                startActivity(goto_quiz_page);
                                finish();
                        }
                    });

                    this_page_question.addView(buttons);

            LinearLayout main = (LinearLayout) findViewById(R.id.page_container);
            this_page.addView(this_page_header);
            this_page.addView(this_page_question);
            main.addView(this_page);
        }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        public void popup(int addToThisLayout, int width, int height, String openday_id){

            String[] openday = this.db.getOpendayInfo(openday_id);

            String message = "";
            if (this.db.language() == true) {
                message = "Er is een opendag op de Hogeschool Rotterdam bij het instituut: " + openday[0] + " op " + openday[1];
            } else {
                message = "There is an openday at the Hogeschool Rotterdam at the institute of: " + openday[0] + " on " + openday[1];
            }


            getWindow().setLayout(width,height);

            LinearLayout pop = (LinearLayout) findViewById(addToThisLayout);
            pop.setBackground(getDrawable(R.drawable.popup_background));

            int amountOfButtons = 5;
            int top_bar_margin = (int) (float) ( (float) height / (float) 50 );
            int top_bar_height = (int) ( (float) height / (float) amountOfButtons );

            LinearLayout.LayoutParams top_bar_params = new LinearLayout.LayoutParams(width, top_bar_height);
                top_bar_params.setMargins(0,0,0, top_bar_margin);

            LinearLayout.LayoutParams layout15 = new LinearLayout.LayoutParams((width / 5), top_bar_height);
            LinearLayout.LayoutParams layout45 = new LinearLayout.LayoutParams(((width / 5) * 4), top_bar_height);

            System.out.println( ( (width / 5) * 4) );

            LinearLayout.LayoutParams button_params;
            if ( ( height -  ( top_bar_height + top_bar_margin ) ) >= width ) { button_params = new LinearLayout.LayoutParams( (int) (width / 3), (int) (width / 3)); button_params.setMargins( (int) (width / 12),(int) (width / 12),(int) (width / 12),(int) (width / 12)); }
            else { button_params = new LinearLayout.LayoutParams(( (int) ( height -  ( top_bar_height + top_bar_margin ) ) / 3 ), (int) ( ( height -  ( top_bar_height + top_bar_margin ) ) / 3 )); button_params.setMargins(( ( height -  ( top_bar_height + top_bar_margin ) ) / 12 ),( ( height -  ( top_bar_height + top_bar_margin ) ) / 12 ),( ( height -  ( top_bar_height + top_bar_margin ) ) / 12 ),( ( height -  ( top_bar_height + top_bar_margin ) ) / 12 )); }

            LinearLayout top_bar = new LinearLayout(this.context);
                top_bar.setGravity(Gravity.CENTER);
                top_bar.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout top_bar_text = new LinearLayout(this.context);
                    top_bar_text.setGravity(Gravity.CENTER);
                    top_bar_text.setLayoutParams(layout45);
                    TextView top_bar_text_text = new TextView(this.context);
                        top_bar_text_text.setText("Share with your friends using:"); top_bar_text_text.setTextSize((int) ((float) ( ( (float) 14 )  * (float) ((float) 688 / (float) ((width / 5) * 4) ) / (float) metrics.density) * (float) 2.625) ); top_bar_text_text.setTextColor(getResources().getColor(R.color.white));
                        top_bar_text.addView(top_bar_text_text);
                    top_bar.addView(top_bar_text);

                LinearLayout close = new LinearLayout(this.context);
                    close.setGravity(Gravity.CENTER);
                    close.setLayoutParams(layout15);
                    TextView close_text = new TextView(this.context);
                        close_text.setText("X"); close_text.setTextSize( (int) ((float) ( (float) 28  * (float) ((float) 1080 / (float) phone_width) / (float) metrics.density) * (float) 2.625) ); close_text.setTextColor(getResources().getColor(R.color.white));
                        close.addView(close_text);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                    top_bar.addView(close);
                pop.addView(top_bar);


            LinearLayout horizontal1 = new LinearLayout(this.context);
                horizontal1.setOrientation(LinearLayout.HORIZONTAL);
                horizontal1.setGravity(Gravity.CENTER);

                LinearLayout twitter = new LinearLayout(this.context);
                    twitter.setBackground(getDrawable(R.drawable.twitter));
                    twitter.setOrientation(LinearLayout.HORIZONTAL);
                    twitter.setLayoutParams(button_params);

            String finalMessage = message;
            String facebookMessage = message.replace(" ", "%20");
            facebookMessage = facebookMessage.replace(":", "%3A");
            facebookMessage = facebookMessage.replace(",", "%2C");
            final String facebookmsg = facebookMessage;

            twitter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                Intent twitterintent = new Intent(Intent.ACTION_SEND);
                                    twitterintent.setType("text/plain");
                                    twitterintent.putExtra(android.content.Intent.EXTRA_TEXT, finalMessage);
                                    twitterintent.setPackage("com.twitter.android");
                                    startActivity(twitterintent);
                            } catch (Exception e) { Toast.makeText(context, "Twitter is not installed!", Toast.LENGTH_LONG).show();}
                        }
                    });
                    horizontal1.addView(twitter);

                LinearLayout facebook = new LinearLayout(this.context);
                    facebook.setBackground(getDrawable(R.drawable.facebook_logo));
                    facebook.setOrientation(LinearLayout.HORIZONTAL);
                    facebook.setLayoutParams(button_params);

                    facebook.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //https://stackoverflow.com/questions/5023602/facebook-share-link-can-you-customize-the-message-body-text
                            String fb_url = "https://www.facebook.com/sharer/sharer.php?u=https%3A%2F%2Fwww.hogeschoolrotterdam.nl%2F&quote=" + facebookmsg;
                            Intent facebookintent = new Intent(Intent.ACTION_VIEW);
                            facebookintent.setData(  Uri.parse(fb_url));
                            startActivity(facebookintent);
                        }
                    });
                    horizontal1.addView(facebook);
                pop.addView(horizontal1);

            LinearLayout horizontal2 = new LinearLayout(this.context);
                horizontal2.setOrientation(LinearLayout.HORIZONTAL);
                horizontal2.setGravity(Gravity.CENTER);

                LinearLayout whatsapp = new LinearLayout(this.context);
                    whatsapp.setBackground(getDrawable(R.drawable.whatsapp_logo));
                    whatsapp.setOrientation(LinearLayout.HORIZONTAL);
                    whatsapp.setLayoutParams(button_params);

                    whatsapp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                Intent whatsappintent = new Intent(Intent.ACTION_SEND);
                                    whatsappintent.setType("text/plain");
                                    whatsappintent.putExtra(android.content.Intent.EXTRA_TEXT, finalMessage);
                                    whatsappintent.setPackage("com.whatsapp");
                                    startActivity(whatsappintent);
                            } catch (Exception e) { Toast.makeText(context, "Whatsapp is not installed!", Toast.LENGTH_LONG).show();}
                        }
                    });
                    horizontal2.addView(whatsapp);


                LinearLayout email = new LinearLayout(this.context);
                    email.setBackground(getDrawable(R.drawable.email_icon));
                    email.setOrientation(LinearLayout.HORIZONTAL);
                    email.setLayoutParams(button_params);

                    email.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                                    emailIntent.setType("message/rfc822");    //<--https://stackoverflow.com/questions/8701634/send-email-intent
                                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@hr.nl"}); // recipients
                                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Openday HRO");
                                    emailIntent.putExtra(Intent.EXTRA_TEXT, finalMessage);
                                    emailIntent.setPackage("com.microsoft.office.outlook");
                                    startActivity(emailIntent);
                            } catch (Exception e){
                                try {
                                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                                        emailIntent.setType("message/rfc822");    //<--https://stackoverflow.com/questions/8701634/send-email-intent
                                        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@hr.nl"}); // recipients
                                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Openday HRO");
                                        emailIntent.putExtra(Intent.EXTRA_TEXT, finalMessage);
                                        emailIntent.setPackage("com.google.android.gm");
                                        startActivity(emailIntent);
                                } catch (Exception f){
                                    // in case outlook and gmail are not installed you can select another app.
                                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                                    emailIntent.setType("message/rfc822");    //<--https://stackoverflow.com/questions/8701634/send-email-intent
                                        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@hr.nl"}); // recipients
                                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Openday HRO");
                                        emailIntent.putExtra(Intent.EXTRA_TEXT, finalMessage);
                                        startActivity(emailIntent);
                                }
                            }
                        }
                    });
                    horizontal2.addView(email);
                pop.addView(horizontal2);

        }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        public void studyCheck_result_page(Boolean check){
            int header_height = (int) ( (float) phone_height / (float) 3.5 );
            int back_button_height = (int) ( (float) phone_height / (float) 8 );
            int studycheckimage_height = header_height - (2 * default_margin);
            int studycheckimage_width = (int) ( ( (float) studycheckimage_height / (float) 1500 ) * (float) 2100 );
            if ( studycheckimage_width >= studycheckimage_height ) {
                studycheckimage_width = studycheckimage_height;
            } else { studycheckimage_height = studycheckimage_width; }

            int textSize = (int) ( (float) ( (float) (float) 30 * (float) ((float) phone_height / (float) 2200) / (float) metrics.density ) * (float) 2.625 );

            LinearLayout this_page = new LinearLayout(this.context);
                this_page.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams this_page_lp = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    this_page.setLayoutParams(this_page_lp);
                LinearLayout this_page_header = new LinearLayout(this.context);
                    this_page_header.setOrientation(LinearLayout.HORIZONTAL);
                    this_page_header.setGravity(Gravity.CENTER);
                    LinearLayout.LayoutParams this_page_header_lp = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, header_height));
                        this_page_header.setLayoutParams(this_page_header_lp);
                    this_page.addView(this_page_header);
                    LinearLayout this_page_header_image = new LinearLayout(this.context);
                    LinearLayout.LayoutParams this_page_header_image_params = new LinearLayout.LayoutParams(studycheckimage_width,studycheckimage_height);
                        this_page_header_image.setLayoutParams(this_page_header_image_params);
                    if (check) { this_page_header_image.setBackground(getDrawable(R.drawable.tumb_up)); }
                    else { this_page_header_image.setBackground(getDrawable(R.drawable.tumb_down)); }
                    this_page_header.addView(this_page_header_image);
                LinearLayout this_page_txt = new LinearLayout(this.context); this_page_txt.setGravity(Gravity.CENTER);
                    this_page_txt.setOrientation(LinearLayout.VERTICAL); this_page_txt.setGravity(Gravity.CENTER);
                    LinearLayout.LayoutParams this_page_txt_lp = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        this_page_txt_lp.setMargins(0,default_margin * 2,0,default_margin * 2);
                        this_page_txt.setLayoutParams(this_page_txt_lp);
                        this_page.addView(this_page_txt);

                        LinearLayout result = new LinearLayout(this.context); result.setGravity(Gravity.CENTER);
                        LinearLayout backbutton = new LinearLayout(this.context); backbutton.setGravity(Gravity.CENTER);
                            backbutton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, back_button_height));
                        TextView result_textview = new TextView(this.context); result.addView(result_textview);
                            if (check) { result_textview.setText("This study is for you!"); } else { result_textview.setText("This study is not for you!"); } result_textview.setTextSize(textSize);
                        TextView backbutton_text = new TextView(this.context);
                            backbutton_text.setText("Close the quiz"); backbutton_text.setTextSize(textSize);
                            backbutton.addView(backbutton_text); backbutton.setBackgroundColor(getResources().getColor(R.color.hro_red));
                            backbutton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            });

                        this_page_txt.addView(result);
                        this_page_txt.addView(backbutton);

            LinearLayout main = (LinearLayout) findViewById(R.id.page_container); main.addView(this_page); main.setBackgroundColor(getResources().getColor(R.color.white));
        }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        public void contact_page(int image, int[] contact_images, int[] social_media_images, String institute_id){
            String callnumber = this.db.getInstituteInfo(institute_id)[4];

            LinearLayout main = (LinearLayout) findViewById(R.id.page_container);

            int header_height = (int) ( (float) phone_height / (float) 3.5 );
            int margin_vertical_big = (int) ( (float) phone_height / (float) 50 );
            int margin_vertical_small = (int) ( ( (float) phone_height / (float) 50 ) / 2 );

            LinearLayout header = new LinearLayout(this.context);
                header.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams header_lp = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, header_height));
                    header.setLayoutParams(header_lp);
                header.setBackground(getDrawable(image));
                main.addView(header);

            String[] title = {"Contact", "Social Media"};
            int longest_title = 0;
            for (int i = 0; i < title.length; i++){ if ( title[i].length() >= longest_title ) { longest_title = title[i].length(); } }

            int amountOfButtons = 3; int button_size = (int) ( ( ( (float) phone_width / amountOfButtons ) / 5) * 3.5 ); int button_horizontal_margin = (int) (button_size / 5);
            int default_text_size = 24; int int_tested_width = 1080; int textSize = (int) ((float) ((float) ((float) default_text_size - ((float) longest_title / 2)) * (float) ((float) int_tested_width / (float) phone_width) / (float) metrics.density) * (float) 2.625);
            LinearLayout.LayoutParams button_lp = new LinearLayout.LayoutParams(button_size, button_size); button_lp.setMargins(button_horizontal_margin,0,button_horizontal_margin, 0 );

            for (int i = 0; i < 2; i++) {

                LinearLayout text_layout = new LinearLayout(context);
                    text_layout.setGravity(Gravity.CENTER);
                    LinearLayout.LayoutParams text_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        if ( i == 0 ) { text_layout_params.setMargins( 0, margin_vertical_big, 0, margin_vertical_big ); }
                        else { text_layout_params.setMargins( 0, margin_vertical_small, 0, margin_vertical_big ); }
                    text_layout.setLayoutParams(text_layout_params);
                    TextView btn_txt = new TextView(this.context);
                        btn_txt.setText(title[i]); btn_txt.setTextSize(textSize);
                        text_layout.addView(btn_txt);
                    main.addView(text_layout);

                LinearLayout btn_layout = new LinearLayout(context);
                    btn_layout.setGravity(Gravity.CENTER);
                    LinearLayout.LayoutParams btn_layout_lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            btn_layout_lp.setMargins( 0, 0, 0, margin_vertical_small );
                    LinearLayout button1 = new LinearLayout(context);
                        button1.setLayoutParams(button_lp); btn_layout.addView(button1);
                    LinearLayout button2 = new LinearLayout(context);
                        button2.setLayoutParams(button_lp); btn_layout.addView(button2);
                    LinearLayout button3 = new LinearLayout(context);
                        button3.setLayoutParams(button_lp); btn_layout.addView(button3);
                    main.addView(btn_layout);

                if ( title[i] == "Contact" ) {
                    button1.setBackground(getDrawable(contact_images[0]));
                    button1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.hogeschoolrotterdam.nl/")));
                        }
                    });
                    button2.setBackground(getDrawable(contact_images[1]));
                    button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(context, askAQuestion_activity.class));
                        }
                    });
                    button3.setBackground(getDrawable(contact_images[2]));
                    button3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + callnumber)));
                        }
                    });
                } else if (title[i] == "Social Media") {
                    button1.setBackground(getDrawable(social_media_images[0]));
                    button1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //https://stackoverflow.com/questions/34564211/open-facebook-page-in-facebook-app-if-installed-on-android/34564284
                            Intent facebook = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com/hogeschoolrotterdam/"));
                            startActivity(facebook);
                        }
                    });
                    button2.setBackground(getDrawable(social_media_images[1]));
                    button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String instagramName = "hogeschoolrotterdam";
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/_u/" + instagramName)));
                        }
                    });
                    button3.setBackground(getDrawable(social_media_images[2]));
                    button3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //https://stackoverflow.com/questions/15497261/open-instagram-user-profile
                            String twitterUsername="hsrotterdam";
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name="+twitterUsername)));

                            } catch (Exception e) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/"+twitterUsername)));
                            }
                        }
                    });
                }
            }
        }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        private String captFirstLetter(String string){
            return string.substring(0,1).toUpperCase()+string.substring(1);
        }
      
        private int calcHeightFromDesign(float elementHeight){
            float designHeight= 1920.f;
            return (int)((elementHeight*phone_height)/designHeight);
        }

        private int calcWithFromDesign(float elementWidth){
            float designWidth= 1080.f;
            return (int)((elementWidth*phone_width)/designWidth);
        }

        private int calcTextSizeInt(float default_text_size,float text_length,float elementWidth){
            return (int) ( (float) ( (float) ( (float) default_text_size - ( (float) text_length / 2 ) ) * (float) ((float) phone_width / (float) elementWidth) / (float) metrics.density ) * (float) 2.625 );
        }

        private float makeTextFit(int availableWidth,String tekst) {

            //https://stackoverflow.com/questions/7259016/scale-text-in-a-view-to-fit/7259136#7259136
            // and edited by myself to fit
            TextView textView=new TextView(context);
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
        private float calcTextSizeFloat(float default_text_size,float text_length,float elementWidth){
            return  (float) ( (float) ( (float) default_text_size - ( (float) text_length / 2 ) ) * (float) ((float) phone_width / (float) elementWidth) / (float) metrics.density ) * (float) 2.625 ;
        }

        private int calcMaxTextLength(String... strings){
            int Chars=0;
            for ( int counter = 0; counter < strings.length; counter++ ) {
                if ( strings[counter].length() > Chars ) { Chars = strings[counter].length(); }
            }
            return Chars;
        }

        private String getMaxText(String... strings){
            String Chars="";
            for ( int counter = 0; counter < strings.length; counter++ ) {
                if ( strings[counter].length() > Chars.length() ) { Chars = strings[counter]; }
            }
            return Chars;
        }

//---------------------------------------function for sending email -----------------------------------------------------------
        //https://stackoverflow.com/questions/6119722/how-to-check-edittexts-text-is-email-address-or-not
        public boolean isEmailValid(String email) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[a-z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }



        public void confirmContactForm(EditText nameView,EditText subjectView,EditText emailView,EditText textFieldView){
            String name = nameView.getText().toString();
            String subject = subjectView.getText().toString();
            String email=emailView.getText().toString().toLowerCase();
            String textField = textFieldView.getText().toString();
            if(name.length()>0 && subject.length()>0&&textField.length()>0) {
                //https://developer.android.com/training/basics/intents/sending.html#java
                if(isEmailValid(email)) {
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("text/html");    //<--https://stackoverflow.com/questions/8701634/send-email-intent
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"0967161@hr.nl"}); // recipients
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact Form Openday: " + subject);
                    emailIntent.putExtra(Intent.EXTRA_TEXT, textField + "\n\nThis email was send by " + name+" with the opendag app.\nPleas anwser on: "+email);
                    startActivity(emailIntent);
                }else{
                    Toast.makeText(context,R.string.email_not_valid,Toast.LENGTH_LONG).show();
                }
            }else{
                String finalText=getString(R.string.fields_empty)+" ";
                if (name.length()==0){
                    finalText+=getText(R.string.name)+", ";
                }
                if (email.length() == 0) {
                    finalText+=getString(R.string.email)+", ";
                }
                if (email.length()==0){
                    finalText+=getString(R.string.subject)+", ";
                }

                if (textField.length() == 0) {
                    finalText+=getString(R.string.question)+", ";
                }

                finalText=finalText.substring(0,finalText.length()-2);
                Toast.makeText(context,finalText,Toast.LENGTH_LONG).show();
            }
        }

//-------------------------------------- end function for sending e-mail --------------------------------------------------------

        private class QuestionItemReturn {
            LinearLayout linearLayout;
            EditText editText;

            public QuestionItemReturn(LinearLayout linearLayoutInv, EditText editTextInv){
                linearLayout=linearLayoutInv;
                editText=editTextInv;
            }
        }

        private QuestionItemReturn questionItem(String name, float textSize, int totalWidth, int inputType,boolean theQuestion){
            LinearLayout group = new LinearLayout(context);
                LinearLayout.LayoutParams layoutParamsLayout=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParamsLayout.setMargins(0,0,0,20);
                group.setLayoutParams(layoutParamsLayout);
                group.setOrientation(LinearLayout.HORIZONTAL);
                group.setLayoutParams(layoutParamsLayout);
                group.setGravity(Gravity.TOP);

                TextView title=new TextView(context);
                    LinearLayout.LayoutParams layoutParamsTitle=new LinearLayout.LayoutParams(totalWidth/4 ,ViewGroup.LayoutParams.WRAP_CONTENT);
                    title.setLayoutParams(layoutParamsTitle);
                    title.setGravity(Gravity.RIGHT|Gravity.TOP);
                    title.setText(name+":");
                    title.setTextSize(textSize);
                group.addView(title);

                EditText editText=new EditText(context);
                    LinearLayout.LayoutParams layoutParamsEditText= new LinearLayout.LayoutParams(totalWidth/4*3, ViewGroup.LayoutParams.WRAP_CONTENT);
                    editText.setLayoutParams(layoutParamsEditText);
                    editText.setGravity(Gravity.TOP);
                    editText.setInputType(InputType.TYPE_CLASS_TEXT|inputType);
                    editText.setTag(name);
                    editText.setHint(name);
                group.addView(editText);

            return new QuestionItemReturn(group,editText);
        }


        public void generateAskQuestionPage(LinearLayout layout){
            LinearLayout linearLayout=new LinearLayout(context);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                int margin=40;
                layoutParams.setMargins(margin,margin,margin,margin);
                linearLayout.setLayoutParams(layoutParams);

                int newWidth=phone_width-margin*2;


                float textSize= calcTextSizeFloat(25,calcMaxTextLength(getString(R.string.name),getString(R.string.subject),getString(R.string.email),getString(R.string.question)),newWidth);

                EditText[] inputFields=new EditText[4];

                QuestionItemReturn name = questionItem(getString(R.string.name),textSize,newWidth,InputType.TYPE_TEXT_VARIATION_PERSON_NAME,false);
                    linearLayout.addView(name.linearLayout);
                    inputFields[0]=name.editText;

                QuestionItemReturn subject = questionItem(getString(R.string.subject),textSize,newWidth,InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE|InputType.TYPE_TEXT_FLAG_AUTO_CORRECT,false);
                    linearLayout.addView(subject.linearLayout);
                    inputFields[1]=subject.editText;

                QuestionItemReturn email= questionItem(getString(R.string.email),textSize,newWidth,InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,false);
                    linearLayout.addView(email.linearLayout);
                    inputFields[2]=email.editText;

                QuestionItemReturn question =questionItem(getString(R.string.question),textSize,newWidth,InputType.TYPE_TEXT_FLAG_MULTI_LINE,true);
                    linearLayout.addView(question.linearLayout);
                    inputFields[3]=question.editText;

                Button confirm = new Button(context);
                    confirm.setText(getText(R.string.Send_question));
                    LinearLayout.LayoutParams layoutParams1=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    confirm.setLayoutParams(layoutParams1);
                    float buttonTextSize= calcTextSizeFloat(25,getString(R.string.Send_question).length(),newWidth);
                    confirm.setTextSize(buttonTextSize);
                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            System.out.println("hello");
                            confirmContactForm(inputFields[0],inputFields[1],inputFields[2],inputFields[3]);
                        }
                    });
                linearLayout.addView(confirm);
            layout.addView(linearLayout);
        }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public LinearLayout.LayoutParams giveLayoutFieldParams(){
        return new LinearLayout.LayoutParams(calcWithFromDesign(400), ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void updateWarning(TextView warning,EditText roomEdit,Spinner buidlingSelector,Spinner floorSelector){
        Room[] rooms=mapManager.ROOMS;

        String tekst=roomEdit.getText().toString();
        int lengte=3-tekst.length();
        for (int i=lengte;i>0;i--){
            tekst="0"+tekst;
        }

        String buidling =buidlingSelector.getSelectedItem().toString();
        int floor=Integer.parseInt(floorSelector.getSelectedItem().toString());
        String room=tekst;

        String rawString = buidling+"."+floor+"."+room;

        boolean error=true;
        for(int i=0;i<rooms.length;i++){
            if(rawString.equals(rooms[i].roomNumber)){
                error=false;
            }
        }
        if (error){
            warning.setTextColor(Color.parseColor("#FFAC52"));
            warning.setText(getResources().getString(R.string.Classroom_not_highlighted));
        }else {
            warning.setTextColor(Color.parseColor("#34AC37"));
            warning.setText(getResources().getString(R.string.Classroom_highlighted));
        }


    }
        private void updateFloorSpinner(Spinner spinner,String buidlingStr,String[] buidlings){
            mapManager test = new mapManager(context,buidlings);

            test.setBuildingWhitoutUpdate(buidlingStr);
            test.updateFloorsInBuilding();
            int[] floors= test.floorsInBuilding;
            String[] stringFloors = new String[floors.length];
            for (int i = 0; i < stringFloors.length; i++) {
                stringFloors[i]=Integer.toString(floors[i]);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.spinner_item, stringFloors);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }

        public void generateSearchPopup(int addToThisLayout){
            EditText roomEdit;
            TextView warning;


            int width=calcWithFromDesign(900);
            int height=calcHeightFromDesign(1300);
            getWindow().setLayout(width,height);

            LinearLayout pop = (LinearLayout) findViewById(addToThisLayout);
                pop.setBackgroundColor(getResources().getColor(R.color.hro_red));
                LinearLayout topBar = new LinearLayout(context);
                topBar.setOrientation(LinearLayout.HORIZONTAL);
                    TextView Title = new TextView(context);
                        LinearLayout.LayoutParams TiltleLayoutParams = new LinearLayout.LayoutParams(calcWithFromDesign(500),calcHeightFromDesign(70));
                        TiltleLayoutParams.setMargins(calcWithFromDesign(40),calcHeightFromDesign(55),0,0);
                        Title.setLayoutParams(TiltleLayoutParams);
                        String titleText = getText(R.string.Search_Classroom_Title).toString();
                        Title.setText(titleText);
                        Title.setTextSize(makeTextFit(calcWithFromDesign(500),titleText));
                        Title.setTypeface(ResourcesCompat.getFont(context, R.font.roboto_bold)); //<-- https://stackoverflow.com/questions/14343903/what-is-the-equivalent-of-androidfontfamily-sans-serif-light-in-java-code
                        Title.setTextColor(getResources().getColor(R.color.white));

                    topBar.addView(Title);

                    ImageView close = new ImageView(context);
                        close.setImageResource(R.drawable.close_image);
                        close.setColorFilter(ContextCompat.getColor(context,R.color.white));
                        LinearLayout.LayoutParams closeLayoutParams = new LinearLayout.LayoutParams(calcWithFromDesign(80),calcHeightFromDesign(80));
                        closeLayoutParams.setMargins(calcWithFromDesign(220),calcHeightFromDesign(55),0,0);
                        close.setLayoutParams(closeLayoutParams);
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                    topBar.addView(close);

                pop.addView(topBar,calcWithFromDesign(900),calcHeightFromDesign(200));

                LinearLayout middleBar= new LinearLayout(context);
                    middleBar.setOrientation(LinearLayout.VERTICAL);
                    middleBar.setGravity(Gravity.CENTER_HORIZONTAL);

                        //selectors get made
                            String passedInstituteID;
                            try { passedInstituteID = getIntent().getStringExtra("InstituteID"); } catch (Exception e){ System.out.println(e); passedInstituteID = null;}

                            if (passedInstituteID == null) {
                                String[] institutes = db.getInstitutes();
                                for (int i = 0; i < institutes.length; i++) {
                                    String[] institute_info = db.getInstituteInfo(institutes[i]);
                                    if (institute_info[1].equals("CMI")) {
                                        passedInstituteID = institute_info[3];
                                    }
                                }
                            }

                            String[] buildings=  db.getFloorplansByInstitute(passedInstituteID);



                            Spinner floorSelector = new Spinner(context);
                                updateFloorSpinner(floorSelector,buildings[0],buildings);


                            Spinner buidlingSelector = new Spinner(context);

                                String[] arraySpinner = buildings;
                                buidlingSelector.getBackground().mutate().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);//<--https://stackoverflow.com/questions/24677414/how-to-change-line-color-in-edittext
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, arraySpinner);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                buidlingSelector.setAdapter(adapter);

                                

                    //put it inside the layout

                        float textSize=makeTextFit(calcWithFromDesign(300),getMaxText(getResources().getString(R.string.buidling)+':',getResources().getString(R.string.floor)+":",captFirstLetter(getResources().getString(R.string.classroom))+":"));

                        LinearLayout.LayoutParams layoutParamsSpinners= new LinearLayout.LayoutParams(calcWithFromDesign(250), ViewGroup.LayoutParams.WRAP_CONTENT);

                        LinearLayout buidling = new LinearLayout(context);
                            buidling.setOrientation(LinearLayout.HORIZONTAL);


                            TextView buidlingTitle = new TextView(context);
                                buidlingTitle.setText(captFirstLetter(getResources().getString(R.string.buidling))+":");
                                buidlingTitle.setTextColor(getResources().getColor(R.color.white));
                                buidlingTitle.setTextSize(textSize);
                                LinearLayout.LayoutParams buidlingTitleparams= giveLayoutFieldParams();
                                buidlingTitleparams.setMargins(0,0,0,calcHeightFromDesign(20));
                                buidlingTitle.setLayoutParams(buidlingTitleparams);
                                buidlingTitle.setGravity(Gravity.RIGHT);

                            buidling.addView(buidlingTitle);
                                buidlingSelector.setLayoutParams(layoutParamsSpinners);
                            buidling.addView(buidlingSelector);

                        LinearLayout floor=new LinearLayout(context);

                            floor.setOrientation(LinearLayout.HORIZONTAL);
                            TextView floorTitle = new TextView(context);
                                floorTitle.setText(captFirstLetter(getResources().getString(R.string.floor))+":");
                                floorTitle.setTextSize(textSize);
                                floorTitle.setTextColor(getResources().getColor(R.color.white));
                                floorTitle.setLayoutParams(giveLayoutFieldParams());
                                floorTitle.setGravity(Gravity.RIGHT);
                            floor.addView(floorTitle);
                                floorSelector.setLayoutParams(layoutParamsSpinners);
                                floorSelector.getBackground().mutate().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);//<--https://stackoverflow.com/questions/24677414/how-to-change-line-color-in-edittext
                            floor.addView(floorSelector);

                        LinearLayout room = new LinearLayout(context);
                            room.setOrientation(LinearLayout.HORIZONTAL);


                            TextView roomTitle = new TextView(context);
                                roomTitle.setText(captFirstLetter(getResources().getString(R.string.classroom))+":");
                                roomTitle.setLayoutParams(giveLayoutFieldParams());
                                roomTitle.setTextSize(textSize);
                                roomTitle.setGravity(Gravity.RIGHT);
                                roomTitle.setTextColor(getResources().getColor(R.color.white));
                            room.addView(roomTitle);


                            warning=new TextView(context);
                                LinearLayout.LayoutParams waringParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                waringParams.setMargins(0,calcHeightFromDesign(40),0,0);
                                warning.setLayoutParams(waringParams);
                                warning.setText(captFirstLetter(getResources().getString(R.string.Classroom_not_highlighted).toString()));
                                warning.setGravity(Gravity.CENTER_HORIZONTAL);
                                warning.setTextColor(Color.parseColor("#FFAC52"));
                                warning.setTextSize(makeTextFit(calcWithFromDesign(800),warning.getText().toString().split("\\n").toString()));

                             roomEdit = new EditText(context);
                                roomEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
                                roomEdit.setHint(captFirstLetter(getResources().getString(R.string.roomnumber)));
                                roomEdit.setHintTextColor(Color.parseColor("#B5FFFFFF"));
                                roomEdit.getBackground().mutate().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);//<--https://stackoverflow.com/questions/24677414/how-to-change-line-color-in-edittext
                                roomEdit.setTextColor(getResources().getColor(R.color.white));
                                LinearLayout.LayoutParams roomEditParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                roomEditParams.setMargins(0, 0,0,0);
                                roomEdit.setLayoutParams(roomEditParams);
                                roomEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
                                roomEdit.setOnKeyListener(new View.OnKeyListener() {
                                    @Override
                                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                                        updateWarning(warning,roomEdit,buidlingSelector,floorSelector);
                                        return false;
                                    }
                                });

                                roomEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                    @Override
                                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                        int lengte=3-v.getText().toString().length();
                                        for (int i=lengte;i>0;i--){
                                            v.setText("0"+v.getText());

                                        }
                                        return true;
                                    }
                                });

                            room.addView(roomEdit);


                            buidlingSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    updateWarning(warning, roomEdit, buidlingSelector, floorSelector);
                                    updateFloorSpinner(floorSelector,buidlingSelector.getSelectedItem().toString(),buildings);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            floorSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    updateWarning(warning, roomEdit, buidlingSelector, floorSelector);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


                        Button search = new Button(context);
                            LinearLayout.LayoutParams searchLayoutParams=new LinearLayout.LayoutParams(calcWithFromDesign(450), ViewGroup.LayoutParams.WRAP_CONTENT);
                            searchLayoutParams.setMargins(0,calcHeightFromDesign(60),0,0);
                            search.setLayoutParams(searchLayoutParams);
                            search.setTextSize(makeTextFit(calcWithFromDesign(160),captFirstLetter(getResources().getString(R.string.search))));
                            search.setBackgroundColor(getResources().getColor(R.color.light_grey));
                            search.setText(captFirstLetter(getResources().getString(R.string.search)));
                            search.setGravity(Gravity.CENTER);
                            search.setTextColor(getResources().getColor(android.R.color.black));
                            search.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String tekst=roomEdit.getText().toString();
                                    int lengte=3-tekst.length();
                                    for (int i=lengte;i>0;i--){
                                        tekst="0"+tekst;

                                    }


                                    Intent searchRoomIntent = new Intent(context,map_activity.class);
                                    String buidling =buidlingSelector.getSelectedItem().toString();
                                    int floor=Integer.parseInt(floorSelector.getSelectedItem().toString());
                                    String room=tekst;


                                    String rawString = buidling+"."+floor+"."+room;

                                    searchRoomIntent.putExtra("building",buidling);
                                    searchRoomIntent.putExtra("floor",Integer.parseInt(floorSelector.getSelectedItem().toString()));
                                    searchRoomIntent.putExtra("rawString",rawString);
                                    startActivity(searchRoomIntent);
                                    finish();
                                }
                            });







                    middleBar.addView(buidling);
                    middleBar.addView(floor);
                    middleBar.addView(room);
                    middleBar.addView(warning);
                    middleBar.addView(search);


                pop.addView(middleBar,calcWithFromDesign(900),calcHeightFromDesign(910));

                LinearLayout bottomBar=new LinearLayout(context);
                    bottomBar.setBackground(getDrawable(R.drawable.onderkant));
                pop.addView(bottomBar,calcWithFromDesign(900),calcHeightFromDesign(195));



        }


    }
}