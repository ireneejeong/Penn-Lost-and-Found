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

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.*;

import edu.upenn.cis350.lostandfoundpenn.Activities.ClaimActivity;
import edu.upenn.cis350.lostandfoundpenn.Data.Item;
import edu.upenn.cis350.lostandfoundpenn.FetchItem;
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

        try{
            URL url = new URL("http://10.0.2.2:3000/fetchItem?");
            FetchItem task = new FetchItem();
            task.execute(url);
            List<Item> items = task.get();
            for (int i = 0; i < items.size(); i++) {
                Item tmp = items.get(i);
                mItemList.add(tmp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // fetch list from server and save it into mItemList


        return mItemList;
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
