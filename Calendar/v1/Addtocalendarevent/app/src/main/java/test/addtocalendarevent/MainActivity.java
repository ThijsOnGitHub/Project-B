package test.addtocalendarevent;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

        /**
         NOTES:
         - This part is for generating the link to add the event. The ending date should always be the startingdate + 1 day.
         - The link should be like this:
         https://calendar.google.com/calendar/r/eventedit?dates=${startingDate}/${endingDate}&text=${title}&location=${myLocation}&details=${message}

         Please note that the multiple instances of ${} are not part of the link. it is just to make it easier to see the input places.

         Working example:
         http://www.google.com/calendar/event?action=TEMPLATE&dates=20180821%2F20180822&text=Litmus%20Live%20London&location=etc.venues%20155%20Bishopsgate&details=Litmus%20Live%20helps%20you%20become%20a%20better%20email%20marketer%20with%20real-world%20advice%2C%20best%20practices%20%26%20practical%20takeaways.
         **/

        String startingDate = "2019 03 06"; // yy mm dd
        String endingDate = "2019 03 07"; // yy mm dd+1
        String eventTitle = "MyTitle";
        String description = "Hello world!";

        String myEventTitle = eventTitle.replaceAll("\\s+","%20");
        String myDescription = description.replaceAll("\\s+","%20");

        String addLink_unfinished = "https://calendar.google.com/calendar/r/eventedit?dates=" +
                startingDate + "/" + endingDate + "&text=" + myEventTitle + "&details=" + myDescription;

        public String URL = addLink_unfinished.replaceAll("\\s+","");

        //this thing listens to the button click and opens a browser that goes the the URL.
        public void myListener (View v) {
            Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
            startActivity(browser);
    }
}
