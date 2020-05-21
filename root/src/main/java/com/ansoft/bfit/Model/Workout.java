package com.ansoft.bfit.Model;

public class Workout {

    String title;
    String rep;
    int drawable;

    public Workout(String title, String rep, int drawable) {
        this.title = title;
        this.rep = rep;
        this.drawable = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
