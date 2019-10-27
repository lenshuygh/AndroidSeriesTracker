package com.lens.profandroidbook.seriestracker;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Series {
    private int id;
    private String title;
    private LocalDate startDate;

    public Series(int id, String title, LocalDate startDate) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
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

    @Override
    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return title + " (" + dateTimeFormatter.format(startDate) + ")";
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof Series){
            return (((Series)obj).getTitle().equals(title)) && (((Series)obj).startDate == startDate);
        }else {
            return false;
        }
    }
}
