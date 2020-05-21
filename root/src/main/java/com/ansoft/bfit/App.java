package com.ansoft.bfit;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.orm.SugarApp;

public class App extends SugarApp {


    public static final String TAG = App.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static App mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setShouldCache(false);
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        req.setShouldCache(false);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public  static synchronized App getInstance() {
        if(mInstance!=null) {
            return mInstance;
        }else{
            mInstance=getInstance();
            return mInstance;
        }
    }


}
