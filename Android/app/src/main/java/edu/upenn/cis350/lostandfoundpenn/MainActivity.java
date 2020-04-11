package edu.upenn.cis350.lostandfoundpenn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;


import java.net.URL;

import edu.upenn.cis350.lostandfoundpenn.Activities.MainPageActivity;
import edu.upenn.cis350.lostandfoundpenn.Activities.SignupActivity;
import edu.upenn.cis350.lostandfoundpenn.Fragments.ReportFragment;
import edu.upenn.cis350.lostandfoundpenn.Fragments.SearchFragment;

import edu.upenn.cis350.lostandfoundpenn.Utils.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    public static final int STARTCLICK_ID = 1;
    EditText emailEditText;
    EditText passwordEditText;
    ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button signupButton = findViewById(R.id.signup);
        loadingProgressBar = findViewById(R.id.loading);
    }

    public String getPassword(String email) {
        try {
            URL url = new URL("http://10.0.2.2:3000/getuser?email=" + email);
            GetWebTask task = new GetWebTask();
            task.execute(url);
            String pw = task.get();
            return pw;
        }catch (Exception e){
            return e.toString();
        }
    }

    public void login(View view) {
        loadingProgressBar.setVisibility(View.VISIBLE);
        if (emailEditText.getText() == null || passwordEditText.getText() == null) {
            Toast.makeText(this, "One or more entries are empty", Toast.LENGTH_LONG).show();
            emailEditText.setText("");
            passwordEditText.setText("");
        }
        String emailInput = emailEditText.getText().toString();
        String pwInput = passwordEditText.getText().toString();

        String pw = getPassword(emailInput);

        //compare input and password
        if (pwInput.equals(pw)) {
            loadingProgressBar.setVisibility(View.INVISIBLE);
            gotoMain();
        } else {
            loadingProgressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_LONG).show();
            emailEditText.setText("");
            passwordEditText.setText("");
        }
    }

    public void goToSignup(View view) {
        Intent i = new Intent(this, SignupActivity.class);
        startActivityForResult(i, STARTCLICK_ID);
    }

    public void gotoMain() {
        Intent i = new Intent(this, MainPageActivity.class);
        i.putExtra("id", emailEditText.getText().toString());
        startActivityForResult(i, STARTCLICK_ID);
    }

    // disable back button
    public void onBackPressed() {
        //do nothing
    }
}
