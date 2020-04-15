package edu.upenn.cis350.lostandfoundpenn;

import android.icu.text.Edits;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONArray;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.Scanner;
import edu.upenn.cis350.lostandfoundpenn.Data.Item;

public class FetchItem extends AsyncTask<URL, String, List<Item>> {
    /*This method is called in background when this object's "execute" method is invoked.
 The arguments passed to "execute" are passed to this method.*/
    protected List<Item> doInBackground(URL... urls) {
        try {
            // get the first URL from the array
            URL url = urls[0];
            // create connection and send HTTP request
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            // read first line of data that is returned
            Scanner in = new Scanner(url.openStream());
            String msg = in.nextLine();
            Log.d("msg", msg);
            ArrayList<Item> list = new ArrayList<Item>();
            // use Android JSON library to parse JSON
            JSONArray jo = new JSONArray(msg);
            for (int i = 0; i < jo.length(); i++) {
                JSONObject item = jo.getJSONObject(i);
                String itemTitle = item.getString("title");
                String itemLocation = item.getString("location");
                Boolean isClaimed = item.getBoolean("isClaimed");
                Boolean isFound = item.getBoolean("isFound");
                Item temp = new Item(itemTitle, itemLocation, "waitingFound");
                if (isClaimed && !isFound) {
                    temp.status = "waitingClaim";
                }
                else if (isClaimed && isFound) {
                    temp.status = "found";
                }
                list.add(temp);
            }

            // assumes that JSON object contains a "name" field

            // this will be passed to onPostExecute method
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

        /*This method is called in foreground after doInBackground finishes.
        It can access and update Views in user interface.*/

    protected void onPostExecute(String msg) {
        // not implemented but you can use this if you’d like
    }

}