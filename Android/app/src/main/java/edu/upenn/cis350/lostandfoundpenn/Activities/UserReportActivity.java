package edu.upenn.cis350.lostandfoundpenn.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;

import edu.upenn.cis350.lostandfoundpenn.AccessWebTask;
import edu.upenn.cis350.lostandfoundpenn.R;

public class UserReportActivity extends AppCompatActivity {

    protected String userID;
    protected String contactInfo;
    protected String itemFeature;
    protected String extraMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edu.upenn.cis350.lostandfoundpenn.R.layout.activity_user_report);
    }

    private void saveInfo() {

        String[] arr = getIntent().getStringArrayExtra("item");

        EditText UserID = (EditText) findViewById(R.id.UserID);
        userID = UserID.getText().toString();

        EditText ContactInfo = (EditText) findViewById(R.id.ContactInfo);
        contactInfo = ContactInfo.getText().toString();

        EditText ItemFeature = (EditText) findViewById(R.id.ItemFeature);
        itemFeature = ItemFeature.getText().toString();

        EditText ExtraMsg = (EditText) findViewById(R.id.ExtraMsg);
        extraMsg = ItemFeature.getText().toString();

        try {
            String fullURL = "http://10.0.2.2:3000/userReport?";

            // concatenate full URL
            fullURL += "name=" + arr[0];
            fullURL += "&location=" + arr[1];
            fullURL += "&status=" + arr[2];
            fullURL += "&userID=" + userID;
            fullURL += "&contactInfo=" + contactInfo;
            fullURL += "&description=" + itemFeature;
            fullURL += "&extraMsg" + extraMsg;
            // add user

            URL url = new URL(fullURL);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);

            String resultMessage = task.get();
            Toast.makeText(this, "RESULT: " + resultMessage, Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }




    }

    public void report(View view) {

        Toast.makeText(this, "Successfully reported!", Toast.LENGTH_SHORT).show();
        saveInfo();



        new AsyncTask<String, String, String>() {
            protected String doInBackground(String...inputs) {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) { }
                return null;
            }

            protected void onPostExecute(String input) {
                UserReportActivity.this.onBackPressed();
            }

        }.execute();




    }
}
