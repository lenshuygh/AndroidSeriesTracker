package com.lens.profandroidbook.seriestracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {
    private static final String TAG_SERIES_FRAGMENT = "TAG_SERIES_FRAGMENT";
    private static final String TAG_EPISODES_FRAGMENT = "TAG_EPISODES_FRAGMENT";

    SeriesListFragment seriesListFragment;

    EpisodeListFragment episodeListFragment;

    SeriesViewModel seriesViewModel;

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            seriesListFragment = new SeriesListFragment();
            fragmentTransaction.add(R.id.main_activity_frame, seriesListFragment, TAG_SERIES_FRAGMENT);
            fragmentTransaction.commitNow();
        } else {
            seriesListFragment = (SeriesListFragment) fragmentManager.findFragmentByTag(TAG_SERIES_FRAGMENT);

        }

    seriesViewModel = ViewModelProviders.of(this).get(SeriesViewModel.class);

    }

    void SwitchFragment(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity_frame,new EpisodeListFragment(),TAG_EPISODES_FRAGMENT);
        fragmentTransaction.commitNow();
    }

}
