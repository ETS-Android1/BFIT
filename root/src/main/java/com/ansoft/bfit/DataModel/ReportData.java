package com.ansoft.bfit.DataModel;

import com.orm.SugarRecord;

public class ReportData extends SugarRecord {


    int year, month, day;
    int totalCompleted, totalCalorieBurned, totalSeconds;

    boolean completed;

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public ReportData(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        totalCompleted = 0;
        totalCalorieBurned = 0;
        totalSeconds = 0;
    }

    public ReportData() {
    }

    public void addTotalCompleted(int completed) {
        totalCompleted += completed;
    }

    public void addTotalCalorieBurned(int calore) {
        totalCalorieBurned += calore;
    }


    public void addTotalSeconds(int seconds) {
        totalSeconds += seconds;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getTotalCompleted() {
        return totalCompleted;
    }

    public int getTotalCalorieBurned() {
        return totalCalorieBurned;
    }

    public int getTotalSeconds() {
        return totalSeconds;
    }
}
