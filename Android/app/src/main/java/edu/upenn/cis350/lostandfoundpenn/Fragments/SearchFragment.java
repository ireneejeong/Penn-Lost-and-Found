package edu.upenn.cis350.lostandfoundpenn.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

import edu.upenn.cis350.lostandfoundpenn.Activities.ClaimActivity;
import edu.upenn.cis350.lostandfoundpenn.Data.Item;
import edu.upenn.cis350.lostandfoundpenn.R;
import edu.upenn.cis350.lostandfoundpenn.Utils.SearchRVAdapter;

public class SearchFragment extends Fragment {

    private ArrayList<Item> mItemList = new ArrayList<>();
    private TextWatcher searchText = null;
    private SearchRVAdapter adapter;
    public static boolean searchByItem = true;
    public static boolean searchByLocation = false;

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
//        Button mItemButton = view.findViewById(R.id.searchItemButton);
//
//        Button mLocButton = view.findViewById(R.id.searchLocationButton);
        Switch mItemSwitch = view.findViewById(R.id.switchItem);
        Switch mLocationSwitch = view.findViewById(R.id.switchLocation);

        mItemSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean on) {
                if (on) {
                    searchByItem = true;
                } else {
                    searchByItem = false;
                }
            }
        });

        mLocationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean on) {
                if (on) {
                    searchByLocation = true;
                } else {
                    searchByLocation = false;
                }
            }
        });

        // Instantiate SearchView
        SearchView mSearchView = view.findViewById(R.id.searchView);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        // Fetch list of items/locations from database
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        updateRecyclerView(this.getContext(), recyclerView);
    }





    // Receive item data from the server
    private ArrayList<Item> loadItemData() {

        // TODO

        // fetch list from server and save it into mItemList

        ArrayList<Item> temp = new ArrayList<>();
        temp.add(new Item("Mac Pro 15", "Towne 100", "waitingFound"));
        temp.add(new Item("AirPod Pro", "Towne 217", "waitingFound"));
        temp.add(new Item("Nintendo Switch", "SKIR AUD", "waitingFound"));
        temp.add(new Item("Chicken Fried Rice", "JMHH 100", "waitingFound"));
        temp.add(new Item("LG Gram", "MOOR 105", "waitingFound"));
        temp.add(new Item("GALAXY Note 10", "LEV AUD", "waitingFound"));
        temp.add(new Item("Super Mario", "SIG LAB", "waitingFound"));
        temp.add(new Item("Mac AIR 15", "Towne 100", "waitingFound"));
        temp.add(new Item("AirPod 2", "Towne 217", "waitingFound"));
        temp.add(new Item("Nintendo GOLD", "SKIR AUD", "waitingFound"));
        temp.add(new Item("Psy", "JMHH 100", "waitingFound"));
        temp.add(new Item("IU", "MOOR 105", "waitingFound"));
        temp.add(new Item("Tae-yeon", "LEV AUD", "waitingFound"));
        temp.add(new Item("BTS", "SIG LAB", "waitingFound"));

        return temp;
    }

    private void updateRecyclerView(Context context, RecyclerView view) {
        // add recyclerView
        ArrayList<Item> data = loadItemData();
        adapter = new SearchRVAdapter(context, data);
        view.setAdapter(adapter);

        // add layoutManager
        view.setLayoutManager(new LinearLayoutManager(context));
        view.setHasFixedSize(true);
    }


    private void itemSearch() {

    }

    private void locationSearch() {

    }


}
