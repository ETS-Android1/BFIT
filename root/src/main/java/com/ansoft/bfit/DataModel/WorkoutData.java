package com.ansoft.bfit.DataModel;

public class WorkoutData {

    String title;
    int description;

    int gifId;
    int kcalBurn;

    public WorkoutData(String title, int description, int gifId, int kcalBurn) {
        this.title = title;
        this.description = description;
        this.gifId = gifId;
        this.kcalBurn = kcalBurn;
    }

    public int getKcalBurn() {
        return kcalBurn;
    }

    public void setKcalBurn(int kcalBurn) {
        this.kcalBurn = kcalBurn;
    }


    public int getGifId() {
        return gifId;
    }

    public void setGifId(int gifId) {
        this.gifId = gifId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        WorkoutData workoutData= (WorkoutData) obj;
        if(this.title.equalsIgnoreCase(workoutData.getTitle())){
            return true;
        }
        return false;
    }
}
