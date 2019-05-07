package project.b;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

class DrawableManager{
    private static Context context = null;
    private static Resources resources;

    public DrawableManager(Context c) {
        context = c;
        resources=context.getResources();
    }

    //By myself
    public int getID(String name){
        int resourceId = resources.getIdentifier(name, "drawable",
                context.getPackageName());
        return resourceId;
    }

    public boolean nameExist(String name){
        if (getID(name)==0){
            return false;
        }else{
            return true;
        }
    }
}


class AttributePack{
    public Button upBut,downBut,leftBut,rightBut;
    ImageView showFloor;
    TextView floorIndicator;

    public AttributePack(ImageView showFloorInvoer ,TextView floorIndicatorinvoer,Button upButton,Button downButton,Button leftButton,Button rightButton){
        showFloor=showFloorInvoer;
        floorIndicator=floorIndicatorinvoer;
        upBut=upButton;
        downBut=downButton;
        leftBut=leftButton;
        rightBut=rightButton;
    }
}

class mapManager{
    public String[] buildingsList= new String[] {"h107","wd103","wn99"};
    public String building=buildingsList[0];
    public int buildingNum=0;
    public int floor=0;
    public Context context;
    public DrawableManager getPic;


    public Button upButton,downButton,leftButton,rightButton;
    ImageView showFloor;
    TextView floorIndicator;

    //,Button upBut,Button downBut,Button leftBut,Button rightBut
    public mapManager(Context c,AttributePack attributes){
        getPic = new DrawableManager(c);
        context=c;
        showFloor=attributes.showFloor;
        floorIndicator=attributes.floorIndicator;
        upButton=attributes.upBut;
        downButton=attributes.downBut;
        leftButton=attributes.leftBut;
        rightButton=attributes.rightBut;
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
        updateVisabilatyButton();
        showFloor.setImageResource(0);
        int id=getPic.getID(createName());
        showFloor.setImageResource(id);
        floorIndicator.setText(createName()+"\n");
    }

    public void updateVisabilatyButton(){
        setVisable(upButton,downButton,leftButton,rightButton);
        if(!checkFloorExist(1,0)){
            setInvisible(upButton);
        }
        if(!checkFloorExist(-1,0)){
            setInvisible(downButton);
        }
        if(!checkFloorExist(0,-1)){
            setInvisible(leftButton);
        }
        if(!checkFloorExist(0,1)){
            setInvisible(rightButton);
        }
    }

    private boolean checkFloorExist(int amountOfFloorHigher, int amountOfBuildingRight){
        if (buildingNum+amountOfBuildingRight<0 ||buildingNum+amountOfBuildingRight>buildingsList.length-1){
            return false;
        }
        return getPic.nameExist(createName(floor+amountOfFloorHigher,buildingsList[buildingNum+amountOfBuildingRight]));
    }

    private void setInvisible(Button... buttons){
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setVisibility(View.INVISIBLE);
        }
    }

    private void setVisable(Button... buttons){
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setVisibility(View.VISIBLE);
        }
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


public class map_activity extends appHelper {
    mapManager floor;
    LayoutHelper layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ImageView showFloor = findViewById(R.id.ImageView_showFloor);
        AttributePack attributes = new AttributePack(showFloor, (TextView) findViewById(R.id.TextView_FloorIndicator), (Button) findViewById(R.id.Button_FloorUp), (Button) findViewById(R.id.Button_FloorDown), (Button) findViewById(R.id.Button_BuildingLeft), (Button) findViewById(R.id.Button_BuildingRight));
        floor = new mapManager(this, attributes);

        Intent home = new Intent(getBaseContext(), MainActivity.class);
        Intent educations = new Intent(getBaseContext(), educations_activity.class);
        Intent about_cmi = new Intent(getBaseContext(), About_activity.class);
        Intent contact = new Intent(getBaseContext(), contact_activity.class);

        Intent[] myIntents = new Intent[]{home,educations,about_cmi,contact};
        int[] images = new int[]{R.drawable.ic_home_white_24dp,R.drawable.baseline_school_24px,R.drawable.ic_location_city_white_24dp,R.drawable.ic_chat_white_24dp};
        String[] text = new String[]{"home","Study programs","About CMI","Contact"};

        layout = new LayoutHelper(this);
        layout.generate_menu(R.id.menu_bar,images,text,myIntents);
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
