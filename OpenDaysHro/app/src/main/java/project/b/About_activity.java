package project.b;



import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.content.Intent;

public class About_activity extends appHelper {

    DatabaseHelper myDatabase;
    LayoutHelper layout;

    String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed a sapien in augue rutrum hendrerit aliquet quis dolor. Etiam augue sapien, euismod eu ex in, consequat fringilla nisl. Maecenas lacinia nulla non nulla dictum, a ornare lorem dignissim. Ut sit amet mollis purus, ac egestas lectus. Proin varius, sapien nec dignissim facilisis, justo tortor tempor est, a cursus est nunc tristique velit. Ut dignissim laoreet tellus at tempus. Fusce vitae suscipit eros. Proin lacinia mauris euismod purus commodo euismod. In hac habitasse platea dictumst. Cras sem turpis, suscipit sed nunc nec, feugiat facilisis eros.\n" +
            "\n" +
            "Duis a odio porta, tempor nisl eu, fringilla mi. In cursus quis lorem id sagittis. Duis tincidunt consequat tellus. Phasellus sit amet est arcu. Maecenas viverra elementum sapien, in fringilla augue suscipit non. Quisque porta semper facilisis. Nam lacinia dapibus dapibus. Ut pharetra a quam at pharetra. Proin sit amet sodales dolor, id sodales dui. Vestibulum pretium convallis vulputate. Donec scelerisque maximus lacus, quis feugiat libero porta ut. Fusce vestibulum felis non lacus sodales, eu laoreet eros imperdiet. Phasellus facilisis congue diam, quis lacinia eros eleifend quis. Donec et risus suscipit, porttitor quam nec, commodo sapien. Aliquam sollicitudin ac ligula et ultrices. Pellentesque massa lectus, finibus non accumsan id, auctor eget elit.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        layout = new LayoutHelper(this);


        layout.generate_page_about_page(R.drawable.blaak,"CMI",lorem,R.id.page_container);

        Intent home = new Intent(getBaseContext(), MainActivity.class);
        Intent educations = new Intent(getBaseContext(), educations_activity.class);
        Intent about_cmi = new Intent(getBaseContext(), About_activity.class);
        Intent contact = new Intent(getBaseContext(), contact_activity.class);

        Intent[] myIntents = new Intent[]{home,educations,about_cmi,contact};
        int[] images = new int[]{R.drawable.ic_home_white_24dp,R.drawable.baseline_school_24px,R.drawable.ic_location_city_grey_24dp,R.drawable.ic_chat_white_24dp};
        String[] text = new String[]{"home","Study programs","About CMI","Contact"};

        layout.generate_menu(R.id.menu_bar,images,text,myIntents);

    }

}
