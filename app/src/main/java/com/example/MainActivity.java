package com.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.myapplicationv1.R;

public class MainActivity extends AppCompatActivity {

    public void run() {
        System.out.println("Hello Wolf");
    }

    int[] drawables = new int[]{R.drawable.beginning_by_ryky,R.drawable.best_friends_by_ryky,R.drawable.bffs_by_synderen,R.drawable.beginning_of_love_by_ryky,R.drawable.better_day_by_ryky,R.drawable.beyond_by_auroralion};

    int imageCounter = 0;
    ImageView mainImage = (ImageView)findViewById(R.id.mainImage);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.leftButton:
                imageCounter = imageCounter-1;
                if (imageCounter<0){
                    imageCounter = drawables.length-1;
                }
                mainImage.setImageResource(drawables[imageCounter]);
                System.out.println("test2");
                break;
            case R.id.rightButton:
                imageCounter = imageCounter + 1;
                if(imageCounter > drawables.length){
                    imageCounter = 0;
                }
                mainImage.setImageResource(drawables[imageCounter]);
                System.out.println("test");
                break;
        }
    }


}

