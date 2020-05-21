package com.ansoft.bfit.Model;

import java.util.ArrayList;

public class Discover {

    String id;
    String name;
    String description;
    String thumbnailLink;
    int difficulty;
    ArrayList<DisWorkout> workoutArrayList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Discover() {
        workoutArrayList=new ArrayList<>();
    }

    public void addWorkout(int id, int rep){
        workoutArrayList.add(new DisWorkout(id, rep));
    }

    public ArrayList<DisWorkout> getWorkoutArrayList() {
        return workoutArrayList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailLink() {
        return thumbnailLink;
    }

    public void setThumbnailLink(String thumbnailLink) {
        this.thumbnailLink = thumbnailLink;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public class DisWorkout{
        int id;
        int rep;

        public DisWorkout(int id, int rep) {
            this.id = id;
            this.rep = rep;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRep() {
            return rep;
        }

        public void setRep(int rep) {
            this.rep = rep;
        }
    }
}
