package com.example.wouter.helloworld;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public void run(){
        System.out.println("Hello World");

    }

//    findViewById(R.id.ratebutton).setOnClickListener(new OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            String url = "market://details?id=<package_name>";
//
//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse(url));
//            startActivity(i);
//        }
//    });

    public void twitterLink(View view) {
        Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.nl"));
        startActivity(browserIntent);
    }


    protected void onCreate(Bundle savedInstanceState) {
        this.run();
    }
}
