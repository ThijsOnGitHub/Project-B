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
    public String[] buildingsList= new String[] {"h107","wd103","wn99"};
    public String building=buildingsList[0];
    public int buildingNum=0;
    public int floor=0;
    public Context context;
    public ImageView showFloor;
    public TextView testView;
    public DrawableManager getPic;
    public Button upButton,downButton,leftButton,rightButton;

    //,Button upBut,Button downBut,Button leftBut,Button rightBut
    public floorManager(Context c,ImageView showFloorInvoer ,TextView testViewi){
        DrawableManager getPic = new DrawableManager(c);
        context=c;
        showFloor=showFloorInvoer;
        testView=testViewi;
        updateImage();
    }

    public void changeBuilding(int amount){
        if (getPic.nameExist(createName(floor,buildingsList[stayBetweenIncl(0,2,buildingNum+amount)]))) {
            buildingNum = stayBetweenIncl(0, 2, buildingNum + amount);
            building = buildingsList[buildingNum];
            updateImage();
        }
    }

    public void changeFloor(int amount){
        if (getPic.nameExist(createName(floor+amount,building))) {
            floor += amount;
            updateImage();
        }
    }

    public String createName(){
        return building+Integer.toString(floor)+"e";
    }

    public String createName(int floor,String building){
        return building+Integer.toString(floor)+"e";
    }


    public void updateImage(){
        showFloor.setImageResource(0);
        int id=getPic.getID(createName());
        showFloor.setImageResource(id);
        testView.setText(createName()+"\n");
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
        floor.changeFloor(1);
    }

    public void clickDown(View v){
       floor.changeFloor(-1);
    }

    public void clickLeft(View v){
        floor.changeBuilding(-1);
    }

    public void clickRight(View v){
        floor.changeBuilding(1);
    }
}
