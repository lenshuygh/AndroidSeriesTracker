package com.lens.profandroidbook.seriestracker;

import android.content.Context;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class SeriesListFragment extends Fragment {
    private List<Series> seriesArrayList = new ArrayList<>();

    private RecyclerView recyclerView;
    private SeriesRecyclerViewAdapter seriesRecyclerViewAdapter = new SeriesRecyclerViewAdapter(seriesArrayList);

    public SeriesListFragment() {

    }

    private final View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            // viewHolder.getItemId();
            // viewHolder.getItemViewType();
            // viewHolder.itemView;

            /*TestItem thisItem = mTestItemList.get(position);
            Toast.makeText(MainActivity.this, "You Clicked: " + thisItem.getTitle(), Toast.LENGTH_SHORT).show();*/

            Series series = seriesArrayList.get(position);
            Toast.makeText(SeriesListFragment.this.getContext(), series.getTitle(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onClick: BOOYAH");
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_series_list, container, false);
        recyclerView = view.findViewById(R.id.list_series);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ");
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
}
