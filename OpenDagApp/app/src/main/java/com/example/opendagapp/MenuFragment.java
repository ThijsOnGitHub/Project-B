package com.example.opendagapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;



public class MenuFragment<on> extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_menu, container, false);
        BottomNavigationView Menu = (BottomNavigationView) view.findViewById(R.id.Menu);
        Menu.setOnNavigationItemSelectedListener(this);
        return view;
    }

    //This helped me: https://www.youtube.com/watch?v=jpaHMcQDaDg
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent= new Intent(getActivity(),getClass());
        switch (menuItem.getItemId()){
            case R.id.Homepage:
                intent=new Intent(getActivity(),Homepage.class);
                break;
            case R.id.Education:
                intent=new Intent(getActivity(),Education.class);
                break;
            case R.id.OpenDay:
                intent= new Intent(getActivity(),OpenDays.class);
                break;
            case R.id.Map:
                intent= new Intent(getActivity(),Map.class);
                break;
            case R.id.Contact:
                intent= new Intent(getActivity(),Map.class);
                break;
        }
        startActivity(intent);
        //
        getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        return false;
    }

}
