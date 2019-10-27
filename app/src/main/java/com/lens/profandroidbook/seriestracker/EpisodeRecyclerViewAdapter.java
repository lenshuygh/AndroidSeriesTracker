package com.lens.profandroidbook.seriestracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class EpisodeRecyclerViewAdapter extends RecyclerView.Adapter<EpisodeRecyclerViewAdapter.ViewHolder> {
    private final List<Episode> episodeList;

    public EpisodeRecyclerViewAdapter(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }

    @NonNull
    @Override
    public EpisodeRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_series, parent, false);
        return new EpisodeRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.episode = episodeList.get(position);
        holder.detailsView.setText(episodeList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public final View parentView;
        public final TextView detailsView;
        public Episode episode;

        public ViewHolder(@NonNull View view){
            super(view);
            parentView = view;
            detailsView = view.findViewById(R.id.list_item_episode_details);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + detailsView.getText() + "'";
        }
    }
}
