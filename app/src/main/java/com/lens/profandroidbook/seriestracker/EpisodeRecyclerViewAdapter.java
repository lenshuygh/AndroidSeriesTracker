package com.lens.profandroidbook.seriestracker;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

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
        holder.binding.listItemEpisodeAcquiredCheckbox.setClickable(false);
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

            binding.getRoot().setOnClickListener(v -> {
                Log.i(TAG, "ViewHolder: clicked -> " + v.getTag());
                CheckBox acquiredCheckbox = this.binding.listItemEpisodeAcquiredCheckbox;
                if(acquiredCheckbox.isChecked()){
                    acquiredCheckbox.setChecked(false);
                }else {
                    acquiredCheckbox.setChecked(true);
                }
            });
        }
    }
}
