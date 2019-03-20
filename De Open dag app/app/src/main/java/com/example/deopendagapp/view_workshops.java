package com.example.deopendagapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class view_workshops extends AppCompatActivity {

    DatabaseHelper myDb;

    private ListView listView_workshop;
    private Workshoplistadapter adapter;
    private List<Workshops> mWorkshoplist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workshops);

        myDb = new DatabaseHelper(this);

        mWorkshoplist = new ArrayList<>();
        listView_workshop = findViewById(R.id.listview);

        viewData();
    }

    private void viewData(){
        Cursor cursor = myDb.viewData();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data to show :(", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                mWorkshoplist.add(new Workshops(
                        cursor.getString(1), // Roomcode column index
                        cursor.getString(2), // Study column index
                        cursor.getString(3), // Starttime column index
                        cursor.getString(4)  // Subject column index
                ));
            }

            adapter = new Workshoplistadapter(getApplicationContext(), mWorkshoplist);
            listView_workshop.setAdapter(adapter);
        }
    }
}
