package project.b;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*

FUNCTIONS:
1. ListItem_openday ( String ListItem_Description, String ListItem_Location, String ListItem_Time , int addToThisLayout)
2. workshop( String ListItem_Description, String ListItem_Location, String ListItem_Time , int addToThisLayout)
3. generate_study_program_menu (int addToThisLayout, int[] List_with_images, String[] List_with_text)
4. generate_page_study_programs (int Image, String Text, int addViewTo)
5. generate_menu (int addToThisLayout, int[] List_with_images, String[] List_with_text, Intent[] gotoThisPage)
6. Image_with_Buttons (int addToThisLinearLayout, int[] images)
7. calendar_page(int addToThisLayout, int Image, int Calendar_Image,
                                  int Share_Image, String EVENT_TITLE, String EVENT_DESCRIPTION, String EVENT_LOCATION,
                                  int EVENT_YEAR, int EVENT_MONTH, int EVENT_DAY, int EVENT_START_HOUR, int EVENT_START_MINUTE,
                                  int EVENT_END_HOUR, int EVENT_END_MINUTE)

Status (updated 05-05-2019):
1. ListItem_openday : Text needs to be scaled and the real info image needs to be implemented.
2. workshop : Text needs to be scaled and the timer needs to be implemented.
3. generate_study_program_menu : need to add support for broken strings (test\ntest) and need to break them if they are larger than x.
4. generate_page_study_programs: Layout needs to be adjusted. also the image needs to has the centered logo and text.
5. generate_menu : done.
6. Image_with_Buttons : done.
7. calendar_page : scaling for the workshops and need to make the share button working. also need to test if the reminder exists / add the reminder.

*/

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


        public void ListItem_openday( String ListItem_Description, String ListItem_Location, String ListItem_Time , int addToThisLayout) {

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


            final String[] infoToPass = {ListItem_Description, ListItem_Location, ListItem_Time};
            LinearLayout_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gotoOpenDay_activity = new Intent(context, opendays_activity.class);
                    gotoOpenDay_activity.putExtra("INFO", infoToPass);
                    gotoOpenDay_activity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(gotoOpenDay_activity);
                }
            });


            LinearLayout main = (LinearLayout)findViewById( addToThisLayout );
                ((LinearLayout)main).addView((LinearLayout)LinearLayout_main);

            if (this.menuColor == R.color.light_grey) { this.menuColor = R.color.dark_grey; } else { this.menuColor = R.color.light_grey; }

        }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        public void workshop_menu( String ListItem_Description, String ListItem_Location, String ListItem_Time , int addToThisLayout) {

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


            final String[] infoToPass = {ListItem_Description, ListItem_Location, ListItem_Time, "page1"};
            LinearLayout_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gotoOpenDay_activity = new Intent(context, opendays_activity.class);
                    gotoOpenDay_activity.putExtra("INFO", infoToPass);
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
                listItem_Location.setText(ListItem_Location); listItem_description.setGravity(Gravity.CENTER);
                listItem_Location.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

            TextView listItem_Time = new TextView(this.context);
                listItem_Time.setText(ListItem_Time); listItem_description.setGravity(Gravity.CENTER);
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
            // The text now is static. In the future this will be fetched from the db. The string Text is for the title which will be passed on with the intent.

            String[] study = this.db.getStudyInfo(ID);

            String study_name = study[2];
            String study_information = study[3];

            String[] contentList = new String[]{study_name,study_information};

            int header_height = (int) ( (float) phone_height / (float) 3.5 );
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
                                  int EVENT_END_HOUR, int EVENT_END_MINUTE){

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
                page.setOrientation(LinearLayout.VERTICAL);
                page.setLayoutParams(page_params);
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
                        addToCalendar_TextView.setText("Add to\ncalendar"); addToCalendar_TextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
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
                            startActivity(share_intent);
                        }
                    });

            LinearLayout share_text = new LinearLayout(this.context);
                share_text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1));
                share_text.setGravity(Gravity.CENTER);
                TextView share_TextView = new TextView(this.context);
                    share_TextView.setText("Share"); share_TextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
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
                button_map.setBackgroundColor(getResources().getColor(R.color.light_grey));
                LinearLayout.LayoutParams button_map_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(phone_height / 10));
                    button_map.setGravity(Gravity.CENTER); button_map.setLayoutParams(button_map_params);
                LinearLayout image_map = new LinearLayout(this.context);
                    LinearLayout.LayoutParams image_map_params = new LinearLayout.LayoutParams((phone_height / 10),(phone_height / 10));
                        image_map.setLayoutParams(image_map_params);
                    image_map.setBackground(getDrawable(R.drawable.ic_map_white_24dp));
                    button_map.addView(image_map);
                button_map.isClickable();
                button_map.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent map = new Intent(context,map_activity.class);
                        map.putExtra("InstituteID", institute_id);
                        startActivity(map);
                    }
                });
                main.addView(button_map);
        }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        public void popup(int addToThisLayout, int width, int height){

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

                    twitter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                Intent twitterintent = new Intent(Intent.ACTION_SEND);
                                    twitterintent.setType("text/plain");
                                    twitterintent.putExtra(android.content.Intent.EXTRA_TEXT, "Check out our new app!");
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
                            String fb_url = "https://www.facebook.com/sharer/sharer.php?u=https%3A%2F%2Fwww.hogeschoolrotterdam.nl%2F&quote=Hi!%20Check%20out%20our%20open%20day!";
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
                                    whatsappintent.putExtra(android.content.Intent.EXTRA_TEXT, "Check out our new app!");
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
                                    emailIntent.putExtra(Intent.EXTRA_TEXT, "This is the default message everyone wants to send.");
                                    emailIntent.setPackage("com.microsoft.office.outlook");
                                    startActivity(emailIntent);
                            } catch (Exception e){
                                try {
                                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                                        emailIntent.setType("message/rfc822");    //<--https://stackoverflow.com/questions/8701634/send-email-intent
                                        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@hr.nl"}); // recipients
                                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Openday HRO");
                                        emailIntent.putExtra(Intent.EXTRA_TEXT, "This is the default message everyone wants to send.");
                                        emailIntent.setPackage("com.google.android.gm");
                                        startActivity(emailIntent);
                                } catch (Exception f){
                                    // in case outlook and gmail are not installed you can select another app.
                                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                                    emailIntent.setType("message/rfc822");    //<--https://stackoverflow.com/questions/8701634/send-email-intent
                                        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@hr.nl"}); // recipients
                                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Openday HRO");
                                        emailIntent.putExtra(Intent.EXTRA_TEXT, "This is the default message everyone wants to send.");
                                        startActivity(emailIntent);
                                }
                            }
                        }
                    });
                    horizontal2.addView(email);
                pop.addView(horizontal2);


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
        private int calcHeightFromDesign(float elementHeight){
            float designHeight= 1080.f;
            return (int)((elementHeight*phone_height)*designHeight);
        }

        private int calcWithFromDesign(float elementWidth){
            float designWidth= 1920.f;
            return (int)((elementWidth*phone_width)*designWidth);
        }

        private int calcTextSizeInt(float default_text_size,float text_length,float elementWidth){
            return (int) ( (float) ( (float) ( (float) default_text_size - ( (float) text_length / 2 ) ) * (float) ((float) phone_width / (float) elementWidth) / (float) metrics.density ) * (float) 2.625 );
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
                            System.out.println("heloo");
                            confirmContactForm(inputFields[0],inputFields[1],inputFields[2],inputFields[3]);
                        }
                    });
                linearLayout.addView(confirm);
            layout.addView(linearLayout);
        }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
}