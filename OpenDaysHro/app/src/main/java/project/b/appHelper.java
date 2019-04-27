package project.b;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import static java.security.AccessController.getContext;

/*

FUNCTIONS:
- ListItem_openday ( String ListItem_Description, String ListItem_Location, String ListItem_Time , int addToThisLayout)
- generate_study_program_menu (int addToThisLayout, int[] List_with_images, String[] List_with_text)
- generate_page_study_programs (int Image, String Text, int addViewTo)
- generate_menu (int addToThisLayout, int[] List_with_images, String[] List_with_text, Intent[] gotoThisPage)

Status (updated 27-04-2019):
- ListItem_openday: Layout needs to be adjusted
- generate_study_program_menu: need to add support for broken strings (test\ntest)
- generate_page_study_programs: Layout needs to be adjusted
- generate_menu: Layout needs to be adjusted.

*/

public class appHelper extends AppCompatActivity {

    // https://stackoverflow.com/questions/3204852/android-add-a-textview-to-linear-layout-programmatically

    @FunctionalInterface
    private interface calc {
        int xyz(int x, int y, int z);
    }

    public class LayoutHelper {
        Context context;
        boolean enable_onclick;

        int menuColor = R.color.dark_grey;

        public LayoutHelper(Context context, boolean Enable_Onclick){ this.context = context; this.enable_onclick = Enable_Onclick; }






        public void ListItem_openday( String ListItem_Description, String ListItem_Location, String ListItem_Time , int addToThisLayout) {
            LinearLayout LinearLayout_main = new LinearLayout(this.context);
                LinearLayout_main.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout_main.isClickable();
                LinearLayout_main.setBackgroundColor(getResources().getColor(menuColor));
                LinearLayout.LayoutParams LinearLayout_main_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
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

            if (this.enable_onclick == true) {
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
            }

            LinearLayout main = (LinearLayout)findViewById( addToThisLayout );
                ((LinearLayout)main).addView((LinearLayout)LinearLayout_main);

            if (this.menuColor == R.color.light_grey) { this.menuColor = R.color.dark_grey; } else { this.menuColor = R.color.light_grey; }

        }






        public void generate_study_program_menu(int addToThisLayout, int[] List_with_images, String[] List_with_text){

            /*
             https://stackoverflow.com/questions/8833825/error-getting-window-size-on-android-the-method-getwindowmanager-is-undefined

             Getting the screen size. In case of an oneplus 6 it is width: 1080, height: 2200.
            */

            int max_ammount_of_buttons_in_a_row = 2;

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
                int phone_width = metrics.widthPixels;
                int phone_height = metrics.heightPixels;
            calc calculator = (x, y, z) -> (int) ( ( (float) x / (float) y) * (float) z);

            int button_size = calculator.xyz(500, 1080, phone_width);
            int standaard_margin = ((FrameLayout.LayoutParams) findViewById(addToThisLayout).getLayoutParams()).leftMargin;
            int button_margin = calculator.xyz(phone_width, 1080, 20) - (standaard_margin / max_ammount_of_buttons_in_a_row);
                if (button_margin < 0) { button_margin = 0; } // prevent crashing (not needed so far)

            // picture_margin = int[]{left,top,right,bottom}
            int[] picture_margin = new int[] {calculator.xyz(90, 500, button_size), calculator.xyz(30, 500, button_size), calculator.xyz(90, 500, button_size), calculator.xyz(10, 500, button_size)};

            System.out.println(button_size + "  " + button_margin + "   { " + picture_margin[0] + ", " + picture_margin[1] + ", " + picture_margin[2] + ", " + picture_margin[3] + " }");

            int ammountOfItems;
            if (List_with_images.length < List_with_text.length) { ammountOfItems = List_with_images.length; } else { ammountOfItems = List_with_text.length; }

            LinearLayout main = (LinearLayout) findViewById(addToThisLayout);
                main.setOrientation(LinearLayout.VERTICAL);
            LinearLayout Main_layout = new LinearLayout(this.context);
                Main_layout.setOrientation(LinearLayout.VERTICAL);

            String longest_string;
            int Chars = 0;

            for ( int counter = 0; counter < List_with_text.length; counter++ ) {
                if ( List_with_text[counter].length() > Chars ) { longest_string = List_with_text[counter]; Chars = List_with_text[counter].length(); }
            }

            // Technische Informatica = 22 char. textSize = 17 for 500 width (22 char). textSize = 22 for 500 width (12 char).
            calc text_size_calculator_backup = (x, y, z) -> (int) ( ( ( (float) z - ( (float) x / 2 ) ) / (float) 500) * (float) y );
            calc text_size_calculator = (x, y, z) -> (int) ( (float) ( (float) ( ( ( (float) z - ( (float) x / 2 ) ) / (float) 500) * (float) y ) / (float) metrics.density ) * (float) 2.625 );

            int textSize_backup = (int) ( (float) ( (float) text_size_calculator.xyz(Chars, button_size, 28) / (float) metrics.density ) * (float) 2.625 );
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

            DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                int phone_width = metrics.widthPixels;
                int phone_height = metrics.heightPixels;

            int header_height = (int) ( (float) phone_height / (float) 3.5 );

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
                this_page_text.setLayoutParams(this_page_text_lp);

            for (int x = 0; x < contentList.length; x++) {
                TextView this_text = new TextView(this.context);
                this_text.setText(contentList[x]);
                this_page_text.addView(this_text);
            }
            LinearLayout main = (LinearLayout) findViewById(addViewTo);
                this_page.addView(this_page_header);
                this_page.addView(this_page_text);
                main.addView(this_page);
        }

