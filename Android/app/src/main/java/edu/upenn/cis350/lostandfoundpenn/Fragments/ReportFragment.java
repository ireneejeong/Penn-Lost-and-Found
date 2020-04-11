package edu.upenn.cis350.lostandfoundpenn.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;
import java.text.SimpleDateFormat;

import edu.upenn.cis350.lostandfoundpenn.AccessWebTask;
import edu.upenn.cis350.lostandfoundpenn.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment implements View.OnClickListener {
    protected String itemTitle;
    protected String itemDescription;
    protected String itemDate;
    protected String itemLocation;
    protected String author;

    protected EditText itemName;
    protected EditText itemDes;
    protected EditText itemD;
    protected EditText itemL;


    public ReportFragment() {
        // Required empty public constructor
        itemTitle = "";
        itemDescription = "";
        itemDate = "";
        itemLocation = "";
        author = "";
    }

    private void addItem() {
        itemName = getView().findViewById(R.id.itemName);
        itemTitle = itemName.getText().toString().toLowerCase();

        itemDes = getView().findViewById(R.id.itemDescription);
        itemDescription = itemDes.getText().toString();

        itemD = getView().findViewById(R.id.itemTime);
        itemDate = itemD.getText().toString();

        itemL = getView().findViewById(R.id.itemLocation);
        itemLocation = itemL.getText().toString().toLowerCase();

        // username of currently logged in User
        author = "sisabel";

        if (itemTitle == null || itemTitle.equals("")) {
            // rejected due to empty Title field
            Toast.makeText(getActivity(), "Please provide a title for the Item.",
                    Toast.LENGTH_LONG).show();
        } else if (itemLocation == null || itemLocation.equals("")) {
            // rejected due to empty Location field
            Toast.makeText(getActivity(), "Please provide the location for the Item.",
                    Toast.LENGTH_LONG).show();
        } else if (!inputDateIsValid()) {
            // rejected due to invalid date format
            Toast.makeText(getActivity(), "Please input a valid date.",
                    Toast.LENGTH_LONG).show();
        } else {
            try {
                String fullURL = "http://10.0.2.2:3000/addItem?";

                // concatenate full URL
                fullURL += "title=" + itemTitle;
                fullURL += "&description=" + itemDescription;
                fullURL += "&date=" + itemDate;
                fullURL += "&location=" + itemLocation;
                fullURL += "&author=" + author; // user

                // since it is a newly added item, it is neither claimed nor found
                fullURL += "&isClaimed=" + false;
                fullURL += "&isFound=" + false;

                URL url = new URL(fullURL);
                AccessWebTask task = new AccessWebTask();
                task.execute(url);

                String resultMessage = task.get();
                Toast.makeText(getActivity(), resultMessage, Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean inputDateIsValid() {
        if (!itemDate.matches("\\d{4}-[01]\\d-[0-3]\\d")) {
            return false;
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);

        try {
            df.parse(itemDate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addItem_btn:
                // add Item to DB
                addItem();

                // clear text fields
                clearInputFields();

                break;

            default:
                break;
        }
    }

    private void clearInputFields() {
        itemName.setText("");
        itemDes.setText("");
        itemD.setText("");
        itemL.setText("");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_report, container, false);

        Button addBtn = v.findViewById(R.id.addItem_btn);
        addBtn.setOnClickListener(this);
        return v;
    }

}
