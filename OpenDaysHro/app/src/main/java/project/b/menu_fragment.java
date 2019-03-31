package project.b;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;


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
