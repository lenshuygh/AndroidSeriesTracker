package com.lens.profandroidbook.seriestracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SeriesRecyclerViewAdapter extends RecyclerView.Adapter<SeriesRecyclerViewAdapter.ViewHolder> {
    private final List<Series> seriesList;

    public SeriesRecyclerViewAdapter(List<Series> seriesList) {
        this.seriesList = seriesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_series,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.series = seriesList.get(position);
        holder.detailsView.setText(seriesList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return seriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final View parentView;
        public final TextView detailsView;
        public Series series;

        public ViewHolder(@NonNull View view) {
            super(view);
            parentView = view;
            detailsView = view.findViewById(R.id.list_item_series_details);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + detailsView.getText() + "'";
        }
    }
}
