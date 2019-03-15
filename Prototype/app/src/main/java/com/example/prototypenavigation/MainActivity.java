package com.example.prototypenavigation;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void navToSchool(View view){
        Uri gmmIntentUri = Uri.parse("google.navigation:q=Wijnhaven 107 ,3011 WN Rotterdam,The Netherlands");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }

    }

    public void navToParking(View view){
        //51.917188, 4.483972
        Uri gmmIntentUri = Uri.parse("geo:51.917188,4.483972?z=10&q=parking&z=30");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }

    };
}
