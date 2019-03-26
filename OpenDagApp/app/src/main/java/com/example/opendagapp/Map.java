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
import android.widget.ImageView;


//rebuilt after https://stackoverflow.com/questions/16369814/how-to-access-the-drawable-resources-by-name-in-android
class DrawableManager{
    private static Context context = null;
    private static Resources resources;

    public DrawableManager(Context c) {
        context = c;
        resources=context.getResources();
    }

    //By myself
    public static int getID(String name){
        int resourceId = resources.getIdentifier(name, "drawable",
                context.getPackageName());
        return resourceId;
    }

    public static boolean nameExist(String name){
        if (getID(name)==0){
            return false;
        }else{
            return true;
        }
    }
}

class floorManager{
    private DrawableManager getPic;
    private String[] buildingsList= new String[] {"h99","wd103","wn107"};
    private String building=buildingsList[0];
    private int buildingNum=0;
    private int floorNumber = 2;
    private ImageView showFloor;

    public floorManager(Context c,ImageView showFloor ){
        getPic= new DrawableManager(c);
        this.showFloor= showFloor;
        updateImage();
    }

    public void changeBuilding(int amount){
        buildingNum= stayBetweenIncl(0,2,buildingNum+amount);
        building=buildingsList[buildingNum];
        updateImage();
    }

    public void floorUp(){
        floorNumber+=1;
        updateImage();
    }


    private String createName(int floorNumber){
        return building+Integer.toString(floorNumber)+"e";
    }

    private String createName(){
        return building+Integer.toString(floorNumber)+"e";
    }

    public void updateImage(){
        String name = createName();
        int id=getPic.getID(name);
        boolean test = R.drawable.h993e==id;
        showFloor.setImageResource(id);

    }

    public void updateImage(int Id){
        String name = createName();
        int id=getPic.getID(name);
        boolean test = R.drawable.h993e==id;
        showFloor.setImageResource(id);
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

public class Map extends AppCompatActivity {
    floorManager floor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ImageView showFloor = (ImageView) findViewById(R.id.showFloor);
        floor = new floorManager(this,showFloor);
        showFloor.setImageResource(R.drawable.h990e);
        showFloor.setImageResource(R.drawable.h992e);
    }

    public void clickFloorUp(View v){
    }


        /*
        floor.updateImage();
        floor.updateImage();
        */



}
