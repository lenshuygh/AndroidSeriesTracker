package com.lens.profandroidbook.seriestracker;

import java.time.LocalDate;

public class Episode {
    private int id;
    private int season;
    private int episode;
    private boolean acquired;
    private LocalDate airDate;
    private int seriesId;

    public Episode(int id, int season, int episode, LocalDate airDate, boolean acquired, int seriesId) {
        this.id = id;
        this.season = season;
        this.episode = episode;
        this.airDate = airDate;
        this.acquired = acquired;
        this.seriesId = seriesId;
    }

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

    public LocalDate getAirDate() {
        return airDate;
    }

    public int getSeriesId() {
        return seriesId;
    }

    @Override
    public String toString() {
        return "("+ seriesId+") S" + season + "E" + episode + " (" + airDate + ") : acquired" + acquired;
    }
}
