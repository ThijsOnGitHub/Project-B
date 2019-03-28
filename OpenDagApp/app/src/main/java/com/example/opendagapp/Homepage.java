package com.example.opendagapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Homepage extends AppCompatActivity {
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        myDb = new DatabaseHelper(this);
    }
}
