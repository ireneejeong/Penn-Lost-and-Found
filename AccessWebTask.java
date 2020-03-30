package edu.upenn.cis350.lostandfoundpenn;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class AccessWebTask extends AsyncTask<URL, String, String> {

    protected String doInBackground(URL... urls) {
        try {
            URL url = urls[0];

            // create connection and send HTTP request
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.connect();

            // read first line of data that is returned
            Scanner in = new Scanner(url.openStream());
            String msg = in.nextLine();
            return msg;

        } catch (Exception e) {
            return "ERROR : " + e.toString();
        }
    }
}
