package edu.upenn.cis350.lostandfoundpenn.Utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import edu.upenn.cis350.lostandfoundpenn.Data.Item;
import edu.upenn.cis350.lostandfoundpenn.R;

public class SearchRVAdapter extends RecyclerView.Adapter<SearchRVAdapter.Holder> {


    private ArrayList<Item> mData = null;


    public SearchRVAdapter(Context context, ArrayList<Item> items) {
        mData = items;
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

    class Holder extends RecyclerView.ViewHolder{

        TextView itemName;
        TextView itemLoc;

        public Holder(View view) {
            super(view);

            itemName = view.findViewById(R.id.itemName);
            itemLoc = view.findViewById(R.id.itemLocation);
        }

        public void bind(Item item) {
            itemName.setText(item.getName());
            itemLoc.setText(item.getLocation());
        }

    }
}