        public void generate_menu(int addToThisLayout, int[] List_with_images, String[] List_with_text, Intent[] gotoThisPage){

            DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
            calc calculator = (x, y, z) -> (int) ( ( (float) x / (float) y ) * (float) z);
            int phone_width = metrics.widthPixels;
            int phone_height = metrics.heightPixels;
            int button_size = calculator.xyz(1, List_with_images.length, phone_width);

            int ammountOfItems;
            if (List_with_images.length < List_with_text.length) { ammountOfItems = List_with_images.length; } else { ammountOfItems = List_with_text.length; }

            LinearLayout main = (LinearLayout) findViewById(addToThisLayout);
            main.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout Main_layout = new LinearLayout(this.context);
            Main_layout.setOrientation(LinearLayout.HORIZONTAL);

            String longest_string;
            int Chars = 0;

            for ( int counter = 0; counter < List_with_text.length; counter++ ) {
                if ( List_with_text[counter].length() > Chars ) { longest_string = List_with_text[counter]; Chars = List_with_text[counter].length(); }
            }

            calc text_size_calculator = (x, y, z) -> (int) ( (float) ( (float) ( ( ( (float) z - ( (float) x / 2 ) ) / (float) 500) * (float) y ) / (float) metrics.density ) * (float) 2.625 );

            int textSize = text_size_calculator.xyz(Chars, button_size, 30);
            System.out.println(metrics.density + "     " + textSize);

            for (int i = 0; i < List_with_images.length; i++){
                LinearLayout Button = new LinearLayout(this.context);
                    Button.setOrientation(LinearLayout.VERTICAL);
                    Button.setBackgroundColor(getResources().getColor(R.color.hro_red));
                    LinearLayout.LayoutParams btnSize = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(button_size, button_size));
                        btnSize.setMargins(0,0,0,0);
                        Button.setLayoutParams(btnSize);
                    RelativeLayout button_image = new RelativeLayout(this.context);
                        button_image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
                        button_image.setGravity(Gravity.CENTER);
                        LinearLayout the_image = new LinearLayout(this.context);
                            LinearLayout.LayoutParams the_image_params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT ));
                                the_image.setLayoutParams(the_image_params);
                            the_image.setBackground(getDrawable(List_with_images[i]));
                        button_image.addView(the_image);
                    RelativeLayout button_text = new RelativeLayout(this.context);
                        button_text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 2));
                        button_text.setGravity(Gravity.CENTER);
                        TextView text = new TextView(this.context);
                            text.setText(List_with_text[i]);
                            text.setTextSize(textSize);
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
    }
}