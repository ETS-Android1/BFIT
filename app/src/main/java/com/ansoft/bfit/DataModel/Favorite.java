package com.ansoft.bfit.DataModel;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

public class Favorite extends SugarRecord {

    int day;

    int level;


    public Favorite() {
    }


    public Favorite(int day, int level) {
        this.day = day;
        this.level = level;
    }
}
