package edu.upenn.cis350.lostandfoundpenn.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import edu.upenn.cis350.lostandfoundpenn.MainActivity;
import edu.upenn.cis350.lostandfoundpenn.R;

public class UserProfile extends AppCompatActivity {
    protected String loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        Intent i = getIntent();
        loggedInUser = i.getStringExtra("loggedInUser");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // display logged in user
        TextView userID = (TextView)findViewById(R.id.userID);
        userID.setText("My username: " + loggedInUser);

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
