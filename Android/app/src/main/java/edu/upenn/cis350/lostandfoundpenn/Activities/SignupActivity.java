package edu.upenn.cis350.lostandfoundpenn.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.net.URL;

import edu.upenn.cis350.lostandfoundpenn.PostWebTask;
import edu.upenn.cis350.lostandfoundpenn.R;

public class SignupActivity extends AppCompatActivity{
    EditText emailEditText;
    EditText passwordEditText;
    EditText contactEditText;
    ProgressBar loadingProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailEditText = (EditText) findViewById(R.id.email);
        passwordEditText = (EditText) findViewById(R.id.password);
        contactEditText = (EditText) findViewById(R.id.contact);

        final Button loginButton = findViewById(R.id.login);
        final Button signupButton = findViewById(R.id.signup);

        loadingProgressBar = findViewById(R.id.loading);

    }

    public void submitSignup(View view) {
        loadingProgressBar.setVisibility(View.VISIBLE);

        //check if valid entries
        if (!isEmailValid()) {
            Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_LONG).show();
            emailEditText.setText("");
            passwordEditText.setText("");
            contactEditText.setText("");
        } else if (!isPasswordValid()) {
            Toast.makeText(this, "Invalid Password: You must include at least one uppercase letter, " +
                    "one lowercase letter, and a number", Toast.LENGTH_LONG).show();
            emailEditText.setText("");
            passwordEditText.setText("");
            contactEditText.setText("");
        } else if (!isContactValid()) {
            Toast.makeText(this, "Invalid Contact information", Toast.LENGTH_LONG).show();
            emailEditText.setText("");
            passwordEditText.setText("");
            contactEditText.setText("");
        } else {
            String email = emailEditText.getText().toString();
            String pw = passwordEditText.getText().toString();
            String contact = contactEditText.getText().toString();

            try {
                String fullURL = "http://10.0.2.2:3000/adduser?";

                // concatenate full URL
                fullURL += "email=" + email;
                fullURL += "&pw=" + pw;
                fullURL += "&contact=" + contact;
                fullURL += "&points=" + 0;

                URL url = new URL(fullURL);
                PostWebTask task = new PostWebTask();
                task.execute(url);

                String resultMessage = task.get();
                Toast.makeText(this, resultMessage, Toast.LENGTH_LONG).show();
                loadingProgressBar.setVisibility(View.INVISIBLE);

                Intent i = new Intent();
                // put the number of clicks into the Intent
                // indicate that this Activity ended normally; set Intent
                setResult(RESULT_OK, i);// ends this Activity
                finish();

            } catch (Exception e) {
                loadingProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean isEmailValid() {
        if (emailEditText.getText() == null) {
            return false;
        }
        String email = emailEditText.getText().toString();
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false;
        } else if (!(email.contains("wharton.upenn.edu") || email.contains("seas.upenn.edu")
                || email.contains("sas.upenn.edu") || email.contains("nursing.upenn.edu"))) {
            return false;
        }
        return true;
    }

    public boolean isPasswordValid() {
        if (passwordEditText.getText() == null) {
            return false;
        }
        String pw = passwordEditText.getText().toString();
        if (!checkLowerCase(pw) || !checkUpperCase(pw) || !checkNumberCase(pw)) {
            return false;
        }
        return true;
    }

    boolean checkLowerCase(String pw) {
        for (int i = 0; i < pw.length(); i++) {
            char c = pw.charAt(i);
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    boolean checkUpperCase(CharSequence pw) {
        for (int i = 0; i < pw.length(); i++) {
            char c = pw.charAt(i);
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    boolean checkNumberCase(CharSequence pw) {
        for (int i = 0; i < pw.length(); i++) {
            char c = pw.charAt(i);
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    public boolean isContactValid() {
        if (contactEditText.getText() == null) {
            return false;
        }
        String pn = contactEditText.getText().toString();
        if (checkDigitCase(pn)) {
            return false;
        }
        return true;
    }

    boolean checkDigitCase(String pn) {
        for (int i = 0; i < pn.length(); i++) {
            char c = pn.charAt(i);
            if (!Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

}
