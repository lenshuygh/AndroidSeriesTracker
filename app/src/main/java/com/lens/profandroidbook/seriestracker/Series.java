package com.lens.profandroidbook.seriestracker;

import androidx.annotation.Nullable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Series {
    private int id;
    private String title;
    private LocalDate startDate;
    private DayOfWeek releaseDayOfWeek;

    public Series(int id, String title, LocalDate startDate, DayOfWeek releaseDayOfWeek) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.releaseDayOfWeek = releaseDayOfWeek;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public DayOfWeek getReleaseDayOfWeek() {
        return releaseDayOfWeek;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return title + " (" + dateTimeFormatter.format(startDate) + ", airs " + releaseDayOfWeek + ")";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Series) {
            return (((Series) obj).getTitle().equals(title)) && (((Series) obj).startDate == startDate);
        } else {
            return false;
        }
    }
}
