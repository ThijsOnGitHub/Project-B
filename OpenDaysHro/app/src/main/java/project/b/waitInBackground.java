package project.b;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.util.concurrent.TimeUnit;

public class waitInBackground extends AsyncTask<Integer, Void, Void> {
    Context mainContext;

    public waitInBackground(Context mainContext) {
        this.mainContext = mainContext;
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        try {
            TimeUnit.MILLISECONDS.sleep(integers[0]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Intent mainActivity = new Intent(mainContext, MainActivity.class);
        mainContext.startActivity(mainActivity);
    }
}
