package com.lens.profandroidbook.seriestracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG_LIST_FRAGMENT = "TAG_LIST_FRAGMENT";

    SeriesListFragment seriesListFragment;

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

        List<Series> seriesListDummyData = new ArrayList<>(0);
        seriesListDummyData.add(new Series(0, "War Of The worlds", LocalDate.of(2019, 10, 11), DayOfWeek.FRIDAY));
        List<Episode> episodeDummyData = new ArrayList<>(0);
        episodeDummyData.add(new Episode(0,1,1,true,0));
        episodeDummyData.add(new Episode(1,1,2,true,0));
        seriesListDummyData.add(new Series(1, "Zomboat", LocalDate.of(2019, 10, 8), DayOfWeek.TUESDAY));
        episodeDummyData.add(new Episode(2,1,1,true,1));
        episodeDummyData.add(new Episode(3,1,2,true,1));
        episodeDummyData.add(new Episode(4,1,3,false,1));
        seriesListFragment.setSeries(seriesListDummyData);
    }

}
