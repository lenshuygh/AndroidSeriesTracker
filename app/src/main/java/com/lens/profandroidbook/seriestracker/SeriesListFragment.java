package com.lens.profandroidbook.seriestracker;

import android.content.Context;
import android.os.Bundle;
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

public class SeriesListFragment extends Fragment {
    private List<Series> seriesArrayList = new ArrayList<>();

    private RecyclerView recyclerView;
    private SeriesRecyclerViewAdapter seriesRecyclerViewAdapter = new SeriesRecyclerViewAdapter(seriesArrayList);

    public SeriesListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_series_list, container, false);
        recyclerView = view.findViewById(R.id.list_series);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(seriesRecyclerViewAdapter);
    }

    public void setSeries(List<Series> seriesList) {
        seriesList.stream().filter(s -> !seriesArrayList.contains(s)).forEach(this::addAndNotify);
    }

    private void addAndNotify(Series s) {
        seriesArrayList.add(s);
        seriesRecyclerViewAdapter.notifyItemInserted(seriesArrayList.indexOf(s));
    }
}
