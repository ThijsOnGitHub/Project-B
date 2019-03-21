package com.example.opendagapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Map extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //https://www.youtube.com/watch?v=SLFrwl1hFcw
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ListView floorChooser = (ListView)findViewById(R.id.Listview_Floors_Floor_Chooser);
        reloadFloors(7,floorChooser);


    }

    public void reloadFloors(int amountOfFloors,ListView floorChooser) {
        ArrayList<String> floors = new ArrayList<>();
        for (int i = amountOfFloors; i >-1; i--) {
            floors.add(Integer.toString(i));
        }
        ArrayAdapter adapter= new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,floors);
        floorChooser.setAdapter(adapter);
    };

}
