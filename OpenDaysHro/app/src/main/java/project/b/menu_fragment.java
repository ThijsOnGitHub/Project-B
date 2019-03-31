package project.b;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class menu_fragment extends Fragment {
    public void swapPage(View v) {
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
