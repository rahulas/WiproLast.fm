package com.rahul.wiprolastfmtask.app;

import android.app.Application;
import android.content.Context;

public class LastFMApplicationClass extends Application {

    public static LastFMApplicationClass mContext;

    public static LastFMApplicationClass getApplication() {
        return mContext;
    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
    }

    /*@Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }*/
}
