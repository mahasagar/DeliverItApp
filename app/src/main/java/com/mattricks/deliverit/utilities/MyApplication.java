package com.mattricks.deliverit.utilities;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by sagar on 1/2/16.
 */
public class MyApplication extends Application {
    private static MyApplication sInstance;

    private RequestQueue mRequestQueue;


    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        mInstance=this;

    }

    public static MyApplication getInstance(){

        return mInstance;


    }


    public static Context getInstanceContext(){

        return mInstance.getApplicationContext();


    }

}