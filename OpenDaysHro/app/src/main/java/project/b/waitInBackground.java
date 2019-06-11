package project.b;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import java.util.concurrent.TimeUnit;

public class waitInBackground extends AsyncTask<Integer, Void, Void> {
    Context mainContext;
    ProgressBar progressBar;

    public waitInBackground(Context mainContext, ProgressBar mprogressBar) {
        this.mainContext = mainContext;
        this.progressBar = mprogressBar;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(progressBar.getProgress() + 1);
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        try {
            Integer steps = (progressBar.getMax() - progressBar.getProgress());
            Integer totaltime = integers[0];

            for (int i = 0; i < steps; i++) {
                if (totaltime <= 0) {
                    progressBar.setProgress(progressBar.getMax());
                    break;
                } else {
                    Long start = System.currentTimeMillis();
                    Integer time = totaltime / (steps - i);
                    TimeUnit.MILLISECONDS.sleep(time);
                    publishProgress();
                    Long time_done = (System.currentTimeMillis() - start);
                    totaltime -= time_done.intValue();
                }
            }

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
