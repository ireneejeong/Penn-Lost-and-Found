package edu.upenn.cis350.lostandfoundpenn.Utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.upenn.cis350.lostandfoundpenn.Activities.ClaimActivity;
import edu.upenn.cis350.lostandfoundpenn.Data.Item;
import edu.upenn.cis350.lostandfoundpenn.Fragments.ReportFragment;
import edu.upenn.cis350.lostandfoundpenn.Fragments.SearchFragment;
import edu.upenn.cis350.lostandfoundpenn.R;

public class SearchRVAdapter
        extends RecyclerView.Adapter<SearchRVAdapter.Holder>
        implements Filterable {

    private ArrayList<Item> mPermantData = new ArrayList<>();
    private ArrayList<Item> mData = null;
    private Context mContext;

    
    public SearchRVAdapter(Context context, ArrayList<Item> items) {
        mPermantData.addAll(items);
        mData = items;
        mContext = context;

    }





    @NonNull
    @Override
    public SearchRVAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_search_rvadapter, parent, false);
        SearchRVAdapter.Holder holder = new SearchRVAdapter.Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRVAdapter.Holder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Item> filteredList = new ArrayList<>();

            // default
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(mPermantData);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Item item : mPermantData) {
                    if (!(SearchFragment.searchByItem ^ SearchFragment.searchByLocation)) {
                        if (item.getName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                        if (item.getLocation().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                    else if (SearchFragment.searchByItem) {
                        if (item.getName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                    else {
                        if (item.getLocation().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }

                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mData.clear();
            mData.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };




    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView itemName;
        TextView itemLoc;
        Button claimButton;


        public Holder(View view) {
            super(view);

            itemName = view.findViewById(R.id.itemName);
            itemLoc = view.findViewById(R.id.itemLocation);
            claimButton = (Button) view.findViewById(R.id.claimButton);
            claimButton.setOnClickListener(this);



        }

        public void bind(Item item) {
            itemName.setText(item.getName());
            itemLoc.setText(item.getLocation());
            claimButton.setText("claim");

        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, ClaimActivity.class);
            Item item = mPermantData.get(getAdapterPosition());
            Log.d("Status", item.status);
            if (item.getStatus().equals("waitingFound")) {
                String[] arr = new String[3];
                arr[0] = item.getName();
                arr[1] = item.getLocation();
                arr[2] = item.getStatus();
                intent.putExtra("item", arr);
                item.status = "waitingClaim";
                Log.d("Status", item.status);
                mContext.startActivity(intent);
            }
            else if (item.status.equals("waitingClaim")) {
                Toast.makeText(mContext, "This item is already claimed by another user!",
                        Toast.LENGTH_LONG).show();
            }
        }
    }




}
