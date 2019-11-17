package com.lens.profandroidbook.seriestracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {
    private static final String TAG_LIST_FRAGMENT = "TAG_LIST_FRAGMENT";

    SeriesListFragment seriesListFragment;

    SeriesViewModel seriesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            seriesListFragment = new SeriesListFragment();
            fragmentTransaction.add(R.id.main_activity_frame, seriesListFragment, TAG_LIST_FRAGMENT);
            fragmentTransaction.commitNow();
        } else {
            seriesListFragment = (SeriesListFragment) fragmentManager.findFragmentByTag(TAG_LIST_FRAGMENT);

        }

    seriesViewModel = ViewModelProviders.of(this).get(SeriesViewModel.class);

    }

}
