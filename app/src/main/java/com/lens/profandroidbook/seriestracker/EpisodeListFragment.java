package com.lens.profandroidbook.seriestracker;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class EpisodeListFragment extends Fragment {
    private List<Episode> episodeList = new ArrayList<>();

    private RecyclerView recyclerView;
    private EpisodeRecyclerViewAdapter episodeRecyclerViewAdapter = new EpisodeRecyclerViewAdapter(episodeList);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_episodes_list, container, false);
        recyclerView = view.findViewById((R.id.list_episodes));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);

        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(episodeRecyclerViewAdapter);
    }

    public void setEpisodes(List<Episode> episodeList) {
        episodeList.stream().filter(s -> !episodeList.contains(s)).forEach(this::addAndNotify);
    }

    private void addAndNotify(Episode s) {
        episodeList.add(s);
        episodeRecyclerViewAdapter.notifyItemInserted(episodeList.indexOf(s));
    }
}