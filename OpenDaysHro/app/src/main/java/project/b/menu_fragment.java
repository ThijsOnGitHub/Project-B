package project.b;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
public class menu_activity extends appHelper {

    LayoutHelper layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        layout = new LayoutHelper(this,true);

        Intent swapToHome = new Intent(this,MainActivity.class); swapToHome.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Intent swapToEducation = new Intent(this,educations_activity.class); swapToEducation.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Intent swapToMap = new Intent(this,map_activity.class); swapToMap.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Intent swapToOpenDays = new Intent(this,opendays_activity.class); swapToOpenDays.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Intent swapToContact = new Intent(this,contact_activity.class); swapToContact.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        Intent about_cmi = new Intent(getBaseContext(), educations_activity.class);

        Intent[] myIntents = new Intent[]{swapToHome,swapToEducation,about_cmi,swapToContact};
        int[] images = new int[]{R.drawable.ic_home_white_24dp,R.drawable.ic_location_city_white_24dp,R.drawable.ic_map_white_24dp,R.drawable.ic_chat_white_24dp};
        String[] text = new String[]{"home","educations","About CMI","Contact"};

        layout.generate_menu(R.id.page_container,images,text,myIntents);
    }

}
 */
public class menu_fragment extends Fragment implements View.OnClickListener{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_menu, container, false);
        view.findViewById(R.id.menuItem_homepage).setOnClickListener(this);
        view.findViewById(R.id.menuItem_education).setOnClickListener(this);
        view.findViewById(R.id.menuItem_map).setOnClickListener(this);
        view.findViewById(R.id.menuItem_openDays).setOnClickListener(this);
        view.findViewById(R.id.menuItem_contact).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent swapToHome = new Intent(getActivity(), MainActivity.class);
        swapToHome.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Intent swapToEducation = new Intent(getActivity(), educations_activity.class);
        swapToEducation.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Intent swapToMap = new Intent(getActivity(), map_activity.class);
        swapToMap.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Intent swapToOpenDays = new Intent(getActivity(), opendays_activity.class);
        swapToOpenDays.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Intent swapToContact = new Intent(getActivity(), contact_activity.class);
        swapToContact.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        switch (v.getId()) {
            case R.id.menuItem_homepage:
                startActivity(swapToHome);
                break;
            case R.id.menuItem_education:
                startActivity(swapToEducation);
                break;
            case R.id.menuItem_map:
                startActivity(swapToMap);
                break;
            case R.id.menuItem_openDays:
                startActivity(swapToOpenDays);
                break;
            case R.id.menuItem_contact:
                startActivity(swapToContact);
                break;
        }
    }
}
