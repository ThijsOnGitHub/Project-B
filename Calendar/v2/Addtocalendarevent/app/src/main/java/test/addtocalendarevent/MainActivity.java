package test.addtocalendarevent;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    EditText Day;
    EditText Month;
    EditText Year;
    EditText Title;
    EditText Description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Day = (EditText)findViewById(R.id.Day);
        Month = (EditText)findViewById(R.id.Month);
        Year = (EditText)findViewById(R.id.Year);

        Title = (EditText)findViewById(R.id.Title);
        Description = (EditText)findViewById(R.id.Description);
    }

        //this thing listens to the button click and opens a browser that goes the the URL.
        @RequiresApi(api = Build.VERSION_CODES.O)
        public void myListener (View v) throws ParseException {
            /**
             NOTES:
             - This part is for generating the link to add the event. The ending date should always be the startingdate + 1 day.
             - The link should be like this:
             https://calendar.google.com/calendar/r/eventedit?dates=${startingDate}/${endingDate}&text=${title}&location=${myLocation}&details=${message}

             Please note that the multiple instances of ${} are not part of the link. it is just to make it easier to see the input places.

             Working example:
             http://www.google.com/calendar/event?action=TEMPLATE&dates=20180821%2F20180822&text=Litmus%20Live%20London&location=etc.venues%20155%20Bishopsgate&details=Litmus%20Live%20helps%20you%20become%20a%20better%20email%20marketer%20with%20real-world%20advice%2C%20best%20practices%20%26%20practical%20takeaways.
             **/

            String year = Year.getText().toString();
            String month = Month.getText().toString();
            String day = Day.getText().toString();
            String eventTitle = Title.getText().toString();
            String description = Description.getText().toString();


            DecimalFormat formatter = new DecimalFormat("00");
            LocalDate myDate_before = LocalDate.of(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));
            int myMonth1 = myDate_before.getMonthValue(); String MONTH_before = formatter.format(myMonth1);
            int myDay1 = myDate_before.getDayOfMonth(); String DAY_before = formatter.format(myDay1);
            LocalDate myDate = myDate_before.plusDays(1);
            int myYear  = myDate.getYear(); String YEAR = Integer.toString(myYear);
            int myMonth = myDate.getMonthValue(); String MONTH = formatter.format(myMonth);
            int myDay = myDate.getDayOfMonth(); String DAY = formatter.format(myDay);

            String myEventTitle = eventTitle.replaceAll("\\s+","%20");
            String myDescription = description.replaceAll("\\s+","%20");



            String addLink_unfinished = "https://calendar.google.com/calendar/r/eventedit?dates=" +
                    year + MONTH_before + DAY_before + "/" + YEAR + MONTH + DAY + "&text=" + myEventTitle + "&details=" + myDescription;

            String URL = addLink_unfinished.replaceAll("\\s+","");

            Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
            startActivity(browser);
    }
}
