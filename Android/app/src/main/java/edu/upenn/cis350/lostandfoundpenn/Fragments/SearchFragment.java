package edu.upenn.cis350.lostandfoundpenn.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import edu.upenn.cis350.lostandfoundpenn.R;

public class SearchFragment extends Fragment {
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // Instantiate Buttons
        Button mItemButton = view.findViewById(R.id.searchItemButton);
        Button mLocButton = view.findViewById(R.id.searchLocationButton);

        // Instantiate SearchView
        SearchView mSearchView = view.findViewById(R.id.searchView);

        // Fetch list of items/locations from database
        // TODO
    }

}
