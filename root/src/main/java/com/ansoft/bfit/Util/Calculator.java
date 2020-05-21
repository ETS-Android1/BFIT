package com.ansoft.bfit.Util;

import android.app.Activity;
import android.util.Log;

import com.ansoft.bfit.DataModel.WorkoutDay;
import com.ansoft.bfit.R;
import com.orm.query.Condition;
import com.orm.query.Select;

public class Calculator {


    public static int calculateDayProgress(int day, int level) {
        int total, completed;
        completed = (int) Select.from(WorkoutDay.class).where(Condition.prop("day").eq(day), Condition.prop("level").eq(level), Condition.prop("completed").eq(1)).count();
        total = (int) Select.from(WorkoutDay.class).where(Condition.prop("day").eq(day), Condition.prop("level").eq(level)).count();
        int percetange = (completed * 100) / total;
        return percetange;


    }


    public static int calculateLevelProgress(int level) {
        int total, completed;
        completed = (int) Select.from(WorkoutDay.class).where(Condition.prop("level").eq(level), Condition.prop("completed").eq(1)).count();
        total = (int) Select.from(WorkoutDay.class).where(Condition.prop("level").eq(level)).count();
        int percetange=1;
        if(total!=0) {
            percetange = (completed * 10000) / (total * 100);
            if (completed > 0 && percetange == 0) {
                percetange = 1;
            }
        }
        return percetange;


    }
}
