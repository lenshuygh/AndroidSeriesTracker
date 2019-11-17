package com.lens.profandroidbook.seriestracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lens.profandroidbook.seriestracker.databinding.ListItemSeriesBinding;

import java.util.List;

public class SeriesRecyclerViewAdapter extends RecyclerView.Adapter<SeriesRecyclerViewAdapter.ViewHolder> {
    private final List<Series> seriesList;
    private View.OnClickListener mOnItemClickListener;

    public SeriesRecyclerViewAdapter(List<Series> seriesList) {
        this.seriesList = seriesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemSeriesBinding binding = ListItemSeriesBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Series series = seriesList.get(position);

        holder.binding.setSeries(series);
        holder.binding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return seriesList.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ListItemSeriesBinding binding;

        public ViewHolder(ListItemSeriesBinding binding) {
            super(binding.getRoot());
            binding.getRoot().setTag(this);
            binding.getRoot().setOnClickListener(mOnItemClickListener);

            this.binding = binding;

        }


    }
}
