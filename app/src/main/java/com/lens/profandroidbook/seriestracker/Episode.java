package com.lens.profandroidbook.seriestracker;

import java.time.DayOfWeek;

public class Episode {
    private int id;
    private int season;
    private int episode;
    private DayOfWeek releaseDay;
    private boolean acquired;

    public int getId() {
        return id;
    }

    public int getSeason() {
        return season;
    }

    public int getEpisode() {
        return episode;
    }

    public DayOfWeek getReleaseDay() {
        return releaseDay;
    }

    public boolean isAcquired() {
        return acquired;
    }

    @Override
    public String toString() {
        return "E" + episode + "S" + season + " (" + releaseDay + ") : acquired" + acquired;
    }
}
