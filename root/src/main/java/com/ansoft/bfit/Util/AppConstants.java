package com.ansoft.bfit.Util;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

public class AppConstants {

    public static String DISCOVER_APP_URL ="https://api.bfit.remo.fun/d/workout?appcode=butt&category=discover";
    public static String EXPERT_APP_URL ="https://api.bfit.remo.fun/d/expert";
    public static String WHATSNEW_APP_URL ="https://api.bfit.remo.fun/d/workout?appcode=butt&category=whatsnew";

    public static String getDiscover(String id){
        return "https://api.bfit.remo.fun/d/workout/"+id;
    }

    public static void showVolleyError(VolleyError error, Context context){

        String message = "Something is wrong. Please try again!";
        if (error instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof ServerError) {
            message = "The server could not be found. Please try again after some time!!";
        } else if (error instanceof AuthFailureError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
        } else if (error instanceof NoConnectionError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof TimeoutError) {
            message = "Connection TimeOut! Please check your internet connection.";
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
