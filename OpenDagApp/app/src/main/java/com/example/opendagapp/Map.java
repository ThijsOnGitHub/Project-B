package com.example.opendagapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.FileNotFoundException;

public class Map extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ImageView showFloor = findViewById(R.id.showFloor);



    }
        /*

        Version 2
        //https://www.youtube.com/watch?v=SLFrwl1hFcw

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
*/
}
