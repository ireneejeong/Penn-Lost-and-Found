package edu.upenn.cis350.lostandfoundpenn;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class GetWebTask extends AsyncTask<String, String, String> {
    /*This method is called in background when this object's "execute" method is invoked.
    The arguments passed to "execute" are passed to this method.*/
    protected String doInBackground(String... urls) {
        try {
            String output = "";
            // get the first URL from the array
            String urlinString = urls[0];
            String type = urls[1];

            URL url = new URL(urlinString);
            // create connection and send HTTP request
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            // read first line of data that is returned
            Scanner in = new Scanner(url.openStream());
            String msg = in.nextLine();
            Log.v("msg", msg);
            // use Android JSON library to parse JSON
            JSONObject jo = new JSONObject(msg);
            if (type.equals("password")) {
                output = jo.getString("password");
            }
            if (type.equals("contact")) {
                output = jo.getString("contact");
            }
            if (type.equals("points")) {
                output = jo.getString("points");
            }
            // assumes that JSON object contains a "name" field
            //String pw = jo.getString("password");
            // this will be passed to onPostExecute method
            //return pw;
            return output;
        } catch (Exception e) {
            return e.toString();
        }
    }
        /*This method is called in foreground after doInBackground finishes.
        It can access and update Views in user interface.*/

    protected void onPostExecute(String msg) {
        // not implemented but you can use this if you’d like
    }
}
