package edu.upenn.cis350.lostandfoundpenn.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.upenn.cis350.lostandfoundpenn.R;

public class EditProfile extends AppCompatActivity {
    String original_contact;
    EditText contactEditText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        original_contact = getIntent().getStringExtra("loggedInContact");
        contactEditText = findViewById(R.id.editContact);

    }

    //go back to my profile with new contact
    public void onSubmitButtonClick(View view) {
        Intent i = new Intent();
        // put the number of clicks into the Intent
        String new_contact = contactEditText.getText().toString();
        if (isContactValid()) {
            i.putExtra("new_contact" , new_contact);
            setResult(RESULT_OK, i);
            finish();
        } else {
            Toast.makeText(this, "Contact information format is invalid", Toast.LENGTH_SHORT).show();
        }
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


    public void returnToMyProfile(View view) {
        Intent i = new Intent(this, UserProfile.class);
        startActivity(i);
    }

}