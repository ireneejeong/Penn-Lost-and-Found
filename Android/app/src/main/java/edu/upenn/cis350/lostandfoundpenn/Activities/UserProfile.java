package edu.upenn.cis350.lostandfoundpenn.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.upenn.cis350.lostandfoundpenn.Data.Item;
import edu.upenn.cis350.lostandfoundpenn.FetchItem;
import edu.upenn.cis350.lostandfoundpenn.FetchMyClaim;
import edu.upenn.cis350.lostandfoundpenn.FetchMyItem;
import edu.upenn.cis350.lostandfoundpenn.GetWebTask;
import edu.upenn.cis350.lostandfoundpenn.MainActivity;
import edu.upenn.cis350.lostandfoundpenn.PostWebTask;
import edu.upenn.cis350.lostandfoundpenn.R;
import edu.upenn.cis350.lostandfoundpenn.Utils.SearchRVAdapter;
import edu.upenn.cis350.lostandfoundpenn.Utils.ViewMyProfileAdapter;

public class UserProfile extends AppCompatActivity {
    protected String loggedInUser;
    protected String loggedInContact;
    protected String loggedInPoints;
    public static final int ButtonClickActivity_ID = 1;
    private ArrayList<Item> myReportList = new ArrayList<>();
    private ArrayList<Item> myClaimList = new ArrayList<>();
    private ViewMyProfileAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        Intent i = getIntent();
        loggedInUser = i.getStringExtra("loggedInUser");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // display logged in username
        TextView userID = (TextView)findViewById(R.id.userID);
        userID.setText("My username: " + loggedInUser);

        // display logged in user's contact
        TextView userContact = (TextView)findViewById(R.id.userContact);
        loggedInContact = getContact(loggedInUser);
        userContact.setText("My contact information: " + loggedInContact);

        // display logged in user's rank
        TextView userRank = (TextView)findViewById(R.id.userRank);
        loggedInPoints = getPoints(loggedInUser);
        userRank.setText("have " + loggedInPoints + " points, " + "General Member");


        //display my reported items
        RecyclerView recyclerReportView = (RecyclerView) findViewById(R.id.recyclerReportView);
        updateRecyclerView(this, recyclerReportView, "report");
        RecyclerView recyclerClaimView = (RecyclerView) findViewById(R.id.recyclerClaimView);
        updateRecyclerView(this, recyclerClaimView, "claim");
    }

    //get a list of items user reported
    public ArrayList<Item> getMyReports() {
        try{
            URL url = new URL("http://10.0.2.2:3000/fetchItem?");
            FetchMyItem task = new FetchMyItem();
            task.execute(url.toString(), loggedInUser);

            List<Item> items = task.get();
            for (int i = 0; i < items.size(); i++) {
                Item tmp = items.get(i);
                myReportList.add(tmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myReportList;
    }

    //get a list of items user claimed
    public ArrayList<Item> getMyClaims() {
        try{
            URL url = new URL("http://10.0.2.2:3000/fetchClaim?");
            FetchMyClaim task = new FetchMyClaim();
            task.execute(url.toString(), loggedInUser);

            List<Item> items = task.get();
            for (int i = 0; i < items.size(); i++) {
                Item tmp = items.get(i);
                Log.e("claims", tmp.getName() + " " + tmp.getStatus());
                myClaimList.add(tmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myClaimList;
    }

    private void updateRecyclerView(Context context, RecyclerView view, String type) {
        // add recyclerView
        ArrayList<Item> data;
        if (type.equals("report")) {
            data = getMyReports();
        } else {
            data = getMyClaims();
        }
        adapter = new ViewMyProfileAdapter(context, data);
        view.setAdapter(adapter);

        // add layoutManager
        view.setLayoutManager(new LinearLayoutManager(context));
        view.setHasFixedSize(true);
    }


    public String getContact(String email) {
        try {
            URL url = new URL("http://10.0.2.2:3000/getuser?email=" + email);
            GetWebTask task = new GetWebTask();
            task.execute(url.toString(), "contact");
            String contact = task.get();
            return contact;
        }catch (Exception e){
            return e.toString();
        }
    }

    public String getPoints(String email) {
        try {
            URL url = new URL("http://10.0.2.2:3000/getuser?email=" + email);
            GetWebTask task = new GetWebTask();
            task.execute(url.toString(), "points");
            String point = task.get();
            return point;
        }catch (Exception e){
            return e.toString();
        }
    }

    //public String determineRank()

    public void editInfo(View view) {
        Intent editProfileIntent = new Intent(this, EditProfile.class);
        editProfileIntent.putExtra("loggedInContact", loggedInContact);
        startActivityForResult(editProfileIntent,ButtonClickActivity_ID);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        // the requestCode lets us know which Activity just ended
        switch (requestCode) {
            case ButtonClickActivity_ID:
                String newContact = intent.getStringExtra("new_contact");
                loggedInContact = newContact;
                updateContact();
                TextView contact = (TextView) findViewById(R.id.userContact);
                contact.setText("My contact information: " + newContact);
                break;
        }
    }

    public void updateContact() {
        try {
            String fullURL = "http://10.0.2.2:3000/editusercontact?";

            // concatenate full URL
            fullURL += "email=" + loggedInUser;
            fullURL += "&contact=" + loggedInContact;

            URL url = new URL(fullURL);
            PostWebTask task = new PostWebTask();
            task.execute(url);

            String resultMessage = task.get();
            Toast.makeText(this, resultMessage, Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    // MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    // Toolbar menu options
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mainPage:
                Intent mainPageIntent = new Intent(this, MainPageActivity.class);
                mainPageIntent.putExtra("loggedInUser", loggedInUser);
                startActivity(mainPageIntent);
                return true;
            case R.id.profile:
                return true;
            /*
            case R.id.reportedItems:
                return true;

             */
            case R.id.logoutMenu:
                Intent logoutIntent = new Intent(this, MainActivity.class);
                startActivity(logoutIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
