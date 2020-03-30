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

import edu.upenn.cis350.lostandfoundpenn.AccessWebTask;
import edu.upenn.cis350.lostandfoundpenn.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment implements View.OnClickListener{
    protected String itemTitle;
    protected String itemDescription;
    protected String itemTime;
    protected String itemLocation;
    // add user


    public ReportFragment() {
        // Required empty public constructor
        itemTitle = "";
        itemDescription = "";
        itemTime = "";
        itemLocation = "";
    }

    private void saveInfo() {
        EditText itemName = getView().findViewById(R.id.itemName);
        itemTitle = itemName.getText().toString();

        EditText itemD = getView().findViewById(R.id.itemDescription);
        itemDescription = itemD.getText().toString();

        EditText itemT = getView().findViewById(R.id.itemTime);
        itemTime = itemT.getText().toString();

        EditText itemL = getView().findViewById(R.id.itemLocation);
        itemLocation = itemL.getText().toString();

        try {
            String fullURL = "http://10.0.2.2:3000/addItem?";

            // concatenate full URL
            fullURL += "title=" + itemTitle;
            fullURL += "&description=" + itemDescription;
            fullURL += "&time=" + itemTime;
            fullURL += "&location=" + itemLocation;
            // add user

            URL url = new URL(fullURL);
            AccessWebTask task = new AccessWebTask();
            task.execute(url);

            String resultMessage = task.get();
            Toast.makeText(getActivity(), "RESULT: " + resultMessage, Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addItem_btn:
                saveInfo();

                Toast.makeText(getActivity(), "Item registered!", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_report, container, false);

        Button addBtn = (Button) v.findViewById(R.id.addItem_btn);
        addBtn.setOnClickListener(this);

        return v;
    }

}
