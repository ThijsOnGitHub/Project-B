package com.example.opendagapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.zip.Inflater;


public class Map extends AppCompatActivity {

    class floorManager{
        public String[] buildingsList= new String[] {"h99","wd103","wn107"};
        public String building=buildingsList[0];
        public int buildingNum=0;
        public int floor=0;
        public Context context;
        public ImageView floorView;
        public TextView testView;

        public floorManager(Context c,ImageView showFloor ,TextView testViewi){
            context=c;
            floorView=showFloor;
            testView=testViewi;
            updateImage();
        }

        public void changeBuilding(int amount){
            buildingNum= stayBetweenIncl(0,2,buildingNum+amount);
            building=buildingsList[buildingNum];
            updateImage();
        }

        public void floorUp(ImageView showFloor){
            floor+=1;
            updateImage();
        }

        public String createName(){
            return building+Integer.toString(floor)+"e";
        }


        public void updateImage(){
            showFloor.setImageResource(0);
            //https://stackoverflow.com/questions/16369814/how-to-access-the-drawable-resources-by-name-in-android
            int id=context.getResources().getIdentifier(createName(),"drawable",context.getPackageName());
            showFloor.setImageResource(id);
            testView.setText(createName()+"\n"+id);
        }

        private int stayBetweenIncl(int min,int max,int number){
            if (number<min){
                return min;
            }else if (number>max){
                return max;
            }
            return number;
        }
    }

    floorManager floor;
    ImageView showFloor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        showFloor= findViewById(R.id.showFloor);
        TextView testView = findViewById(R.id.TestView);
        floor = new floorManager(this,showFloor,testView);
    }

    public void clickUp(View v){
        floor.floorUp(showFloor);
    }
}
