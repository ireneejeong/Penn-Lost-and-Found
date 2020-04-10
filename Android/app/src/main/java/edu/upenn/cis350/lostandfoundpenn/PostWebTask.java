package edu.upenn.cis350.lostandfoundpenn;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class PostWebTask extends AsyncTask<URL, String, String> {
    /*This method is called in background when this object's "execute" method is invoked.
    The arguments passed to "execute" are passed to this method.*/
    protected String doInBackground(URL... urls) {
        try {
            // get the first URL from the array
            URL url = urls[0];
            Log.v("url", url.toString());

            // create connection and send HTTP request
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.connect();

            // read first line of data that is returned
            Scanner in = new Scanner(url.openStream());
            String msg = in.nextLine();
            Log.v("message", msg);
            return msg;

        } catch (Exception e) {
            Log.v("error", e.toString());
            return "ERROR : " + e.toString();
        }
    }
    /*This method is called in foreground after doInBackground finishes.
    It can access and update Views in user interface.*/

    protected void onPostExecute(String msg) {
//      not implemented but you can use this if you’d like
    }
}