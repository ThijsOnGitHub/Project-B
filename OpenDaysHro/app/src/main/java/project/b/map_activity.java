package project.b;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

class DrawableManager{
    private static Context context = null;
    private static Resources resources;

    public DrawableManager(Context c) {
        context = c;
        resources=context.getResources();
    }

    //By myself
    public int getID(String name){
        int resourceId = resources.getIdentifier(name, "drawable", context.getPackageName());
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

class Room{
    public String roomNumber;
    public int X,Y,width,height;
    public float photoshopWidth,photoshopHeight;


    public Room(String roomNumber,int X,int Y,int width,int height,float photoshopWidth,float photoshopHeight){
        this.roomNumber=roomNumber.toLowerCase();
        this.X=X;
        this.Y=Y;
        this.width=width;
        this.height=height;
        this.photoshopWidth=photoshopWidth;
        this.photoshopHeight=photoshopHeight;
    }
}

class AttributePackForMapManger {
    public Button upBut,downBut,leftBut,rightBut,zoomInBut,zoomOutBut,resetZoomBut;
    ImageView showFloor,showRoom;
    LinearLayout floorSel,buildingSel;
    FrameLayout mapContainer;

    public AttributePackForMapManger(ImageView showFloorInvoer ,
                                     ImageView showRoom,
                                     FrameLayout mapContainer,
                                     Button upButton,
                                     Button downButton,
                                     Button leftButton,
                                     Button rightButton,
                                     Button zoomInButton,
                                     Button zoomOutButton,
                                     Button resetZoomButton,
                                     LinearLayout floorSelector,
                                     LinearLayout buildingSelector){
        showFloor=showFloorInvoer;
        this.showRoom=showRoom;
        this.mapContainer=mapContainer;
        upBut=upButton;
        downBut=downButton;
        leftBut=leftButton;
        rightBut=rightButton;
        zoomInBut=zoomInButton;
        zoomOutBut=zoomOutButton;
        resetZoomBut=resetZoomButton;
        floorSel=floorSelector;
        buildingSel=buildingSelector;
    }
}

class mapManager{
    public String[] buildingsList ;
    public String building;
    public int buildingNum=0;
    public int floor=0;
    public Context context;
    public DrawableManager getPic;
    static Room[] ROOMS = new Room[]{new Room("h.2.204",1200,450,230,380,1469,1117),
            new Room("h.2.111",499,260,177,125,1469,1117),
            new Room("wn.5.023",
                    604,556,344,287,1572,1117)};


    public Button upButton,downButton,leftButton,rightButton,zoomInButton,zoomOutButton,resetZoomButton;
    ImageView showFloor,showRoom;
    float scale,startScale,maxscale;
    int movedSpaceX, movedSpaceY;
    LinearLayout floorSelector,buildingSelector;
    int[] floorsInBuilding;
    FrameLayout mapContainer;
    Room lastSelectRoom;

    public mapManager(Context c,String[] buildings){
        context=c;
        buildingsList=buildings;
        getPic = new DrawableManager(c);
        context=c;
    }

    //Button upBut,Button downBut,Button leftBut,Button rightBut
    public mapManager(Context c, AttributePackForMapManger attributes, String[] buildings){
        //elements
        showFloor=attributes.showFloor;
        this.showRoom=attributes.showRoom;
        this.mapContainer=attributes.mapContainer;
        upButton=attributes.upBut;
        downButton=attributes.downBut;
        leftButton=attributes.leftBut;
        rightButton=attributes.rightBut;
        zoomInButton=attributes.zoomInBut;
        zoomOutButton=attributes.zoomOutBut;
        resetZoomButton=attributes.resetZoomBut;
        floorSelector=attributes.floorSel;
        buildingSelector=attributes.buildingSel;


        //vars
        getPic = new DrawableManager(c);
        context=c;
        buildingsList=buildings;
        building=buildingsList[0];
        startScale=1.0f;
        maxscale=8.0f;





        //starting procces
        updateImage();
    }

    // ---------------------------------- floor + building changer -----------------------------------------

    public void setFloor(int newFloor){
        if(checkFloorExist(newFloor,building)){
            floor=newFloor;
            updateImage();
        }
    }

    public void setBuildingWhitoutUpdate(String newBuilding){
        if(checkFloorExist(floor,newBuilding)){
            building=newBuilding;
            for (int i = 0; i < buildingsList.length; i++) {
                if (building==buildingsList[i]){
                    buildingNum=i;
                }
            }
        }
    }

    public void setBuilding(String newBuilding){
        if(checkFloorExist(floor,newBuilding)){
            building=newBuilding;
            for (int i = 0; i < buildingsList.length; i++) {
                if (building==buildingsList[i]){
                    buildingNum=i;
                }
            }
            updateImage();
        }
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



    public void updateImage(){
        updateFloorSecector();
        updateBuildingSelector();
        showFloor.setImageResource(0);
        String name=createName();
        int id=getPic.getID(name);
        showFloor.setImageResource(id);
        movedSpaceX =0;
        movedSpaceY =0;
        scale=startScale;

        //temporary
        showRoom.setVisibility(View.GONE);
        if (lastSelectRoom!=null) {
            String[] roomSplit = lastSelectRoom.roomNumber.split("\\.");
            if (name.equals(roomSplit[0]+roomSplit[1])){
                drawRoom(lastSelectRoom);
            }
        }
        mapContainer.setScaleX(scale);
        mapContainer.setScaleY(scale);
        mapContainer.scrollTo(0,0);
        updateVisabilatyButtons();
    }

    public void updateFloorsInBuilding(){
        int minFloor=0;
        int maxFloor=0;
        int checkFloor=0;
        while (checkFloorExist(checkFloor,building)){
            minFloor=checkFloor;
            checkFloor-=1;
        }
        checkFloor=0;
        while (checkFloorExist(checkFloor,building)){
            maxFloor=checkFloor;
            checkFloor+=1;
        }

        floorsInBuilding=new int[maxFloor-minFloor+1];
        for (int i = 0; i < floorsInBuilding.length; i++) {
            floorsInBuilding[i]=minFloor+i;
        }

    }

    private void updateBuildingSelector(){
        int buildingsAvailable=0;
        for (int i = 0; i < buildingsList.length;i++) {
            if(checkFloorExist(floor,buildingsList[i])){
                buildingsAvailable+=1;
            };
        }
        int width= buildingSelector.getLayoutParams().width/buildingsAvailable;
        buildingSelector.removeAllViews();
        for (int i = 0; i < buildingsList.length ; i++) {
            if (checkFloorExist(floor,buildingsList[i])) {
                //https://www.youtube.com/watch?v=s_PfopWcMwI

                LinearLayout item = new LinearLayout(context);
                    item.setOrientation(LinearLayout.VERTICAL);
                    item.setPadding(15, 5, 15, 5);
                    item.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT));
                    String buildingStr = buildingsList[i];

                    item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setBuilding(buildingStr);
                        }
                    });
                    if (building.equals(buildingStr)) {
                        item.setBackgroundColor(context.getResources().getColor(R.color.dark_grey));
                    } else {
                        item.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
                    }


                    int indexOfSplit = buildingStr.split("[0-9]", 2)[0].length();
                    String numberBuildingString = buildingStr.substring(0, indexOfSplit);
                    String characterBuildingString = buildingStr.substring(indexOfSplit);

                    TextView numberBuilding = new TextView(context);
                        numberBuilding.setTextSize(21.f);
                        numberBuilding.setPadding(0,5,0,5);
                        numberBuilding.setText(buildingStr);
                        numberBuilding.setGravity(Gravity.CENTER);
                        numberBuilding.setTextColor(Color.parseColor("#000000"));
                        item.addView(numberBuilding);



                buildingSelector.addView(item);
            }
        }
    }

    private void drawRoom(Room room){
        //https://stackoverflow.com/questions/12463155/get-the-displayed-size-of-an-image-inside-an-imageview
        lastSelectRoom=room;
        //https://stackoverflow.com/questions/40691174/how-can-i-get-wrap-content-or-match-parent-layout-width-and-height-in-android
        showFloor.post(new Runnable(){
            public void run(){
                showRoom.setVisibility(View.VISIBLE);
                float actualHeight, actualWidth;
                float imageViewHeight = showFloor.getMeasuredHeight(), imageViewWidth = showFloor.getMeasuredWidth();

                float bitmapHeight = showFloor.getDrawable().getIntrinsicHeight(), bitmapWidth = showFloor.getDrawable().getIntrinsicWidth();

                if (imageViewHeight * bitmapWidth <= imageViewWidth * bitmapHeight) {
                    actualWidth = bitmapWidth * imageViewHeight / bitmapHeight;
                    actualHeight = imageViewHeight;
                } else {
                    actualHeight = bitmapHeight * imageViewWidth / bitmapWidth;
                    actualWidth = imageViewWidth;
                }

                float photoshopHeight=room.photoshopHeight;
                float photoshopWidht=room.photoshopWidth;

                float newX= (room.X*actualWidth)/photoshopWidht;
                float newY= (room.Y*actualHeight)/photoshopHeight;

                float emptyWidth=(imageViewWidth-actualWidth)/2;
                float emptyHeight=(imageViewHeight-actualHeight)/2;

                FrameLayout.LayoutParams  newLayoutParams=new FrameLayout.LayoutParams((int)((room.width*actualWidth)/photoshopWidht),(int)((room.height*actualHeight)/photoshopHeight));
                newLayoutParams.setMargins((int)(newX+emptyWidth),(int)(newY+emptyHeight),0,0);
                showRoom.setLayoutParams(newLayoutParams);
                
            }
        });


}

    public void colorRoom(String roomNumber){
        int amount=0;
        for (int i = 0; i <ROOMS.length; i++) {
            if (ROOMS[i].roomNumber.equals(roomNumber)){
                drawRoom(ROOMS[i]);
                amount+=1;
            }
        }
        if (amount==0){
            Toast.makeText(context,context.getString(R.string.Classroom_not_highlighted),Toast.LENGTH_LONG).show();
        }
    }

    private void updateFloorSecector(){
        floorSelector.removeAllViews();

        updateFloorsInBuilding();
        for (int i = 0; i < floorsInBuilding.length; i++) {
            int workFloor=floorsInBuilding[floorsInBuilding.length-i-1];
            TextView workBut=new TextView(context);
                workBut.setText(Integer.toString(workFloor));
                workBut.setPadding(15,5,15,5);
                workBut.setTextSize(20.0f);
                if (workFloor==floor){
                    workBut.setBackgroundColor(context.getResources().getColor(R.color.dark_grey));
                }
                workBut.setTextColor(Color.parseColor("#000000"));
                workBut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setFloor(workFloor);
                    }
                });
            floorSelector.addView(workBut);
        }
    }



    public String createName(){
        return (building+floor).replace("-","_");
    }

    public String createName(int floor,String building){
        return (building+floor).replace("-","_");
    }

    // ------------------------------- check existence ---------------------------------
    private boolean checkFloorExistAfterChange(int amountOfFloorHigher, int amountOfBuildingRight){
        if (buildingNum+amountOfBuildingRight<0 ||buildingNum+amountOfBuildingRight>buildingsList.length-1){
            return false;
        }
        return checkFloorExist(floor+amountOfFloorHigher,buildingsList[buildingNum+amountOfBuildingRight]);
    }

    private boolean checkFloorExist(int floor,String building){
        return getPic.nameExist(createName(floor,building));
    }


    // ------------------------------ visabilaty buttons ------------------------------------
    public void updateVisabilatyButtons(){
        setVisable(upButton,downButton,leftButton,rightButton,zoomInButton,zoomOutButton,resetZoomButton);
        if(!checkFloorExistAfterChange(1,0)){
            setInvisible(upButton);
        }
        if(!checkFloorExistAfterChange(-1,0)){
            setInvisible(downButton);
        }
        if(!checkFloorExistAfterChange(0,-1)){
            setInvisible(leftButton);
        }
        if(!checkFloorExistAfterChange(0,1)){
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

    //---------------------------------- zoom process -----------------------------------------
    public void moveSpace(int movementOnX,int movementOnY){
        mapContainer.scrollBy(movementOnX,movementOnY);

        float width=mapContainer.getMeasuredWidth();
        float height=mapContainer.getMeasuredHeight();

        int extraSpaceX=Math.round((((scale)*width)-(width*startScale))/2);
        int extraSpaceY=Math.round((((scale)*height)-(height*startScale))/2);

        int scrollX=mapContainer.getScrollX();
        int scrollY=mapContainer.getScrollY();

        movedSpaceX=stayBetweenIncl(extraSpaceX*-1,extraSpaceX,scrollX);
        movedSpaceY=stayBetweenIncl(extraSpaceY*-1,extraSpaceY,scrollY);

        mapContainer.scrollTo(movedSpaceX, movedSpaceY);
        updateVisabilatyButtons();
    }


    private void scaleCheck(){
        if(scale>=startScale && scale<=maxscale) {
            mapContainer.setScaleX(scale);
            mapContainer.setScaleY(scale);
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
        scaleCheck();
    }

    public void scaleTimes(float amount){
        scale*=amount;
        scaleCheck();
    }
}


public class map_activity extends appHelper implements GestureDetector.OnGestureListener, View.OnTouchListener {
    mapManager floor;
    LayoutHelper layout;
    GestureDetector gestureDetector;
    ScaleGestureDetector scaleGestureDetector;
    String passedInstituteID;
    FrameLayout mapContainer;
    ImageView showFloor,showRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        showFloor = findViewById(R.id.ImageView_showFloor);
        showRoom =findViewById(R.id.ImageView_showroom);
        mapContainer=findViewById(R.id.mapContainer);
        AttributePackForMapManger attributes = new AttributePackForMapManger(showFloor,
                showRoom,
                mapContainer,
                (Button) findViewById(R.id.Button_FloorUp),
                (Button) findViewById(R.id.Button_FloorDown),
                (Button) findViewById(R.id.Button_BuildingLeft),
                (Button) findViewById(R.id.Button_BuildingRight),
                (Button) findViewById(R.id.button_ZoomIn),
                (Button) findViewById(R.id.button_ZoomOut),
                (Button)findViewById(R.id.button_ResetZoom),
                (LinearLayout)findViewById(R.id.linearLayout_floorSelector_Floorplan),
                (LinearLayout)findViewById(R.id.linearLayout_buidlingSelector_Floorplan));

        layout = new LayoutHelper(this);

        try { passedInstituteID = getIntent().getStringExtra("InstituteID"); } catch (Exception e){ System.out.println(e); passedInstituteID = null;}

        if (passedInstituteID == null) {
            String[] institutes = layout.db.getInstitutes();
            for (int i = 0; i < institutes.length; i++) {
                String[] institute_info = layout.db.getInstituteInfo(institutes[i]);
                if (institute_info[1].equals("CMI")) {
                    passedInstituteID = institute_info[3];
                }
            }
        }

        String[] buildings=  layout.db.getFloorplansByInstitute(passedInstituteID);

        floor = new mapManager(this, attributes,buildings);
        gestureDetector=new GestureDetector(this,this);
        scaleGestureDetector= new ScaleGestureDetector(this, new ScaleListener() );
        mapContainer.setOnTouchListener(this);

        try{
            String buildingString=getIntent().getExtras().getString("building").toLowerCase();
            String rawString=getIntent().getExtras().getString("rawString").toLowerCase();
            floor.colorRoom(rawString);
            int floorInt = getIntent().getExtras().getInt("floor");
            floor.setFloor(floorInt);
            floor.setBuilding(buildingString);
        }catch (Exception e){
            System.out.println(e);
        }


        /* -------------------------menu--------------------------*/
        Intent home = new Intent(getBaseContext(), MainActivity.class);
        Intent educations = new Intent(getBaseContext(), educations_activity.class);
        Intent about_cmi = new Intent(getBaseContext(), About_activity.class);
        Intent contact = new Intent(getBaseContext(), contact_activity.class);

        Intent[] myIntents = new Intent[]{home,educations,about_cmi,contact};
        int[] images = new int[]{R.drawable.ic_home_white_24dp,R.drawable.baseline_school_24px,R.drawable.ic_location_city_grey_24dp,R.drawable.ic_chat_white_24dp};

        String[] text = new String[]{getResources().getString(R.string.Home),getResources().getString(R.string.Study_Programs),getResources().getString(R.string.About_Institute),getResources().getString(R.string.Conctact)};

        layout.generate_menu(R.id.menu_bar,images,text,myIntents);
    }

    public void clickSearch(View v){
        Intent openSearch=new Intent(this,searchRoom_activaty.class);
        startActivity(openSearch);
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
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events
        scaleGestureDetector.onTouchEvent(ev);
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId()==mapContainer.getId() && event.getPointerCount()==1){
            gestureDetector.onTouchEvent(event);
            return true;
        }
        return false;
    }

    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            floor.scaleTimes(detector.getScaleFactor());
            return true;
        }
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
        if((velocityX>3.0f ||velocityY>3.0f)&&!(floor.scale>floor.startScale)) {
            if ((Math.abs(differenceX) > Math.abs(differenceY))) {
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



}
