package project.b;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class appHelper extends AppCompatActivity {

    // https://stackoverflow.com/questions/3204852/android-add-a-textview-to-linear-layout-programmatically

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
            int ammountOfItems;
            if (List_with_images.length < List_with_text.length) { ammountOfItems = List_with_images.length; } else { ammountOfItems = List_with_text.length; }

            LinearLayout main = (LinearLayout) findViewById(addToThisLayout);
                main.setOrientation(LinearLayout.VERTICAL);
            LinearLayout Main_layout = new LinearLayout(this.context);
                Main_layout.setOrientation(LinearLayout.VERTICAL);
            for (int i = 0; i < ammountOfItems; i++){

                //zet er 2 naast elkaar
                if (i + 1 < ammountOfItems){
                    i++;
                    LinearLayout horizontal = new LinearLayout(this.context);
                    horizontal.setOrientation(LinearLayout.HORIZONTAL);

                    LinearLayout Button = new LinearLayout(this.context);
                        Button.setBackground(getDrawable(List_with_images[i-1]));
                        LinearLayout.LayoutParams btnSize = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(500, 500));
                            btnSize.setMargins(10,10,10,10);
                            Button.setLayoutParams(btnSize);
                        Button.isClickable();
                        Button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // do stuff
                            }
                        });

                    LinearLayout Button2 = new LinearLayout(this.context);
                        Button2.setBackground(getDrawable(List_with_images[i]));
                        LinearLayout.LayoutParams btn2Size = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(500, 500));
                            btn2Size.setMargins(10,10,10,10);
                            Button2.setLayoutParams(btn2Size);
                        Button2.isClickable();
                        Button2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // do stuff
                            }
                        });

                    horizontal.addView(Button);
                    horizontal.addView(Button2);
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
                        Button.setBackground(getDrawable(List_with_images[i]));
                        LinearLayout.LayoutParams btnSize = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(500, 500));
                            btnSize.setMargins(10,10,10,10);
                            Button.setLayoutParams(btnSize);
                        Button.isClickable();
                        Button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // do stuff
                            }
                        });

                    horizontal.addView(Button);
                    Main_layout.addView(horizontal);
                }
            }
            main.addView(Main_layout);
        }
    }
}