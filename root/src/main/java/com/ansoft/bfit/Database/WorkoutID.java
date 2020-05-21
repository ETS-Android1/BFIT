package com.ansoft.bfit.Database;

import com.ansoft.bfit.DataModel.WorkoutData;
import com.ansoft.bfit.R;

public class WorkoutID {

    public static WorkoutData getWorkoutFromID(int id) {

        switch(id){
            case 0:
                return new WorkoutData("ADDUCTOR STRETCH IN STANDING", R.string.w_01, R.drawable.w_01, 10);


            case 1:
                return new WorkoutData("BUTT BRIDGE", R.string.w_02, R.drawable.w_02, 10);


            case 2:
                return new WorkoutData("CURTSY LUNGES", R.string.w_03, R.drawable.w_03, 10);


            case 3:
                return new WorkoutData("DONKEY KICKS LEFT", R.string.w_04, R.drawable.w_04, 10);


            case 4:
                return new WorkoutData("DONKEY KICKS RIGHT", R.string.w_05, R.drawable.w_05, 10);


            case 5:
                return new WorkoutData("FIRE HYDRANT LEFT", R.string.w_06, R.drawable.w_06, 10);


            case 6:
                return new WorkoutData("FIRE HYDRANT RIGHT", R.string.w_07, R.drawable.w_07, 10);


            case 7:
                return new WorkoutData("FLUTTER KICKS", R.string.w_08, R.drawable.w_08, 10);


            case 8:
                return new WorkoutData("HEEL TOUCH", R.string.w_09, R.drawable.w_09, 10);


            case 9:
                return new WorkoutData("LEFT LUNGE KNEE HOPS", R.string.w_10, R.drawable.w_10, 10);


            case 10:
                return new WorkoutData("LUNGES", R.string.w_11, R.drawable.w_11, 10);


            case 11:
                return new WorkoutData("MOUNTAIN CLIMBER", R.string.w_12, R.drawable.w_12, 10);


            case 12:
                return new WorkoutData("PLIE SQUATS", R.string.w_13, R.drawable.w_13, 10);


            case 13:
                return new WorkoutData("RIGHT LUNGE KNEE HOPS", R.string.w_14, R.drawable.w_14, 10);


            case 14:
                return new WorkoutData("SIDE LUNGES", R.string.w_15, R.drawable.w_15, 10);


            case 15:
                return new WorkoutData("SIDE-LYING LEG LIFT LEFT", R.string.w_16, R.drawable.w_16, 10);


            case 16:
                return new WorkoutData("SIDE-LYING LEG LIFT RIGHT", R.string.w_17, R.drawable.w_17, 10);


            case 17:
                return new WorkoutData("SPLIT SQUAT LEFT", R.string.w_18, R.drawable.w_18, 10);


            case 18:
                return new WorkoutData("SPLIT SQUAT RIGHT", R.string.w_19, R.drawable.w_19, 10);


            case 19:
                return new WorkoutData("SQUATS", R.string.w_20, R.drawable.w_20, 10);


            default:
                return new WorkoutData("SQUATS", R.string.w_20, R.drawable.w_20, 10);

        }
    }
}
