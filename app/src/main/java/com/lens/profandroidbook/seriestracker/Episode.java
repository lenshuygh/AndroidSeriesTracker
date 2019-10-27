package com.lens.profandroidbook.seriestracker;

import java.time.LocalDate;

public class Episode {
    private int id;
    private int season;
    private int episode;
    private boolean acquired;
    private LocalDate airDate;

    public int getId() {
        return id;
    }

    public int getSeason() {
        return season;
    }

    public int getEpisode() {
        return episode;
    }

    public boolean isAcquired() {
        return acquired;
    }

    @Override
    public String toString() {
        return "E" + episode + "S" + season + " (" + airDate + ") : acquired" + acquired;
    }
}
