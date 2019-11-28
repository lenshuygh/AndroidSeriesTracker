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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class SeriesListFragment extends Fragment {
    private List<Series> seriesArrayList = new ArrayList<>();

    private RecyclerView recyclerView;
    private SeriesRecyclerViewAdapter seriesRecyclerViewAdapter = new SeriesRecyclerViewAdapter(seriesArrayList);

    SeriesViewModel seriesViewModel;

    public static final String TAG_EPISODES_FRAGMENT = "TAG_EPISODES_FRAGMENT";

    public SeriesListFragment() {

    }

    private final View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();

            Series series = seriesArrayList.get(position);
            Toast.makeText(SeriesListFragment.this.getContext(), series.getTitle(), Toast.LENGTH_SHORT).show();
            Log.i(TAG, "SeriesListFragment -> onClick: series clicked: " + series.getTitle());

            Context context = getActivity().getApplicationContext();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("SeriesId",series.getId());
            editor.apply();
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.main_activity_frame,new EpisodeListFragment(),TAG_EPISODES_FRAGMENT);
            fragmentTransaction.commitNow();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "SeriesListFragment -> onCreate: ");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "SeriesListFragment -> onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_series_list, container, false);
        recyclerView = view.findViewById(R.id.list_series);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "SeriesListFragment -> onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);

        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(seriesRecyclerViewAdapter);
        seriesRecyclerViewAdapter.setOnItemClickListener(onItemClickListener);
    }

    public void setSeries(List<Series> seriesList) {
        seriesList.stream().filter(s -> !seriesArrayList.contains(s)).forEach(this::addAndNotify);
    }

    private void addAndNotify(Series s) {
        seriesArrayList.add(s);
        seriesRecyclerViewAdapter.notifyItemInserted(seriesArrayList.indexOf(s));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "SeriesListFragment -> onActivityCreated: ");
        super.onActivityCreated(savedInstanceState);

        seriesViewModel = ((MainActivity) this.getActivity()).seriesViewModel;

        seriesViewModel.getSeries().observe(getViewLifecycleOwner(), new Observer<List<Series>>() {
            @Override
            public void onChanged(List<Series> series) {
                Log.i(TAG, "SeriesListFragment -> onChanged !!!! ");
                if(series != null){
                    setSeries(series);
                }
            }
        });
    }
}
