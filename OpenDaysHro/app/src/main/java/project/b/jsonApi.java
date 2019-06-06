package project.b;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

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

public class jsonApi extends AsyncTask<String, Void, Void> {
    String data = "";
    Boolean finish = false;
    DatabaseHelper db;
    Context mainContext;

    public jsonApi(Context context) {
        db = new DatabaseHelper(context);
        mainContext = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }
            finish = true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(this.data);
            this.db.fillDatabaseWithJson(jsonObject);
            TimeUnit.MILLISECONDS.sleep(1250);
            Intent mainActivity = new Intent(mainContext, MainActivity.class);
            mainContext.startActivity(mainActivity);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

