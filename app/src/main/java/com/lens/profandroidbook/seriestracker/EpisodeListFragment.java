package com.lens.profandroidbook.seriestracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class EpisodeListFragment extends Fragment {
    private List<Episode> episodeArrayList = new ArrayList<>();

    private RecyclerView recyclerView;
    private EpisodeRecyclerViewAdapter episodeRecyclerViewAdapter = new EpisodeRecyclerViewAdapter(episodeArrayList);

    public EpisodeListFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "EpisodeListFragment -> onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "EpisodeListFragment -> onCreateView");
        View view = inflater.inflate(R.layout.fragment_episodes_list, container, false);
        recyclerView = view.findViewById((R.id.list_episodes));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "EpisodeListFragment -> onViewCreated");
        super.onViewCreated(view, savedInstanceState);

        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(episodeRecyclerViewAdapter);

        Context appContext = getActivity().getApplicationContext();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(appContext);

        int seriesId = sharedPreferences.getInt("SeriesId",0);
        Log.i(TAG, "EpisodeListFragment -> onViewCreated -> from shared prefs => series id = " + seriesId);

        Toast.makeText(EpisodeListFragment.this.getContext(), Integer.toString(seriesId), Toast.LENGTH_SHORT).show();
    }

    public void setEpisodes() {
        List<Episode> episodeToAddList = new ArrayList<>();
        LocalDate date = LocalDate.now();
        Episode episode1 = new Episode(0, 1, 1, date,true, 5464564);
        Episode episode2 = new Episode(1, 1, 2, date,true, 5464564);
        episodeToAddList.add(episode1);
        episodeToAddList.add(episode2);
        episodeToAddList.stream().filter(s -> !episodeArrayList.contains(s)).forEach(this::addAndNotify);
    }

    private void addAndNotify(Episode s) {
        Log.i(TAG, "EpisodeListFragment -> addAndNotify: " + s);
        episodeArrayList.add(s);
        episodeRecyclerViewAdapter.notifyItemInserted(episodeArrayList.indexOf(s));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "EpisodeListFragment -> onActivityCreated: ");
        super.onActivityCreated(savedInstanceState);

        setEpisodes();
    }
}
