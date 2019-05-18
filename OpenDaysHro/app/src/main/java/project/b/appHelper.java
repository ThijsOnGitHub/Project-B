package project.b;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Outline;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

import static java.security.AccessController.getContext;

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

        int menuColor = R.color.dark_grey;

        public LayoutHelper(Context context){
            this.context = context;
            this.imageCounter = 0;
            this.metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            this.phone_width = metrics.widthPixels;
            this.phone_height = metrics.heightPixels;
            this.default_margin = (int) ( (float) 2.4 * (float)( (float) phone_width / (float) 100) );
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
                info_button.setBackground(getDrawable(R.drawable.calendar_icon));


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


        public void generate_study_program_menu(int addToThisLayout, int[] List_with_images, String[] List_with_text){

            /*
             https://stackoverflow.com/questions/8833825/error-getting-window-size-on-android-the-method-getwindowmanager-is-undefined

             Getting the screen size. In case of an oneplus 6 it is width: 1080, height: 2200.
            */

            int max_ammount_of_buttons_in_a_row = 2;


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

                        String this_button_text = List_with_text[i];
                        Button.isClickable();
                        Button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent gotoPage = new Intent(context, educations_activity.class);
                                    gotoPage.putExtra("NAME", this_button_text);
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

                        String this_button_text = List_with_text[i];
                        Button.isClickable();
                        Button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent gotoPage = new Intent(context, educations_activity.class);
                                    gotoPage.putExtra("NAME", this_button_text);
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


        public void generate_page_study_programs(int Image, String Text, int addViewTo){
            // The text now is static. In the future this will be fetched from the db. The string Text is for the title which will be passed on with the intent.

            String content = "(Computer Science) Fulltime study:\n" +
                    "\n" +
                    "(This course is also available as a part-time course)\n" +
                    "\n" +
                    "We will train you into becoming a software engineer. You’ll be able to work on software with a variety of purposes. You’ll become capable of analyzing, designing and implementing complex IT-systems.\n" +
                    "\n" +
                    "Software can be found al around us. You are the specialist in making big and complex software information which work quickly, efficiently and safely. For example, you could think of developing a functional app, but you could also think of analyzing large quantities of data form social media. When developing these systems, you will come to know various programming languages. For instance, you will be programming using python and Java/C# during your first year. Starting from your second year, you will be able to choose which language you wish to use. Besides the programming and developing of various applications you will also learn to cooperate with various people on a project.\n" +
                    "\n" +
                    "After completing the course, you will be widely employable within the discipline. From functions such as data engineer, software developer and software engineer you’ll also be able to grow even further.\n" +
                    "\n" +
                    "Are you interested in new technological developments? Are you curious? A little quirky and someone who doesn’t give up easily? Then this course is for you!";

            String[] contentList = new String[]{Text,content};

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
                            LinearLayout.LayoutParams the_image_params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT ));
                                the_image_params.setMargins(margin_horizontal,margin_vertical,margin_horizontal,margin_vertical);
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

        public void generate_page_about_page(int Image, String Title, String Text, int addViewTo){

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
                        startActivity(map);
                    }
                });
                main.addView(button_map);
        }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }
}