package edu.upenn.cis350.lostandfoundpenn.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import edu.upenn.cis350.lostandfoundpenn.Fragments.ReportFragment;
import edu.upenn.cis350.lostandfoundpenn.Fragments.SearchFragment;
import edu.upenn.cis350.lostandfoundpenn.MainActivity;
import edu.upenn.cis350.lostandfoundpenn.R;
import edu.upenn.cis350.lostandfoundpenn.Utils.ViewPagerAdapter;

public class MainPageActivity extends AppCompatActivity {
    private ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
    private String loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        loggedInUser = i.getStringExtra("id");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Create Tab-layout
        createTabs();

    }

    // MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                Intent profileIntent = new Intent(this, UserProfile.class);
                profileIntent.putExtra("loggedInUser", loggedInUser);
                startActivity(profileIntent);

                return true;

            case R.id.reportedItems:
                return true;
            case R.id.logoutMenu:
                Intent logoutIntent = new Intent(this, MainActivity.class);
                startActivity(logoutIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createTabs() {
        // Create Tabs
        adapter.addFragment(new ReportFragment(loggedInUser), "Report");
        adapter.addFragment(new SearchFragment(), "Search");

        // Link Fragments with ViewPager
        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabs);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
