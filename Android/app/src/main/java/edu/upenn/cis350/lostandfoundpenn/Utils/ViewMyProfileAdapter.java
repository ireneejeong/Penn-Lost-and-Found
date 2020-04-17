package edu.upenn.cis350.lostandfoundpenn.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.upenn.cis350.lostandfoundpenn.Data.Item;
import edu.upenn.cis350.lostandfoundpenn.R;

public class ViewMyProfileAdapter extends RecyclerView.Adapter<ViewMyProfileAdapter.Holder> {

    private ArrayList<Item> mData = null;
    private Context mContext;

    public ViewMyProfileAdapter(Context context, ArrayList<Item> items) {
        mData = items;
        mContext = context;
    }


    @NonNull
    @Override
    public ViewMyProfileAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_myprofile_adapter, parent, false);
        ViewMyProfileAdapter.Holder holder = new ViewMyProfileAdapter.Holder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class Holder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView itemLoc;
        TextView itemStatus;


        public Holder(View view) {
            super(view);

            itemName = view.findViewById(R.id.itemName);
            itemLoc = view.findViewById(R.id.itemLocation);
            itemStatus = view.findViewById(R.id.itemStatus);


        }

        public void bind(Item item) {
            itemName.setText(item.getName());
            itemLoc.setText(item.getLocation());
            itemStatus.setText(item.getStatus());
        }
    }
}