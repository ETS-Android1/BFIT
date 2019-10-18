package com.ansoft.bfit.Database;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.ansoft.bfit.R;

public class SPDataManager {



    public static void setProgress(String level, int day, Activity activity){
        SharedPreferences sharedpreferences = activity.getSharedPreferences(activity.getString(R.string.app_name)+"Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("pref_progress"+level, day);
        editor.commit();
    }

    public static void setGender(int gender, Activity activity){
        SharedPreferences sharedpreferences = activity.getSharedPreferences(activity.getString(R.string.app_name)+"Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(activity.getString(R.string.pref_gender), gender);
        editor.commit();
    }


    public static void setFrequency(int frequency, Activity activity){
        SharedPreferences sharedpreferences = activity.getSharedPreferences(activity.getString(R.string.app_name)+"Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(activity.getString(R.string.pref_frequency), frequency);
        editor.commit();
    }


    public static void setActivity(int sactivity, Activity activity){
        SharedPreferences sharedpreferences = activity.getSharedPreferences(activity.getString(R.string.app_name)+"Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(activity.getString(R.string.pref_activity), sactivity);
        editor.commit();
    }


    public static void setPushup(int pushup, Activity activity){
        SharedPreferences sharedpreferences = activity.getSharedPreferences(activity.getString(R.string.app_name)+"Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(activity.getString(R.string.pref_pushup), pushup);
        editor.commit();
    }



    public static void setReminderTime(String remiderTime, Activity activity){
        SharedPreferences sharedpreferences = activity.getSharedPreferences(activity.getString(R.string.app_name)+"Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(activity.getString(R.string.pref_reminder), remiderTime);
        editor.commit();
    }



    public static void setDataTaken(boolean taken, Activity activity){
        SharedPreferences sharedpreferences = activity.getSharedPreferences(activity.getString(R.string.app_name)+"Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(activity.getString(R.string.pref_data_taken), taken);
        editor.commit();
    }




    public static int getProgress(String level, Activity activity){
        SharedPreferences sharedpreferences = activity.getSharedPreferences(activity.getString(R.string.app_name)+"Preferences", Context.MODE_PRIVATE);
        return sharedpreferences.getInt("pref_progress"+level, 0);
    }


    public static int getGender(Activity activity){
        SharedPreferences sharedpreferences = activity.getSharedPreferences(activity.getString(R.string.app_name)+"Preferences", Context.MODE_PRIVATE);
        return sharedpreferences.getInt(activity.getString(R.string.pref_gender), 0);
    }


    public static int getFrequency(Activity activity){
        SharedPreferences sharedpreferences = activity.getSharedPreferences(activity.getString(R.string.app_name)+"Preferences", Context.MODE_PRIVATE);
        return sharedpreferences.getInt(activity.getString(R.string.pref_frequency), 0);
    }


    public static int getActivity(Activity activity){
        SharedPreferences sharedpreferences = activity.getSharedPreferences(activity.getString(R.string.app_name)+"Preferences", Context.MODE_PRIVATE);
        return sharedpreferences.getInt(activity.getString(R.string.pref_activity), 0);
    }


    public static int getPushup(Activity activity){
        SharedPreferences sharedpreferences = activity.getSharedPreferences(activity.getString(R.string.app_name)+"Preferences", Context.MODE_PRIVATE);
        return sharedpreferences.getInt(activity.getString(R.string.pref_pushup), 0);
    }



    public static String getReminderTime(Activity activity){
        SharedPreferences sharedpreferences = activity.getSharedPreferences(activity.getString(R.string.app_name)+"Preferences", Context.MODE_PRIVATE);
        return sharedpreferences.getString(activity.getString(R.string.pref_reminder), activity.getString(R.string.default_reminder_time));
    }



    public static boolean isDataTaken(Activity activity){
        SharedPreferences sharedpreferences = activity.getSharedPreferences(activity.getString(R.string.app_name)+"Preferences", Context.MODE_PRIVATE);
        return sharedpreferences.getBoolean(activity.getString(R.string.pref_data_taken), false);
    }


}
