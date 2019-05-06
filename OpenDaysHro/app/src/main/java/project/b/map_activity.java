package project.b;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    public Button upBut,downBut,leftBut,rightBut,zoomInBut,zoomOutBut,resetZoomBut;
    ImageView showFloor;
    TextView floorIndicator;

    public AttributePack(ImageView showFloorInvoer ,TextView floorIndicatorinvoer,Button upButton,Button downButton,Button leftButton,Button rightButton,Button zoomInButton,Button zoomOutButton,Button resetZoomButton){
        showFloor=showFloorInvoer;
        floorIndicator=floorIndicatorinvoer;
        upBut=upButton;
        downBut=downButton;
        leftBut=leftButton;
        rightBut=rightButton;
        zoomInBut=zoomInButton;
        zoomOutBut=zoomOutButton;
        resetZoomBut=resetZoomButton;
    }
}

class mapManager{
    public String[] buildingsList ;
    public String building;
    public int buildingNum=0;
    public int floor=0;
    public Context context;
    public DrawableManager getPic;



    public Button upButton,downButton,leftButton,rightButton,zoomInButton,zoomOutButton,resetZoomButton;
    ImageView showFloor;
    TextView floorIndicator;
    float scale,startScale,maxscale;
    int movedSpaceX, movedSpaceY;

    //Button upBut,Button downBut,Button leftBut,Button rightBut
    public mapManager(Context c,AttributePack attributes,String[] buildings){
        //elements
        showFloor=attributes.showFloor;
        floorIndicator=attributes.floorIndicator;
        upButton=attributes.upBut;
        downButton=attributes.downBut;
        leftButton=attributes.leftBut;
        rightButton=attributes.rightBut;
        zoomInButton=attributes.zoomInBut;
        zoomOutButton=attributes.zoomOutBut;
        resetZoomButton=attributes.resetZoomBut;

        //vars
        getPic = new DrawableManager(c);
        context=c;
        buildingsList=buildings;
        building=buildingsList[0];
        startScale=1.5f;
        maxscale=8.0f;




        //starting procces
        updateImage();
    }


    public void moveSpace(int movementOnX,int movementOnY){
        movedSpaceX +=movementOnX;
        movedSpaceY +=movementOnY;

        int extraSpaceX=Math.round(((scale-1.5f)/0.5f)*177);
        int extraSpaceY=Math.round(((scale-1.5f)/0.5f)*135);

        movedSpaceX=stayBetweenIncl(extraSpaceX*-1,extraSpaceX,movedSpaceX);
        movedSpaceY=stayBetweenIncl(extraSpaceY*-1,extraSpaceY,movedSpaceY);

        showFloor.scrollTo(movedSpaceX, movedSpaceY);
        updateVisabilatyButtons();
    }


    private void zoomCheck(){
        if(!(scale<=startScale & scale>=maxscale)) {
            showFloor.setScaleX(scale);
            showFloor.setScaleY(scale);
        }else if (scale<=startScale){
            scale=startScale;
        }else{
            scale=maxscale;
        }
        moveSpace(0,0);
        updateVisabilatyButtons();
    }

    public void scaleAdd(float amount){
        scale+=amount;
        zoomCheck();
    }

    public void scaleTimes(float amount){
        scale*=amount;
        zoomCheck();
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
        floorIndicator.setText(createName()+"\n");
        movedSpaceX =0;
        movedSpaceY =0;
        scale= startScale;
        showFloor.setScaleX(scale);
        showFloor.setScaleY(scale);
        showFloor.scrollTo(0,0);
        updateVisabilatyButtons();
    }

    public void updateVisabilatyButtons(){
        setVisable(upButton,downButton,leftButton,rightButton,zoomInButton,zoomOutButton,resetZoomButton);
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
        if(scale<=startScale){
            setInvisible(zoomOutButton);
        }
        if(scale>=maxscale){
            setInvisible(zoomInButton);
        }
        if(scale<=startScale & movedSpaceX==0 & movedSpaceY==0){
            setInvisible(resetZoomButton);
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


public class map_activity extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener, ScaleGestureDetector.OnScaleGestureListener {
    mapManager floor;
    GestureDetector gestureDetector;
    ScaleGestureDetector scaleGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ImageView showFloor = findViewById(R.id.ImageView_showFloor);
        AttributePack attributes = new AttributePack(showFloor, (TextView) findViewById(R.id.TextView_FloorIndicator),
                (Button) findViewById(R.id.Button_FloorUp),
                (Button) findViewById(R.id.Button_FloorDown),
                (Button) findViewById(R.id.Button_BuildingLeft),
                (Button) findViewById(R.id.Button_BuildingRight),
                (Button) findViewById(R.id.button_ZoomIn),
                (Button) findViewById(R.id.button_ZoomOut),
                (Button)findViewById(R.id.button_ResetZoom));
        String[] buildings=new String[]{"h107","wd103","wn99"};
        floor = new mapManager(this, attributes,buildings);
        gestureDetector=new GestureDetector(this,this);
        scaleGestureDetector= new ScaleGestureDetector(this,this);
        showFloor.setOnTouchListener(this);
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

    public void clickZoomIn(View v){
        floor.scaleAdd(0.5f);
    }

    public void clickZoomOut(View v){
        floor.scaleAdd(-0.5f);
    }

    public void clickResetZoom(View v){
        floor.updateImage();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId()==R.id.ImageView_showFloor){
            gestureDetector.onTouchEvent(event);
            scaleGestureDetector.onTouchEvent(event);
            return true;
        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        floor.moveSpace(Math.round(distanceX),Math.round(distanceY));
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        float differenceX=e2.getX()-e1.getX();
        float differenceY=e2.getY()-e1.getY();
        System.out.println(differenceX);
        System.out.println(differenceY);
        if(velocityX>1.5f |velocityY>1.5f) {
            if (Math.abs(differenceX) > Math.abs(differenceY)) {
                if (differenceX < 0) {
                    floor.changeFloor(-1);
                } else {
                    floor.changeFloor(1);
                }
            } else {
                if (differenceY < 0) {
                    floor.changeBuilding(-1);
                } else {
                    floor.changeBuilding(1);
                }
            }
        }
        return true;
    }


    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        System.out.println(detector.getScaleFactor());
        floor.scaleTimes(detector.getScaleFactor());
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }
}
