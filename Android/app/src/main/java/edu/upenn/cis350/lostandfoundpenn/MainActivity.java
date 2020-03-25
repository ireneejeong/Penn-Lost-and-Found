package edu.upenn.cis350.lostandfoundpenn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

import edu.upenn.cis350.lostandfoundpenn.Fragments.ReportFragment;
import edu.upenn.cis350.lostandfoundpenn.Fragments.SearchFragment;
import edu.upenn.cis350.lostandfoundpenn.Utils.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isRegistered = true; // TODO: Fetch registered state from db

        if (!isRegistered) {
            // TODO : GO TO REGISTER ACTIVITY
        }

        // Create Tab-layout
        createTabs();

    }

    private void createTabs() {
        // Create Tabs
        adapter.addFragment(new ReportFragment(), "Report");
        adapter.addFragment(new SearchFragment(), "Search");

        // Link Fragments with ViewPager
        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabs);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
