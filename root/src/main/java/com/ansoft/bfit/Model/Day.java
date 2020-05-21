package com.ansoft.bfit.Model;

public class Day {

    int dayNum;
    int progress;

    public Day(int dayNum, int progress) {
        this.dayNum = dayNum;
        this.progress = progress;
    }

    public int getDayNum() {
        return dayNum;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
