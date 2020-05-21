package com.ansoft.bfit.DataModel;

import com.orm.SugarRecord;

public class WorkoutDay extends SugarRecord {


    int day;
    int workoutid;
    int reptype;
    int rep;
    boolean completed;
    int timetaken;
    int level;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getWorkoutid() {
        return workoutid;
    }

    public void setWorkoutid(int workoutid) {
        this.workoutid = workoutid;
    }

    public int getReptype() {
        return reptype;
    }

    public void setReptype(int reptype) {
        this.reptype = reptype;
    }

    public int getRep() {
        return rep;
    }

    public void setRep(int rep) {
        this.rep = rep;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getTimetaken() {
        return timetaken;
    }

    public void setTimetaken(int timetaken) {
        this.timetaken = timetaken;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public WorkoutDay() {
    }

    public WorkoutDay(int day, int workoutid, int reptype, int rep, boolean completed, int timetaken, int level) {
        this.day = day;
        this.workoutid = workoutid;
        this.reptype = reptype;
        this.rep = rep;
        this.completed = completed;
        this.timetaken = timetaken;
        this.level = level;
    }
}
