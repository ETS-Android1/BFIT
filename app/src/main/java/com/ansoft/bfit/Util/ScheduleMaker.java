package com.ansoft.bfit.Util;

import android.content.Context;
import android.util.Log;

import com.ansoft.bfit.DataModel.WorkoutDay;
import java.io.IOException;
import java.io.InputStream;

public class ScheduleMaker {


    public static void makeSchedule(Context context, double difficultyIndex1, int difficulty1, double difficultyIndex2, int difficulty2, double difficultyIndex3, int difficulty3){
        WorkoutDay.deleteAll(WorkoutDay.class);
        String content= loadStringFromAssets(context, "schedule.txt");

        try {
            String lines[] = content.split("\\r?\\n");
            for (String line : lines) {
                String ss[]=line.split("::");
                if(ss.length>1) {
                    try {
                        int day = Integer.parseInt(ss[0]);
                        int id = Integer.parseInt(ss[1]);
                        int rep = Integer.parseInt(ss[3]);
                        int reptype = 0;

                        if (id == 7) {
                            reptype = 1;
                        }

                        WorkoutDay workoutDay1 = new WorkoutDay(day, id, reptype, (int) Math.round(rep * difficultyIndex1), false, 0, difficulty1);
                        workoutDay1.save();


                        WorkoutDay workoutDay2 = new WorkoutDay(day, id, reptype, (int) Math.round(rep * difficultyIndex2), false, 0, difficulty2);
                        workoutDay2.save();


                        WorkoutDay workoutDay3 = new WorkoutDay(day, id, reptype, (int) Math.round(rep * difficultyIndex3), false, 0, difficulty3);
                        workoutDay3.save();

                    } catch (Exception e) {

                        Log.e("Error", e.getMessage());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String loadStringFromAssets(Context context, String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
