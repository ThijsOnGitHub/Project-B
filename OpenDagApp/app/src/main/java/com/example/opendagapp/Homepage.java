package com.example.opendagapp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Homepage extends AppCompatActivity {
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        myDb = new DatabaseHelper(this);
//        myDb.createInstitute("Communicatie, Media en Informatietechnologie", "CMI", "", "Het instituut voor Communicatie, Media en Informatietechnologie (CMI) heeft met de opleidingen Communicatie, Informatica, Technische Informatica, Creative Media and Game Technologies en Communication and Multimedia Design maar liefst 3000 studenten die een waardevolle bijdrage leveren aan de onbegrensde wereld van communicatie, media en ICT.");
//        myDb.createOpenday("27-01-2019", "09:00:00", "16:00:00", "Communicatie, Media en Informatietechnologie");
    }
}
