package com.example.deopendagapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tweede Scherm met als tekst Hello world! openen.
        Button btnSecondActivity = (Button)findViewById(R.id.btnSecondActivity);
        btnSecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), SecondActivity.class);
                startIntent.putExtra("com.example.deopendagapp.tekstommeetetesten", "Hello World!");
                startActivity(startIntent);
            }
        });

        // Google.com openen

        Button btnGoogle = (Button) findViewById(R.id.btnGoogle);
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String google = "https://www.google.com";
                Uri webadress = Uri.parse(google);

                Intent googleIntent = new Intent(Intent.ACTION_VIEW, webadress);
                if (googleIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(googleIntent);
                }
            }
        });
    }
}
