package com.example.prototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editText_Roomcode, editText_Study, editText_Subject, editText_Startdatetime;
    Button button_Submit, button_Viewworkshops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this); // DatabaseHelper.java

        editText_Roomcode = findViewById(R.id.editText_Roomcode);
        editText_Study = findViewById(R.id.editText_Study);
        editText_Subject = findViewById(R.id.editText_Subject);
        editText_Startdatetime = findViewById(R.id.editText_Startdatatime);
        button_Submit = findViewById(R.id.button_submit);
        button_Viewworkshops = findViewById(R.id.button_viewdata);

        InsertData();
        Activity_viewworkshops();
    }

    public void InsertData() {
        button_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = myDb.insertData(editText_Roomcode.getText().toString(), editText_Study.getText().toString(), editText_Startdatetime.getText().toString(), editText_Subject.getText().toString());
                if (result == true) {
                    Toast.makeText(MainActivity.this, "Data is inserted :)", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data is not inserted :(", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void Activity_viewworkshops() {
        button_Viewworkshops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, view_workshops.class));
            }
        });
    }

    // showMessage string title, string message (ALERT BOX)
    public void showAlertMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
