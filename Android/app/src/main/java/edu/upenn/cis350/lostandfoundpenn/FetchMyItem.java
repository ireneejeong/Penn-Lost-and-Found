package edu.upenn.cis350.lostandfoundpenn;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.Scanner;
import edu.upenn.cis350.lostandfoundpenn.Data.Item;

public class FetchMyItem extends AsyncTask<String, String, List<Item>> {
    /*This method is called in background when this object's "execute" method is invoked.
 The arguments passed to "execute" are passed to this method.*/
    protected List<Item> doInBackground(String... urls) {
        try {
            // get the first URL from the array
            String urlinString = urls[0];
            String userId = urls[1];
            URL url = new URL(urlinString);

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
                if (item.getString("author").equals(userId)) {
                    String itemTitle = item.getString("title");
                    String itemLocation = item.getString("location");
                    Boolean isClaimed = item.getBoolean("isClaimed");
                    Boolean isFound = item.getBoolean("isFound");
                    Item temp = new Item(itemTitle, itemLocation, "waiting for a claim");
                    if (isClaimed && !isFound) {
                        temp.status = "has been claimed";
                    }
                    else if (isClaimed && isFound) {
                        temp.status = "found by owner";
                    }
                    list.add(temp);
                }
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