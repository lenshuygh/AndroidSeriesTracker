package com.lens.profandroidbook.seriestracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class EpisodeListFragment extends Fragment {
    private List<Episode> episodeArrayList = new ArrayList<>(0);

    private RecyclerView recyclerView;
    private EpisodeRecyclerViewAdapter episodeRecyclerViewAdapter = new EpisodeRecyclerViewAdapter(episodeArrayList);

    EpisodesViewModel episodesViewModel;

    public EpisodeListFragment() {

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
        recyclerView = view.findViewById(R.id.list_episodes);
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

        int seriesId = sharedPreferences.getInt("SeriesId", 0);
        Log.i(TAG, "EpisodeListFragment -> onViewCreated -> from shared prefs => series id = " + seriesId);

        String seriesTitle = sharedPreferences.getString("SeriesTitle","Series Title");

        Toast.makeText(EpisodeListFragment.this.getContext(), Integer.toString(seriesId), Toast.LENGTH_SHORT).show();

        ((TextView) view.findViewById(R.id.episodes_list_title)).setText(seriesTitle);
    }

    public void setEpisodes(List<Episode> episodeList){
        Log.i(TAG, "EpisodeListFragment -> setEpisodes: size list = " + episodeList.size());
        episodeList.stream().filter(e-> !episodeArrayList.contains(e)).forEach(this::addAndNotify);
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

        episodesViewModel = ViewModelProviders.of(this).get(EpisodesViewModel.class);
        episodesViewModel.getEpisodes().observe(getViewLifecycleOwner(), new Observer<List<Episode>>() {
            @Override
            public void onChanged(List<Episode> episodes) {
                Log.i(TAG, "EpisodeListFragment -> onChanged !!!! ");
                    setEpisodes(episodes);
            }
        });
    }
}
