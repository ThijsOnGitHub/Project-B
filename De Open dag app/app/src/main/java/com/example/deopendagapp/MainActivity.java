package com.example.deopendagapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName, editSurname, editMarks;
    Button btnAddData, btnViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        editName = (EditText)findViewById(R.id.edittext_name);
        editSurname = (EditText)findViewById(R.id.edittext_surname);
        editMarks = (EditText)findViewById(R.id.edittext_marks);
        btnAddData = (Button)findViewById(R.id.button_adddata);
        btnViewAll = (Button)findViewById(R.id.button_viewall);

        AddData(); // addData button
        viewAll(); // viewAll button
    }
    // INSERT name, surname, marks into the 'students_table' TABLE WHEN clicked on btnAddData
    public void AddData() {
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if true then data is inserted :)
                boolean isInserted = myDb.insertData(editName.getText().toString(), editSurname.getText().toString(), editMarks.getText().toString());

                if (isInserted == true) {
                    showBottomMessage("Data is inserted");
                } else  {
                    showBottomMessage("Data is not inserted");
                }
            }
        });
    }

    // view All the data that already is in the database when clicked on btnViewall
    public void viewAll() {
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.getAllData();
                // No data available
                if (res.getCount() == 0) {
                    showAlertMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();

                while (res.moveToNext()) {
                    // 0, 1, 2, 3 = index of the table columns
                    buffer.append("ID : " + res.getString(0) + "\n");
                    buffer.append("NAME : " + res.getString(1) + "\n");
                    buffer.append("SURNAME : " + res.getString(2) + "\n");
                    buffer.append("MARKS : " + res.getString(3) + "\n\n");
                }

                showAlertMessage("Data", buffer.toString());
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

    // showMessage string message (BOTTOM BOX)
    public void showBottomMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
