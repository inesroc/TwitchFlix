package com.example.twitchflix.VideoOnDemand;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.twitchflix.R;

import java.util.ArrayList;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.DataObjectHolder> {

    private ArrayList<MovieObject> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView label;
        TextView dateTime;
        String url;

        DataObjectHolder(View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.textView);
            dateTime = itemView.findViewById(R.id.textView2);
            String LOG_TAG = "MyRecyclerViewAdapter";
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(url, v);
        }
    }

    void setOnItemClickListener(MyClickListener myClickListener) {
        ViewAdapter.myClickListener = myClickListener;
    }

    ViewAdapter(ArrayList<MovieObject> myDataset) {
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_row, parent, false);

        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataObjectHolder holder, int position) {
        holder.label.setText(mDataset.get(position).getTitle());
        holder.dateTime.setText(mDataset.get(position).getInfo());
        holder.url = mDataset.get(position).getUrl();
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        void onItemClick(String url, View v);
    }
}
