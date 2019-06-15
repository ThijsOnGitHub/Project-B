package project.b;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class jsonApi extends AsyncTask<String, Integer, Void> {
    String data = "";
    DatabaseHelper db;
    Context mainContext;
    Integer waitTime;
    Long starttime;
    ProgressBar progressBar;

    public jsonApi(Context context, Integer mseconds, ProgressBar mprogressBar) {
        db = new DatabaseHelper(context);
        mainContext = context;
        waitTime = mseconds;
        progressBar = mprogressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.starttime = System.currentTimeMillis();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        System.out.println(values[0] + "/" + progressBar.getMax());
        progressBar.setProgress(values[0]);
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            Long amountoflines = null;
            Integer lines_total = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                amountoflines = bufferedReader.lines().count();
                lines_total = amountoflines.intValue();
            }

            progressBar.setMax((int) ((lines_total * 2)));

            httpURLConnection = (HttpURLConnection) url.openConnection();
            inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            Integer i = 0;
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
                i++;
                publishProgress(i);
            }

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(this.data);
                Integer current = progressBar.getProgress();
                this.db.fillDatabaseWithJson(jsonObject);

                Long time = (System.currentTimeMillis() - this.starttime);
                int time_taken = time.intValue();
                this.waitTime -= time_taken;

                if (this.waitTime < 0) {
                    this.waitTime = 0;
                }

                if (this.waitTime == 0) {
                    progressBar.setProgress(progressBar.getMax());
                } else {
                    Integer steps = progressBar.getMax() - progressBar.getProgress();
                    Integer total_time = this.waitTime;
                    for (int j = progressBar.getProgress(); j < progressBar.getMax(); j += 15) {
                        if (total_time <= 0) {
                            break;
                        } else {
                            Long start = System.currentTimeMillis();
                            Integer int_time = this.waitTime / steps;
                            TimeUnit.MILLISECONDS.sleep(int_time);
                            publishProgress(j);
                            Long time_done = (System.currentTimeMillis() - start);
                            total_time -= time_done.intValue();
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        progressBar.setProgress(progressBar.getMax());
        Intent mainActivity = new Intent(mainContext, MainActivity.class);
        mainContext.startActivity(mainActivity);
    }
}

