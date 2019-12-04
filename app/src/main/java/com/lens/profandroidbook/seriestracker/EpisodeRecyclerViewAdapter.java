package com.lens.profandroidbook.seriestracker;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lens.profandroidbook.seriestracker.databinding.ListItemEpisodeBinding;

import java.util.List;

import static android.content.ContentValues.TAG;

class EpisodeRecyclerViewAdapter extends RecyclerView.Adapter<EpisodeRecyclerViewAdapter.ViewHolder> {
    private final List<Episode> episodeList;

    public EpisodeRecyclerViewAdapter(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "EpisodeRecyclerViewAdapter -> onCreateViewHolder: ");
        ListItemEpisodeBinding binding = ListItemEpisodeBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i(TAG, "EpisodeRecyclerViewAdapter -> onBindViewHolder: ");
        Episode episode = episodeList.get(position);

        holder.binding.setEpisode(episode);
        holder.binding.executePendingBindings();
        holder.itemView.setTag(episode);
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public final ListItemEpisodeBinding binding;

        public ViewHolder(ListItemEpisodeBinding binding){
            super(binding.getRoot());
            Log.i(TAG, "EpisodeRecyclerViewAdapter -> ViewHolder: constructor");
            binding.getRoot().setTag(this);

            this.binding = binding;
        }

    }
}
